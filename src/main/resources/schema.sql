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
