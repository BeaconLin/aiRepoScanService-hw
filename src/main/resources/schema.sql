-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT
);

-- 插入测试数据
INSERT INTO users (name, email, age) VALUES ('张三', 'zhangsan@example.com', 25);
INSERT INTO users (name, email, age) VALUES ('李四', 'lisi@example.com', 30);
INSERT INTO users (name, email, age) VALUES ('王五', 'wangwu@example.com', 28);
INSERT INTO users (name, email, age) VALUES ('赵六', 'zhaoliu@example.com', 35);

-- 代码仓扫描任务表
CREATE TABLE IF NOT EXISTS t_repo_scan_task (
    task_id VARCHAR(36) PRIMARY KEY,
    task_name VARCHAR(100) NOT NULL,
    repo_url VARCHAR(500) NOT NULL,
    branch VARCHAR(100) NOT NULL,
    path_list TEXT,
    s3_path TEXT,
    creator VARCHAR(50) NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    task_status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    assistant_version VARCHAR(50)
);

-- 扫描结果表
CREATE TABLE IF NOT EXISTS t_scan_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id VARCHAR(36) NOT NULL,
    file_name VARCHAR(500) NOT NULL,
    rule_name VARCHAR(200) NOT NULL,
    line INT NOT NULL,
    code_block TEXT,
    context TEXT,
    warn TEXT,
    is_issue BOOLEAN,
    marker VARCHAR(50),
    mark_time TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES t_repo_scan_task(task_id) ON DELETE CASCADE
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_task_id ON t_scan_result(task_id);
CREATE INDEX IF NOT EXISTS idx_rule_name ON t_scan_result(rule_name);
CREATE INDEX IF NOT EXISTS idx_is_issue ON t_scan_result(is_issue);

-- ============================================
-- 插入测试数据 - 代码仓扫描任务
-- ============================================

-- 任务1：已完成的任务
INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) 
VALUES ('task-001', 'Spring Boot项目代码扫描', 'https://github.com/example/spring-boot-demo.git', 'main', 'src/main/java,src/test/java', 's3://scan-results/task-001/', 'zhangsan', '2024-01-15 10:00:00', 'COMPLETED', 'v1.2.0');

-- 任务2：扫描中的任务
INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) 
VALUES ('task-002', '微服务架构代码审查', 'https://github.com/example/microservice-app.git', 'develop', 'src/main/java/com/example/service', 's3://scan-results/task-002/', 'lisi', '2024-01-16 14:30:00', 'SCANNING', 'v1.3.0');

-- 任务3：已创建但未开始的任务
INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) 
VALUES ('task-003', '前端代码质量检查', 'https://github.com/example/frontend-app.git', 'master', 'src/components,src/utils', 's3://scan-results/task-003/', 'wangwu', '2024-01-17 09:15:00', 'CREATED', 'v1.1.0');

-- 任务4：已完成的任务（zhangsan的另一个任务）
INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) 
VALUES ('task-004', 'API接口代码扫描', 'https://github.com/example/api-service.git', 'main', 'src/main/java/com/api', 's3://scan-results/task-004/', 'zhangsan', '2024-01-18 16:45:00', 'COMPLETED', 'v1.2.0');

-- 任务5：失败的任务
INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, s3_path, creator, create_time, task_status, assistant_version) 
VALUES ('task-005', '数据库访问层扫描', 'https://github.com/example/data-access.git', 'feature/db-optimize', 'src/main/java/com/dao', 's3://scan-results/task-005/', 'zhaoliu', '2024-01-19 11:20:00', 'FAILED', 'v1.0.0');

-- ============================================
-- 插入测试数据 - 扫描结果
-- ============================================

