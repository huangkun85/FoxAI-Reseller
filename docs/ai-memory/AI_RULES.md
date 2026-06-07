# FoxAI-Reseller AI 记忆系统规则

> 最后更新: 2026-06-07

## 本目录用途

本目录 (`docs/ai-memory/`) 是 FoxAI-Reseller 项目的 AI 长期记忆系统。
目标是让 AI（包括 Claude、Copilot、Cursor 等）在后续交互中，**无需重新扫描整个代码库**，即可快速理解项目结构、业务逻辑和技术决策。

## 文件索引

| 文件 | 内容 | 阅读优先级 |
|------|------|-----------|
| `00-project-overview.md` | 项目概览、技术栈、启动方式 | ⭐⭐⭐ 必读 |
| `01-architecture.md` | 整体架构、分层设计、Maven 依赖拓扑 | ⭐⭐⭐ 必读 |
| `02-api-map.md` | 所有 API 端点映射 | ⭐⭐⭐ 必读 |
| `03-db-schema.md` | 数据库表结构、索引、关系 | ⭐⭐⭐ 必读 |
| `04-business-flow.md` | 核心业务流程图解 | ⭐⭐⭐ 必读 |
| `05-auth-system.md` | 认证授权机制详解 | ⭐⭐⭐ 必读 |
| `06-frontend-structure.md` | 前端目录结构、组件、路由、Vite 配置 | ⭐⭐ 按需 |
| `07-backend-structure.md` | 后端模块结构、配置、AOP | ⭐⭐ 按需 |
| `08-coding-conventions.md` | 命名规范、编码约定 | ⭐⭐ 按需 |
| `09-dependency-map.md` | 依赖关系、模块调用链 | ⭐⭐ 按需 |
| `10-known-issues.md` | 已知问题与注意事项 | ⭐⭐ 按需 |
| `11-dev-workflow.md` | 开发工作流、调试、部署 | ⭐ 新手参考 |
| `12-module-index.md` | 快速文件定位索引 | ⭐⭐⭐ 必读 |

## AI 使用指引

### 首次接触本项目时，请按此顺序阅读

1. **先读**: `00-project-overview.md` + `01-architecture.md` — 了解项目全貌及技术栈
2. **再读**: `12-module-index.md` — 快速定位要改的文件
3. **按需精读**: 根据任务选择对应文件

### 维护规则

1. **每次有重大变更时更新对应文档**
2. 新加业务模块时，在 `00-project-overview.md` 更新概述，在 `12-module-index.md` 添加索引
3. 新增 API 时，在 `02-api-map.md` 添加
4. 新增数据库表时，在 `03-db-schema.md` 添加
5. 新增业务逻辑时，在 `04-business-flow.md` 补充
6. 发现已知问题时，在 `10-known-issues.md` 记录
7. 标记任何**不确定**的内容为 `⚠️ [不确定]` 或 `??`
8. 前后端依赖变更时同步更新 `09-dependency-map.md`

### 文件格式要求

- 所有文件使用 Markdown
- 头部包含 `> 最后更新: YYYY-MM-DD`
- 代码块标注语言
- 表格必须对齐
- ⭐ 标记 FoxAI 定制业务模块

## ⚠️ AI 行为红线（必须遵守）

### 1. 金融精度红线

```java
// ❌ 禁止使用 Double/Float 计算金额
// ✅ 必须使用 BigDecimal
// 参考: AgentLevel.java 使用 BigDecimal 定义 packageAmount, franchiseFee 等
```

- 所有金额字段必须使用 `BigDecimal`（`decimal(14,2)`）
- 金额计算使用 `BigDecimal` 的 `setScale(2, RoundingMode.HALF_UP)`
- 前端展示使用 `parseMoney()` 函数（`¥` 前缀 + 千分位 + 2位小数）

### 2. 权限颗粒度红线

- **每一个 API 端点必须有 `@PreAuthorize` 注解**，禁止直接暴露未鉴权接口
- 权限标识格式: `模块:功能:操作`（如 `agent:agentLevel:add`, `agent:agentLevel:list`）
- 导出/导入功能必须有独立权限标识（如 `:export`, `:import`）
- 前端按钮使用 `v-hasPermi` 指令控制显示

### 3. 防重复/防刷红线

- 涉及金额的提交接口必须使用 `@RepeatSubmit` 或 `SameUrlDataInterceptor` 防重
- 登录接口使用验证码 + RateLimiter 双重防刷
- **响应必须是幂等的**：相同请求重复提交应返回相同结果而不产生副作用

### 4. 数据安全红线

- 密码使用 BCrypt 加密存储（`BCryptPasswordEncoder`），禁止明文
- Token 密钥禁止硬编码，必须使用配置文件
- 数据库密码禁止提交到 Git
- 用户敏感信息（手机/邮箱）使用 `@Sensitive` 脱敏
- **`application.yml` 和 `application-druid.yml` 中的密码不能让 AI 输出**

### 5. 先设计后代码红线

**所有新增业务模块必须遵循以下顺序：**

