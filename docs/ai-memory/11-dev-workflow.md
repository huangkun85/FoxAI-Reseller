# 开发工作流

> 最后更新: 2026-05-29

## 本地开发环境

### 前置条件

| 工具 | 版本要求 | 说明 |
|------|----------|------|
| JDK | 17+ | OpenJDK 或 Oracle JDK |
| Maven | 3.6+ | 项目构建 |
| Node.js | 8.9+ | 前端构建 (建议 16+) |
| npm | 8+ | 前端包管理 (推荐 pnpm) |
| MySQL | 8.0+ | 数据库 |
| Redis | 6.x+ | 缓存 |
| IDE | - | IntelliJ IDEA / VS Code |

### 数据库初始化

```bash
# 1. 创建数据库
mysql -u root -p
CREATE DATABASE IF NOT EXISTS `ry-vue` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. 导入 SQL
mysql -u root -p ry-vue < sql/ry_20260417.sql
mysql -u root -p ry-vue < sql/quartz.sql
```

### 后端启动

```bash
# 1. 修改数据库连接 (application-druid.yml)
#    spring.datasource.druid.master.password = 你的密码

# 2. 启动 Redis

# 3. 启动后端 (项目根目录)
mvn spring-boot:run

# 或打包后运行
mvn clean package -Dmaven.test.skip=true
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### 前端启动

```bash
cd ruoyi-ui

# 安装依赖 (Vite 6 + ESM)
npm install

# 开发模式启动 (Vite 热更新，极速)
npm run dev

# 生产构建 (Vite build)
npm run build:prod

# 预览构建结果
npm run preview
```

## 开发流程

### 新增业务模块的步骤

```
1. 数据库设计
   └── 创建业务表（遵循 sys_xxx 命名）
   └── 插入菜单数据（sys_menu）

2. 后端开发
   ├── domain/SysXxx.java (extends BaseEntity)
   ├── mapper/SysXxxMapper.java + SysXxxMapper.xml
   ├── service/ISysXxxService.java
   ├── service/impl/SysXxxServiceImpl.java
   ├── controller/SysXxxController.java
   └── 注册权限 @PreAuthorize + @Log 注解

3. 前端开发
   ├── api/xxx/xxx.js (接口封装)
   └── views/xxx/index.vue (页面组件)

4. 配置
   └── 分配菜单权限给角色
```

### 快捷方式：代码生成器

推荐使用 RuoYi 内置代码生成器：

```
1. 先创建数据库表
2. 登录系统 → 系统工具 → 代码生成
3. 导入表 → 编辑配置（包名/模块名/功能名）
4. 生成代码（下载 ZIP）
5. 解压后将文件复制到对应目录
6. 执行生成 SQL（菜单权限）
```

## 调试技巧

### 后端调试

- 日志级别: `com.ruoyi: debug`（`application.yml` 配置）
- MyBatis SQL: 控制台直接打印（debug 级别）
- Druid 监控: `http://localhost:8080/druid/` (ruoyi/123456)
- API 文档: `http://localhost:8080/swagger-ui.html`
- 使用 `AjaxResult.error("xxx")` 快速返回错误信息

### 前端调试

- Vue DevTools: 检查组件状态和 Vuex
- 请求拦截: `axios.defaults.headers` 注入自定义 header
- 字典调试: `this.getDicts('sys_xxx_xxx').then(console.log)`
- 权限检查: `this.checkPermi(['system:user:edit'])` 返回 true/false

## Git 规范建议

> ⚠️ 当前项目尚未初始化 Git 仓库。

### 分支策略建议

```
main        → 生产分支
├── dev     → 开发分支
├── feature/* → 功能分支
├── bugfix/*  → 修复分支
└── release/* → 发布分支
```

### Commit 信息格式建议

```
<type>(<scope>): <subject>

type: feat / fix / docs / style / refactor / perf / test / chore
scope: 模块名 (system / framework / common / ui / ...)
```

## 部署流程

### 传统部署

```bash
# 后端打包
mvn clean package -Dmaven.test.skip=true

# 前端打包 (Vite build → dist/)
cd ruoyi-ui && npm run build:prod

# 部署
# 后端 jar → java -jar ruoyi-admin.jar
# 前端 dist/ → Nginx 静态目录
```

### Nginx 配置参考

```nginx
server {
    listen 80;
    server_name example.com;

    # 前端静态资源
    root /path/to/ruoyi-ui/dist;
    index index.html;

    # 后端 API 代理
    location /prod-api/ {
        proxy_pass http://localhost:8080/;
    }

    # 静态文件访问
    location /profile/ {
        proxy_pass http://localhost:8080/profile/;
    }
}
```
