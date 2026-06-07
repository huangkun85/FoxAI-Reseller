-- ============================================================
-- 分销商注册体系 — 幂等 DDL + DML
-- 可反复执行
-- ============================================================

-- ----------------------------
-- 1. sys_user 新增 parent_id 字段
-- ----------------------------
ALTER TABLE `sys_user`
    ADD COLUMN `parent_id` bigint(20) DEFAULT 0 COMMENT '上级分销商ID(0=一级分销商)' AFTER `dept_id`;

-- ----------------------------
-- 2. 菜单：分销商管理 → 分销商审核
-- ----------------------------
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2110, '分销商审核', 2100, 2, 'approval', 'reseller/approval/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:approval:list', 'peoples', 'admin', sysdate(), '', NULL, '分销商审核菜单');

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2111, '查询', 2110, 1, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:approval:list', '#', 'admin', sysdate(), '', NULL, NULL);

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2112, '审核通过', 2110, 2, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:approval:approve', '#', 'admin', sysdate(), '', NULL, NULL);

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2113, '驳回', 2110, 3, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:approval:reject', '#', 'admin', sysdate(), '', NULL, NULL);
