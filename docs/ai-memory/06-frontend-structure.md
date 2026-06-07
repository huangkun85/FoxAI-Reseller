# 前端结构

> 最后更新: 2026-05-29

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.5.26 | 前端框架 (Composition API + `<script setup>`) |
| Element Plus | 2.13.1 | UI 组件库 (Element UI 的 Vue 3 版本) |
| Vite | 6.4.1 | 构建工具 (替代 Vue CLI/webpack) |
| Vue Router | 4.6.4 | 路由管理 |
| Pinia | 3.0.4 | 状态管理 (替代 Vuex) |
| Axios | 1.13.2 | HTTP 客户端 |
| ECharts | 5.6.0 | 图表 |
| Quill | 2.0.2 | 富文本编辑器 (via @vueup/vue-quill 1.2.0) |
| VueUse | 14.1.0 | Vue 3 Composition 工具库 (useWindowSize 等) |
| JSEncrypt | 3.3.2 | 前端密码加密 |

---

## 目录结构

```
ruoyi-ui/
├── index.html                        # 入口 HTML (Vite 模板语法: %VITE_APP_TITLE%)
├── vite.config.js                    # Vite 配置 (替代 vue.config.js)
├── vite/
│   └── plugins/
│       ├── index.js                  # 插件汇总
│       ├── auto-import.js            # unplugin-auto-import 自动导入
│       ├── compression.js            # vite-plugin-compression GZip 压缩
│       ├── svg-icon.js               # vite-plugin-svg-icons SVG 雪碧图
│       └── setup-extend.js           # unplugin-vue-setup-extend-plus 组件名扩展
├── src/
│   ├── api/                          # API 请求层
│   │   ├── login.js                  # 登录/注册/验证码
│   │   ├── menu.js                   # 菜单树
│   │   ├── monitor/                  # 监控模块 API
│   │   │   ├── cache.js
│   │   │   ├── job.js
│   │   │   ├── jobLog.js
│   │   │   ├── logininfor.js
│   │   │   ├── online.js
│   │   │   ├── operlog.js
│   │   │   └── server.js
│   │   ├── system/                   # 系统管理 API
│   │   │   ├── config.js
│   │   │   ├── dept.js
│   │   │   ├── dict/
│   │   │   │   ├── data.js
│   │   │   │   └── type.js
│   │   │   ├── menu.js
│   │   │   ├── notice.js
│   │   │   ├── post.js
│   │   │   ├── role.js
│   │   │   └── user.js
│   │   └── tool/
│   │       └── gen.js                # 代码生成 API
│   ├── assets/                       # 静态资源
│   │   ├── icons/                    # SVG 图标
│   │   ├── images/                   # 图片 (profile.jpg, login-background.jpg)
│   │   └── styles/                   # 全局样式 (SCSS)
│   ├── components/                   # 通用组件
│   │   ├── Breadcrumb/               # 面包屑
│   │   ├── Crontab/                  # Cron 表达式生成器 (10个子组件)
│   │   ├── DictTag/                  # 字典标签渲染
│   │   ├── Editor/                   # 富文本编辑器 (vue-quill)
│   │   ├── ExcelImportDialog/        # Excel 导入
│   │   ├── FileUpload/               # 文件上传
│   │   ├── Hamburger/                # 折叠按钮
│   │   ├── HeaderSearch/             # 头部搜索
│   │   ├── IconSelect/               # 图标选择器
│   │   ├── ImagePreview/             # 图片预览
│   │   ├── ImageUpload/              # 图片上传
│   │   ├── Pagination/               # 分页组件
│   │   ├── ParentView/               # 父视图
│   │   ├── RightToolbar/             # 右侧工具栏
│   │   ├── RuoYi/                    # 文档与 Git 链接
│   │   ├── Screenfull/               # 全屏
│   │   ├── SizeSelect/               # 大小选择
│   │   ├── SvgIcon/                  # SVG 图标组件
│   │   ├── TreePanel/                # 树形面板
│   │   └── iFrame/                   # iframe 嵌入
│   ├── directive/                    # 自定义指令
│   │   ├── index.js                  # 指令注册
│   │   ├── permission/
│   │   │   ├── hasPermi.js           # v-hasPermi 权限指令
│   │   │   └── hasRole.js            # v-hasRole 角色指令
│   │   └── common/
│   │       └── copyText.js           # v-copyText 复制指令
│   ├── layout/                       # 布局组件
│   │   ├── components/
│   │   │   ├── AppMain.vue
│   │   │   ├── Copyright/
│   │   │   ├── HeaderNotice/         # 头部通知
│   │   │   ├── IframeToggle/
│   │   │   ├── InnerLink/
│   │   │   ├── Navbar.vue
│   │   │   ├── Settings/
│   │   │   ├── Sidebar/              # 侧边栏 (递归多级)
│   │   │   ├── TagsView/             # 标签页
│   │   │   ├── TopBar/
│   │   │   └── TopNav/
│   │   ├── index.vue                 # 主布局
│   │   └── components/index.js       # 组件导出
│   ├── plugins/                      # 插件
│   │   ├── auth.js                   # 认证
│   │   ├── cache.js                  # 缓存 (sessionStorage + localStorage)
│   │   ├── download.js               # 下载
│   │   ├── index.js                  # 插件注册
│   │   ├── modal.js                  # 弹窗
│   │   └── tab.js                    # 标签页
│   ├── router/                       # 路由
│   │   └── index.js                  # 常量路由 + 动态路由 (createWebHistory)
│   ├── store/                        # Pinia 状态管理
│   │   ├── index.js                  # createPinia()
│   │   └── modules/
│   │       ├── app.js                # 应用状态 (侧边栏/设备)
│   │       ├── dict.js               # 字典
│   │       ├── lock.js               # 屏幕锁
│   │       ├── permission.js          # 权限 + 动态路由生成
│   │       ├── settings.js           # 设置 (主题/布局/标签)
│   │       ├── tagsView.js           # 标签页
│   │       └── user.js               # 用户
│   ├── utils/                        # 工具函数
│   │   ├── auth.js                   # Token Cookie 操作
│   │   ├── dict.js                   # 字典工具类 (替代原 utils/dict/ 目录)
│   │   ├── dynamicTitle.js           # 动态标题
│   │   ├── errorCode.js              # 错误码映射
│   │   ├── generator/                # 表单构建器代码生成 (config/css/drawingDefault/html/js/render)
│   │   ├── index.js                  # 通用工具
│   │   ├── jsencrypt.js              # RSA 加密
│   │   ├── passwordRule.js           # 密码规则
│   │   ├── permission.js             # 权限检查
│   │   ├── request.js                # Axios 封装 (含防重复提交/401处理/文件下载)
│   │   ├── ruoyi.js                  # RuoYi 工具函数 (parseTime/resetForm/handleTree 等)
│   │   ├── scroll-to.js              # 滚动
│   │   ├── theme.js                  # 主题切换
│   │   └── validate.js               # 表单验证 (isHttp/isEmpty/isPathMatch 等)
│   ├── views/                        # 页面视图
│   │   ├── dashboard/                # 首页仪表盘 (BarChart/LineChart/PieChart/PanelGroup 等)
│   │   ├── error/                    # 401/404
│   │   ├── login.vue                 # 登录页
│   │   ├── register.vue              # 注册页
│   │   ├── lock.vue                  # 锁屏页
│   │   ├── index.vue                 # 首页
│   │   ├── redirect/index.vue        # 重定向
│   │   ├── monitor/                  # 监控页 (cache/job/druid/logininfor/online/operlog/server)
│   │   ├── system/                   # 系统管理页 (user/role/menu/dept/post/dict/config/notice)
│   │   └── tool/                     # 工具页 (build/gen/swagger)
│   ├── App.vue                       # 根组件
│   ├── main.js                       # 入口 (createApp + 挂载全局方法/组件)
│   ├── permission.js                 # 路由守卫
│   └── settings.js                   # 项目设置 (theme/footer/logo)
├── package.json
├── babel.config.js
├── html/ie.html                      # IE 兼容提示
└── public/                           # 静态资源 (favicon.ico)
```

