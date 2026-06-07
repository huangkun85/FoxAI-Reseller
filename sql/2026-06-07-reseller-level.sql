-- ============================================================
-- 分销商等级体系 — 幂等 DDL + DML
-- 可反复执行，不会重复插入数据
-- ============================================================

-- ----------------------------
-- 1. 删除旧表 agent_level（若存在）
-- ----------------------------
DROP TABLE IF EXISTS `agent_level`;

-- ----------------------------
-- 2. 创建新表 reseller_level
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reseller_level` (
  `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `level_code`      varchar(50)  NOT NULL COMMENT '等级代码(bronze/silver/gold/diamond)',
  `level_name`      varchar(50)  DEFAULT NULL COMMENT '等级名称',
  `package_amount`  decimal(14,2) DEFAULT NULL COMMENT '套餐金额',
  `franchise_fee`   decimal(14,2) DEFAULT NULL COMMENT '加盟费',
  `token_quota`     decimal(14,2) DEFAULT NULL COMMENT 'Token额度',
  `bonus_amount`    decimal(14,2) DEFAULT NULL COMMENT '赠送额度',
  `commission_rate` decimal(14,2) DEFAULT NULL COMMENT '下级返佣比例(%)',
  `referral_reward` decimal(14,2) DEFAULT NULL COMMENT '推荐奖励金额',
  `sort_order`      int(4)       DEFAULT 0 COMMENT '显示顺序',
  `status`          char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `remark`          varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by`       varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
  `update_by`       varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_level_code` (`level_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销商等级配置表';

-- ----------------------------
-- 3. 字典类型：reseller_level
-- ----------------------------
INSERT IGNORE INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (200, '分销商等级', 'reseller_level', '0', 'admin', sysdate(), '', null, '分销商等级代码');

-- ----------------------------
-- 4. 字典数据：bronze/silver/gold/diamond
-- ----------------------------
INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (300, 1, '铜牌代理', 'bronze', 'reseller_level', '', 'info', 'N', '0', 'admin', sysdate(), '', null, NULL);

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (301, 2, '银牌代理', 'silver', 'reseller_level', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, NULL);

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (302, 3, '金牌代理', 'gold', 'reseller_level', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, NULL);

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (303, 4, '钻石代理', 'diamond', 'reseller_level', '', 'danger', 'N', '0', 'admin', sysdate(), '', null, NULL);

-- ----------------------------
-- 5. 菜单：分销商管理 → 分销商等级体系
-- ----------------------------
-- 父菜单：分销商管理
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2100, '分销商管理', 0, 5, 'reseller', NULL, NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'peoples', 'admin', sysdate(), '', NULL, '分销商管理目录');

-- 子菜单：分销商等级体系
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2101, '分销商等级体系', 2100, 1, 'level', 'system/resellerLevel/index', NULL, NULL, 1, 0, 'C', '0', '0', 'resellerLevel:list', 'tree-table', 'admin', sysdate(), '', NULL, '分销商等级体系菜单');

-- 按钮权限：查询
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2102, '查询', 2101, 1, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'resellerLevel:query', '#', 'admin', sysdate(), '', NULL, NULL);

-- 按钮权限：新增
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2103, '新增', 2101, 2, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'resellerLevel:add', '#', 'admin', sysdate(), '', NULL, NULL);

-- 按钮权限：修改
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2104, '修改', 2101, 3, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'resellerLevel:edit', '#', 'admin', sysdate(), '', NULL, NULL);

-- 按钮权限：删除
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2105, '删除', 2101, 4, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'resellerLevel:remove', '#', 'admin', sysdate(), '', NULL, NULL);

-- 按钮权限：导出
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2106, '导出', 2101, 5, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'resellerLevel:export', '#', 'admin', sysdate(), '', NULL, NULL);

-- ----------------------------
-- 6. 种子数据：reseller_level 表
-- ----------------------------
INSERT IGNORE INTO `reseller_level` (`id`, `level_code`, `level_name`, `package_amount`, `franchise_fee`, `token_quota`, `bonus_amount`, `commission_rate`, `referral_reward`, `sort_order`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1, 'bronze', '铜牌代理', 10000.00, 5000.00, 5000.00, 1000.00, 5.00, 500.00, 1, '0', NULL, 'admin', sysdate(), NULL, NULL);

INSERT IGNORE INTO `reseller_level` (`id`, `level_code`, `level_name`, `package_amount`, `franchise_fee`, `token_quota`, `bonus_amount`, `commission_rate`, `referral_reward`, `sort_order`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2, 'silver', '银牌代理', 30000.00, 10000.00, 20000.00, 2000.00, 8.00, 1500.00, 2, '0', NULL, 'admin', sysdate(), NULL, NULL);

INSERT IGNORE INTO `reseller_level` (`id`, `level_code`, `level_name`, `package_amount`, `franchise_fee`, `token_quota`, `bonus_amount`, `commission_rate`, `referral_reward`, `sort_order`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (3, 'gold', '金牌代理', 100000.00, 30000.00, 70000.00, 3000.00, 12.00, 5000.00, 3, '0', NULL, 'admin', sysdate(), NULL, NULL);

INSERT IGNORE INTO `reseller_level` (`id`, `level_code`, `level_name`, `package_amount`, `franchise_fee`, `token_quota`, `bonus_amount`, `commission_rate`, `referral_reward`, `sort_order`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (4, 'diamond', '钻石代理', 300000.00, 50000.00, 250000.00, 5000.00, 15.00, 15000.00, 4, '0', NULL, 'admin', sysdate(), NULL, NULL);
