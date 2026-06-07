# 数据库结构

> 最后更新: 2026-06-07
> 数据库: `foxai-reseller` (MySQL)
> SQL 文件: `sql/ry_20260417.sql`, `sql/quartz.sql`, `sql/2026-06-07-reseller-level.sql`

## 表关系总图

```
sys_dept (1) ──< sys_user (N)
sys_user (M) ──< sys_user_role >── (N) sys_role (M)
sys_role (M) ──< sys_role_menu >── (N) sys_menu (M)
sys_role (M) ──< sys_role_dept >── (N) sys_dept (M)
sys_user (M) ──< sys_user_post >── (N) sys_post (M)
sys_notice (1) ──< sys_notice_read (N)
```

## 表清单

### 1. 部门表 `sys_dept`

| 列名 | 类型 | 说明 |
|------|------|------|
| dept_id | bigint(20) PK | 部门ID (auto_increment) |
| parent_id | bigint(20) | 父部门ID |
| ancestors | varchar(50) | 祖级列表 (如 `0,100,101`) |
| dept_name | varchar(30) | 部门名称 |
| order_num | int(4) | 显示顺序 |
| leader | varchar(20) | 负责人 |
| phone | varchar(11) | 联系电话 |
| email | varchar(50) | 邮箱 |
| status | char(1) | 状态 (0正常 1停用) |
| del_flag | char(1) | 删除标志 (0存在 2删除) |
| create_by / create_time / update_by / update_time | - | 审计字段 |

### 2. 用户表 `sys_user`

| 列名 | 类型 | 说明 |
|------|------|------|
| user_id | bigint(20) PK | 用户ID (auto_increment) |
| dept_id | bigint(20) FK | 部门ID → sys_dept.dept_id |
| parent_id | bigint(20) | 上级分销商ID（0=一级分销商，默认0） |
| user_name | varchar(30) | 用户账号 (唯一) |
| nick_name | varchar(30) | 用户昵称 |
| user_type | varchar(2) | 用户类型 (00系统用户, 01分销商) |
| email | varchar(50) | 邮箱 |
| phonenumber | varchar(11) | 手机号 |
| sex | char(1) | 性别 (0男 1女 2未知) |
| avatar | varchar(100) | 头像地址 |
| password | varchar(100) | BCrypt 加密密码 |
| status | char(1) | 账号状态 (0正常 1停用) |
| del_flag | char(1) | 删除标志 (0存在 2删除) |
| login_ip | varchar(128) | 最后登录IP |
| login_date | datetime | 最后登录时间 |
| pwd_update_date | datetime | 密码最后更新时间 |
| remark | varchar(500) | 备注 |

**初始化数据**: admin/admin123 用户 (user_id=1, dept_id=103)

### 3. 岗位表 `sys_post`

| 列名 | 类型 | 说明 |
|------|------|------|
| post_id | bigint(20) PK | 岗位ID |
| post_code | varchar(64) | 岗位编码 (如 `ceo`, `se`, `hr`, `user`) |
| post_name | varchar(50) | 岗位名称 |
| post_sort | int(4) | 显示顺序 |
| status | char(1) | 状态 (0正常 1停用) |
| remark | varchar(500) | 备注 |

### 4. 角色表 `sys_role`

| 列名 | 类型 | 说明 |
|------|------|------|
| role_id | bigint(20) PK | 角色ID (auto_increment) |
| role_name | varchar(30) | 角色名称 |
| role_key | varchar(100) | 角色权限字符串 (如 `admin`, `common`) |
| role_sort | int(4) | 显示顺序 |
| data_scope | char(1) | 数据范围: 1全部 2自定义 3本部门 4本部门及以下 |
| menu_check_strictly | tinyint(1) | 菜单树选择项是否关联显示 |
| dept_check_strictly | tinyint(1) | 部门树选择项是否关联显示 |
| status | char(1) | 状态 (0正常 1停用) |
| del_flag | char(1) | 删除标志 |

### 5. 菜单表 `sys_menu`

| 列名 | 类型 | 说明 |
|------|------|------|
| menu_id | bigint(20) PK | 菜单ID (auto_increment) |
| menu_name | varchar(50) | 菜单名称 |
| parent_id | bigint(20) | 父菜单ID |
| order_num | int(4) | 显示顺序 |
| path | varchar(200) | 路由地址 |
| component | varchar(255) | 组件路径 |
| query | varchar(255) | 路由参数 |
| route_name | varchar(50) | 路由名称 |
| is_frame | int(1) | 是否为外链 (0是 1否) |
| is_cache | int(1) | 是否缓存 |
| menu_type | char(1) | 菜单类型: M目录 C菜单 F按钮 |
| visible | char(1) | 显示状态 (0显示 1隐藏) |
| status | char(1) | 菜单状态 (0正常 1停用) |
| perms | varchar(100) | 权限标识 (如 `system:user:list`) |
| icon | varchar(100) | 图标 |

