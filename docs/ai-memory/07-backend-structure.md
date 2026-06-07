# 后端结构

> 最后更新: 2026-05-28

## Maven 模块依赖关系

```
ruoyi-admin (启动器 + Controller)
    │
    ├── ruoyi-framework (核心框架配置)
    │     ├── ruoyi-system (业务逻辑)
    │     │     └── ruoyi-common (公共工具)
    │     └── ruoyi-common
    │
    ├── ruoyi-quartz (定时任务)
    │     └── ruoyi-system
    │
    ├── ruoyi-generator (代码生成)
    │     ├── ruoyi-common
    │     └── ruoyi-system
    │
    └── ruoyi-common
```

## 包扫描机制

启动类 `RuoYiApplication.java`:
```java
@SpringBootApplication
@EnableAsync  // 启用异步
// 自动扫描 com.ruoyi 下所有组件
```

MyBatis 配置: `classpath*:mapper/**/*Mapper.xml` 自动加载 Mapper XML。

## 关键配置类

| 配置类 | 路径 | 作用 |
|--------|------|------|
| SecurityConfig | `ruoyi-framework/config/SecurityConfig.java` | Spring Security 安全配置 |
| DruidConfig | `ruoyi-framework/config/DruidConfig.java` | 多数据源 + Druid 监控 |
| RedisConfig | `ruoyi-framework/config/RedisConfig.java` | Redis 序列化配置 |
| MyBatisConfig | `ruoyi-framework/config/MyBatisConfig.java` | MyBatis 配置 |
| CaptchaConfig | `ruoyi-framework/config/CaptchaConfig.java` | Kaptcha 验证码 |
| ResourcesConfig | `ruoyi-framework/config/ResourcesConfig.java` | 静态资源配置 |
| ThreadPoolConfig | `ruoyi-framework/config/ThreadPoolConfig.java` | 线程池配置 |
| I18nConfig | `ruoyi-framework/config/I18nConfig.java` | 国际化 |
| FilterConfig | `ruoyi-framework/config/FilterConfig.java` | XSS/防盗链等过滤器 |
| ApplicationConfig | `ruoyi-framework/config/ApplicationConfig.java` | 通用配置 |
| SwaggerConfig | `ruoyi-admin/web/core/config/SwaggerConfig.java` | SpringDoc 配置 |

## AOP 切面

| 切面 | 注解 | 作用 |
|------|------|------|
| LogAspect | `@Log` | 操作日志记录 |
| DataSourceAspect | `@DataSource` | 多数据源切换 |
| DataScopeAspect | `@DataScope` | 数据权限过滤 |
| RateLimiterAspect | `@RateLimiter` | 请求限流 |
| RepeatSubmitInterceptor | (非注解) | 防重复提交 |

## MyBatis Mapper XML 结构

Mapper XML 位于各模块 `resources/mapper/` 目录下。

```
ruoyi-system/src/main/resources/mapper/
├── system/
│   ├── SysConfigMapper.xml
│   ├── SysDeptMapper.xml
│   ├── SysDictDataMapper.xml
│   ├── SysDictTypeMapper.xml
│   ├── SysLogininforMapper.xml
│   ├── SysMenuMapper.xml
│   ├── SysNoticeMapper.xml
│   ├── SysNoticeReadMapper.xml
│   ├── SysOperLogMapper.xml
│   ├── SysPostMapper.xml
│   ├── SysRoleDeptMapper.xml
│   ├── SysRoleMapper.xml
│   ├── SysRoleMenuMapper.xml
│   ├── SysUserMapper.xml
│   ├── SysUserPostMapper.xml
│   └── SysUserRoleMapper.xml

ruoyi-quartz/src/main/resources/mapper/quartz/
├── SysJobMapper.xml
└── SysJobLogMapper.xml

ruoyi-generator/src/main/resources/mapper/generator/
├── GenTableColumnMapper.xml
└── GenTableMapper.xml
```

## 统一响应格式

### 成功响应
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": { ... }
}
```

### 分页响应
```json
{
  "code": 200,
  "msg": "查询成功",
  "rows": [ ... ],
  "total": 100
}
```

### 错误响应
```json
{
  "code": 500,
  "msg": "错误信息"
}
```

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 401 | 未认证 / Token 过期 |
| 403 | 无权限 |
| 500 | 服务器内部错误 |
| 601 | 业务警告 |

## 异步任务 (`AsyncFactory`)

| 任务 | 说明 |
|------|------|
| recordLogininfor | 异步记录登录日志 |
| recordOperLog | 异步记录操作日志 |
| recordSysUserOnline | 记录在线用户 |

## 全局异常处理 (`GlobalExceptionHandler`)

| 异常类型 | HTTP 状态码 | 处理方式 |
|----------|-------------|----------|
| DemoModeException | 500 | 演示模式提示 |
| ServiceException | 500 | 业务异常提示 |
| AccessDeniedException | 403 | 无权限提示 |
| AuthenticationException | 401 | 认证失败 |
| Exception | 500 | 通用异常兜底 |
