## 该项目是个人开发的基于RBAC模型的权限管理系统，同时添加了API资源管理的功能
## 使用工具：SpringBoot + Mysql + Redis 
## 主要接口说明

## 1 用户操作（/user)
### 1.1 添加用户   
> 请求方式：POST
> IP地址:8080/user/add

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
username | 用户名 | String | 必填
password | 密码 | String | 必填
email | 邮箱 |String | 选填
remark | 备注 | String | 选填

### 1.2 更改用户信息
> 请求方式：POST
> IP地址:8080/user/update/{id}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
username | 用户名 | String | 必填
password | 密码 | String | 必填
email | 邮箱 |String | 选填
remark | 备注 | String | 选填

### 1.3 查询用户
> 请求方式：GET
> IP地址:8080/user/get/{id}

### 1.4 删除用户
> 请求方式：GET
> IP地址:8080/user/delete/{id}

### 1.5 获取所有用户
> 请求方式：GET
> IP地址:/user/showAll

## 2 角色操作（/role)
### 2.1 添加角色
> 请求方式：POST
> IP地址:8080/role/add

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | 角色名称 | String | 必填
type | 角色类型 | int | 选填 1：普通，2：其他（默认为1）
status | 状态 | int | 选填 1：可用，0：冻结（默认为1）
remark | 备注 | String | 选填

### 2.2 修改角色
> 请求方式：POST
> IP地址:8080/role/update/{id}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | 角色名称 | String | 必填
type | 角色类型 | int | 选填 1：普通，2：其他（默认为1）
status | 状态 | int | 选填 1：可用，0：冻结（默认为1）
remark | 备注 | String | 选填

### 2.3 查询角色
> 请求方式：GET
> IP地址:8080/role/get/{id}

### 2.4 删除角色
> 请求方式：GET
> IP地址:8080/role/delete/{id}

### 2.5 显示所有角色
> 请求方式：GET
> IP地址:8080/role/showAll


## 3 权限操作
### 3.1 添加权限
> 请求方式：POST
> IP地址:8080/acl/add/{moduleId}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
code | 权限码 | String | 必填
name | 权限名称 | String | 必填
url | 请求的url | String | 必填
type | 类型 | int | 选填 1：菜单；2：按钮；3：其他（默认为3）
status | 状态 | int | 选填 1：正常；0：冻结（默认为1）
seq | 权限在当前模块下的顺序，由小到大 | int | 选填
remark | 备注 | String | 选填

### 3.2 修改权限
> 请求方式：POST
> IP地址:8080/acl/update/{id}

字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
code | 权限码 | String | 必填
name | 权限名称 | String | 必填
url | 请求的url | String | 必填
type | 类型 | int | 选填 1：菜单；2：按钮；3：其他（默认为3）
status | 状态 | int | 选填 1：正常；0：冻结（默认为1）
seq | 权限在当前模块下的顺序，由小到大 | int | 选填
remark | 备注 | String | 选填

### 3.3 删除权限
> 请求方式：GET
> IP地址:8080/acl/delete/{id}

### 3.4 查询权限
> 请求方式：GET
> IP地址:8080/acl/get/{id}

### 3.5 查询所有权限
> 请求方式：GET
> IP地址:8080/acl/showAll

## 4 权限模块操作（/aclModule)
### 4.1 添加权限模块
> 请求方式：POST
> IP地址:8080/aclModule/add

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | 权限模块名称 | String | 必填
parent_id | 父节点ID | int | 必填
level | 权限模块层级 | String | 必填
seq | 权限模块在当前层级下的顺序，由小到大 | int | 选填
status | 状态 | int | 选填 1：正常，0：冻结（默认为1）
remark | 备注 | String | 选填 

### 4.2 查询权限模块
> 请求方式：GET
> IP地址:8080/aclModule/{id}

