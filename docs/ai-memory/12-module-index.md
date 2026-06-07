# 模块索引

> 最后更新: 2026-06-07
> 🔍 用途：快速定位项目中的类和关键文件
> ⭐ = FoxAI 定制业务模块

## 启动与入口

| 文件 | 路径 |
|------|------|
| Spring Boot 启动类 | `ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java` |
| 前端入口 | `ruoyi-ui/src/main.js` |
| 前端 App 根组件 | `ruoyi-ui/src/App.vue` |
| Vite 配置 | `ruoyi-ui/vite.config.js` |
| Vite 插件汇总 | `ruoyi-ui/vite/plugins/index.js` |
| 根 POM | `pom.xml` |

## 后端配置文件

| 文件 | 路径 |
|------|------|
| 应用主配置 | `ruoyi-admin/src/main/resources/application.yml` |
| 数据源配置 | `ruoyi-admin/src/main/resources/application-druid.yml` |
| Logback | `ruoyi-admin/src/main/resources/logback.xml` |
| MyBatis 全局 | `ruoyi-admin/src/main/resources/mybatis/mybatis-config.xml` |

## Controller 层 (ruoyi-admin)

| Controller | 文件路径 | 功能 |
|------------|----------|------|
| SysLoginController | `web/controller/system/SysLoginController.java` | 登录/退出/锁屏 |
| SysRegisterController | `web/controller/system/SysRegisterController.java` | 注册 |
| SysIndexController | `web/controller/system/SysIndexController.java` | 首页/用户信息/路由 |
| SysUserController | `web/controller/system/SysUserController.java` | 用户 CRUD |
| SysRoleController | `web/controller/system/SysRoleController.java` | 角色 CRUD + 数据权限 |
| SysMenuController | `web/controller/system/SysMenuController.java` | 菜单 CRUD + 树 |
| SysDeptController | `web/controller/system/SysDeptController.java` | 部门 CRUD + 树 |
| SysPostController | `web/controller/system/SysPostController.java` | 岗位 CRUD |
| SysDictTypeController | `web/controller/system/SysDictTypeController.java` | 字典类型 CRUD |
| SysDictDataController | `web/controller/system/SysDictDataController.java` | 字典数据 CRUD |
| SysConfigController | `web/controller/system/SysConfigController.java` | 参数配置 CRUD |
| SysNoticeController | `web/controller/system/SysNoticeController.java` | 通知公告 CRUD |
| SysProfileController | `web/controller/system/SysProfileController.java` | 个人中心 |
| **ResellerLevelController** ⭐ | `web/controller/system/ResellerLevelController.java` | **分销商等级 CRUD** |
| **ResellerRegisterController** ⭐ | `web/controller/system/ResellerRegisterController.java` | **分销商注册** |
| **ResellerApprovalController** ⭐ | `web/controller/system/ResellerApprovalController.java` | **分销商审核** |
| CaptchaController | `web/controller/common/CaptchaController.java` | 验证码 |
| CommonController | `web/controller/common/CommonController.java` | 通用请求/文件下载 |
| SysUserOnlineController | `web/controller/monitor/SysUserOnlineController.java` | 在线用户 |
| SysOperlogController | `web/controller/monitor/SysOperlogController.java` | 操作日志 |
| SysLogininforController | `web/controller/monitor/SysLogininforController.java` | 登录日志 |
| ServerController | `web/controller/monitor/ServerController.java` | 服务监控 |
| CacheController | `web/controller/monitor/CacheController.java` | 缓存监控 |
| TestController | `web/controller/tool/TestController.java` | Swagger 测试 |
| SysJobController | `ruoyi-quartz/.../controller/SysJobController.java` | 定时任务 |
| SysJobLogController | `ruoyi-quartz/.../controller/SysJobLogController.java` | 任务日志 |
| GenController | `ruoyi-generator/.../controller/GenController.java` | 代码生成 |

## Core Service 层

