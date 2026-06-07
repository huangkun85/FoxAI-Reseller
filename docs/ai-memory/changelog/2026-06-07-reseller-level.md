# Changelog: 分销商等级体系重构

> 日期: 2026-06-07
> 类型: bugfix / refactor

## 变更摘要

将旧的 `AgentLevel`（代理商等级）模块全面重构为 `ResellerLevel`（分销商等级）模块，涵盖表结构、Java 代码、前端页面、权限体系、字典和菜单的全面替换。

## 修改文件清单

### 🔴 删除（原 agent_level 文件）

| 文件 | 路径 |
|------|------|
| Domain | `ruoyi-system/.../domain/AgentLevel.java` |
| Mapper | `ruoyi-system/.../mapper/AgentLevelMapper.java` |
| Mapper XML | `ruoyi-system/.../resources/mapper/system/AgentLevelMapper.xml` |
| Service 接口 | `ruoyi-system/.../service/IAgentLevelService.java` |
| Service 实现 | `ruoyi-system/.../service/impl/AgentLevelServiceImpl.java` |
| Controller | `ruoyi-admin/.../controller/system/AgentLevelController.java` |
| 前端 API | `ruoyi-ui/src/api/system/agentLevel.js` |
| 前端页面 | `ruoyi-ui/src/views/system/agentLevel/`（整目录） |

### 🟢 新增（reseller_level 文件）

| 文件 | 路径 |
|------|------|
| Domain | `ruoyi-system/.../domain/ResellerLevel.java` |
| Mapper | `ruoyi-system/.../mapper/ResellerLevelMapper.java` |
| Mapper XML | `ruoyi-system/.../resources/mapper/system/ResellerLevelMapper.xml` |
| Service 接口 | `ruoyi-system/.../service/IResellerLevelService.java` |
| Service 实现 | `ruoyi-system/.../service/impl/ResellerLevelServiceImpl.java` |
| Controller | `ruoyi-admin/.../controller/system/ResellerLevelController.java` |
| 前端 API | `ruoyi-ui/src/api/system/resellerLevel.js` |
| 前端页面 | `ruoyi-ui/src/views/system/resellerLevel/index.vue` |
| SQL 脚本 | `sql/2026-06-07-reseller-level.sql` |

### 📝 更新（AI 记忆文件）

| 文件 | 变更内容 |
|------|----------|
| `00-project-overview.md` | 模块描述从 AgentLevel 改为 ResellerLevel |
| `01-architecture.md` | Controller/Service/Domain/Mapper 路径全部替换 |
| `02-api-map.md` | API 端点从 agentLevel 替换为 resellerLevel |
| `03-db-schema.md` | 表定义从 agent_level 替换为 reseller_level |
| `12-module-index.md` | 所有类名/文件路径替换 |
| `claude.md` | 更新项目状态说明 |

## API 变更

| 旧路径 | 新路径 | 旧权限 | 新权限 |
|--------|--------|--------|--------|
| `/system/agentLevel/list` | `/system/resellerLevel/list` | `agent:agentLevel:list` | `resellerLevel:list` |
| `/system/agentLevel/{id}` | `/system/resellerLevel/{id}` | `agent:agentLevel:query` | `resellerLevel:query` |
| `/system/agentLevel` (POST) | `/system/resellerLevel` (POST) | `agent:agentLevel:add` | `resellerLevel:add` |
| `/system/agentLevel` (PUT) | `/system/resellerLevel` (PUT) | `agent:agentLevel:edit` | `resellerLevel:edit` |
| `/system/agentLevel/{ids}` | `/system/resellerLevel/{ids}` | `agent:agentLevel:remove` | `resellerLevel:remove` |
| `/system/agentLevel/export` | `/system/resellerLevel/export` | `agent:agentLevel:export` | `resellerLevel:export` |

## 数据库变更

| 项目 | 旧值 | 新值 |
|------|------|------|
| 表名 | `agent_level` | `reseller_level` |
| 列名 | `token_fee` | `token_quota` |
| 字典类型 | `agent_level_type` | `reseller_level` |
| 菜单父级 | 无（独立） | `分销商管理` (menu_id=2100) |

## 字典数据

类型: `reseller_level`，包含 bronze/silver/gold/diamond 四个等级。

## 菜单结构

```
分销商管理 (2100, parent=0)
  └─ 分销商等级体系 (2101, parent=2100)
       ├─ 查询 (2102, perms=resellerLevel:query)
       ├─ 新增 (2103, perms=resellerLevel:add)
       ├─ 修改 (2104, perms=resellerLevel:edit)
       ├─ 删除 (2105, perms=resellerLevel:remove)
       └─ 导出 (2106, perms=resellerLevel:export)
```

## 上线注意事项

1. 首次部署需执行 `sql/2026-06-07-reseller-level.sql`，会 **DROP** 旧的 `agent_level` 表
2. 旧的 `agent_level_type` 字典需手动清理（SQL 未包含删除逻辑）
3. 旧的 `agent:agentLevel:*` 权限标识不再存在，相关角色需重新分配权限
4. 菜单 ID 从 2100 开始，确保不与现有菜单冲突