### 4.3 修改权限模块
> 请求方式：POST
> IP地址:8080/aclModule/update/{id}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | 权限模块名称 | String | 必填
parent_id | 父节点ID | int | 必填
level | 权限模块层级 | String | 必填
seq | 权限模块在当前层级下的顺序，由小到大 | int | 选填
status | 状态 | int | 选填 1：正常，0：冻结（默认为1）
remark | 备注 | String | 选填 

### 4.4 查询子权限模块
> 请求方式：GET
> IP地址:8080/aclModule/getByParentId/{id}

### 4.5 删除权限模块
> 请求方式：GET
> IP地址:8080/aclModule/delete/{id}

## 5 用户角色修改
### 5.1 分配角色
> 请求方式：POST
> IP地址:8080/roleUser/allot/{userId}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
roles | 角色组 | int数组 | 必填

### 5.2 查询用户的角色
> 请求方式：GET
> IP地址:8080/roleUser/getRoles/{userId}

### 5.3 删除用户某个权限
> 请求方式：GET
> IP地址:8080/roleUser/removeRole/{userId}?roleId={roleId}

### 5.4 删除用户某组权限
> 请求方式：POST
> IP地址:8080/roleUser/removeRoles/{userId}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
roles | 角色组 | int数组 | 必填


## 6 权限角色操作
### 6.1 分配角色权限
> 请求方式：POST
> IP地址:8080/roleAcl/allot/{roleId}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
acls | 权限组 | int数组 | 必填

### 6.2 获取角色权限
> 请求方式：GET
> IP地址:8080/roleAcl/getAcls/{roleId}

### 6.3 删除权限集合
> 请求方式：POST
> IP地址:8080/roleAcl/removeAcls/{roleId}

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
acls | 权限组 | int数组 | 必填

## 7 登录操作
### 7.1 用户登录
> 请求方式：POST
> IP地址:8080/auth

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
username | 用户名 | String | 必填
password | 密码 | String | 必填

**返回参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
assess_token | 令牌 | String | 

> **assess_token在每次请求常规页面时都必须在hearders带上。**

## 8 日志操作（/log）
### 8.1 查询单条日志
> 请求方式：GET
> IP地址:8080/log/get/{id}

### 8.2 查询所有日志记录
> 请求方式：GET
> IP地址:8080/log/showAll

## 9 API操作 (/api)
### 9.1 添加API 
> 请求方式：POST
> IP地址:8080/api/add

**请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
apiInfo | api信息 | JSON对象 | 必填
parametess | 参数信息 | JSONARRAY对象 | 必填
codes | 状态码信息 | JSONARRAY对象 | 必填

**APIINFO请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | api名称 | String | 必填
url | api路径 | String | 必填
protocol | 协议 | int | 选填（默认为0，即HTTP）
method | 请求方式 | int | 选填（默认为0，即GET）
request_format | 请求格式 | int | 选填（默认为0，即JSON)
response_format | 返回格式 | int | 选填（默认为0，即JSON)
example_url | 示例 | String | 选填
status | 状态 | int | 选填 0:普通（默认）   1：通过审核的  2： 通过测试的

**PARAMETERS请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
parameter_name | 参数名称 | String | 必填
type | 参数类型 |  | 选填
required | 是否必填 | int | 选填  0:否（默认）   1：是
remark | 说明 | String | 选填 说明
kind | 类型 | int |  选填 0：请求参数 1：返回参数

**CODES请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
status_code | 状态码 | String | 必填
description | 描述信息 | String | 必填

**示例：**
```json
{  
	"apiInfo" : {
		"name" : "短信服务2",
		"url" : "localhost:8080/msg" 
	},
	"parameters" : [
		{
			"parameter_name" : "apiKey"
		},
		{ 
			"parameter_name" : "statusCode",
			"kind" : 1
		}
	
	],
	"codes" : [
		{
			"status_code" : "110001",
			"description" : "手机号码格式非法"
		},
				{
			"status_code" : "110002",
			"description" : "模板ID格式非法"
		}
	]
}
```

### 9.2 查询Api信息
> 请求方式：GET
> IP地址:8080/api/get/{id}

