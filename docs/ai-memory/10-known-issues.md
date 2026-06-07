# 已知问题与注意事项

> 最后更新: 2026-05-29

## 当前状态

> ⚠️ **此项目是基于 RuoYi-Vue v3.9.2 的框架，前端已从 Vue 2 + Element UI 升级到 Vue 3 + Element Plus。以下内容为基于框架特点的风险提示。**

## 框架已知问题

### 1. jjwt 版本过旧 (0.9.1)

- jjwt 0.9.1 使用已弃用的 API（`Jwts.builder().signWith()` 无算法参数已废弃）
- 建议升级到 0.12.x+ 以获取安全修复和新特性
- 当前 `TokenService.java` 使用 `SignatureAlgorithm.HS512` 枚举，在 jjwt 0.12+ 中已移除

### 2. Spring Boot 4.x 兼容性

- 项目使用 Spring Boot 4.0.3（2024 年底发布），相对前瞻
- 某些第三方库的兼容性需验证（特别是 Quartz 调度器）
- Druid 使用 `druid-spring-boot-4-starter` 版本

### ⚠️ 3. 前端 Vue 2 → Vue 3 升级适配

前端已从 Vue 2 + Element UI + Vuex + Vue CLI 升级到 **Vue 3 + Element Plus + Pinia + Vite 6**。
以下为升级后需关注的风险点：

| 风险项 | 详情 |
|--------|------|
| `unplugin-auto-import` | 自动导入 Vue API（ref/computed/onMounted 等），在 IDE 中可能报 "未定义"，需配置 ESLint |
| `package.json` 中 `type: "module"` | 所有 JS 文件为 ESM 模块，CommonJS 语法 (`require`) 不可用 |
| `sass-embedded` | 替代 `sass`，确保与 Node.js 版本兼容 |
| `vite-plugin-svg-icons` | 使用虚拟模块 `virtual:svg-icons-register`，非 webpack 的 svg-sprite-loader |
| 字典工具 | 从 `utils/dict/` 目录（5个文件）简化为 `utils/dict.js` 单文件，需确认功能完整 |
| Element Plus 组件差异 | 部分组件 API 与 Element UI 不同（`el-table` 插槽语法等），升级后需逐页验证 |
| 暗黑模式 | `index.html` 中 `html.dark .login` 样式利用了 Element Plus 暗黑主题 |
| `.env` 文件 | 未找到 `.env.*` 文件，环境变量配置需确认如何设置 |
| `vuedraggable` | 从 v2 升级到 v4，API 有变化 |
| 未迁移的旧文件 | `babel.config.js` 可能不再需要（Vite 使用 esbuild 转译）|

### 4. XSS 过滤的潜在误伤

- XSS 过滤器默认启用，排除 `/system/notice`（公告内容含 HTML）
- 新增业务模块时需注意：若返回 HTML 内容，需加入 `xss.excludes`
- 过滤器基于 `XssHttpServletRequestWrapper` 包装请求，可能影响大文件上传的性能

### 4. 操作日志参数长度限制

- `oper_param` 字段 `varchar(2000)`——请求参数过长时会被截断
- `json_result` 字段 `varchar(2000)`——响应过长时也会被截断
- 如果后续业务涉及大 JSON 交互，考虑改为 `TEXT` 类型

### 5. 公告内容字段类型

- `sys_notice.notice_content` 为 `longblob`
- 在管理工具中不易直接查看，建议保持 `longblob` 不变（支持大文本 + HTML）

### 6. 默认密码安全风险

- 默认初始密码 `123456`（通过 `sys_config` 表配置）
- 初始密码修改提醒默认开启，但管理员可能忽略
- 建议生产环境修改 `sys.user.initPassword` 参数

### 7. Redis 数据持久化

- 当前 Redis 未配置持久化策略（默认 RDB）
- 若 Redis 重启，所有在线用户会话将丢失（用户需重新登录）
- 生产环境建议配置 AOF 或 RDB 持久化

### 8. Token 无刷新机制

- Token 有效期 30 分钟，到期前 20 分钟内自动续期
- 但如果用户持续活跃超过 30 分钟，Token 不会主动刷新（依赖每次请求的 Filter）
- 若用户 20 分钟内无任何请求，Token 到期后直接 401

## 配置注意事项

### 从库数据源
- 默认关闭 (`slave.enabled=false`)
- 开启后需配置 `application-druid.yml` 中的 slave 连接信息
- 使用 `@DataSource(DataSourceType.SLAVE)` 注解切换

### 防盗链
- 默认关闭 (`referer.enabled=false`)
- 生产环境建议开启，配置 `allowed-domains`

### XSS 过滤
- 默认开启 (`xss.enabled=true`)
- 排除 `/system/notice`（公告内容允许 HTML）
- 新增业务需注意

## 性能注意事项

### 数据库
- `sys_oper_log` 和 `sys_logininfor` 是增长最快的表
- 建议定期归档或清理（已有清空 API）
- 索引集中在 status / oper_time / business_type

### Redis
- 在线用户数 = Redis 中 `login_tokens:*` 键的数量
- 扫描所有在线用户使用 `keys` 命令，在大量在线用户时可能阻塞 Redis
- 生产环境建议改为 `SCAN` 命令

## 待确定事项

| 事项 | 状态 | 建议 |
|------|------|------|
| 项目定制业务模块 | 未开发 | 需确认 FoxAI-Reseller 的具体业务需求 |
| 前端 Vue 2 → Vue 3 升级计划 | 未计划 | 若依官方已转移重心至 Vue 3 |
| 生产环境 Redis 配置 | 未确认 | 集群/哨兵/单机 |
| 生产环境数据库 | 未确认 | 单机/主从/读写分离 |
| Docker 部署 | 未配置 | 框架支持，需补充 Dockerfile |
| 第三方登录 (OAuth) | 未集成 | 框架预留但未实现 |
| 分布式文件存储 | 未配置 | 框架预留但未实现 |
| CI/CD | 未配置 | 需补充 |
