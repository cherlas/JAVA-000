-- 原始数据库
CREATE TABLE user_s (`id` varchar(20),`name` varchar(25),'identity_id' varchar(13), PRIMARY KEY (id));

-- 分表
-- db1
CREATE TABLE user_s_0 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));
CREATE TABLE user_s_1 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));
-- ...
CREATE TABLE user_s_15 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));

-- db2
CREATE TABLE user_s_0 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));
CREATE TABLE user_s_1 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));
-- ...
CREATE TABLE user_s_15 (`id` varchar(20),`name` varchar(25), 'identity_id' varchar(13), PRIMARY KEY (id));