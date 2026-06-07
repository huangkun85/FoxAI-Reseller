# Changelog: 一级分销商注册与审批

> 日期: 2026-06-07
> 类型: feature

## 变更摘要

实现一级分销商注册、管理员审批、登录后等级选择引导的完整流程。登录页重写为居中卡片 + Tab 切换（登录/注册）布局。

## 修改文件清单

### 🔧 修改

| 文件 | 变更内容 |
|------|----------|
| `SysUser.java` | 新增 `parentId`、`userType` 字段 |
| `LoginUser.java` | 新增 `userType`、`parentId` 字段及 getter/setter |
| `SysUserMapper.xml` | resultMap/select/insert/update 加入 `parent_id`、`user_type` |
| `login.vue` | 重写为居中卡片 + Tab 切换布局 |
| `router/index.js` | 新增 `/reseller/level-select` 路由 |

### 🟢 新增

| 文件 | 说明 |
|------|------|
| `sql/2026-06-07-reseller-register.sql` | sys_user 加 parent_id + 菜单权限 |
| `ResellerRegisterController.java` | POST `/reseller/register` |
| `IResellerRegisterService.java` | 注册服务接口 |
| `ResellerRegisterServiceImpl.java` | 注册实现（user_type='01', status='1'） |
| `ResellerRegisterBody.java` | 注册请求体（含 email） |
| `ResellerApprovalController.java` | 分销商审核 CRUD |
| `IResellerApprovalService.java` | 审核服务接口 |
| `ResellerApprovalServiceImpl.java` | 审核实现 |
| `api/reseller/register.js` | 前端注册 API |
| `api/reseller/approval.js` | 前端审核 API |
| `api/reseller/level.js` | 前端等级选择 API |
| `views/reseller/approval/index.vue` | 管理员审核页 |
| `views/reseller/level-select/index.vue` | 登录后等级引导页 |

### 🔴 删除

| 文件 | 原因 |
|------|------|
| `register.vue` | 注册功能内联到 login.vue 的 Tab 面板 |

## API 变更

| 方法 | 路径 | 鉴权 | 说明 |
|------|------|------|------|
| POST | `/reseller/register` | 公开 | 分销商注册 |
| GET | `/reseller/approval/pending-list` | 管理员 | 待审核列表 |
| POST | `/reseller/approval/approve/{userId}` | 管理员 | 审核通过 |
| POST | `/reseller/approval/reject/{userId}` | 管理员 | 驳回 |

## 数据库变更

- `sys_user` 新增列: `parent_id bigint(20) DEFAULT 0`

## 菜单结构

```
分销商管理 (2100)
  ├─ 分销商等级体系 (2101) ← 已有
  └─ 分销商审核 (2110, perms=reseller:approval:list)
       ├─ 查询 (2111)
       ├─ 审核通过 (2112, perms=reseller:approval:approve)
       └─ 驳回 (2113, perms=reseller:approval:reject)
```

## 注册流程

1. 登录页 Tab"注册"→ 填写用户名 + 邮箱 + 密码 + 验证码
2. POST `/reseller/register` → 创建 `sys_user` (`user_type='01'`, `status='1'`)
3. 弹出成功提示 → 自动切换到登录 Tab，用户名预填
4. 管理员登录 → 分销商管理 → 分销商审核 → 审批通过
5. 分销商通过登录页正常登录使用系统

## 修复记录

### 2026-06-07: 注册接口 401 修复

- **问题**: `POST /reseller/register` 未在 `SecurityConfig` 的 `permitAll` 列表中，未登录请求返回 401
- **修复**: `SecurityConfig.java:103` 将 `"/reseller/register"` 加入 `.requestMatchers()`
- **验证**: `mvn compile -q` 通过

## 上线注意事项

1. 首次部署需执行 `sql/2026-06-07-reseller-register.sql`
2. 旧 `register.vue` 已删除，确认无其他入口引用
3. 登录页布局改为卡片居中，确认暗黑模式样式正常
