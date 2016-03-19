CREATE TABLE IF NOT EXISTS `employee` (
	`employee_id` varchar(36) NOT NULL,
	`first_name` varchar(100) NOT NULL,
	`middle_name` varchar(100),
	`last_name` varchar(100) NOT NULL,
	`gender` varchar(20),
	`title` varchar(100),
	`birth_dt` date,
	PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `address` (
	`address_id` varchar(36) NOT NULL,
	`fk_employee_address` varchar(36) NOT NULL,
	`primary_address` boolean NOT NULL,
	`line_1` varchar(200) NOT NULL,
	`line_2` varchar(200),
	`state` varchar(100),
	`postal_code` varchar(16) NOT NULL,
	`city` varchar(100) NOT NULL,
	`country` varchar(100) NOT NULL,
	`type` varchar(100),
	PRIMARY KEY (`address_id`),
	FOREIGN KEY (fk_employee_address) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `email` (
	`email_id` varchar(36) NOT NULL,
	`fk_employee_email` varchar(36) NOT NULL,
	`primary_email` boolean NOT NULL,
	`email` varchar(200) NOT NULL,
	PRIMARY KEY (`email_id`),
	FOREIGN KEY (fk_employee_email) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `telephone` (
	`telephone_id` varchar(36) NOT NULL,
	`fk_employee_telephone` varchar(36) NOT NULL,
	`primary_telephone` boolean NOT NULL,
	`country_code` varchar(4) NOT NULL,
	`area_code` varchar(5) NOT NULL,
	`number` varchar(20) NOT NULL,
	`extension` varchar(10),
	PRIMARY KEY (`telephone_id`),
	FOREIGN KEY (fk_employee_telephone) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `task` (
	`task_id` varchar(36) NOT NULL,
	`fk_employee_task` varchar(36) NOT NULL,
	`primary_task` boolean NOT NULL,
	`name` varchar(100) NOT NULL,
	`description` text,
	PRIMARY KEY (`task_id`),
	FOREIGN KEY (fk_employee_task) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `team` (
	`team_id` varchar(36) NOT NULL,
	`name` varchar(100) NOT NULL,
	`description` text,
	PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `employee_team` (
	`fk_employee_employee_team` varchar(36) NOT NULL,
	`fk_team_employee_team` varchar(36) NOT NULL,
	`primary_team` boolean NOT NULL,
	FOREIGN KEY (fk_employee_employee_team) REFERENCES employee(employee_id),
	FOREIGN KEY (fk_team_employee_team) REFERENCES team(team_id),
	PRIMARY KEY (`fk_employee_employee_team`, `fk_team_employee_team`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;