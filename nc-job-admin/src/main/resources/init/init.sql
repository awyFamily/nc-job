DROP TABLE `nc_job_info`;
CREATE TABLE `nc_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(32)  NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group_id` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) DEFAULT NULL  COMMENT '任务执行CRON',
  `job_desc` varchar(255)  NULL DEFAULT '',
  `alarm_email` varchar(255) DEFAULT NULL DEFAULT '' COMMENT '报警邮件',
  `route_strategy` TINYINT(1) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_fail_retry_count` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行 2-手动执行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  `create_by` varchar(32) NULL DEFAULT ''	COMMENT '创建人',
  `update_by` varchar(32)  NULL DEFAULT '' COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `has_delete` tinyint(1) DEFAULT '0' COMMENT '逻辑删除 0-正常 1-删除',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE `nc_job_group`;
CREATE TABLE `nc_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT '执行器主键ID',
  `has_available` TINYINT(1) DEFAULT '1' COMMENT '是否可用， 0-不可以 1-可用',
  `name` varchar(64) NOT NULL COMMENT 'task名称和serverId组成唯一约束',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE `nc_job_log`;
CREATE TABLE `nc_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_info_id` int(11) NOT NULL COMMENT '执行器主键ID',
  `trigger_type` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '触发类型',
  `executor_status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '执行状态 0-执行中 1-调度失败 2-任务执行失败 3-任务执行成功',
  `retry_number` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '重试次数',
  `log_desc` varchar(128)  NULL DEFAULT '' COMMENT 'task名称和serverId组成唯一约束',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `nc_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `nc_job_lock` ( `lock_name`) VALUES ( 'schedule_lock');