CREATE TABLE `sy_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '名称',
  `is_active` bit(1) DEFAULT b'1' COMMENT '是否生效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 放在记录日志的数据库中
CREATE TABLE `op_log_meta_data` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `trace_id` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'request trace id',
  `op_id` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'op id',
  `biz_id` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'biz id',
  `biz_desc` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'biz desc',
  `status` smallint(6) DEFAULT NULL COMMENT 'log status: 0-init，1-processing，2-fail，3-complete',
  `meta_data` text COLLATE utf8_unicode_ci COMMENT 'meta data json',
  `biz_code` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'biz code',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;