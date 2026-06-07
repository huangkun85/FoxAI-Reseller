# API 映射

> 最后更新: 2026-05-29

## 分销商模块

### 分销商注册 (`/reseller`)

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| POST | `/reseller/register` | ❌ 公开（验证码） | 分销商注册（用户名+邮箱+密码） |

### 分销商审核 (`/reseller/approval`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/reseller/approval/pending-list` | `reseller:approval:list` | 待审核分销商列表 |
| POST | `/reseller/approval/approve/{userId}` | `reseller:approval:approve` | 审核通过 |
| POST | `/reseller/approval/reject/{userId}` | `reseller:approval:reject` | 驳回 |

## 公开接口（无需认证）

| 方法 | 路径 | 控制器 | 说明 |
|------|------|--------|------|
| POST | `/login` | SysLoginController | 用户登录 |
| POST | `/register` | SysRegisterController | 用户注册（需配置开启） |
| GET | `/captchaImage` | CaptchaController | 获取验证码图片 |
| POST | `/logout` | SysLoginController | 退出登录 |
| POST | `/unlockscreen` | SysLoginController | 解锁屏幕 |
| GET | `/getInfo` | SysIndexController | 获取用户信息与菜单 |
| GET | `/getRouters` | SysIndexController | 获取路由信息 |

## 系统管理模块

### 用户管理 (`/system/user`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/user/list` | `system:user:list` | 分页查询用户列表 |
| GET | `/system/user/{userId}` | `system:user:query` | 查询用户详情 |
| POST | `/system/user` | `system:user:add` | 新增用户 |
| PUT | `/system/user` | `system:user:edit` | 修改用户 |
| DELETE | `/system/user/{userIds}` | `system:user:remove` | 删除用户 |
| PUT | `/system/user/resetPwd` | `system:user:resetPwd` | 重置密码 |
| PUT | `/system/user/changeStatus` | `system:user:edit` | 修改用户状态 |
| GET | `/system/user/authRole/{userId}` | `system:user:edit` | 查询用户角色分配 |
| PUT | `/system/user/authRole` | `system:user:edit` | 分配用户角色 |
| GET | `/system/user/deptTree` | `system:user:list` | 部门树数据 |
| POST | `/system/user/export` | `system:user:export` | 导出用户列表 |
| POST | `/system/user/importData` | `system:user:import` | 导入用户 |
| GET | `/system/user/importTemplate` | `system:user:import` | 导入模板 |

### 角色管理 (`/system/role`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/role/list` | `system:role:list` | 分页查询角色列表 |
| GET | `/system/role/{roleId}` | `system:role:query` | 查询角色详情 |
| POST | `/system/role` | `system:role:add` | 新增角色 |
| PUT | `/system/role` | `system:role:edit` | 修改角色 |
| DELETE | `/system/role/{roleIds}` | `system:role:remove` | 删除角色 |
| PUT | `/system/role/dataScope` | `system:role:edit` | 数据权限修改 |
| PUT | `/system/role/changeStatus` | `system:role:edit` | 修改角色状态 |
| GET | `/system/role/optionselect` | `system:role:query` | 获取角色选择列表 |
| GET | `/system/role/authUser/{roleId}` | `system:role:edit` | 已分配用户列表 |
| PUT | `/system/role/authUser/selectAll` | `system:role:edit` | 批量选择用户 |
| PUT | `/system/role/authUser/cancelAll` | `system:role:edit` | 批量取消授权 |
| POST | `/system/role/export` | `system:role:export` | 导出角色 |

### 菜单管理 (`/system/menu`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/menu/list` | `system:menu:list` | 查询菜单列表 |
| GET | `/system/menu/{menuId}` | `system:menu:query` | 查询菜单详情 |
| POST | `/system/menu` | `system:menu:add` | 新增菜单 |
| PUT | `/system/menu` | `system:menu:edit` | 修改菜单 |
| DELETE | `/system/menu/{menuId}` | `system:menu:remove` | 删除菜单 |
| GET | `/system/menu/treeselect` | `system:menu:query` | 菜单树 |
| GET | `/system/menu/roleMenuTreeselect/{roleId}` | `system:menu:query` | 角色菜单树 |

### 部门管理 (`/system/dept`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/dept/list` | `system:dept:list` | 查询部门列表 |
| GET | `/system/dept/{deptId}` | `system:dept:query` | 查询部门详情 |
| POST | `/system/dept` | `system:dept:add` | 新增部门 |
| PUT | `/system/dept` | `system:dept:edit` | 修改部门 |
| DELETE | `/system/dept/{deptId}` | `system:dept:remove` | 删除部门 |
| GET | `/system/dept/treeselect` | `system:dept:query` | 部门树 |

