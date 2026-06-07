# 开发规范

> 最后更新: 2026-05-29

## 命名规范

### 后端 Java

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | PascalCase | `SysUserServiceImpl` |
| 方法名 | camelCase | `selectUserList()` |
| 变量名 | camelCase | `userName`, `loginDate` |
| 常量 | UPPER_SNAKE_CASE | `Constants.LOGIN_SUCCESS` |
| 包名 | 全小写 | `com.ruoyi.system.service` |
| Mapper | `SysXxxMapper` | `SysUserMapper` |
| Service 接口 | `ISysXxxService` | `ISysUserService` |
| Service 实现 | `SysXxxServiceImpl` | `SysUserServiceImpl` |
| Controller | `SysXxxController` | `SysUserController` |
| Domain | `SysXxx` | `SysUser` |
| VO | `XxxVo` | `RouterVo`, `MetaVo` |
| DTO | (未使用独立 DTO，复用 Domain) | - |

### 前端 Vue/JS

| 类型 | 规范 | 示例 |
|------|------|------|
| 组件名 (.vue) | PascalCase | `Pagination/index.vue` |
| JS 文件 | camelCase | `user.js`, `login.js` |
| 变量/函数 | camelCase | `getInfo()`, `queryParams` |
| Pinia action | camelCase | `login()`, `getInfo()`, `logOut()` |
| 常量 | UPPER_SNAKE_CASE | `VITE_APP_BASE_API` (Vite) |
| 组件目录 | PascalCase | `Breadcrumb/`, `DictTag/` |
| Composition API | 使用 `<script setup>` | 所有 .vue 文件 |
| 响应式状态 | `ref()` / `reactive()` | `const count = ref(0)` |
| 计算属性 | `computed()` | `const total = computed(...)` |
| 生命周期 | `onMounted()`, `onUnmounted()` | 替代 Vue 2 的 `created/mounted` |
| 状态管理 | `defineStore('name', {})` | `const useXStore = defineStore(...)` |
| 环境变量 | `import.meta.env.VITE_*` | 替代 `process.env.VUE_APP_*` |

### 数据库

| 类型 | 规范 | 示例 |
|------|------|------|
| 表名 | `sys_xxx` / `gen_xxx` | `sys_user`, `gen_table` |
| 列名 | snake_case | `user_name`, `dept_id` |
| 主键 | `xxx_id` | `user_id`, `role_id` |
| 关联表 | `sys_a_b` | `sys_user_role` |
| 字段统一前缀 | `create_by`, `create_time`, `update_by`, `update_time`, `remark` | 所有表 |

## 编码约定

### Java

1. **Controller**: 继承 `BaseController`，方法使用 `@Log` + `@PreAuthorize`，返回 `AjaxResult`
2. **Service**: 接口 + 实现分离，实现类使用 `@Service`，事务使用 `@Transactional`
3. **Mapper**: 接口 + XML 分离，参数使用 `@Param` 或在 XML 中使用 `parameterType`
4. **实体类**: 继承 `BaseEntity`，支持 `@Excel` 注解导出
5. **分页**: 统一使用 `PageHelper.startPage()` + `TableDataInfo`
6. **枚举**: 统一放在 `com.ruoyi.common.enums` 包
7. **常量**: 统一放在 `com.ruoyi.common.constant` 包
8. **异常**: 业务异常抛 `ServiceException`，资源不存在抛具体异常
9. **注解**: 操作日志 `@Log`，权限 `@PreAuthorize`，数据权限 `@DataScope`，多数据源 `@DataSource`

### SQL 约定

1. 逻辑删除使用 `del_flag` 字段（0=存在，2=删除）
2. 状态字段使用 `status`（0=正常，1=停用）
3. 数据权限过滤基于部门 `dept_id`
4. 外键约束在应用层维护，数据库层不使用物理外键
5. 统一使用 `CHAR(1)` 而非 `TINYINT` 表示状态

## 前端代码风格

1. 组件名使用 PascalCase，目录名与组件名一致
2. 每个独立页面一个目录（`index.vue` 作为入口）
3. API 请求放在 `src/api/` 对应模块
4. 页面组件使用 `onMounted()` 调用 `getPageList()` 加载数据
5. 列表页 CRUD 操作后刷新列表
6. 字典数据通过 `useDict('sys_xxx_xxx')` 解构获取 (Composition API)
7. 权限控制使用 `v-hasPermi` / `v-hasRole` 指令
8. 导出功能通过 `proxy.download(...)` 或全局 `download()` 实现
9. **所有 .vue 文件使用 `<script setup>` 语法**，不再使用 Options API
10. 组件使用 `defineProps()` / `defineEmits()` 声明属性与事件
11. 全局方法通过 `app.config.globalProperties` 挂载，组件内通过 `getCurrentInstance().proxy.xxx` 访问
12. Pinia store 在组件内直接调用 (无需 `mapState` 或 `mapActions`)
13. 使用 `@vueuse/core` 工具函数替代自定义 `mixin`

## 代码生成模板

代码生成器的输出规范：

```
生成的代码结构:
├── controller/  SysXxxController.java
├── domain/      SysXxx.java (extends BaseEntity)
├── mapper/      SysXxxMapper.java + SysXxxMapper.xml
├── service/
│   ├── ISysXxxService.java
│   └── impl/SysXxxServiceImpl.java
└── 前端:
    ├── views/xxx/index.vue
    └── api/xxx/xxx.js
```

## RuoYi 历史版本记录

| 值 | 说明 |
|----|------|
| 框架版本 | v3.9.2 |
| Spring Boot | 4.x (JDK 17+) |
| 首次迁移 | Spring Boot 3.x → 4.x (2024-2025) |
| UI 版本 | Vue 3 + Element Plus (原 Vue 2 + Element UI 已升级) |

> ⚠️ 注意: 若依项目已官方迁移重心至 Vue 3 版本。此项目仍使用 Vue 2，升级需整体迁移。
