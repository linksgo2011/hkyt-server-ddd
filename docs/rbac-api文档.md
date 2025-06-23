# 🔐 RBAC API 接口文档

## Base URL

所有API的基础URL为：`/api`

## 认证

除了登录接口，所有API请求都需要在Header中包含有效的JWT令牌：

```
Authorization: Bearer <token>
```

## 响应格式

所有API响应均使用JSON格式，包含以下标准字段：

```json
{
  "code": "200",        // 状态码，200表示成功，其他表示不同类型的错误
  "message": "成功",     // 状态描述
  "data": { ... }       // 实际返回的数据，错误时可能为null
}
```

## 1. 用户管理

### 1.1 用户注册

- **URL**: `/users/register`
- **方法**: `POST`
- **描述**: 注册新用户
- **请求体**:
```json
{
  "username": "user123",
  "password": "Password123",
  "email": "user@example.com"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "用户注册成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "user123",
    "email": "user@example.com",
    "enabled": true,
    "roles": []
  }
}
```

### 1.2 用户登录

- **URL**: `/auth/login`
- **方法**: `POST`
- **描述**: 用户登录并获取令牌
- **请求体**:
```json
{
  "username": "user123",
  "password": "Password123"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "username": "user123",
      "email": "user@example.com"
    }
  }
}
```

### 1.3 获取当前用户信息

- **URL**: `/users/current`
- **方法**: `GET`
- **描述**: 获取当前登录用户信息
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "user123",
    "email": "user@example.com",
    "enabled": true,
    "roles": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440001",
        "name": "USER",
        "description": "普通用户"
      }
    ]
  }
}
```

### 1.4 获取用户列表

- **URL**: `/users`
- **方法**: `GET`
- **描述**: 获取用户列表，支持分页
- **参数**:
    - `page`: 页码，默认0
    - `size`: 每页记录数，默认10
    - `sort`: 排序字段，默认id
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "content": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "username": "user123",
        "email": "user@example.com",
        "enabled": true
      }
    ],
    "totalElements": 50,
    "totalPages": 5,
    "size": 10,
    "number": 0
  }
}
```

### 1.5 获取单个用户

- **URL**: `/users/{id}`
- **方法**: `GET`
- **描述**: 获取指定ID的用户详情
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "user123",
    "email": "user@example.com",
    "enabled": true,
    "roles": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440001",
        "name": "USER",
        "description": "普通用户"
      }
    ]
  }
}
```

### 1.6 更新用户

- **URL**: `/users/{id}`
- **方法**: `PUT`
- **描述**: 更新指定ID的用户信息
- **请求体**:
```json
{
  "email": "newemail@example.com",
  "enabled": true
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "用户更新成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "user123",
    "email": "newemail@example.com",
    "enabled": true
  }
}
```

### 1.7 删除用户

- **URL**: `/users/{id}`
- **方法**: `DELETE`
- **描述**: 删除指定ID的用户
- **响应**:
```json
{
  "code": "200",
  "message": "用户删除成功",
  "data": null
}
```

### 1.8 添加角色给用户

- **URL**: `/users/{userId}/roles/{roleId}`
- **方法**: `POST`
- **描述**: 给指定用户添加角色
- **响应**:
```json
{
  "code": "200",
  "message": "角色添加成功",
  "data": null
}
```

### 1.9 移除用户角色

- **URL**: `/users/{userId}/roles/{roleId}`
- **方法**: `DELETE`
- **描述**: 移除用户的指定角色
- **响应**:
```json
{
  "code": "200",
  "message": "角色移除成功",
  "data": null
}
```

### 1.10 获取用户权限

- **URL**: `/users/current/permissions`
- **方法**: `GET`
- **描述**: 获取当前用户的所有权限
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "roles": ["USER", "EDITOR"],
    "permissions": ["read:users", "write:users", "read:posts"]
  }
}
```

### 1.11 检查用户权限

