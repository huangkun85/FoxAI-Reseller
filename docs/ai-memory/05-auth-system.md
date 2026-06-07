# 认证与权限系统

> 最后更新: 2026-05-28

## 架构总览

```
┌─────────────────────────────────────────────────────┐
│                   认证 (Authentication)               │
│  用户名+密码 → BCrypt验证 → JWT签发 → Redis会话     │
├─────────────────────────────────────────────────────┤
│                   授权 (Authorization)                │
│  Spring Security + @PreAuthorize + PermissionService  │
├─────────────────────────────────────────────────────┤
│                  数据权限 (Data Scope)                 │
│  @DataScope 注解 → DataScopeAspect → SQL 拦截        │
└─────────────────────────────────────────────────────┘
```

## 1. Token 认证机制

### Token 结构

- **JWT Token**: HS512 签名，载体包含 `login_user_key` (UUID)
- **Header**: `Authorization: Bearer <token>`
- **有效期**: 30 分钟（配置项 `token.expireTime`）
- **自动续期**: 当剩余有效期 < 20 分钟时自动刷新

### Redis 缓存

| Key 模式 | 存储内容 | 说明 |
|----------|----------|------|
| `login_tokens:{uuid}` | LoginUser 对象 | 用户会话信息 |
| `captcha_codes:{uuid}` | 验证码文本 | 验证码 |
| `pwd_err_cnt:{username}` | 错误次数 | 密码重试计数 |
| `dict:{dictType}` | 字典数据列表 | 字典缓存 |

### Token 验证链

```
1. JwtAuthenticationTokenFilter (自定义)
   ↓
2. UsernamePasswordAuthenticationFilter (Spring Security)
   ↓
3. ExceptionTranslationFilter
   ↓
4. FilterSecurityInterceptor (@PreAuthorize 生效)
```

## 2. 密码策略

| 策略 | 配置项 | 默认值 | 说明 |
|------|--------|--------|------|
| 最大重试次数 | `user.password.maxRetryCount` | 5 | 超过后锁定 |
| 锁定时间 | `user.password.lockTime` | 10 分钟 | - |
| 加密算法 | - | BCrypt | Spring Security BCryptPasswordEncoder |
| 初始密码 | `sys.user.initPassword` | 123456 | 可在系统参数中修改 |
| 初始密码修改提醒 | `sys.account.initPasswordModify` | 1 (开启) | 登录时弹窗提醒 |
| 密码更新周期 | `sys.account.passwordValidateDays` | 0 (不限制) | 超期登录时提醒 |
| 密码字符范围 | `sys.account.chrtype` | 0 (任意) | 详见下方 |

### 密码字符范围配置 (`chrtype`)

| 值 | 说明 |
|----|------|
| 0 | 任意字符 |
| 1 | 数字 (0-9) |
| 2 | 英文字母 (a-z, A-Z) |
| 3 | 字母和数字 |
| 4 | 字母、数字和特殊字符 (~!@#$%^&*()-=_+) |

## 3. 权限模型 (RBAC)

```
用户 (User) ──< 用户-角色 >── 角色 (Role) ──< 角色-菜单 >── 菜单 (Menu)
                                                      │
                                                      ├── 目录 (M): 菜单目录
                                                      ├── 菜单 (C): 页面路由
                                                      └── 按钮 (F): 操作权限标识
```

### 权限标识命名规范

```
system:user:list      查询
system:user:add       新增
system:user:edit      修改
system:user:remove    删除
system:user:export    导出
system:user:import    导入
system:user:resetPwd  重置密码
```

### 权限校验方式

**前端**:
```html
<el-button v-hasPermi="['system:user:edit']">修改</el-button>
<el-button v-hasRole="['admin']">管理员操作</el-button>
```

权限指令: `src/directive/permission/hasPermi.js` / `hasRole.js`

**后端**:
```java
@PreAuthorize("@ss.hasPermi('system:user:list')")
@PreAuthorize("@ss.hasRole('admin')")
```

Bean `ss` 定义在 `PermissionService` 中。

## 4. 数据权限

### 数据范围级别 (`sys_role.data_scope`)

| 值 | 说明 | SQL 效果 |
|----|------|----------|
| 1 | 全部数据权限 | 不加限制 |
| 2 | 自定数据权限 | 关联 sys_role_dept 查询部门ID |
| 3 | 本部门数据权限 | sys_dept.dept_id = 当前用户dept_id |
| 4 | 本部门及以下数据权限 | dept_id IN (当前部门 + 所有子部门) |
| 5 | 仅本人数据权限 | sys_user.user_id = 当前用户 |

### 实现方式

```java
@DataScope(deptAlias = "d", userAlias = "u")
@Override
public List<SysUser> selectUserList(SysUser user) { ... }
```

`DataScopeAspect` 拦截后，根据用户角色数据范围拼接 SQL 过滤条件。

## 5. 安全防护

| 防护措施 | 实现方式 | 配置 |
|----------|----------|------|
| XSS 过滤 | XssFilter + XssHttpServletRequestWrapper | `xss.enabled=true`, 排除 `/system/notice` |
| CSRF | Spring Security 禁用 CSRF | `csrf.disable()` |
| 防盗链 | RefererFilter | `referer.enabled=false` |
| 防重复提交 | @RepeatSubmit + SameUrlDataInterceptor | 间隔 1 秒 |
| SQL 注入 | SqlUtil 转义 + MyBatis 参数绑定 | - |
| 请求限流 | @RateLimiter | 自定义注解 |
| 敏感数据脱敏 | @Sensitive + 序列化器 | 手机号/邮箱等 |

## 6. 在线用户管理

- 在线用户列表通过扫描 Redis 中 `login_tokens:*` 键获取
- 强制退出通过删除 Redis 中的 LoginUser 对象实现
- 角色权限变更后，自动刷新所有持有该角色的在线用户的权限缓存 (`TokenService.refreshPermissionByRoleId()`)

## 7. 注册功能

- 系统管理员注册：默认关闭（`sys.account.registerUser = false`），接口 `POST /register`
- 分销商注册：独立接口 `POST /reseller/register`，始终开放（需验证码）
- 分销商注册时 `user_type='01'`、`parent_id=0`、`status='0'`（注册即激活，可立即登录）
- 管理员在 `分销商管理 → 分销商审核` 中确认付款、补充 Token 信息（非控制登录权限）