-- 任务1的扫描结果（已完成，包含已标注和未标注的数据）
INSERT INTO t_scan_result (task_id, file_name, rule_name, line, code_block, context, warn, is_issue, marker, mark_time) VALUES
('task-001', 'src/main/java/com/example/controller/UserController.java', '空指针风险', 25, 'User user = userService.getUserById(id);\nif (user == null) {\n    return null;', 'public ResponseEntity<User> getUser(@PathVariable Long id) {\n    User user = userService.getUserById(id);\n    if (user == null) {\n        return null;\n    }\n    return ResponseEntity.ok(user);\n}', '在返回null之前应该先检查user是否为null，避免潜在的NullPointerException', TRUE, 'zhangsan', '2024-01-15 11:00:00'),
('task-001', 'src/main/java/com/example/service/UserService.java', '资源未关闭', 42, 'FileInputStream fis = new FileInputStream(file);\n// 使用文件流', 'public void processFile(String filePath) throws IOException {\n    FileInputStream fis = new FileInputStream(filePath);\n    // 使用文件流\n    // 缺少finally块或try-with-resources', '文件流未正确关闭，可能导致资源泄漏，建议使用try-with-resources语句', TRUE, 'zhangsan', '2024-01-15 11:05:00'),
('task-001', 'src/main/java/com/example/util/StringUtils.java', '硬编码字符串', 18, 'String status = "active";', 'public String getStatus() {\n    String status = "active";\n    return status;\n}', '硬编码字符串应该提取为常量，便于维护和国际化', FALSE, 'lisi', '2024-01-15 11:10:00'),
('task-001', 'src/main/java/com/example/dao/UserDao.java', 'SQL注入风险', 56, 'String sql = "SELECT * FROM users WHERE name = \'" + name + "\'";', 'public List<User> findByName(String name) {\n    String sql = "SELECT * FROM users WHERE name = \'" + name + "\'";\n    return jdbcTemplate.query(sql, rowMapper);\n}', '直接拼接SQL字符串存在SQL注入风险，应该使用参数化查询', NULL, NULL, NULL),
('task-001', 'src/main/java/com/example/model/User.java', '未使用的导入', 3, 'import java.util.ArrayList;', 'package com.example.model;\nimport java.util.ArrayList;\nimport java.util.List;\n\npublic class User {\n    // 未使用ArrayList', '导入了ArrayList但未使用，应该删除未使用的导入', FALSE, NULL, NULL),
('task-001', 'src/test/java/com/example/controller/UserControllerTest.java', '测试覆盖率不足', 12, '@Test\npublic void testGetUser() {\n    // 缺少异常情况测试', 'public class UserControllerTest {\n    @Test\n    public void testGetUser() {\n        // 缺少异常情况测试\n    }\n}', '测试用例缺少对异常情况的覆盖，建议添加边界条件和异常场景测试', NULL, NULL, NULL);

-- 任务2的扫描结果（扫描中，部分结果）
INSERT INTO t_scan_result (task_id, file_name, rule_name, line, code_block, context, warn, is_issue, marker, mark_time) VALUES
('task-002', 'src/main/java/com/example/service/OrderService.java', '循环依赖', 35, '@Autowired\nprivate PaymentService paymentService;', 'public class OrderService {\n    @Autowired\n    private PaymentService paymentService;\n    // PaymentService也注入了OrderService', '检测到循环依赖，可能导致Spring容器初始化失败', NULL, NULL, NULL),
('task-002', 'src/main/java/com/example/config/SecurityConfig.java', '密码硬编码', 28, 'String password = "admin123";', 'public void configure(AuthenticationManagerBuilder auth) throws Exception {\n    String password = "admin123";\n    // 密码硬编码', '密码不应该硬编码在代码中，应该使用配置文件或环境变量', NULL, NULL, NULL);

-- 任务4的扫描结果（已完成，全部已标注）
INSERT INTO t_scan_result (task_id, file_name, rule_name, line, code_block, context, warn, is_issue, marker, mark_time) VALUES
('task-004', 'src/main/java/com/api/controller/ProductController.java', '缺少参数验证', 15, 'public Product getProduct(@PathVariable Long id) {', 'public Product getProduct(@PathVariable Long id) {\n    return productService.findById(id);\n}', '缺少对id参数的有效性验证，应该添加@Valid或手动验证', TRUE, 'zhangsan', '2024-01-18 17:00:00'),
('task-004', 'src/main/java/com/api/service/ProductService.java', '异常处理不当', 48, 'try {\n    return repository.findById(id);\n} catch (Exception e) {\n    return null;\n}', 'public Product findById(Long id) {\n    try {\n        return repository.findById(id);\n    } catch (Exception e) {\n        return null;\n    }\n}', '捕获所有异常并返回null会隐藏错误信息，应该记录日志或抛出更具体的异常', TRUE, 'zhangsan', '2024-01-18 17:05:00'),
('task-004', 'src/main/java/com/api/util/DateUtils.java', '日期格式化线程不安全', 22, 'SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");', 'public class DateUtils {\n    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");\n    // SimpleDateFormat不是线程安全的', 'SimpleDateFormat不是线程安全的，多线程环境下可能出现问题，建议使用DateTimeFormatter', FALSE, 'lisi', '2024-01-18 17:10:00');