- **URL**: `/users/current/permissions/{permissionName}/check`
- **方法**: `GET`
- **描述**: 检查当前用户是否拥有指定权限
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": true
}
```

## 2. 角色管理

### 2.1 创建角色

- **URL**: `/roles`
- **方法**: `POST`
- **描述**: 创建新角色
- **请求体**:
```json
{
  "name": "EDITOR",
  "description": "内容编辑"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "角色创建成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "name": "EDITOR",
    "description": "内容编辑",
    "permissions": []
  }
}
```

### 2.2 获取角色列表

- **URL**: `/roles`
- **方法**: `GET`
- **描述**: 获取所有角色列表
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440001",
      "name": "ADMIN",
      "description": "系统管理员"
    },
    {
      "id": "550e8400-e29b-41d4-a716-446655440002",
      "name": "USER",
      "description": "普通用户"
    }
  ]
}
```

### 2.3 获取单个角色

- **URL**: `/roles/{id}`
- **方法**: `GET`
- **描述**: 获取指定ID的角色详情
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "name": "ADMIN",
    "description": "系统管理员",
    "permissions": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440003",
        "name": "read:users",
        "description": "查看用户信息"
      }
    ]
  }
}
```

### 2.4 更新角色

- **URL**: `/roles/{id}`
- **方法**: `PUT`
- **描述**: 更新指定ID的角色信息
- **请求体**:
```json
{
  "description": "系统超级管理员"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "角色更新成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "name": "ADMIN",
    "description": "系统超级管理员"
  }
}
```

### 2.5 删除角色

- **URL**: `/roles/{id}`
- **方法**: `DELETE`
- **描述**: 删除指定ID的角色
- **响应**:
```json
{
  "code": "200",
  "message": "角色删除成功",
  "data": null
}
```

### 2.6 添加权限给角色

- **URL**: `/roles/{roleId}/permissions/{permissionId}`
- **方法**: `POST`
- **描述**: 给指定角色添加权限
- **响应**:
```json
{
  "code": "200",
  "message": "权限添加成功",
  "data": null
}
```

### 2.7 移除角色权限

- **URL**: `/roles/{roleId}/permissions/{permissionId}`
- **方法**: `DELETE`
- **描述**: 移除角色的指定权限
- **响应**:
```json
{
  "code": "200",
  "message": "权限移除成功",
  "data": null
}
```

## 3. 权限管理

### 3.1 创建权限

- **URL**: `/permissions`
- **方法**: `POST`
- **描述**: 创建新权限
- **请求体**:
```json
{
  "name": "read:users",
  "description": "查看用户信息"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "权限创建成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "name": "read:users",
    "description": "查看用户信息"
  }
}
```

### 3.2 获取权限列表

- **URL**: `/permissions`
- **方法**: `GET`
- **描述**: 获取所有权限列表
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440003",
      "name": "read:users",
      "description": "查看用户信息"
    },
    {
      "id": "550e8400-e29b-41d4-a716-446655440004",
      "name": "write:users",
      "description": "修改用户信息"
    }
  ]
}
```

### 3.3 获取单个权限

- **URL**: `/permissions/{id}`
- **方法**: `GET`
- **描述**: 获取指定ID的权限详情
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "name": "read:users",
    "description": "查看用户信息"
  }
}
```

### 3.4 更新权限

- **URL**: `/permissions/{id}`
- **方法**: `PUT`
- **描述**: 更新指定ID的权限信息
- **请求体**:
```json
{
  "description": "查看系统用户信息"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "权限更新成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "name": "read:users",
    "description": "查看系统用户信息"
  }
}
```

### 3.5 删除权限

- **URL**: `/permissions/{id}`
- **方法**: `DELETE`
- **描述**: 删除指定ID的权限
- **响应**:
```json
{
  "code": "200",
  "message": "权限删除成功",
  "data": null
}
```

## 4. 菜单管理

### 4.1 创建菜单

- **URL**: `/menus`
- **方法**: `POST`
- **描述**: 创建新菜单
- **请求体**:
```json
{
  "name": "用户管理",
  "path": "/users",
  "component": "user/UserList",
  "icon": "el-icon-user",
  "parentId": null,
  "order": 1,
  "hidden": false,
  "permissionId": "550e8400-e29b-41d4-a716-446655440003"
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "菜单创建成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440005",
    "name": "用户管理",
    "path": "/users",
    "component": "user/UserList",
    "icon": "el-icon-user",
    "parentId": null,
    "order": 1,
    "hidden": false
  }
}
```

### 4.2 获取菜单列表

- **URL**: `/menus`
- **方法**: `GET`
- **描述**: 获取所有菜单列表，以树状结构返回
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440005",
      "name": "系统管理",
      "path": "/system",
      "component": "Layout",
      "icon": "el-icon-setting",
      "parentId": null,
      "order": 1,
      "hidden": false,
      "children": [
        {
          "id": "550e8400-e29b-41d4-a716-446655440006",
          "name": "用户管理",
          "path": "/system/users",
          "component": "system/user/index",
          "icon": "el-icon-user",
          "parentId": "550e8400-e29b-41d4-a716-446655440005",
          "order": 1,
          "hidden": false,
          "children": []
        }
      ]
    }
  ]
}
```