**菜单层级示例**:
```
系统管理 (M) ─┬─ 用户管理 (C) ── 用户查询/新增/修改/删除/导出/导入/重置密码 (F)
              ├─ 角色管理 (C) ── 角色查询/新增/修改/删除/导出 (F)
              ├─ 菜单管理 (C)
              ├─ 部门管理 (C)
              ├─ 岗位管理 (C)
              ├─ 字典管理 (C)
              ├─ 参数设置 (C)
              ├─ 通知公告 (C)
              └─ 日志管理 (M) ─┬─ 操作日志 (C)
                                └─ 登录日志 (C)
系统监控 (M) ─┬─ 在线用户 (C)
              ├─ 定时任务 (C)
              ├─ 数据监控 (C)
              ├─ 服务监控 (C)
              ├─ 缓存监控 (C)
              └─ 缓存列表 (C)
系统工具 (M) ─┬─ 表单构建 (C)
              ├─ 代码生成 (C)
              └─ 系统接口 (C)
```

### 6-9. 关联表

| 表名 | 关联关系 | 说明 |
|------|----------|------|
| `sys_user_role` | user_id + role_id (联合主键) | 用户-N→角色 (多对多) |
| `sys_role_menu` | role_id + menu_id (联合主键) | 角色-N→菜单 (多对多) |
| `sys_role_dept` | role_id + dept_id (联合主键) | 角色-N→部门 (多对多，数据权限) |
| `sys_user_post` | user_id + post_id (联合主键) | 用户-N→岗位 (多对多) |

### 10. 操作日志表 `sys_oper_log`

| 列名 | 类型 | 说明 |
|------|------|------|
| oper_id | bigint(20) PK | 日志主键 |
| title | varchar(50) | 模块标题 |
| business_type | int(2) | 业务类型 (0其它 1新增 2修改 3删除) |
| method | varchar(200) | 方法名称 |
| request_method | varchar(10) | 请求方式 |
| operator_type | int(1) | 操作类别 |
| oper_name | varchar(50) | 操作人员 |
| dept_name | varchar(50) | 部门名称 |
| oper_url | varchar(255) | 请求URL |
| oper_ip | varchar(128) | 主机地址 |
| oper_location | varchar(255) | 操作地点 |
| oper_param | varchar(2000) | 请求参数 |
| json_result | varchar(2000) | 返回参数 |
| status | int(1) | 操作状态 (0正常 1异常) |
| error_msg | varchar(2000) | 错误消息 |
| oper_time | datetime | 操作时间 |
| cost_time | bigint(20) | 消耗时间 (毫秒) |

**索引**: business_type, status, oper_time

### 11. 字典类型表 `sys_dict_type`

| 列名 | 说明 |
|------|------|
| dict_id PK | 字典主键 |
| dict_name | 字典名称 (如 `用户性别`) |
| dict_type (UNIQUE) | 字典类型 (如 `sys_user_sex`) |
| status | 状态 |

**预置类型**: `sys_user_sex`, `sys_show_hide`, `sys_normal_disable`, `sys_job_status`, `sys_job_group`, `sys_yes_no`, `sys_notice_type`, `sys_notice_status`, `sys_oper_type`, `sys_common_status`

### 12. 字典数据表 `sys_dict_data`

| 列名 | 说明 |
|------|------|
| dict_code PK | 字典编码 |
| dict_sort | 排序 |
| dict_label | 标签 (如 `男`) |
| dict_value | 键值 (如 `0`) |
| dict_type FK | → sys_dict_type.dict_type |
| css_class | 样式属性 |
| list_class | 回显样式 (primary/danger/warning/info/success) |
| is_default | 是否默认 (Y/N) |
| status | 状态 |

### 13. 参数配置表 `sys_config`

| 列名 | 说明 |
|------|------|
| config_id PK | 参数主键 |
| config_name | 参数名称 |
| config_key | 参数键名 (如 `sys.account.captchaEnabled`) |
| config_value | 参数键值 |
| config_type | 系统内置 (Y/N) |

