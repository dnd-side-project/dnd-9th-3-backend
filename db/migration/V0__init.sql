CREATE TABLE IF NOT EXISTS `member` (
  `member_id` varchar(255) NOT NULL,
  `emails` varchar(255) DEFAULT NULL,
  `interests` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `oauth_id` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `oauth` (
  `oauth_id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`oauth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `record` (
  `record_number` varchar(255) NOT NULL,
  `place_latitude` double DEFAULT NULL,
  `place_longitude` double DEFAULT NULL,
  `place_title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `record_date` datetime(6) DEFAULT NULL,
  `record_score` bigint(20) DEFAULT NULL,
  `recorder_id` varchar(255) DEFAULT NULL,
  `recorder_name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`record_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `image` (
  `image_id` varchar(255) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `upload_time` datetime(6) DEFAULT NULL,
  `record_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  FOREIGN KEY (`record_number`) REFERENCES `record` (`record_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;