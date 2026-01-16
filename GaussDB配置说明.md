# GaussDB数据库配置说明

## 关于GaussDB

华为GaussDB是华为云推出的企业级分布式数据库，主要有以下几个版本：

1. **GaussDB(for MySQL)** - 兼容MySQL协议，可以使用MySQL驱动或GaussDB专用驱动
2. **GaussDB(for PostgreSQL)** - 兼容PostgreSQL协议
3. **GaussDB(openGauss)** - 基于openGauss内核

## 快速开始

### 方案一：使用GaussDB专用驱动（推荐）

#### 1. 添加依赖

已在 `pom.xml` 中添加GaussDB驱动依赖：

```xml
<dependency>
    <groupId>com.huawei.gaussdb</groupId>
    <artifactId>gaussdb-jdbc</artifactId>
    <version>8.0.0</version>
</dependency>
```

**注意**：如果Maven中央仓库找不到该依赖，需要：
- 从华为云官网下载GaussDB JDBC驱动JAR包
- 安装到本地Maven仓库，或
- 将JAR包放到项目的 `lib` 目录并手动引入

#### 2. 配置数据库连接

编辑 `demo/src/main/resources/application.properties`：

```properties
# GaussDB数据库配置
spring.datasource.url=jdbc:gaussdb://your_host:port/database_name?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.driver-class-name=com.huawei.gaussdb.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### 3. 创建数据库

在GaussDB中执行：

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS demo_db 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE demo_db;
```

### 方案二：使用MySQL驱动（适用于GaussDB(for MySQL)）

如果使用GaussDB(for MySQL)，由于它兼容MySQL协议，可以直接使用MySQL驱动：

#### 1. 修改pom.xml

取消注释MySQL驱动，注释掉GaussDB驱动：

```xml
<!-- 使用MySQL驱动连接GaussDB(for MySQL) -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### 2. 配置连接

```properties
# 使用MySQL协议连接GaussDB(for MySQL)
spring.datasource.url=jdbc:mysql://your_host:port/database_name?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 详细配置步骤

### 步骤1：获取GaussDB连接信息

从华为云控制台或GaussDB管理员处获取：
- **主机地址** (host)
- **端口** (port，默认3306 for MySQL协议，5432 for PostgreSQL协议)
- **数据库名** (database_name)
- **用户名** (username)
- **密码** (password)

### 步骤2：修改application.properties

将 `application-gaussdb.properties` 的内容复制到 `application.properties`，并修改连接信息：

```properties
spring.datasource.url=jdbc:gaussdb://your_host:3306/demo_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 步骤3：更新SQL脚本

当前的 `schema.sql` 已经兼容GaussDB，可以直接使用。

### 步骤4：启动应用

```bash
cd demo
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

## 不同GaussDB版本的连接配置

### GaussDB(for MySQL)

```properties
# 使用GaussDB驱动
spring.datasource.url=jdbc:gaussdb://host:3306/dbname
spring.datasource.driver-class-name=com.huawei.gaussdb.jdbc.Driver

# 或使用MySQL驱动（兼容模式）
spring.datasource.url=jdbc:mysql://host:3306/dbname
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### GaussDB(for PostgreSQL)

```properties
spring.datasource.url=jdbc:postgresql://host:5432/dbname
spring.datasource.driver-class-name=org.postgresql.Driver
```

### GaussDB(openGauss)

```properties
spring.datasource.url=jdbc:opengauss://host:5432/dbname
spring.datasource.driver-class-name=org.opengauss.Driver
```

## 常见问题

### 1. 找不到GaussDB驱动依赖

**问题**：Maven无法下载 `com.huawei.gaussdb:gaussdb-jdbc`

**解决方案**：
- **方案A**：使用MySQL驱动（如果使用GaussDB(for MySQL)）
- **方案B**：手动安装驱动
  1. 从华为云官网下载GaussDB JDBC驱动JAR包
  2. 安装到本地Maven仓库：
     ```bash
     mvn install:install-file -Dfile=gaussdb-jdbc-8.0.0.jar -DgroupId=com.huawei.gaussdb -DartifactId=gaussdb-jdbc -Dversion=8.0.0 -Dpackaging=jar
     ```
  3. 或将JAR包放到 `demo/lib` 目录，在pom.xml中引用：
     ```xml
     <dependency>
         <groupId>com.huawei.gaussdb</groupId>
         <artifactId>gaussdb-jdbc</artifactId>
         <version>8.0.0</version>
         <scope>system</scope>
         <systemPath>${project.basedir}/lib/gaussdb-jdbc-8.0.0.jar</systemPath>
     </dependency>
     ```

### 2. 连接超时

**原因**：网络问题或防火墙阻止

**解决**：
- 检查网络连接
- 确认防火墙规则
- 增加连接超时时间：
  ```properties
  spring.datasource.hikari.connection-timeout=60000
  ```

### 3. 认证失败

**原因**：用户名或密码错误

**解决**：检查GaussDB连接信息是否正确

### 4. 字符编码问题

**解决**：确保连接URL包含编码参数：
```properties
spring.datasource.url=jdbc:gaussdb://host:port/db?useUnicode=true&characterEncoding=utf8
```

## 生产环境建议

1. **使用连接池**：已默认使用HikariCP，配置合理
2. **SSL连接**：生产环境建议启用SSL
3. **独立用户**：使用专用数据库用户，不要使用管理员账户
4. **密码安全**：使用强密码，考虑使用配置中心管理密码
5. **监控告警**：配置数据库监控和告警
6. **备份策略**：制定数据备份和恢复策略

## 测试连接

启动应用后，访问接口测试：

```bash
# 查询所有用户
curl http://localhost:8080/api/users

# 如果返回数据，说明连接成功
```

## 驱动下载地址

- **华为云GaussDB文档**: https://support.huaweicloud.com/
- **GaussDB JDBC驱动**: 从华为云控制台或文档中心下载

## 注意事项

1. **版本兼容性**：确保JDBC驱动版本与GaussDB服务器版本兼容
2. **协议选择**：GaussDB(for MySQL)可以使用MySQL驱动，但建议使用GaussDB专用驱动以获得最佳性能
3. **时区设置**：确保时区配置正确，避免时间数据问题
4. **SSL配置**：根据环境要求配置SSL连接

---

**提示**：如果遇到驱动依赖问题，建议先使用MySQL驱动方案（适用于GaussDB(for MySQL)），这是最简单快速的方案。
