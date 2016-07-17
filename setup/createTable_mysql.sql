/*
 * RDB: MySQL
 */

create database chat;

CREATE USER 'user'@'localhost' IDENTIFIED BY 'useruser';

GRANT ALL PRIVILEGES ON chat.* TO 'user'@'localhost';

use chat;

CREATE TABLE `user_info` (
  `user_name` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(200) NOT NULL DEFAULT '',
  `authority` varchar(20) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `chat_log` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `sender` varchar(50) NOT NULL DEFAULT '',
  `receiver` varchar(50) DEFAULT '',
  `message` varchar(500) DEFAULT '',
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender` (`sender`),
  CONSTRAINT `chat_log_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `user_info` (`user_name`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

commit;