| Service | 实现类路径 | 功能 |
|---------|-----------|------|
| SysLoginService | `ruoyi-framework/.../web/service/SysLoginService.java` | 登录校验 + 验证码 |
| TokenService | `ruoyi-framework/.../web/service/TokenService.java` | JWT Token 管理 + 续期 |
| PermissionService (@ss) | `ruoyi-framework/.../web/service/PermissionService.java` | 权限校验 Bean |
| SysPermissionService | `ruoyi-framework/.../web/service/SysPermissionService.java` | 用户权限/角色查询 |
| SysPasswordService | `ruoyi-framework/.../web/service/SysPasswordService.java` | 密码重试 + 锁定 |
| UserDetailsServiceImpl | `ruoyi-framework/.../web/service/UserDetailsServiceImpl.java` | Spring Security 用户加载 |
| SysRegisterService | `ruoyi-framework/.../web/service/SysRegisterService.java` | 用户注册 |
| **IResellerLevelService** ⭐ | `ruoyi-system/.../service/impl/ResellerLevelServiceImpl.java` | **分销商等级配置** |
| **IResellerRegisterService** ⭐ | `ruoyi-system/.../service/impl/ResellerRegisterServiceImpl.java` | **分销商注册** |
| **IResellerApprovalService** ⭐ | `ruoyi-system/.../service/impl/ResellerApprovalServiceImpl.java` | **分销商审批** |

## Domain 实体

| 实体 | Mapper | Mapper XML | 表名 |
|------|--------|------------|------|
| SysUser | SysUserMapper | `SysUserMapper.xml` | `sys_user` |
| SysRole | SysRoleMapper | `SysRoleMapper.xml` | `sys_role` |
| SysMenu | SysMenuMapper | `SysMenuMapper.xml` | `sys_menu` |
| SysDept | SysDeptMapper | `SysDeptMapper.xml` | `sys_dept` |
| SysPost | SysPostMapper | `SysPostMapper.xml` | `sys_post` |
| SysConfig | SysConfigMapper | `SysConfigMapper.xml` | `sys_config` |
| SysDictType | SysDictTypeMapper | `SysDictTypeMapper.xml` | `sys_dict_type` |
| SysDictData | SysDictDataMapper | `SysDictDataMapper.xml` | `sys_dict_data` |
| SysNotice | SysNoticeMapper | `SysNoticeMapper.xml` | `sys_notice` |
| SysNoticeRead | SysNoticeReadMapper | `SysNoticeReadMapper.xml` | `sys_notice_read` |
| SysOperLog | SysOperLogMapper | `SysOperLogMapper.xml` | `sys_oper_log` |
| SysLogininfor | SysLogininforMapper | `SysLogininforMapper.xml` | `sys_logininfor` |
| SysUserOnline | (Redis 存储) | — | Redis `login_tokens:*` |
| **ResellerLevel** ⭐ | ResellerLevelMapper | `ResellerLevelMapper.xml` | `reseller_level` |
| SysUser (扩展) | SysUserMapper | `SysUserMapper.xml` | `sys_user` (新增 parent_id, user_type) |
| SysRoleDept | SysRoleDeptMapper | `SysRoleDeptMapper.xml` | `sys_role_dept` |
| SysRoleMenu | SysRoleMenuMapper | `SysRoleMenuMapper.xml` | `sys_role_menu` |
| SysUserRole | SysUserRoleMapper | `SysUserRoleMapper.xml` | `sys_user_role` |
| SysUserPost | SysUserPostMapper | `SysUserPostMapper.xml` | `sys_user_post` |

所有 Domain 类路径: `ruoyi-system/src/main/java/com/ruoyi/system/domain/`
所有 Mapper XML 路径: `ruoyi-system/src/main/resources/mapper/system/`
所有 Mapper 接口路径: `ruoyi-system/src/main/java/com/ruoyi/system/mapper/`

## 安全与配置核心类

