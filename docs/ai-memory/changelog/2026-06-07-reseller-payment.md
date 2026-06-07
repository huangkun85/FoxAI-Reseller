# Changelog: 分销商付款申请与账户体系

> 日期: 2026-06-07
> 类型: feature

## 变更摘要

建立完整的分销商付费链路：付款申请（等级选择 + 表单）→ 管理员审核 → 自动切换 user_type → 账户管理（额度/API Key）。同时引入全后端权限控制机制。

## 用户类型

| `user_type` | 含义 | 可见菜单 |
|-------------|------|----------|
| `'00'` | 系统管理员 | 全部（含审核/账户管理） |
| `'01'` | 未付费分销商 | 付款申请、付款记录 |
| `'02'` | 已付费分销商 | Dashboard、付款记录 |

## 新增文件

### 🔧 后端

| 文件 | 说明 |
|------|------|
| `ResellerPayment.java` | 付款申请实体 |
| `ResellerPaymentMapper.java` + `.xml` | |
| `IResellerPaymentService.java` + `impl` | |
| `ResellerPaymentController.java` | 提交 + 查询 |
| `ResellerAccount.java` | 分销商账户实体 |
| `ResellerAccountMapper.java` + `.xml` | |
| `IResellerAccountService.java` + `impl` | |
| `ResellerAccountController.java` | 管理端CRUD + 分销商查询 |
| `IResellerPaymentApprovalService.java` + `impl` | 审核通过（改 user_type + 建 account） |
| `ResellerPaymentApprovalController.java` | 审核接口 |
| `SysPermissionService.java` | filterPermsByUserType() 动态过滤 |

### 🆕 前端

| 文件 | 说明 |
|------|------|
| `views/reseller/payment/apply/index.vue` | 付款申请（4等级横向卡片+表单） |
| `views/reseller/payment/history/index.vue` | 付款记录 |
| `views/reseller/dashboard/index.vue` | 已付费 Dashboard（额度/API Key） |
| `views/reseller/payment-approval/index.vue` | 管理员付款审核 |
| `views/reseller/account/index.vue` | 管理员账户管理 |
| `api/reseller/payment.js` | |
| `api/reseller/account.js` | |
| `api/reseller/dashboard.js` | |

### 🔴 删除

| 文件 | 原因 |
|------|------|
| `views/reseller/level-select/index.vue` | 被付款申请页取代 |
| `views/reseller/approval/index.vue` | 由付款审核页取代 |

## Schema 变更

- 新增 `reseller_payment` — 付款申请表
- 新增 `reseller_account` — 分销商账户表
- 新增 `sys_dict_type` → `sys_payment_status`（0待审核/1已通过/2已驳回）
- 新增 `sys_config` → `sys.reseller.bank.account`（对公账户 JSON）

## 菜单结构

```
分销商管理 (2100)
  ├─ 等级体系 (2101)
  ├─ 付款申请 (2120, perms=reseller:payment:apply)        ← user_type=01
  ├─ 付款记录 (2121, perms=reseller:payment:history)       ← 01+02
  ├─ Dashboard (2122, perms=reseller:dashboard)             ← user_type=02
  ├─ 付款审核 (2130, perms=reseller:payment:approve)        ← admin
  └─ 账户管理 (2140, perms=reseller:account:list)           ← admin
```

## 审核通过逻辑

```java
1. reseller_payment.status → '1'
2. sys_user.user_type → '02'
3. 自动创建 reseller_account 空行（初始 quota=0）
4. 管理员在"账户管理"页面手动填写 quota / API Key
```

## 上线注意事项

1. 执行 `sql/2026-06-07-reseller-payment.sql` 创建表 + 字典 + 菜单 + 配置
2. 首次需在"角色管理"中为 `common` 角色分配所有新菜单权限
3. `sys.reseller.bank.account` 参数值可在"系统管理→参数设置"中修改