### 4.3 获取当前用户可访问菜单

- **URL**: `/menus/current`
- **方法**: `GET`
- **描述**: 获取当前用户基于权限可访问的菜单
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440005",
      "name": "系统管理",
      "path": "/system",
      "component": "Layout",
      "icon": "el-icon-setting",
      "parentId": null,
      "order": 1,
      "hidden": false,
      "children": [
        {
          "id": "550e8400-e29b-41d4-a716-446655440006",
          "name": "用户管理",
          "path": "/system/users",
          "component": "system/user/index",
          "icon": "el-icon-user",
          "parentId": "550e8400-e29b-41d4-a716-446655440005",
          "order": 1,
          "hidden": false,
          "children": []
        }
      ]
    }
  ]
}
```

### 4.4 获取单个菜单

- **URL**: `/menus/{id}`
- **方法**: `GET`
- **描述**: 获取指定ID的菜单详情
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440005",
    "name": "系统管理",
    "path": "/system",
    "component": "Layout",
    "icon": "el-icon-setting",
    "parentId": null,
    "order": 1,
    "hidden": false,
    "requiredPermission": {
      "id": "550e8400-e29b-41d4-a716-446655440003",
      "name": "read:users",
      "description": "查看用户信息"
    }
  }
}
```

### 4.5 更新菜单

- **URL**: `/menus/{id}`
- **方法**: `PUT`
- **描述**: 更新指定ID的菜单信息
- **请求体**:
```json
{
  "name": "系统设置",
  "icon": "el-icon-s-tools",
  "order": 2
}
```
- **响应**:
```json
{
  "code": "200",
  "message": "菜单更新成功",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440005",
    "name": "系统设置",
    "path": "/system",
    "component": "Layout",
    "icon": "el-icon-s-tools",
    "parentId": null,
    "order": 2,
    "hidden": false
  }
}
```

### 4.6 删除菜单

- **URL**: `/menus/{id}`
- **方法**: `DELETE`
- **描述**: 删除指定ID的菜单
- **响应**:
```json
{
  "code": "200",
  "message": "菜单删除成功",
  "data": null
}
```

## 5. 系统日志

### 5.1 获取操作日志

- **URL**: `/logs/operations`
- **方法**: `GET`
- **描述**: 获取系统操作日志列表，支持分页和日期范围筛选
- **参数**:
    - `page`: 页码，默认0
    - `size`: 每页记录数，默认10
    - `startTime`: 开始时间，格式yyyy-MM-dd HH:mm:ss
    - `endTime`: 结束时间，格式yyyy-MM-dd HH:mm:ss
    - `username`: 用户名，可选
- **响应**:
```json
{
  "code": "200",
  "message": "成功",
  "data": {
    "content": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440007",
        "userId": "550e8400-e29b-41d4-a716-446655440000",
        "username": "admin",
        "operation": "添加用户",
        "method": "POST",
        "params": "{\"username\":\"user123\",\"email\":\"user@example.com\"}",
        "ipAddress": "192.168.1.1",
        "createTime": "2023-03-15 14:30:00",
        "status": 1,
        "errorMessage": null
      }
    ],
    "totalElements": 50,
    "totalPages": 5,
    "size": 10,
    "number": 0
  }
}
```

## 6. 错误码说明

| 错误码 | 描述 |
| ------ | ---- |
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证或认证失败 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 409 | 资源冲突 |
| 500 | 服务器内部错误 |