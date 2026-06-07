-- ============================================================
-- 分销商付款申请 + 账户体系 — 幂等 DDL + DML
-- ============================================================

-- ----------------------------
-- 1. 付款申请表 reseller_payment
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reseller_payment` (
  `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`           bigint(20)   NOT NULL COMMENT '分销商用户ID',
  `level_id`          bigint(20)   NOT NULL COMMENT '选择的等级ID',
  `reseller_type`     char(1)      DEFAULT '0' COMMENT '类型(0个人 1企业)',
  `id_number`         varchar(50)  DEFAULT NULL COMMENT '证件号',
  `id_card_image`     varchar(500) DEFAULT NULL COMMENT '证件图片路径',
  `transfer_image`    varchar(500) DEFAULT NULL COMMENT '转账凭证图片路径',
  `amount`            decimal(14,2) DEFAULT NULL COMMENT '付款总金额',
  `status`            char(1)      DEFAULT '0' COMMENT '状态(0待审核 1已通过 2已驳回)',
  `remark`            varchar(500) DEFAULT NULL COMMENT '审核备注',
  `create_by`         varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       datetime     DEFAULT NULL COMMENT '创建时间',
  `update_by`         varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       datetime     DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销商付款申请表';

-- ----------------------------
-- 2. 分销商账户表 reseller_account
-- ----------------------------
CREATE TABLE IF NOT EXISTS `reseller_account` (
  `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`           bigint(20)   NOT NULL COMMENT '分销商用户ID',
  `level_id`          bigint(20)   NOT NULL COMMENT '等级ID',
  `token_quota`       decimal(14,2) DEFAULT 0.00 COMMENT 'Token总额度',
  `token_used`        decimal(14,2) DEFAULT 0.00 COMMENT '已用额度',
  `bonus_amount`      decimal(14,2) DEFAULT 0.00 COMMENT '赠送额度',
  `api_key`           varchar(200) DEFAULT NULL COMMENT 'API Key',
  `api_secret`        varchar(200) DEFAULT NULL COMMENT 'API Secret',
  `endpoint_url`      varchar(200) DEFAULT NULL COMMENT 'API地址',
  `status`            char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `remark`            varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by`         varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       datetime     DEFAULT NULL COMMENT '创建时间',
  `update_by`         varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       datetime     DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销商账户表';

-- ----------------------------
-- 3. 对公账户信息 sys_config
-- ----------------------------
INSERT IGNORE INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (100, '对公账户信息', 'sys.reseller.bank.account',
        '{\"accountName\":\"湖南灵狐人工智能科技有限公司\",\"accountNumber\":\"731913431010003\",\"bankName\":\"招商银行股份有限公司长沙广电支行\"}',
        'Y', 'admin', sysdate(), '', null, '分销商对公转账账户信息');

-- ----------------------------
-- 4. 字典：付款状态 sys_payment_status
-- ----------------------------
INSERT IGNORE INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (201, '付款状态', 'sys_payment_status', '0', 'admin', sysdate(), '', null, '付款申请状态');

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (310, 1, '待审核', '0', 'sys_payment_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, NULL);

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (311, 2, '已通过', '1', 'sys_payment_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, NULL);

INSERT IGNORE INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (312, 3, '已驳回', '2', 'sys_payment_status', '', 'danger', 'N', '0', 'admin', sysdate(), '', null, NULL);

-- ----------------------------
-- 5. 一级菜单：Dashboard / 付款业务（含子菜单）
-- ----------------------------
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2150, 'Dashboard', 0, 6, 'reseller/dashboard', 'reseller/dashboard/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:dashboard', 'dashboard', 'admin', sysdate(), '', NULL, '分销商仪表盘');

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2160, '付款业务', 0, 7, 'reseller/payment', NULL, NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'money', 'admin', sysdate(), '', NULL, '付款业务目录');

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2161, '套餐购买', 2160, 1, 'apply', 'reseller/payment/apply/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:payment:apply', 'shopping', 'admin', sysdate(), '', NULL, '分销商套餐购买');

-- 幂等更新：确保菜单名称为"套餐购买"
UPDATE `sys_menu` SET `menu_name` = '套餐购买' WHERE `menu_id` = 2161;

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2162, '支付查询', 2160, 2, 'history', 'reseller/payment/history/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:payment:history', 'date', 'admin', sysdate(), '', NULL, '分销商支付查询');

-- ----------------------------
-- 6. 二级菜单（管理员用，放在分销商管理下）
-- ----------------------------
-- 付款审核
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2130, '付款审核', 2100, 6, 'payment/approval', 'reseller/payment-approval/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:payment:approve', 'peoples', 'admin', sysdate(), '', NULL, '管理员付款审核');

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2131, '查询', 2130, 1, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:payment:approve:list', '#', 'admin', sysdate(), '', NULL, NULL);

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2132, '通过', 2130, 2, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:payment:approve:pass', '#', 'admin', sysdate(), '', NULL, NULL);

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2133, '驳回', 2130, 3, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:payment:approve:reject', '#', 'admin', sysdate(), '', NULL, NULL);

-- 账户管理
INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2140, '账户管理', 2100, 7, 'account', 'reseller/account/index', NULL, NULL, 1, 0, 'C', '0', '0', 'reseller:account:list', 'edit', 'admin', sysdate(), '', NULL, '管理员管理分销商账户');

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2141, '查询', 2140, 1, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:account:list', '#', 'admin', sysdate(), '', NULL, NULL);

INSERT IGNORE INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2142, '编辑', 2140, 2, NULL, NULL, NULL, NULL, 1, 0, 'F', '0', '0', 'reseller:account:edit', '#', 'admin', sysdate(), '', NULL, NULL);

-- ----------------------------
-- 7. 角色菜单分配：admin 拥有全部新菜单权限；common 拥有分销商端菜单
-- ----------------------------
INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(1, 2150), (1, 2160), (1, 2161), (1, 2162),
(1, 2130), (1, 2131), (1, 2132), (1, 2133),
(1, 2140), (1, 2141), (1, 2142);

INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(2, 2150), (2, 2160), (2, 2161), (2, 2162);

-- 分销商可查看等级列表（用于套餐购买的等级选择）
INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(2, 2101), (2, 2102);
