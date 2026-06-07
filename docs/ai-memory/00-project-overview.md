# 项目概览

> 最后更新: 2026-06-07

## 基本信息

| 项目 | 值 |
|------|-----|
| 项目名称 | FoxAI-Reseller |
| 框架 | RuoYi-Vue v3.9.2（若依前后端分离版） |
| 项目路径 | `D:\WorkSpace\FoxAI\FoxAI-Reseller` |
| 根包名 | `com.ruoyi` |
| 数据库名 | `foxai-reseller` (MySQL) |
| 数据库配置文件 | `ruoyi-admin/src/main/resources/application-druid.yml` |

## 项目定位

FoxAI-Reseller 是基于 RuoYi-Vue 二次开发的 **Token 中转站分销系统**。核心业务是为 AI Token 提供分销渠道管理，包括：

- **分销商等级体系**（`reseller_level` 表）：定义不同等级分销商的套餐金额、加盟费、Token 额度、返佣比例、推荐奖励等
- **分销商注册与审批**：独立注册入口（Tab 切换），用户名+邮箱+密码注册，注册即激活
- **分销商付款与账户**：付款申请（等级选择+表单）→ 管理员审核 → user_type 切换 → 账户管理（额度/API Key）
- **后续待开发**：订单管理、Token 分配、结算对账等分销核心业务

## 业务边界

| 范围 | 包含 | 不包含 |
|------|------|--------|
| 系统管理 | 用户/角色/菜单/部门/岗位/字典/参数/通知 | - |
| 监控运维 | 在线用户/操作日志/登录日志/定时任务/缓存/服务 | - |
| 代码生成 | 基于 Velocity 模板的 CRUD 代码生成器 | - |
| ⭐ 分销业务 | 等级配置、注册与审批、付款申请、账户管理（额度/API Key） | 订单、结算、Token 发放等（待开发） |

## 当前状态

- **RuoYi 原生脚手架**：完整可用（已升级至 Vue 3 + Element Plus）
- **定制业务模块**：`ResellerLevel`（等级配置）、`ResellerRegister`（注册与审批）、`ResellerPayment/Account`（付款与账户管理）
- **待开发**：代理商管理、商品管理、订单系统、支付对接、结算分账

## 技术栈速览

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 4.0.3 |
| JDK | OpenJDK | **21** (pom.xml 中 `<java.version>21</java.version>`) |
| ORM | MyBatis + MyBatis-Spring-Boot-Starter | 4.0.1 |
| 数据库连接池 | Druid | 1.2.28 |
| 数据库 | MySQL | 驱动: mysql-connector-j |
| 缓存 | Redis (Lettuce) | Spring Data Redis |
| 权限 | Spring Security + JWT (jjwt 0.9.1) | - |
| 密码加密 | BCryptPasswordEncoder | - |
| 验证码 | Kaptcha | 2.3.3 |
| 分页 | PageHelper | 2.1.1 |
| API 文档 | SpringDoc OpenAPI (Swagger UI) | 3.0.2 |
| JSON | FastJSON2 | 2.0.61 |
| 前端框架 | Vue 3 | 3.5.26 |
| UI 组件库 | Element Plus | 2.13.1 |
| 构建工具 | Vite | 6.4.1 |
| 状态管理 | Pinia | 3.0.4 |
| 路由 | Vue Router 4 | 4.6.4 |
| HTTP 客户端 | Axios | 1.13.2 |
| 图标 | SVG Sprite (vite-plugin-svg-icons) | - |
| 富文本 | Quill (via @vueup/vue-quill) | 1.2.0 |
| 图表 | ECharts | 5.6.0 |
| VueUse | @vueuse/core | 14.1.0 |

## 模块结构

| Maven 模块 | 目录 | 说明 |
|------------|------|------|
| ruoyi-admin | `ruoyi-admin/` | 启动入口 + Web 层（Controller + 配置） |
| ruoyi-framework | `ruoyi-framework/` | 核心配置层（Security、Redis、Druid、AOP、过滤器） |
| ruoyi-system | `ruoyi-system/` | 业务逻辑层（Service、Mapper、Domain） |
| ruoyi-common | `ruoyi-common/` | 公共工具层（注解、常量、异常、工具类、核心域） |
| ruoyi-quartz | `ruoyi-quartz/` | 定时任务模块（基于 Quartz） |
| ruoyi-generator | `ruoyi-generator/` | 代码生成器模块（基于 Velocity 模板） |
| ruoyi-ui | `ruoyi-ui/` | 前端 Vue 3 + Element Plus + Pinia + Vite 6 |

## 构建与运行

- **构建工具**: Maven (后端) + Vite 6 (前端)
- **后端端口**: 8080
- **前端端口**: 80 (Vite dev server)
- **前端代理**: Vite devServer 将 `/dev-api` 请求代理到 `http://localhost:8080`
- **前端入口**: `index.html` → `src/main.js`

### 启动步骤

1. 创建 MySQL 数据库 `foxai-reseller`，执行 `sql/ry_20260417.sql` + `sql/quartz.sql`
2. 执行 `sql/2026-06-07-reseller-level.sql` 创建 `reseller_level` 表、字典、菜单及种子数据
3. 启动 Redis 服务
4. 修改 `application-druid.yml` 数据库连接配置（当前指向 `h3-cn.com`）
5. 在项目根目录执行 `mvn spring-boot:run`
6. 前端进入 `ruoyi-ui/` 目录，执行 `npm install && npm run dev`

### 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 超级管理员 |
| ry | admin123 | 普通角色 |

## 核心配置文件

| 文件 | 路径 | 作用 |
|------|------|------|
| 应用主配置 | `ruoyi-admin/src/main/resources/application.yml` | 端口、Redis、Token、MyBatis 等 |
| 数据源配置 | `ruoyi-admin/src/main/resources/application-druid.yml` | MySQL 连接、Druid 连接池 |
| 前端配置 | `ruoyi-ui/vite.config.js` | 代理、打包、GZip |
| Vite 插件入口 | `ruoyi-ui/vite/plugins/index.js` | auto-import, compression, svg-icon, setup-extend |
| 根 POM | `pom.xml` | 多模块配置、依赖管理 |

## 关键端口与地址

- 后端 API: `http://localhost:8080`
- 前端页面: `http://localhost:80`
- Druid 监控: `http://localhost:8080/druid/` (ruoyi/123456)
- Swagger 文档: `http://localhost:8080/swagger-ui.html`
- API JSON: `http://localhost:8080/v3/api-docs`

## ⚠️ 注意事项

1. **数据库名**: 实际为 `foxai-reseller`（配置文件中），有文档错误地写为 `ry-vue`
2. **JDK 版本**: 实际为 **21**，不是 17（pom.xml: `<java.version>21</java.version>`）
3. **Redis**: 实际指向 `h3-cn.com:6379`，database=4（不是 localhost）
4. **reseller_level 表**: 旧的 `agent_level` 表已被替换，SQL 脚本在 `sql/2026-06-07-reseller-level.sql`