| 类 | 路径 | 功能 |
|----|------|------|
| SecurityConfig | `ruoyi-framework/.../config/SecurityConfig.java` | Spring Security 配置 |
| JwtAuthenticationTokenFilter | `ruoyi-framework/.../security/filter/JwtAuthenticationTokenFilter.java` | JWT 过滤 + Token 续期 |
| AuthenticationEntryPointImpl | `ruoyi-framework/.../security/handle/AuthenticationEntryPointImpl.java` | 401 处理 |
| LogoutSuccessHandlerImpl | `ruoyi-framework/.../security/handle/LogoutSuccessHandlerImpl.java` | 退出处理 |
| DruidConfig | `ruoyi-framework/.../config/DruidConfig.java` | 多数据源 + 监控 |
| RedisConfig | `ruoyi-framework/.../config/RedisConfig.java` | Redis 序列化 (FastJson2) |
| GlobalExceptionHandler | `ruoyi-framework/.../web/exception/GlobalExceptionHandler.java` | 全局异常处理 |
| AsyncFactory | `ruoyi-framework/.../manager/factory/AsyncFactory.java` | 异步日志工厂 |

## AOP 切面

| 类 | 路径 | 注解 | 功能 |
|----|------|------|------|
| LogAspect | `framework/aspectj/LogAspect.java` | @Log | 操作日志 |
| DataScopeAspect | `framework/aspectj/DataScopeAspect.java` | @DataScope | 数据权限 SQL 拼接 |
| RateLimiterAspect | `framework/aspectj/RateLimiterAspect.java` | @RateLimiter | 请求限流 |
| DataSourceAspect | `framework/aspectj/DataSourceAspect.java` | @DataSource | 多数据源切换 |

## 前端关键文件

| 文件 | 路径 | 功能 |
|------|------|------|
| 路由配置 | `ruoyi-ui/src/router/index.js` | 常量路由 + 动态路由 |
| 路由守卫 | `ruoyi-ui/src/permission.js` | 登录/权限校验 |
| Axios 封装 | `ruoyi-ui/src/utils/request.js` | Token 注入/防重复/401/下载 |
| Pinia 用户状态 | `ruoyi-ui/src/store/modules/user.js` | 登录/登出/用户信息 |
| Pinia 权限状态 | `ruoyi-ui/src/store/modules/permission.js` | 动态路由生成 (import.meta.glob) |
| 权限指令 | `ruoyi-ui/src/directive/permission/hasPermi.js` | v-hasPermi |
| 字典工具 | `ruoyi-ui/src/utils/dict.js` | useDict() Composition API |
| 字典组件 | `ruoyi-ui/src/components/DictTag/index.vue` | 字典标签渲染 |
| 分页组件 | `ruoyi-ui/src/components/Pagination/index.vue` | 分页 |
| **等级 API** ⭐ | `ruoyi-ui/src/api/system/resellerLevel.js` | 分销商等级接口封装 |
| **等级页面** ⭐ | `ruoyi-ui/src/views/system/resellerLevel/index.vue` | 分销商等级 CRUD |
| **注册 API** ⭐ | `ruoyi-ui/src/api/reseller/register.js` | 分销商注册 |
| **审核 API** ⭐ | `ruoyi-ui/src/api/reseller/approval.js` | 分销商审核 |
| **等级选择 API** ⭐ | `ruoyi-ui/src/api/reseller/level.js` | 等级选择引导页 |
| **审核页面** ⭐ | `ruoyi-ui/src/views/reseller/approval/index.vue` | 管理员审核列表 |
| **等级引导页** ⭐ | `ruoyi-ui/src/views/reseller/level-select/index.vue` | 登录后等级选择 |
| 登录页 | `ruoyi-ui/src/views/login.vue` | 登录 |
| 首页 | `ruoyi-ui/src/views/index.vue` | 仪表盘 |

## 数据库 SQL

| 文件 | 路径 | 说明 |
|------|------|------|
| 主库 DDL + 种子数据 | `sql/ry_20260417.sql` | 所有 sys_* 表 (672 行) |
| Quartz 调度表 DDL | `sql/quartz.sql` | Quartz 任务相关表 |
| reseller_level 建表 + 字典 + 菜单 + 种子数据 | `sql/2026-06-07-reseller-level.sql` | 幂等脚本，可反复执行 |
| reseller_register: sys_user 加 parent_id + 菜单 | `sql/2026-06-07-reseller-register.sql` | 分销商注册体系 |

## 文档

| 文件 | 路径 |
|------|------|
| 项目 README | `README.md` |
| AI 长期记忆系统 | `docs/ai-memory/` |
| AI 行为准则 | `docs/ai-memory/AI_RULES.md` |
