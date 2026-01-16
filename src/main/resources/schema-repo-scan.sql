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
    FOREIGN KEY (task_id) REFERENCES t_repo_scan_task(task_id) ON DELETE CASCADE,
    INDEX idx_task_id (task_id),
    INDEX idx_rule_name (rule_name),
    INDEX idx_is_issue (is_issue)
);

-- 插入测试数据（可选）
-- INSERT INTO t_repo_scan_task (task_id, task_name, repo_url, branch, path_list, creator, task_status, assistant_version) 
-- VALUES ('test-task-001', '测试任务1', 'https://github.com/example/repo.git', 'main', 'src/main/java,src/test/java', 'admin', 'COMPLETED', 'v1.0.0');
