# API 测试说明

## 项目结构

```
demo/
├── entity/          # 实体层
│   └── User.java
├── dao/             # 数据访问层接口
│   └── UserDao.java
│   └── impl/        # DAO实现类
│       └── UserDaoImpl.java
├── mapper/          # MyBatis Mapper层
│   └── UserMapper.java
├── service/         # 服务层接口
│   └── UserService.java
│   └── impl/        # Service实现类
│       └── UserServiceImpl.java
└── controller/      # 控制器层
    └── UserController.java
```

## 启动项目

```bash
cd demo
.\mvnw.cmd spring-boot:run
```

项目启动后，访问地址：`http://localhost:8080`

## API 接口列表

### 1. 查询所有用户
**GET** `/api/users`

**响应示例：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "name": "张三",
      "email": "zhangsan@example.com",
      "age": 25
    },
    {
      "id": 2,
      "name": "李四",
      "email": "lisi@example.com",
      "age": 30
    }
  ]
}
```

### 2. 根据ID查询用户
**GET** `/api/users/{id}`

**示例：** `GET /api/users/1`

**响应示例：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "id": 1,
    "name": "张三",
    "email": "zhangsan@example.com",
    "age": 25
  }
}
```

### 3. 根据姓名搜索用户
**GET** `/api/users/search?name=张三`

**响应示例：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "name": "张三",
      "email": "zhangsan@example.com",
      "age": 25
    }
  ]
}
```

### 4. 创建用户
**POST** `/api/users`

**请求头：** `Content-Type: application/json`

**请求体：**
```json
{
  "name": "新用户",
  "email": "newuser@example.com",
  "age": 22
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 5,
    "name": "新用户",
    "email": "newuser@example.com",
    "age": 22
  }
}
```

### 5. 更新用户
**PUT** `/api/users/{id}`

**示例：** `PUT /api/users/1`

**请求头：** `Content-Type: application/json`

**请求体：**
```json
{
  "name": "张三（已更新）",
  "email": "zhangsan_updated@example.com",
  "age": 26
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": 1,
    "name": "张三（已更新）",
    "email": "zhangsan_updated@example.com",
    "age": 26
  }
}
```

### 6. 删除用户
**DELETE** `/api/users/{id}`

**示例：** `DELETE /api/users/1`

**响应示例：**
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

## 使用 curl 测试

### Windows PowerShell 测试命令

```powershell
# 1. 查询所有用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Get

# 2. 根据ID查询用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Get

# 3. 根据姓名搜索
Invoke-RestMethod -Uri "http://localhost:8080/api/users/search?name=张三" -Method Get

# 4. 创建用户
$body = @{
    name = "新用户"
    email = "newuser@example.com"
    age = 22
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -Body $body -ContentType "application/json"

# 5. 更新用户
$body = @{
    name = "张三（已更新）"
    email = "zhangsan_updated@example.com"
    age = 26
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Put -Body $body -ContentType "application/json"

# 6. 删除用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Delete
```

## 使用 Postman 或浏览器测试

1. **GET 请求**：可以直接在浏览器中访问
   - `http://localhost:8080/api/users`
   - `http://localhost:8080/api/users/1`
   - `http://localhost:8080/api/users/search?name=张三`

2. **POST/PUT/DELETE 请求**：需要使用 Postman 或类似工具

## H2 数据库控制台

访问 `http://localhost:8080/h2-console` 可以查看数据库内容

- JDBC URL: `jdbc:h2:mem:testdb`
- 用户名: `sa`
- 密码: (留空)

## 注意事项

- 项目使用 H2 内存数据库，重启后数据会丢失
- 项目已预置4条测试数据（张三、李四、王五、赵六）
- 所有接口返回统一的 JSON 格式：`{code, message, data}`
