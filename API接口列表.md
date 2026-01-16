# 本地接口调用文档

## 基础信息

- **服务器地址**: `http://localhost:8080`
- **基础路径**: `/api/users`
- **数据格式**: JSON
- **字符编码**: UTF-8

---

## 接口列表

### 1. 查询所有用户

**接口地址**: `GET /api/users`

**请求方式**: GET

**请求参数**: 无

**响应示例**:
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
    },
    {
      "id": 3,
      "name": "王五",
      "email": "wangwu@example.com",
      "age": 28
    },
    {
      "id": 4,
      "name": "赵六",
      "email": "zhaoliu@example.com",
      "age": 35
    }
  ]
}
```

**调用方式**:
- 浏览器: `http://localhost:8080/api/users`
- curl: `curl http://localhost:8080/api/users`
- PowerShell: `Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Get`

---

### 2. 根据ID查询用户

**接口地址**: `GET /api/users/{id}`

**请求方式**: GET

**路径参数**:
- `id` (Long, 必填) - 用户ID

**响应示例** (成功):
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

**响应示例** (失败):
```json
{
  "code": 404,
  "message": "用户不存在",
  "data": null
}
```

**调用方式**:
- 浏览器: `http://localhost:8080/api/users/1`
- curl: `curl http://localhost:8080/api/users/1`
- PowerShell: `Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Get`

---

### 3. 根据姓名搜索用户

**接口地址**: `GET /api/users/search`

**请求方式**: GET

**查询参数**:
- `name` (String, 必填) - 用户姓名（支持模糊搜索）

**响应示例**:
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

**调用方式**:
- 浏览器: `http://localhost:8080/api/users/search?name=张三`
- curl: `curl "http://localhost:8080/api/users/search?name=张三"`
- PowerShell: `Invoke-RestMethod -Uri "http://localhost:8080/api/users/search?name=张三" -Method Get`

---

### 4. 创建用户

**接口地址**: `POST /api/users`

**请求方式**: POST

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "name": "新用户",
  "email": "newuser@example.com",
  "age": 22
}
```

**请求参数说明**:
- `name` (String, 必填) - 用户姓名
- `email` (String, 必填) - 用户邮箱
- `age` (Integer, 可选) - 用户年龄

**响应示例** (成功):
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

**响应示例** (失败):
```json
{
  "code": 500,
  "message": "创建失败",
  "data": null
}
```

**调用方式** (PowerShell):
```powershell
$body = @{
    name = "新用户"
    email = "newuser@example.com"
    age = 22
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -Body $body -ContentType "application/json"
```

**调用方式** (curl):
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"新用户","email":"newuser@example.com","age":22}'
```

---

### 5. 更新用户

**接口地址**: `PUT /api/users/{id}`

**请求方式**: PUT

**路径参数**:
- `id` (Long, 必填) - 用户ID

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "name": "张三（已更新）",
  "email": "zhangsan_updated@example.com",
  "age": 26
}
```

**响应示例** (成功):
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

**响应示例** (失败):
```json
{
  "code": 404,
  "message": "用户不存在或更新失败",
  "data": null
}
```

**调用方式** (PowerShell):
```powershell
$body = @{
    name = "张三（已更新）"
    email = "zhangsan_updated@example.com"
    age = 26
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Put -Body $body -ContentType "application/json"
```

**调用方式** (curl):
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"张三（已更新）","email":"zhangsan_updated@example.com","age":26}'
```

---

### 6. 删除用户

**接口地址**: `DELETE /api/users/{id}`

**请求方式**: DELETE

**路径参数**:
- `id` (Long, 必填) - 用户ID

**响应示例** (成功):
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

**响应示例** (失败):
```json
{
  "code": 404,
  "message": "用户不存在或删除失败",
  "data": null
}
```

**调用方式** (PowerShell):
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Delete
```

**调用方式** (curl):
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

---

## 其他可用端点

### H2 数据库控制台

**地址**: `http://localhost:8080/h2-console`

**用途**: 查看和管理H2内存数据库

**连接信息**:
- JDBC URL: `jdbc:h2:mem:testdb`
- 用户名: `sa`
- 密码: (留空)

---

## 统一响应格式

所有接口返回统一的JSON格式：

```json
{
  "code": 200,           // 状态码：200成功，404未找到，500服务器错误
  "message": "操作结果描述",
  "data": {}             // 数据内容，可能为对象、数组或null
}
```

---

## 测试数据

项目启动时会自动插入以下测试数据：

| ID | 姓名 | 邮箱 | 年龄 |
|----|------|------|------|
| 1 | 张三 | zhangsan@example.com | 25 |
| 2 | 李四 | lisi@example.com | 30 |
| 3 | 王五 | wangwu@example.com | 28 |
| 4 | 赵六 | zhaoliu@example.com | 35 |

---

## 注意事项

1. **数据持久化**: 项目使用H2内存数据库，应用重启后数据会丢失
2. **字符编码**: 所有接口支持UTF-8编码，可以正常处理中文
3. **错误处理**: 所有接口都有统一的错误响应格式
4. **ID自动生成**: 创建用户时，ID会自动生成，无需在请求体中提供

---

## 快速测试脚本 (PowerShell)

```powershell
# 1. 查询所有用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Get

# 2. 查询ID为1的用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Get

# 3. 搜索姓名包含"张"的用户
Invoke-RestMethod -Uri "http://localhost:8080/api/users/search?name=张" -Method Get

# 4. 创建新用户
$newUser = @{
    name = "测试用户"
    email = "test@example.com"
    age = 20
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -Body $newUser -ContentType "application/json"

# 5. 更新用户（假设ID为1）
$updateUser = @{
    name = "更新后的名字"
    email = "updated@example.com"
    age = 25
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Put -Body $updateUser -ContentType "application/json"

# 6. 删除用户（假设ID为1）
Invoke-RestMethod -Uri "http://localhost:8080/api/users/1" -Method Delete
```