---

## 与旧版 (Vue 2) 的关键差异对照

| 维度 | 旧版 (升级前) | 新版 (升级后) |
|------|---------------|---------------|
| 框架 | Vue 2.6.12 (Options API) | Vue 3.5.26 (Composition API) |
| UI 库 | Element UI 2.15.14 | Element Plus 2.13.1 |
| 状态管理 | Vuex 3.6.0 (store.commit/dispatch) | Pinia 3.0.4 (defineStore, 直接 this.xxx) |
| 路由 | Vue Router 3.4.9 (new Router) | Vue Router 4.6.4 (createRouter) |
| 构建 | Vue CLI 4.4.6 (webpack) | Vite 6.4.1 (ESM) |
| 配置 | `vue.config.js` | `vite.config.js` |
| 环境变量 | `process.env.VUE_APP_*` | `import.meta.env.VITE_*` |
| 组件写法 | `export default { data() {} }` | `<script setup>` + `ref()`/`reactive()` |
| 全局属性 | `Vue.prototype.xxx` | `app.config.globalProperties.xxx` |
| SVG 图标 | `svg-sprite-loader` (webpack loader) | `vite-plugin-svg-icons` |
| GZip | `compression-webpack-plugin` | `vite-plugin-compression` |
| 自动导入 | 无 | `unplugin-auto-import` |
| 工具库 | 无 | `@vueuse/core` (useWindowSize, useEventListener 等) |
| 富文本 | quill 直接引入 | `@vueup/vue-quill` (Vue 3 封装) |
| 组件拖拽 | vuedraggable@2 | vuedraggable@4 (兼容 Vue 3) |
| 状态检查 | `vuex` 无内置持久化 | `pinia` + 自定义缓存插件 |
| 字典工具 | `utils/dict/` 目录 (5个文件) | `utils/dict.js` (单文件) |
| CSS 模块 | `@import` SCSS | `@use` SCSS (现代语法) |
| 指令 | dialog/drag, clipboard, permission | permission, copyText (精简) |
| 路由匹配 | `path: '*'` 通配 | `path: "/:pathMatch(.*)*"` |