**返回示例：**
```json
{
    "status": "200",
    "msg": "查询成功！",
    "data": {
        "id": 4,
        "name": "短信服务",
        "url": "localhost:8080/msg",
        "protocol": "HTTP",
        "method": 0,
        "request_format": 0,
        "response_format": 0,
        "example_url": null,
        "status": 0,
        "parameters": [
            {
                "id": 2,
                "parameter_name": "statusCode",
                "type": 0,
                "required": 0,
                "remark": null,
                "kind": 1
            },
            {
                "id": 1,
                "parameter_name": "apiKey",
                "type": 0,
                "required": 0,
                "remark": null,
                "kind": 0
            }
        ],
        "codes": [
            {
                "id": 2,
                "status_code": "110002",
                "description": "模板ID格式非法"
            },
            {
                "id": 1,
                "status_code": "110001",
                "description": "手机号码格式非法"
            }
        ]
    },
    "datetime": "2019-04-10 14:10:21"
}
```


### 9.3 查询所有API信息
> 请求方式：GET
> IP地址:8080/api/showAll

### 9.4 修改Api信息
> 请求方式：POST
> IP地址:8080/api/update/{id}

**APIINFO请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
name | api名称 | String | 必填
url | api路径 | String | 必填
protocol | 协议 | int | 选填（默认为0，即HTTP）
method | 请求方式 | int | 选填（默认为0，即GET）
request_format | 请求格式 | int | 选填（默认为0，即JSON)
response_format | 返回格式 | int | 选填（默认为0，即JSON)
example_url | 示例 | String | 选填
status | 状态 | int | 选填 0:普通（默认）   1：通过审核的  2： 通过测试的

### 9.5 根据状态信息查询
> 请求方式：GET
> IP地址:8080/api/getByStatus/{status}

### 9.6 修改Api状态信息
> 请求方式：GET
> IP地址:8080/api/updateStatus/{id}

### 9.7 删除Api
> 请求方式：GET
> IP地址:8080/api/delete/{id}

## 10 参数信息操作(/parameter)
### 10.1 添加参数信息
> 请求方式：POST
> IP地址:8080/parameter/add/{infoId}

**PARAMETERS请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
parameter_name | 参数名称 | String | 必填
type | 参数类型 |  | 选填
required | 是否必填 | int | 选填  0:否（默认）   1：是
remark | 说明 | String | 选填 说明
kind | 类型 | int |  选填 0：请求参数 1：返回参数

### 10.2 修改参数信息 
> 请求方式：POST
> IP地址:8080/parameter/update/{id}

**PARAMETERS请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
parameter_name | 参数名称 | String | 必填
type | 参数类型 |  | 选填
required | 是否必填 | int | 选填  0:否（默认）   1：是
remark | 说明 | String | 选填 说明
kind | 类型 | int |  选填 0：请求参数 1：返回参数

### 10.3 查询参数信息
> 请求方式：GET
> IP地址:8080/parameter/get/{id}

### 10.4 查询api的所有参数
> 请求方式：GET
> IP地址:8080/parameter/getParameterByInfo/{infoId}

### 10.5 删除参数
> 请求方式：GET
> IP地址:8080/parameter/delete/{id}

## 11 状态码操作（/code)
### 11.1 添加状态码
> 请求方式：POST
> IP地址:8080/code/add/{info_id}

**CODES请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
status_code | 状态码 | String | 必填
description | 描述信息 | String | 必填

###  11.2 修改状态码
> 请求方式：POST
> IP地址:8080/code/update/{id}

**CODES请求参数：**
字段名称 | 字段含义 | 字段类型 | 备注 
:-: | :-: | :-: | :-:
status_code | 状态码 | String | 必填
description | 描述信息 | String | 必填

###  11.3 查询状态码
> 请求方式：GET
> IP地址:8080/code/get/{id}

###  11.4 查询api的所有状态码
> 请求方式：GET
> IP地址:8080/code/getCodeByInfo/{infoId}

### 11.5 删除状态码
> 请求方式：GET
> IP地址:8080/code/delete/{id}




























