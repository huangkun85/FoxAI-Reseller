# FoxAI-Reseller 项目上下文 (AI 绑定文件)

## 项目定位
基于 RuoYi-Vue v3.9.2（前后端分离版）二次开发的 Token 中转站分销系统。

项目概述
本项目包含 Java (后端) 和 Vue3 (前端) 两部分。

AI 助手在生成、修改任何代码后，必须自动执行编译/构建命令并修复所有错误，确保代码可构建。

构建命令配置
1. 后端 (Java)
构建工具：Maven (pom.xml 在项目根目录)
每次修改 Java 文件后，立即执行：
Bash
mvn compile -q
如果使用了 Lombok 或注解处理器，确保已安装插件并在 pom.xml 中配置。
注意：跳过测试 (-DskipTests) 可选，但编译阶段必须通过。
2. 前端 (Vue3 + TypeScript + Vite)
构建工具：npm / pnpm (package.json 在 frontend/ 或根目录)
每次修改 .vue, .ts, .js, .css 等前端文件后，立即执行：
Bash
npm run build
# 或者如果项目配置了类型检查命令：
npm run type-check   # 若有 vue-tsc --noEmit
# 或者同时执行类型检查和构建：
npm run build:type   # 若在 package.json 中定义了组合脚本
推荐：确保 package.json 中包含 "type-check": "vue-tsc --noEmit" 脚本，用于快速类型检查。
工作流程
每次代码生成/修改后，AI 必须：

识别修改的文件属于前端还是后端。
执行对应的构建命令。
捕获控制台输出。
如果构建失败：

AI 分析错误日志，提取关键错误信息。
自动定位到错误代码位置，修改代码。
重复执行构建，直到成功。
最多重试 3 次，如果仍失败则暂停并提示用户手动干预。
如果构建成功：

输出 “✅ 构建通过：Java/Vue3” 等确认信息。
继续后续开发任务。
特殊场景处理
同时修改前后端：先执行前端构建，再执行后端构建（或并行，如果支持）。
依赖变更：如果修改了 pom.xml 或 package.json，需先执行 mvn dependency:resolve 或 npm install，再执行构建。
代码格式化：如果构建工具包含 lint 检查（如 ESLint、Spotless），必须修复所有 lint 错误，确保代码风格一致。
无法自动修复：对于复杂错误（如设计问题、类型不兼容），应给出清晰错误说明和修复建议，然后等待用户确认。