**关键参数**:
- `sys.index.skinName` → `skin-blue`
- `sys.user.initPassword` → `123456`
- `sys.account.captchaEnabled` → `true`
- `sys.account.registerUser` → `false`
- `sys.login.blackIPList` → `（空）`
- `sys.account.initPasswordModify` → `1`（提醒修改初始密码）
- `sys.account.passwordValidateDays` → `0`（不限制密码更新周期）
- `sys.account.chrtype` → `0`（任意字符范围）

### 14. 登录日志表 `sys_logininfor`

| 列名 | 说明 |
|------|------|
| info_id PK | 访问ID |
| user_name | 用户账号 |
| ipaddr | 登录IP |
| login_location | 登录地点 |
| browser | 浏览器类型 |
| os | 操作系统 |
| status | 登录状态 (0成功 1失败) |
| msg | 提示消息 |
| login_time | 访问时间 |

**索引**: status, login_time

### 15. 定时任务表 `sys_job`

| 列名 | 说明 |
|------|------|
| job_id PK | 任务ID |
| job_name | 任务名称 |
| job_group | 任务组名 (DEFAULT/SYSTEM) |
| invoke_target | 调用目标字符串 `ryTask.ryNoParams` |
| cron_expression | Cron表达式 |
| misfire_policy | 错误策略 (1立即 2执行一次 3放弃) |
| concurrent | 是否并发 (0允许 1禁止) |
| status | 状态 (0正常 1暂停) |
| 联合主键: (job_id, job_name, job_group) |

### 16. 任务日志表 `sys_job_log`

| 列名 | 说明 |
|------|------|
| job_log_id PK | 任务日志ID |
| job_name + job_group + invoke_target | 任务标识 |
| status | 执行状态 (0正常 1失败) |
| exception_info | 异常信息 |
| start_time / end_time | 执行时间 |

### 17. 通知公告表 `sys_notice`

| 列名 | 说明 |
|------|------|
| notice_id PK | 公告ID |
| notice_title | 公告标题 |
| notice_type | 类型 (1通知 2公告) |
| notice_content | longblob 内容 (支持HTML) |
| status | 状态 (0正常 1关闭) |

### 18. 公告已读表 `sys_notice_read`

| 列名 | 说明 |
|------|------|
| read_id PK | 已读主键 |
| notice_id FK | 公告ID → sys_notice |
| user_id FK | 用户ID → sys_user |
| read_time | 阅读时间 |
| **唯一约束**: (user_id, notice_id) |

### 19-20. 代码生成相关表

| 表名 | 说明 |
|------|------|
| `gen_table` | 代码生成业务表（存储表结构元数据） |
| `gen_table_column` | 代码生成字段表（存储列元数据） |

### 21. 分销商等级配置表 `reseller_level`

| 列名 | 类型 | 说明 |
|------|------|------|
| id | bigint(20) PK | 主键ID |
| level_code | varchar(50) UNIQUE | 等级代码（对应字典 value） |
| level_name | varchar(50) | 等级名称 |
| package_amount | decimal(14,2) | 套餐金额 |
| franchise_fee | decimal(14,2) | 加盟费 |
| token_quota | decimal(14,2) | Token额度（原名 token_fee） |
| bonus_amount | decimal(14,2) | 赠送额度 |
| commission_rate | decimal(14,2) | 下级返佣比例(%) |
| referral_reward | decimal(14,2) | 推荐奖励金额 |
| sort_order | int(4) | 显示顺序 |
| status | char(1) | 状态（0正常 1停用） |
| remark | varchar(500) | 备注 |
| create_by / create_time / update_by / update_time | - | 审计字段 |

**字典类型**: `reseller_level`，值为 `bronze/silver/gold/diamond`
**SQL 文件**: `sql/2026-06-07-reseller-level.sql`
**旧表已删除**: `agent_level`（已由 `reseller_level` 替代）
**旧字典已替换**: `agent_level_type` 由 `reseller_level` 替代

## 数据库配置

- **主库**: `jdbc:mysql://h3-cn.com:3306/foxai-reseller`
- **从库**: 默认关闭 (slave.enabled=false)
- **连接池**: Druid (initialSize=5, maxActive=20)
- **慢SQL阈值**: 1000ms

## 重要索引

| 表 | 索引名 | 字段 |
|----|--------|------|
| sys_oper_log | idx_sys_oper_log_bt | business_type |
| sys_oper_log | idx_sys_oper_log_s | status |
| sys_oper_log | idx_sys_oper_log_ot | oper_time |
| sys_logininfor | idx_sys_logininfor_s | status |
| sys_logininfor | idx_sys_logininfor_lt | login_time |
| sys_dict_type | dict_type (UNIQUE) | dict_type |
| sys_notice_read | uk_user_notice (UNIQUE) | user_id, notice_id |
