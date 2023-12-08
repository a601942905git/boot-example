# 1.sql脚本

## 1.1 读写分离
```sql
CREATE TABLE `single_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key1` varchar(100) DEFAULT NULL,
  `key2` int DEFAULT NULL,
  `key3` varchar(100) DEFAULT NULL,
  `key_part1` varchar(100) DEFAULT NULL,
  `key_part2` varchar(100) DEFAULT NULL,
  `key_part3` varchar(100) DEFAULT NULL,
  `common_field` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_key2` (`key2`),
  KEY `idx_key1` (`key1`),
  KEY `idx_key3` (`key3`),
  KEY `idx_key_part` (`key_part1`,`key_part2`,`key_part3`)
) ENGINE=InnoDB AUTO_INCREMENT=90002 DEFAULT CHARSET=utf8mb3
```

## 1.2 分片
```sql
create table t_order_0(
    order_id bigint not null primary key,
    user_id bigint not null,
    price decimal(15, 2) not null
);

create table t_order_1(
    order_id bigint not null primary key,
    user_id bigint not null,
    price decimal(15, 2) not null
);

create table t_order_item_0(
    order_item_id bigint not null primary key,
    order_id bigint not null,
    user_id bigint not null,
    product_name varchar(50) not null,
    product_price decimal(15, 2) not null,
    product_quantity int not null
);

create table t_order_item_1(
    order_item_id bigint not null primary key,
    order_id bigint not null,
    user_id bigint not null,
    product_name varchar(50) not null,
    product_price decimal(15, 2) not null,
    product_quantity int not null
);
```