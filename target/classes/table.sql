CREATE TABLE `tasks` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `belong_id` int(11) DEFAULT NULL,
                         `task_key` varchar(255) DEFAULT NULL,
                         `crontab_rule` varchar(255) DEFAULT NULL,
                         `start_time` datetime DEFAULT NULL,
                         `end_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 |
