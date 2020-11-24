-- CREATE DATABASE 'emall';
-- use 'emall';

CREATE TABLE `USER` (
	`user_id` char(19) COMMENT '用户id' PRIMARY KEY,
	`user_name` VARCHAR(15) COMMENT '用户姓名',
	`sex` ENUM ('0',
		'1') COMMENT '性别，0-男, 1-女',
	`birth_day` DATE COMMENT '生日',
	`email` VARCHAR(100) COMMENT '邮箱',
	`mobile` VARCHAR(20) COMMENT '电话号码',
	`enable_flag` ENUM ('Y',
		'N') DEFAULT 'Y' COMMENT '是否可用',
	`created_by` VARCHAR(19) COMMENT '创建人',
	`created_date` DATE COMMENT '创建时间',
	`last_updated_by` VARCHAR(19) COMMENT '最后更新人',
	`last_updated_date` DATE COMMENT '最后更新时间',
	`vip_birth_day` DATE COMMENT '加入会员时间')
COMMENT '用户表' engine = InnoDB DEFAULT charset = 'utf8mb4';

CREATE TABLE GOOD (
	good_id VARCHAR(19) PRIMARY KEY COMMENT '商品id',
	good_name VARCHAR(200) NOT NULL COMMENT '商品名称',
	brand VARCHAR(100) NOT NULL COMMENT '商品品牌',
	barcode VARCHAR(40) NOT NULL COMMENT '商品条码',
	quantity INT NOT NULL COMMENT '商品数量',
	warehouse_id VARCHAR (19) NOT NULL COMMENT '库房位置',
	enable_flag ENUM ('Y','N') DEFAULT 'Y' COMMENT '是否可用',
	created_by VARCHAR (19) COMMENT '创建人',
	created_date DATE COMMENT '创建时间',
	last_updated_by VARCHAR (19) COMMENT '最后更新人',
	last_updated_date DATE COMMENT '最后更新时间')
COMMENT '商品' engine = InnoDB DEFAULT charset = 'utf8mb4';

CREATE TABLE WAREHOUSE (
	id CHAR(19) NOT NULL PRIMARY KEY COMMENT '库房id',
	name VARCHAR(100) COMMENT '库房名称',
	NO VARCHAR(100) COMMENT '库房编码',
	address VARCHAR(100) COMMENT '库房地址',
	enable_flag ENUM ('Y',
		'N') DEFAULT 'Y' COMMENT '是否可用',
	created_by VARCHAR (19) COMMENT '创建人',
	created_date DATE COMMENT '创建时间',
	last_updated_by VARCHAR (19) COMMENT '最后更新人',
	last_updated_date DATE COMMENT '最后更新时间')
COMMENT '库房' engine = InnoDB DEFAULT charset = 'utf8mb4';

CREATE TABLE ORDERS (
	id CHAR(19) PRIMARY KEY COMMENT '订单id',
	amount DOUBLE NOT NULL COMMENT '订单总金额',
	paied ENUM ('Y','N') DEFAULT 'N' COMMENT '是否支付',
	enable_flag ENUM ('Y','N') DEFAULT 'Y' COMMENT '是否可用',
	created_by VARCHAR (19) COMMENT '创建人',
	created_date DATE COMMENT '创建时间',
	last_updated_by VARCHAR (19) COMMENT '最后更新人',
	last_updated_date DATE COMMENT '最后更新时间')
COMMENT '订单' engine = InnoDB DEFAULT charset = 'utf8mb4';

CREATE TABLE ORDER_ITEM (
	id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'id',
	order_id VARCHAR(19) NOT NULL COMMENT '关联的订单id',
	good_id VARCHAR(19) NOT NULL COMMENT '货物id',
	num int DEFAULT 1 COMMENT '数量',
	unit VARCHAR(20) COMMENT '单位',
	unit_price DOUBLE COMMENT '单价',
	discount DOUBLE COMMENT '折扣',
	discount_mount DOUBLE COMMENT '折扣钱数',
	total_price DOUBLE COMMENT '商品总价',
	created_by VARCHAR (19) COMMENT '创建人',
	created_date DATE COMMENT '创建时间',
	last_updated_by VARCHAR (19) COMMENT '最后更新人',
	last_updated_date DATE COMMENT '最后更新时间')
COMMENT '订单条目' engine = InnoDB DEFAULT charset = 'utf8mb4';