### 岗位管理 (`/system/post`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/post/list` | `system:post:list` | 分页查询岗位 |
| GET | `/system/post/{postId}` | `system:post:query` | 查询岗位详情 |
| POST | `/system/post` | `system:post:add` | 新增岗位 |
| PUT | `/system/post` | `system:post:edit` | 修改岗位 |
| DELETE | `/system/post/{postIds}` | `system:post:remove` | 删除岗位 |
| GET | `/system/post/optionselect` | `system:post:query` | 获取岗位选择列表 |
| POST | `/system/post/export` | `system:post:export` | 导出岗位 |

### 字典管理 (`/system/dict`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/dict/type/list` | `system:dict:list` | 字典类型列表 |
| GET | `/system/dict/type/{dictId}` | `system:dict:query` | 字典类型详情 |
| POST | `/system/dict/type` | `system:dict:add` | 新增字典类型 |
| PUT | `/system/dict/type` | `system:dict:edit` | 修改字典类型 |
| DELETE | `/system/dict/type/{dictIds}` | `system:dict:remove` | 删除字典类型 |
| GET | `/system/dict/data/list` | `system:dict:list` | 字典数据列表 |
| GET | `/system/dict/data/{dictCode}` | `system:dict:query` | 字典数据详情 |
| POST | `/system/dict/data` | `system:dict:add` | 新增字典数据 |
| PUT | `/system/dict/data` | `system:dict:edit` | 修改字典数据 |
| DELETE | `/system/dict/data/{dictCodes}` | `system:dict:remove` | 删除字典数据 |
| GET | `/system/dict/type/optionselect` | - | 字典类型下拉（公开） |
| GET | `/system/dict/data/type/{dictType}` | - | 按类型查字典数据（公开） |
| POST | `/system/dict/type/export` | `system:dict:export` | 导出字典类型 |
| POST | `/system/dict/data/export` | `system:dict:export` | 导出字典数据 |

### 参数配置 (`/system/config`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/config/list` | `system:config:list` | 分页查询参数 |
| GET | `/system/config/{configId}` | `system:config:query` | 参数详情 |
| POST | `/system/config` | `system:config:add` | 新增参数 |
| PUT | `/system/config` | `system:config:edit` | 修改参数 |
| DELETE | `/system/config/{configIds}` | `system:config:remove` | 删除参数 |
| GET | `/system/config/configKey/{configKey}` | - | 按 Key 查参数值 |
| POST | `/system/config/export` | `system:config:export` | 导出参数 |

### 通知公告 (`/system/notice`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/notice/list` | `system:notice:list` | 分页查询公告 |
| GET | `/system/notice/{noticeId}` | `system:notice:query` | 公告详情 |
| POST | `/system/notice` | `system:notice:add` | 新增公告 |
| PUT | `/system/notice` | `system:notice:edit` | 修改公告 |
| DELETE | `/system/notice/{noticeIds}` | `system:notice:remove` | 删除公告 |

## 监控模块

### 在线用户 (`/monitor/online`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/monitor/online/list` | `monitor:online:list` | 分页查询在线用户 |
| DELETE | `/monitor/online/{tokenId}` | `monitor:online:forceLogout` | 强退用户 |

### 定时任务 (`/monitor/job`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/monitor/job/list` | `monitor:job:list` | 分页查询任务 |
| GET | `/monitor/job/{jobId}` | `monitor:job:query` | 任务详情 |
| POST | `/monitor/job` | `monitor:job:add` | 新增任务 |
| PUT | `/monitor/job` | `monitor:job:edit` | 修改任务 |
| DELETE | `/monitor/job/{jobIds}` | `monitor:job:remove` | 删除任务 |
| PUT | `/monitor/job/changeStatus` | `monitor:job:changeStatus` | 修改任务状态 |
| POST | `/monitor/job/run` | `monitor:job:edit` | 立即执行任务 |
| GET | `/monitor/job/log/list` | `monitor:job:list` | 任务日志列表 |
| GET | `/monitor/job/log/{jobLogId}` | `monitor:job:list` | 任务日志详情 |
| POST | `/monitor/job/export` | `monitor:job:export` | 导出任务 |

### 数据监控 (`/monitor/druid`)

- Druid Monitor 页面，需登录后可访问

### 服务监控 (`/monitor/server`)

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/monitor/server` | 登录可访问 | 获取 CPU、内存、JVM、磁盘信息 |

### 缓存监控 (`/monitor/cache`)

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | `/monitor/cache` | 登录可访问 | 获取 Redis 信息 |
| GET | `/monitor/cache/getNames` | 登录可访问 | 缓存名称列表 |
| GET | `/monitor/cache/getKeys/{cacheName}` | 登录可访问 | 缓存键列表 |
| GET | `/monitor/cache/getValue/{cacheName}/{cacheKey}` | 登录可访问 | 缓存值详情 |
| DELETE | `/monitor/cache/clearCacheName/{cacheName}` | 登录可访问 | 清除名称缓存 |
| DELETE | `/monitor/cache/clearCacheKey/{cacheKey}` | 登录可访问 | 清除键缓存 |
| DELETE | `/monitor/cache/clearCacheAll` | 登录可访问 | 清除所有缓存 |

### 操作日志 (`/monitor/operlog`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/monitor/operlog/list` | `monitor:operlog:list` | 分页查询操作日志 |
| DELETE | `/monitor/operlog/{operIds}` | `monitor:operlog:remove` | 删除操作日志 |
| DELETE | `/monitor/operlog/clean` | `monitor:operlog:remove` | 清空操作日志 |
| POST | `/monitor/operlog/export` | `monitor:operlog:export` | 导出操作日志 |

