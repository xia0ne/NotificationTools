CREATE TABLE `tasks` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `belong_id` int(11) NOT NULL,
                         `task_key` varchar(255) NOT NULL,
                         `crontab_rule` varchar(255) NOT NULL,
                         `start_time` datetime NOT NULL,
                         `end_time` datetime NOT NULL,
                         `message` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `idx_unique_combination` (`task_key`,`crontab_rule`,`message`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4