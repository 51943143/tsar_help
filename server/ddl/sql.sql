CREATE TABLE `t_server_metric` (
  `dest_url` varchar(200) COLLATE utf8_bin NOT NULL,
  `time` varchar(50) COLLATE utf8_bin NOT NULL,
  `cpu` varchar(20) default  NULL,
  `mem` varchar(20) default  NULL,
  `tcp_retran` varchar(20) default  NULL,
  `traffic_in` varchar(20) default  NULL,
  `traffic_out` varchar(20) default  NULL,
  `io_sda` varchar(20) default  NULL,
  `load_rate` varchar(20) default  NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`dest_url`,`time`)
)