### 登录日志 (`/monitor/logininfor`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/monitor/logininfor/list` | `monitor:logininfor:list` | 分页查询登录日志 |
| DELETE | `/monitor/logininfor/{infoIds}` | `monitor:logininfor:remove` | 删除登录日志 |
| DELETE | `/monitor/logininfor/clean` | `monitor:logininfor:remove` | 清空登录日志 |
| POST | `/monitor/logininfor/unlock` | `monitor:logininfor:unlock` | 账户解锁 |
| POST | `/monitor/logininfor/export` | `monitor:logininfor:export` | 导出登录日志 |

## 系统工具模块

### 代码生成 (`/tool/gen`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/tool/gen/list` | `tool:gen:list` | 分页查询业务表 |
| GET | `/tool/gen/{tableId}` | `tool:gen:query` | 业务表详情 |
| PUT | `/tool/gen` | `tool:gen:edit` | 修改业务配置 |
| DELETE | `/tool/gen/{tableIds}` | `tool:gen:remove` | 删除业务表 |
| POST | `/tool/gen/importTable` | `tool:gen:import` | 导入表 |
| GET | `/tool/gen/preview/{tableId}` | `tool:gen:preview` | 预览代码 |
| POST | `/tool/gen/genCode/{tableName}` | `tool:gen:code` | 生成代码(下载) |
| POST | `/tool/gen/syncTable/{tableName}` | `tool:gen:code` | 同步数据库 |
| GET | `/tool/gen/dbTableList` | `tool:gen:list` | 数据库表列表 |

### 表单构建 (`/tool/build`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/tool/build` | 表单构建页面（前端路由） |

### 分销商等级体系 (`/system/resellerLevel`)

| 方法 | 路径 | 权限标识 | 说明 |
|------|------|----------|------|
| GET | `/system/resellerLevel/list` | `resellerLevel:list` | 分页查询等级列表 |
| GET | `/system/resellerLevel/{id}` | `resellerLevel:query` | 查询等级详情 |
| POST | `/system/resellerLevel` | `resellerLevel:add` | 新增等级 |
| PUT | `/system/resellerLevel` | `resellerLevel:edit` | 修改等级 |
| DELETE | `/system/resellerLevel/{ids}` | `resellerLevel:remove` | 删除等级（支持批量） |
| POST | `/system/resellerLevel/export` | `resellerLevel:export` | 导出等级列表 |

## 前端 API 文件映射

| API 文件 | 路径 | 对应后端 |
|----------|------|----------|
| `src/api/login.js` | 登录/注册/获取信息/验证码 | SysLoginController, SysRegisterController |
| `src/api/menu.js` | 菜单树 | SysMenuController |
| `src/api/system/user.js` | 用户管理 | SysUserController |
| `src/api/system/role.js` | 角色管理 | SysRoleController |
| `src/api/system/menu.js` | 菜单管理 | SysMenuController |
| `src/api/system/dept.js` | 部门管理 | SysDeptController |
| `src/api/system/post.js` | 岗位管理 | SysPostController |
| `src/api/system/dict/type.js` | 字典类型 | SysDictTypeController |
| `src/api/system/dict/data.js` | 字典数据 | SysDictDataController |
| `src/api/system/config.js` | 参数配置 | SysConfigController |
| `src/api/system/resellerLevel.js` | 分销商等级 | ResellerLevelController |
| `src/api/reseller/register.js` | 分销商注册 | ResellerRegisterController |
| `src/api/reseller/approval.js` | 分销商审核 | ResellerApprovalController |
| `src/api/reseller/level.js` | 等级选择 | ResellerLevelController |
| `src/api/system/notice.js` | 通知公告 | SysNoticeController |
| `src/api/monitor/online.js` | 在线用户 | SysUserOnlineController |
| `src/api/monitor/job.js` | 定时任务 | SysJobController |
| `src/api/monitor/jobLog.js` | 任务日志 | SysJobLogController |
| `src/api/monitor/operlog.js` | 操作日志 | SysOperlogController |
| `src/api/monitor/logininfor.js` | 登录日志 | SysLogininforController |
| `src/api/monitor/server.js` | 服务监控 | ServerController |
| `src/api/monitor/cache.js` | 缓存监控 | CacheController |
| `src/api/tool/gen.js` | 代码生成 | GenController |
