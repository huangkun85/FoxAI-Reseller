-- ============================================================
-- 分销商订单制迁移 — 幂等 DDL 脚本
-- ============================================================

-- 1. reseller_payment 表结构变更：新增字段、移除旧字段
ALTER TABLE `reseller_payment`
    ADD COLUMN `order_no` varchar(50) DEFAULT NULL COMMENT '订单号' AFTER `id`,
    ADD COLUMN `contact_name` varchar(50) DEFAULT NULL COMMENT '联系人姓名' AFTER `id_number`,
    ADD COLUMN `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系人电话' AFTER `contact_name`,
    ADD COLUMN `contact_email` varchar(100) DEFAULT NULL COMMENT '联系人邮箱' AFTER `contact_phone`;

-- id_card_image 和 transfer_image 列已存在于旧表，此处不做 DROP 确保已存在表不受影响
-- 新应用代码不再读写这两个字段

-- 2. 订单号唯一索引（首次执行会创建，重复执行跳过即可）
ALTER TABLE `reseller_payment` ADD UNIQUE INDEX `uk_order_no` (`order_no`);

-- 3. 确保菜单名称为"套餐购买"
UPDATE `sys_menu` SET `menu_name` = '套餐购买' WHERE `menu_id` = 2161;

-- 4. 状态字典值说明（仅供参考，无 DDL 变更）
-- sys_payment_status: 0=待付款, 1=已付款, 2=已驳回
