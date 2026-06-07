# 业务流程图

> 最后更新: 2026-05-28

## 1. 认证与授权流程

### 登录流程

```
用户输入账号密码
       │
       ▼
前端 POST /login  │  验证码校验 (从Redis获取)
       │
       ▼
SysLoginService.login()
   ├── 1. ValidateCaptcha() — Redis中读取验证码，匹配后删除
   ├── 2. loginPreCheck() — 空值/长度/IP黑名单校验
   ├── 3. AuthenticationManager.authenticate()
   │       └── UserDetailsServiceImpl.loadUserByUsername() → 查询 sys_user
   │       └── SysPasswordService 检查密码错误次数（Redis缓存）
   │       └── BCryptPasswordEncoder 密码匹配
   ├── 4. 异步记录登录日志 → sys_logininfor
   ├── 5. 更新用户登录信息 (login_ip, login_date)
   └── 6. TokenService.createToken()
         └── 生成 UUID (JWT claims 载体)
         └── LoginUser 存入 Redis (key: login_tokens:uuid)
         └── JWT 签名返回给前端
       │
       ▼
前端存储 Token → Cookie (Admin-Token)
       │
       ▼
前端调用 /getInfo 获取用户信息 + 权限 + 路由
```

### 请求鉴权流程

```
前端请求 → Authorization: Bearer <jwt>
       │
       ▼
JwtAuthenticationTokenFilter
   ├── 从 Header 提取 Token
   ├── 解析 JWT Claims → 获取 uuid
   ├── 从 Redis 取 LoginUser (login_tokens:uuid)
   ├── 验证 Token 有效期 (剩余<20分钟则刷新)
   └── 设置 SecurityContextHolder
       │
       ▼
Controller 方法 @PreAuthorize("@ss.hasPermi('system:user:list')")
   ├── PermissionService.hasPermi() → 比对 LoginUser.permissions
   │
   ▼
通过 → 执行业务逻辑
拒绝 → 401 Unauthorized
```

## 2. 用户管理流程

```
用户管理页面
   ├── 列表: GET /system/user/list (分页 + 条件查询)
   │     └── @DataScope(deptAlias="d", userAlias="u") 自动拼接部门数据权限
   │     └── PageHelper 自动分页
   ├── 新增: POST /system/user
   │     └── 密码 BCrypt 加密
   │     └── 插入 sys_user + 关联 sys_user_role + sys_user_post
   ├── 编辑: PUT /system/user
   │     └── 更新 sys_user + 重新关联角色/岗位
   ├── 删除: DELETE /system/user/{userIds}
   │     └── 逻辑删除 (del_flag=2)
   └── 重置密码: PUT /system/user/resetPwd
```

## 3. 角色-权限分配流程

```
角色管理 → 编辑角色
   ├── 分配菜单权限: 更新 sys_role_menu
   │     └── 清理该角色的在线用户权限缓存
   ├── 数据权限设置: 更新 sys_role.data_scope + sys_role_dept
   └── 分配用户: 更新 sys_user_role
```

## 4. 数据权限控制

```
@DataScope(deptAlias="d", userAlias="u")
       │
       ▼
DataScopeAspect 拦截 Service 方法
   ├── 根据 role.data_scope 判断
   │    ├── 1 全部数据: 不加限制
   │    ├── 2 自定义: 关联 sys_role_dept
   │    ├── 3 本部门: WHERE d.dept_id = 当前用户dept_id
   │    └── 4 本部门及以下: WHERE d.dept_id IN (当前部门及其子部门)
   └── 动态拼接 SQL 到 Mapper 参数
```

## 5. 操作日志记录

```
@Log(title="用户管理", businessType=BusinessType.INSERT)
       │
       ▼
LogAspect 环绕通知
   ├── 方法执行前: 记录开始时间
   ├── 方法执行: proceed()
   ├── 方法执行后 (finally):
   │    ├── 采集: 请求URL, IP, 参数, 方法名, 耗时
   │    └── 异步写入: sys_oper_log
   └── 异常时: 记录 error_msg + status=1
```

## 6. 字典服务流程

```
前端 <el-select> 绑定字典
       │
       ▼
DictData 组件 → GET /system/dict/data/type/{dictType}
       │
       ▼
后端 SysDictDataServiceImpl
   ├── 从 Redis 获取 (dict:类型名)
   ├── 无缓存 → 查 MySQL sys_dict_data → 写入 Redis
   └── 返回 JSON
       │
       ▼
前端 DictTag 组件渲染标签样式 (list_class: primary/danger/warning/info/success)
```

## 7. 定时任务执行流程

```
Quartz Scheduler (调度器)
       │
       ▼
AbstractQuartzJob.execute()
   ├── 记录执行开始时间
   ├── JobInvokeUtil.invokeMethod() → 反射调用目标方法
   ├── 记录执行结束时间
   └── 写入 sys_job_log
```

## 8. 代码生成流程

```
前端导入数据库表 → POST /tool/gen/importTable
       │
       ▼
GenTableServiceImpl
   ├── 读取表结构信息 (column_name, column_type, 注释)
   ├── 写入 gen_table + gen_table_column
   └── 生成配置 (包名/模块名/功能名)
       │
       ▼
前端编辑配置 → 预览/生成代码
   └── Velocity 模板引擎渲染
       ├── Controller.java
       ├── Service / ServiceImpl.java
       ├── Mapper.java / Mapper.xml
       ├── Domain.java
       ├── Vue 页面 (index.vue)
       └── SQL 菜单数据
```

## 核心业务流转概要

| 流程 | 涉及表 | 关键类 |
|------|--------|--------|
| 用户登录 | sys_user, sys_logininfor | SysLoginService, TokenService |
| 权限校验 | sys_role, sys_menu, sys_role_menu | PermissionService |
| 数据权限 | sys_dept, sys_role_dept | DataScopeAspect |
| 操作日志 | sys_oper_log | LogAspect, AsyncFactory |
| 字典缓存 | sys_dict_type, sys_dict_data | SysDictDataServiceImpl, RedisCache |
| 定时任务 | sys_job, sys_job_log | AbstractQuartzJob |
| 公告阅读 | sys_notice, sys_notice_read | SysNoticeServiceImpl |

## 注意

> 当前的 FoxAI 项目是基于 RuoYi 框架的基础版本，**尚未发现定制化的业务模块**。任何新增的业务功能将扩展在此业务流程之上。