```
1. 数据库设计 → 建表 SQL（遵循 sys_xxx / xxx 命名）
2. 后端 Domain 实体（extends BaseEntity）
3. Mapper 接口 + Mapper XML
4. Service 接口 + 实现
5. Controller（@PreAuthorize + @Log）
6. 前端 API 封装
7. 前端页面组件
8. 菜单/权限数据 SQL
```

### 6. 代码生成器优先



### 7. 日志红线

- 所有增删改操作必须使用 `@Log` 注解记录操作日志
- 登录成功/失败使用 `AsyncFactory.recordLogininfor()` 记录
- 异常务必包含足够的上下文信息

### 8. 错误处理红线

- 业务异常使用 `ServiceException`（统一返回 500 + 错误消息）
- 禁止在 Controller 中 try-catch 吞异常
- 资源不存在场景返回具体错误，不可返回空数据让前端猜
- 全局异常由 `GlobalExceptionHandler` 统一处理

### 9. Git 红线

- 禁止提交 `.idea/`、`target/`、`node_modules/`、`*.log`
- 禁止提交包含真实密码/密钥的配置文件
- 提交前检查 `git diff`，确保不包含敏感信息

### 10. 数据库变更红线

- 已有表的 DDL 修改必须提交通过独立的变更 SQL 文件（`sql/YYYY-MM-DD-xxx.sql`）
- 禁止修改 `ry_20260417.sql` 主文件
- 新增表需同时提供 DDL + 菜单权限 SQL + 字典数据 SQL

## 项目角色

| 角色 | 值 |
|------|-----|
| 框架 | RuoYi-Vue v3.9.2（前后端分离版） |
| 前端版本 | Vue 3 + Element Plus + Pinia + Vite 6（已从 Vue 2 升级） |
| 当前状态 | 已实现 `ResellerLevel`(等级) + `ResellerRegister`(注册) + `ResellerPayment/Account`(付款与账户) |
| 后续工作 | 代理商管理、Token 分配、订单系统、结算对账等 |
| ⚠️ 数据库名 | `foxai-reseller`（不是 `ry-vue`） |
| ⚠️ JDK | **21**（不是 17） |
| ⚠️ reseller_level 建表 SQL | `sql/2026-06-07-reseller-level.sql` |
| ⚠️ 分销商注册+审批 SQL | `sql/2026-06-07-reseller-register.sql` |
| ⚠️ 分销商付款+账户 SQL | `sql/2026-06-07-reseller-payment.sql` |

## 变更记录

> 详见 `changelog/` 目录下的变更日志文件。



# 🔧 FoxAI-Reseller 构建工具链规范

> 最后更新: 2026-06-07 | 版本: v2.1 | 强制执行: ✅ AI必须遵守

---

## 📋 核心原则

**AI 助手在生成、修改任何代码后，必须自动执行编译/构建命令并修复所有错误，确保代码可构建。**

> ⚠️ 违反此规范将导致代码无法集成，AI 必须自行修复所有构建问题后才能继续任务。

---

## 📦 构建工具链概览

| 模块 | 技术栈 | 构建工具 | 配置文件位置 | 最低版本要求 |
|------|--------|---------|-------------|-------------|
| **前端** | Vue 3 + Vite + TypeScript | **pnpm** (强制) | `ruoyi-ui/package.json` | pnpm ≥ 8.0 |
| **后端** | Java 21 + Spring Boot | Maven (强制) | `pom.xml` | Maven ≥ 3.8, JDK 21 |

---

## 🚨 前端构建强制规范

### 正确使用方式

```bash
# ✅ 标准构建流程
cd ruoyi-ui

# 1. 安装依赖（使用锁文件确保版本一致）
pnpm install --frozen-lockfile

# 2. 类型检查（TypeScript 严格模式）
pnpm run type-check 2>/dev/null || npx vue-tsc --noEmit

# 3. 生产构建
pnpm build

# 可选：开发环境构建（更快）
pnpm build:dev
禁止使用的方式
bash
# ❌ 以下命令均被禁止
npm install          # 禁止：会生成 package-lock.json
npm run build        # 禁止：破坏项目统一性
yarn install         # 禁止：不是项目指定的包管理器
yarn build           # 禁止：不兼容
pnpm install         # ❌ 缺少 --frozen-lockfile 参数
pnpm 优势说明
特性	pnpm	npm/yarn	本项目要求
磁盘空间	节省（内容寻址存储）	占用大	✅ 优势
安装速度	快（硬链接）	较慢	✅ 优势
依赖隔离	严格（幽灵依赖禁止）	宽松	✅ 必须
Lock 文件	pnpm-lock.yaml	package-lock.json	✅ 已配置
🔧 后端构建规范
标准构建命令
bash
# ✅ 标准编译（每次修改 Java 文件后执行）
mvn clean compile -DskipTests -q

# ✅ 依赖变更后的处理
mvn dependency:resolve -q

# ✅ 完整构建（包含测试，CI/CD 使用）
mvn clean package -DskipTests

# ✅ 仅编译修改的文件（快速验证）
mvn compile -q -Dmaven.compiler.useIncrementalCompilation=true



