# 依赖关系

> 最后更新: 2026-05-29

## Maven 依赖树

### ruoyi-common（核心公共依赖）

| 依赖 | 版本 | 用途 |
|------|------|------|
| spring-boot-starter-web | 4.0.3 | Web 基础 |
| spring-boot-starter-aop | 4.0.3 | AOP 切面 |
| spring-boot-starter-validation | 4.0.3 | 参数校验 |
| spring-boot-starter-data-redis | 4.0.3 | Redis 客户端 (Lettuce) |
| druid-spring-boot-4-starter | 1.2.28 | Druid 连接池 |
| mybatis-spring-boot-starter | 4.0.1 | MyBatis ORM |
| pagehelper-spring-boot-starter | 2.1.1 | 分页插件 |
| mysql-connector-j | (Spring Boot 管理) | MySQL 驱动 |
| fastjson2 | 2.0.61 | JSON 解析 |
| commons-io | 2.21.0 | IO 工具 |
| poi-ooxml | 4.1.2 | Excel 操作 |
| jjwt | 0.9.1 | JWT 生成/解析 |
| kaptcha | 2.3.3 | 验证码 |
| oshi-core | 6.10.0 | 系统监控 (CPU/内存) |
| springdoc-openapi-starter-webmvc-ui | 3.0.2 | API 文档 |
| velocity-engine-core | 2.3 | 代码生成模板引擎 |
| yauaa | 8.1.0 | UA 解析 |
| jaxb-api | 2.3.1 | XML 绑定 (JWT 依赖) |

### 前端依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| vue | 3.5.26 | 核心框架 (Composition API) |
| element-plus | 2.13.1 | UI 组件库 (Element UI 的 Vue 3 版) |
| @element-plus/icons-vue | 2.3.2 | Element Plus 图标库 |
| vue-router | 4.6.4 | 路由 |
| pinia | 3.0.4 | 状态管理 (替代 Vuex) |
| axios | 1.13.2 | HTTP 请求 |
| echarts | 5.6.0 | 图表 |
| @vueup/vue-quill | 1.2.0 | 富文本编辑器 (Vue 3 封装) |
| @vueuse/core | 14.1.0 | Vue 3 Composition 工具库 |
| js-cookie | 3.0.5 | Cookie 操作 |
| jsencrypt | 3.3.2 | RSA 加密 |
| clipboard | 2.0.11 | 剪贴板 |
| file-saver | 2.0.5 | 文件保存 |
| nprogress | 0.2.0 | 进度条 |
| fuse.js | 7.1.0 | 模糊搜索 (HeaderSearch) |
| vue-cropper | 1.1.1 | 图片裁剪 (Vue 3 兼容) |
| sortablejs | 1.15.7 | 拖拽库 (被 FileUpload/ImageUpload/editTable 直接引用) |
| vuedraggable | 4.1.0 | 拖拽排序 (Vue 3 版) |
| vite (dev) | 6.4.1 | 构建工具 (替代 Vue CLI) |
| @vitejs/plugin-vue (dev) | 5.2.4 | Vite Vue 3 插件 |
| sass-embedded (dev) | 1.97.2 | SCSS 编译 |
| unplugin-auto-import (dev) | 0.18.6 | 自动导入 Vue API |
| vite-plugin-compression (dev) | 0.5.1 | GZip 压缩 |
| vite-plugin-svg-icons (dev) | 2.0.1 | SVG 雪碧图 |
| unplugin-vue-setup-extend-plus (dev) | 1.0.1 | setup 语法扩展 |

## 模块间关键调用链

```
请求 → SysUserController
    → SysUserServiceImpl (业务逻辑)
        → SysUserMapper (数据库)
        → SysRoleMapper (查询用户角色)
        → SysDeptMapper (查询用户部门)
        → RedisCache (缓存处理)
    → LogAspect (操作日志)
    → DataScopeAspect (数据权限)
```

```
登录 → SysLoginController
    → SysLoginService.login()
        → AuthenticationManager.authenticate()
            → UserDetailsServiceImpl.loadUserByUsername()
                → SysUserMapper.selectUserByUserName()
                → SysRoleMapper.selectRolesByUserId()
        → TokenService.createToken()
            → RedisCache (存储 LoginUser)
            → JWT 签名 (jjwt)
        → AsyncFactory.recordLogininfor() (异步)
            → SysLogininforMapper.insertLogininfor()
```

## 关键外部依赖说明

| 依赖 | 强依赖? | 替代方案 |
|------|---------|----------|
| MySQL | 是 | 可切换为 PostgreSQL（需改 Mapper XML） |
| Redis | 是 | 会话/验证码/缓存都依赖 Redis |
| Druid | 是 | 可使用 HikariCP 替代 |
| JWT (jjwt 0.9.1) | 是 | jjwt 0.9.1 较旧，可升级到 0.12.x |
| FastJSON2 | 是 | 可替换为 Jackson |
| Kaptcha | 是 | 验证码生成 |

## Spring Boot Starter 依赖链

```
spring-boot-starter-web
  ├── spring-webmvc
  ├── spring-web
  ├── tomcat-embed-core
  ├── jackson-databind
  └── spring-boot-starter

spring-boot-starter-data-redis
  ├── lettuce-core (Netty 异步)
  └── spring-data-redis

mybatis-spring-boot-starter
  ├── mybatis
  └── mybatis-spring

pagehelper-spring-boot-starter
  └── pagehelper (MyBatis 分页拦截器)
```
