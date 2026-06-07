# 系统架构

> 最后更新: 2026-06-07

## 整体架构图（描述）

```
┌─────────────────────────────────────────────────────────┐
│                   前端 (Vue 3 + Element Plus + Vite 6)   │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐  │
│  │  路由    │ │  状态管理 │ │  Axios   │ │  组件    │  │
│  │ VueRouter4│ │  Pinia   │ │  请求    │ │ElementPlus│  │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘  │
└──────────────────────┬──────────────────────────────────┘
                       │ HTTP (JSON) / Bearer Token
                       ▼
┌─────────────────────────────────────────────────────────┐
│             后端 (Spring Boot 4.0.3 + JDK 21)            │
│                                                         │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐  │
│  │Controller│ │ Service  │ │  Mapper  │ │ Security │  │
│  │  (REST)  │ │ (业务)   │ │ (MyBatis)│ │  JWT     │  │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘  │
│                    │          │                         │
│              ┌─────┘          └─────┐                   │
│              ▼                      ▼                   │
│         ┌──────────┐          ┌──────────┐              │
│         │  MySQL   │          │  Redis   │              │
│         │foxai-reseller│      │  h3-cn.com:6379/db:4    │
│         └──────────┘          └──────────┘              │
└─────────────────────────────────────────────────────────┘
```

## 若依版本类型

**RuoYi-Vue 前后端分离版 v3.9.2**（非微服务版）

- 后端单体 war/jar，前端独立部署
- 无 Nacos / Gateway 组件
- 单数据源 + 可配置从库

## 请求生命周期

```
用户请求 → Nginx/CORS → Spring Security FilterChain
    → JwtAuthenticationTokenFilter（验证 Token → Redis 取 LoginUser）
    → Controller（参数校验 + 权限校验 @PreAuthorize）
    → Service（业务逻辑 + 数据权限 @DataScope）
    → Mapper（MyBatis XML SQL）
    → MySQL（返回数据）
    → Response（统一 JSON: AjaxResult / TableDataInfo）
```

## 后端分层详解

### ruoyi-admin（Web 层 / 启动入口）

```
com.ruoyi.web.controller
├── common/         # CaptchaController, CommonController
├── monitor/        # CacheController, ServerController,
│                   # SysLogininforController, SysOperlogController, SysUserOnlineController
├── system/         # Sys*Controller (原生) + AgentLevelController (业务)
│   ├── SysLoginController.java          → POST /login, /logout
│   ├── SysRegisterController.java       → POST /register
│   ├── SysIndexController.java          → GET /getInfo, /getRouters
│   ├── SysUserController.java           → 用户 CRUD
│   ├── SysRoleController.java           → 角色 CRUD + 数据权限
│   ├── SysMenuController.java           → 菜单管理
│   ├── SysDeptController.java           → 部门管理
│   ├── SysPostController.java           → 岗位管理
│   ├── SysDictTypeController.java       → 字典类型
│   ├── SysDictDataController.java       → 字典数据
│   ├── SysConfigController.java         → 参数配置
│   ├── SysNoticeController.java         → 通知公告
│   ├── SysProfileController.java        → 个人中心
│   ├── ResellerLevelController.java ⭐  → 分销商等级 CRUD
│   ├── ResellerRegisterController.java ⭐ → POST /reseller/register
│   └── ResellerApprovalController.java ⭐ → 分销商审核 API
└── tool/           # TestController (Swagger 演示)
```

### ruoyi-system（业务层 + 数据层）

```
com.ruoyi.system
├── domain/
│   ├── ResellerLevel.java ⭐           → 分销商等级实体 (extends BaseEntity)
│   ├── SysCache.java, Sys*.java        → RuoYi 原生实体
│   └── vo/                             → RouterVo, MetaVo
├── mapper/
│   ├── ResellerLevelMapper.java ⭐     → 分销商等级 Mapper 接口
│   ├── Sys*Mapper.java                 → RuoYi 原生 Mapper
├── service/
│   ├── IResellerLevelService.java ⭐   → 分销商等级 Service 接口
│   └── ISys*Service.java               → RuoYi 原生 Service 接口
└── service/impl/
    ├── ResellerLevelServiceImpl.java ⭐ → 分销商等级 Service 实现
    └── Sys*ServiceImpl.java            → RuoYi 原生 Service 实现
```

**Mapper XML 路径**: `ruoyi-system/src/main/resources/mapper/system/`
- `ResellerLevelMapper.xml` ⭐ — 分销商等级的 CRUD + 唯一性校验 SQL
- `Sys*Mapper.xml` — 原生 Mapper XML

### ruoyi-framework（核心配置层）