---

## 路由架构

### 常量路由 (constantRoutes)
- `/redirect` — 重定向
- `/login` — 登录
- `/register` — 注册
- `/:pathMatch(.*)*` — 404 通配 (Vue Router 4 语法)
- `/401` — 无权限
- `/` → `/index` — 首页
- `/lock` — 锁屏
- `/user/profile/:activeTab?` — 个人中心 (可选参数)

### 动态路由 (dynamicRoutes)
权限控制的隐藏路由：
- `/system/user-auth/role/:userId` — 分配角色
- `/system/role-auth/user/:roleId` — 分配用户
- `/system/dict-data/index/:dictId` — 字典数据
- `/monitor/job-log/index/:jobId` — 调度日志
- `/tool/gen-edit/index/:tableId` — 代码生成编辑

### 动态菜单加载流程
```
permission.js 路由守卫
   ↓
有 Token → useUserStore().getInfo()
   ↓
usePermissionStore().generateRoutes() → 请求 /getRouters
   ↓
import.meta.glob('./../../views/**/*.vue') 匹配组件
   ↓
filterAsyncRouter() + filterDynamicRoutes()
   ↓
router.addRoute() 逐个注册动态路由
```

---

## Axios 请求封装 (`utils/request.js`)

| 特性 | 实现 |
|------|------|
| Token 注入 | 请求拦截器自动添加 `Authorization: Bearer` |
| 防重复提交 | 1 秒内相同请求 (sessionStorage 校验) |
| 响应拦截 | 401(重新登录) / 500 / 601 |
| 文件下载 | 通用 `download()` 函数 (ElLoading 加载状态) |
| Get 参数处理 | 自动将 params 拼接到 URL |
| 超时 | 10 秒 |
| 环境变量 | `import.meta.env.VITE_APP_BASE_API` |

