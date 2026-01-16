# GaussDB 快速配置指南

## 两种配置方案

### 方案一：使用MySQL驱动（最简单，推荐）

适用于 **GaussDB(for MySQL)**，因为它兼容MySQL协议。

#### 步骤1：修改pom.xml

在 `pom.xml` 中，注释掉GaussDB驱动，使用MySQL驱动：

```xml
<!-- 使用MySQL驱动连接GaussDB(for MySQL) -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### 步骤2：修改application.properties

编辑 `demo/src/main/resources/application.properties`：

```properties
# GaussDB(for MySQL) 配置 - 使用MySQL协议
spring.datasource.url=jdbc:mysql://your_host:3306/demo_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password

# 注释掉H2配置
# spring.h2.console.enabled=true
# spring.h2.console.path=/h2-console
```

#### 步骤3：创建数据库

在GaussDB中执行：

```sql
CREATE DATABASE IF NOT EXISTS demo_db 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;
```

#### 步骤4：启动应用

```bash
cd demo
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

---

### 方案二：使用GaussDB专用驱动

如果需要使用GaussDB专用驱动，需要先获取驱动JAR包。

#### 步骤1：获取GaussDB驱动

从华为云控制台或文档中心下载GaussDB JDBC驱动JAR包。

#### 步骤2：安装驱动到本地Maven仓库

```bash
mvn install:install-file \
  -Dfile=gaussdb-jdbc-8.0.0.jar \
  -DgroupId=com.huawei.gaussdb \
  -DartifactId=gaussdb-jdbc \
  -Dversion=8.0.0 \
  -Dpackaging=jar
```

#### 步骤3：配置连接

```properties
spring.datasource.url=jdbc:gaussdb://your_host:3306/demo_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.driver-class-name=com.huawei.gaussdb.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 连接信息示例

根据您的GaussDB实例，修改以下信息：

```properties
# 示例：华为云GaussDB实例
spring.datasource.url=jdbc:mysql://gaussdb-xxx.rds.region.rds.huaweicloud.com:3306/demo_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=YourPassword123
```

---

## 验证连接

启动应用后，访问：

```
http://localhost:8080/api/users
```

如果返回用户列表，说明连接成功！

---

## 需要帮助？

- 查看详细配置：`GaussDB配置说明.md`
- 检查日志：查看控制台输出的错误信息
- 测试连接：使用数据库客户端工具先测试连接