```
com.ruoyi.framework
├── aspectj/        # DataScopeAspect, DataSourceAspect, LogAspect, RateLimiterAspect
├── config/         # SecurityConfig, RedisConfig, DruidConfig, MyBatisConfig,
│   │               # CaptchaConfig, FilterConfig, I18nConfig, ResourcesConfig,
│   │               # ThreadPoolConfig, ServerConfig, ApplicationConfig
│   └── properties/ # DruidProperties, PermitAllUrlProperties
├── datasource/     # DynamicDataSource, DynamicDataSourceContextHolder
├── interceptor/    # RepeatSubmitInterceptor, SameUrlDataInterceptor
├── manager/        # AsyncManager, ShutdownManager, AsyncFactory
├── security/       # AuthenticationContextHolder, PermissionContextHolder
│   ├── filter/     # JwtAuthenticationTokenFilter
│   └── handle/     # AuthenticationEntryPointImpl, LogoutSuccessHandlerImpl
└── web/
    ├── domain/     # Server, Cpu, Jvm, Mem, Sys, SysFile
    ├── exception/  # GlobalExceptionHandler
    └── service/    # PermissionService, SysLoginService, SysPasswordService,
                     # SysPermissionService, SysRegisterService, TokenService,
                     # UserDetailsServiceImpl
```

### ruoyi-common（公共工具层）

```
com.ruoyi.common
├── annotation/     # @Log, @DataScope, @DataSource, @Excel, @Anonymous,
│                   # @RateLimiter, @RepeatSubmit, @Sensitive
├── config/         # RuoYiConfig
├── constant/       # Constants, CacheConstants, UserConstants, HttpStatus,
│                   # GenConstants, ScheduleConstants
├── core/           # 核心基类
│   ├── controller/ # BaseController (startPage, getDataTable, success/error)
│   ├── domain/     # BaseEntity, TreeEntity, AjaxResult, R, TreeSelect
│   │   └── entity/ # SysDept, SysDictData, SysDictType, SysMenu, SysRole, SysUser
│   ├── model/      # LoginBody, LoginUser, RegisterBody
│   ├── page/       # PageDomain, TableDataInfo, TableSupport
│   └── redis/      # RedisCache
├── enums/          # BusinessType, OperatorType, DataSourceType, UserStatus 等
├── exception/      # GlobalException, ServiceException, DemoModeException
│   ├── base/       # BaseException
│   ├── file/       # FileUploadException, FileSizeLimitExceededException 等
│   └── user/       # CaptchaException, UserPasswordNotMatchException 等
├── filter/         # XssFilter, RefererFilter, RepeatableFilter
└── utils/          # StringUtils, SecurityUtils, ServletUtils, IpUtils,
                     # Arith, DateUtils, DictUtils, ExcelUtil 等
```

## 数据库配置（实际）

```yaml
# 来源: ruoyi-admin/src/main/resources/application-druid.yml
spring.datasource.druid.master:
  url: jdbc:mysql://h3-cn.com:3306/foxai-reseller?...
  username: root
  password: root123456
  # 从库默认关闭
  slave.enabled: false
```

```yaml
# 来源: ruoyi-admin/src/main/resources/application.yml
spring.data.redis:
  host: h3-cn.com
  port: 6379
  database: 4
  password: A123456b
```

## 前端架构速览

```
ruoyi-ui/
├── index.html              # 入口 (Vite 模板 %VITE_APP_TITLE%)
├── vite.config.js          # Vite 配置（代理 /dev-api → localhost:8080）
├── vite/plugins/           # 插件: auto-import, compression, svg-icon, setup-extend
├── src/
│   ├── api/                # API 封装（按模块）
│   │   └── system/resellerLevel.js ⭐  → 分销商等级 API
│   ├── views/
│   │   └── system/resellerLevel/index.vue ⭐  → 分销商等级页面
│   ├── store/              # Pinia: user, permission, app, dict, settings, tagsView, lock
│   ├── router/index.js     # 常量路由 + 动态路由
│   ├── permission.js       # 路由守卫
│   └── utils/request.js    # Axios 封装 (Token 注入/防重复/401处理)
```

## Maven 模块依赖拓扑

```
ruoyi-admin (启动器 + Controller)
    │── ruoyi-framework (核心配置)
    │      ├── ruoyi-system (业务逻辑)
    │      │     └── ruoyi-common (公共工具)
    │      └── ruoyi-common
    │── ruoyi-quartz (定时任务)
    │      └── ruoyi-system
    │── ruoyi-generator (代码生成)
    │      ├── ruoyi-common
    │      └── ruoyi-system
    └── ruoyi-common
```

## 设计模式

| 模式 | 实现 | 示例 |
|------|------|------|
| 统一响应 | AjaxResult / R / TableDataInfo | 所有 Controller 返回 |
| 统一异常 | GlobalExceptionHandler | 全局 @ControllerAdvice |
| 控制器基类 | BaseController | 封装 startPage/success/error |
| 实体基类 | BaseEntity | createBy/createTime/updateBy/updateTime/remark |
| 树形实体 | TreeEntity | 扩展 parentId/children, 用于部门/菜单 |
| 数据权限 | @DataScope + AOP SQL 拼接 | DataScopeAspect |
| 多数据源 | @DataSource + AOP 切换 | DataSourceAspect |
| 异步任务 | @Async + AsyncManager | 日志记录/在线用户 |
| 接口+实现分离 | IService + ServiceImpl | 所有业务模块 |