---

## 认证拦截流程

```javascript
router.beforeEach(async (to, from) => {
  // 1. 白名单路径直接放行
  // 2. 有 Token → 已登录 → 跳转首页
  // 3. 无 Token → 跳转 /login?redirect=xxx
  // 4. 有 Token 但角色为空 → 拉取用户信息 + 生成动态路由
  // 5. 锁屏状态 → 强制跳转 /lock
})
```

---

## Pinia 状态管理

Pinia 是 Vue 3 推荐的官方状态管理库，替代 Vuex。

```javascript
// store/modules/user.js
const useUserStore = defineStore('user', {
  state: () => ({
    token: getToken(),
    roles: [],
    permissions: []
  }),
  actions: {
    login(userInfo) { ... },
    getInfo() { ... },
    logOut() { ... }
  }
})
export default useUserStore

// 使用方式 (在组件中)
const userStore = useUserStore()
userStore.login(formData)
```

Pinia vs Vuex 要点：
- 无 `mutations`，直接在 actions 中修改 state (`this.xxx = value`)
- 无 `getters` 命名空间概念，直接在 store 上访问
- 零配置 TypeScript 支持
- 通过 `defineStore` 定义，通过 `useXxxStore()` 使用

---

## 前端 Vite 配置要点

```javascript
// vite.config.js
export default defineConfig({
  base: '/',
  plugins: createVitePlugins(env, isBuild),
  resolve: { alias: { '@': './src' } },
  server: {
    port: 80,
    proxy: {
      '/dev-api': { target: 'http://localhost:8080', rewrite: p => p.replace(/^\/dev-api/, '') }
    }
  }
})
```

---

## 字典系统

```javascript
// utils/dict.js — 单文件实现 (原为 utils/dict/ 目录 5个文件)
export function useDict(...dictTypes) {
  const dicts = reactive({})
  // ... 调用 getDicts 加载字典数据
  return toRefs(dicts)
}

// 组件中使用
const { sys_user_sex } = useDict('sys_user_sex')
```

---

## 页面列表组件模式

所有 CRUD 页面遵循统一模式 (Composition API)：

```vue
<template>
  <!-- 搜索区域 -->
  <el-form :model="queryParams">
  </el-form>
  <!-- 操作按钮 -->
  <el-button v-hasPermi="['xxx:add']">新增</el-button>
  <!-- 表格 -->
  <el-table :data="dataList">
  </el-table>
  <!-- 分页 -->
  <pagination v-if="total > 0" />
</template>

<script setup>
import { getList } from "@/api/xxx/xxx"

const queryParams = ref({ pageNum: 1, pageSize: 10 })
const dataList = ref([])
const total = ref(0)

function getPageList() {
  getList(queryParams.value).then(res => {
    dataList.value = res.rows
    total.value = res.total
  })
}

onMounted(() => { getPageList() })
</script>
```

---

## 已移除 / 替换的旧组件

| 旧文件/组件 | 状态 | 替代 |
|-------------|------|------|
| `layout/mixin/ResizeHandler.js` | 移除 | `@vueuse/core` useWindowSize |
| `layout/components/Sidebar/Item.vue` | 移除 | 内联模板 |
| `layout/components/Sidebar/FixiOSBug.js` | 移除 | 不需要 |
| `directive/dialog/drag.js` | 移除 | Element Plus 内置支持 |
| `directive/dialog/dragHeight.js` | 移除 | Element Plus 内置支持 |
| `directive/dialog/dragWidth.js` | 移除 | Element Plus 内置支持 |
| `utils/dict/` 整个目录 (5文件) | 移除 | `utils/dict.js` 单文件 |
| `store/getters.js` | 移除 | Pinia 直接访问 |
| `components/PanThumb/index.vue` | 移除 | 不再使用 |
| `views/index_v1.vue` | 移除 | 统一为 index.vue |
| `.env` 相关文件 | 未找到 | 需确认环境变量配置方式 |
