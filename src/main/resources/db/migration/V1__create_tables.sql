--
-- Table structure for table `config_control`
--

CREATE TABLE `config_control` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(250) DEFAULT NULL,
  `config_value` varchar(250) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `user`
--

CREATE TABLE `user_data` (
  `id` int(11) NOT NULL,
  `password` varchar(90) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `is_active` BIT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `config_auth`
--

CREATE TABLE `config_auth` (
  `id_user` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `date_login` datetime NOT NULL,
  `date_refresh` datetime NOT NULL,
  PRIMARY KEY (`id_user`),
  CONSTRAINT `fk_config_auth_id_user` FOREIGN KEY (`id_user`) REFERENCES `user_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `broker_data_snowball`
--

CREATE TABLE `broker_data_snowball` (
  `id` varchar(50) NOT NULL,
  `date_transaction` datetime NOT NULL,
  `previous_balance` NUMERIC(15,4) DEFAULT NULL,
  `actual_balance` NUMERIC(15,4) DEFAULT NULL,
  `movement_description` varchar(250) DEFAULT NULL,
  `company` varchar(250) DEFAULT NULL,
  `total_issues` int(11) DEFAULT NULL,
  `balance_entry` NUMERIC(15,4) DEFAULT NULL,
  `balance_exit` NUMERIC(15,4) DEFAULT NULL,
  `type_payment` varchar(250) DEFAULT NULL,
  `status` varchar(250) DEFAULT NULL,
  `reference` varchar(250) DEFAULT NULL,
  `track_table` varchar(50) DEFAULT NULL,
  `track_table_id` int(11) DEFAULT NULL,
  `status_movement` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_issue`
--

CREATE TABLE `catalog_issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `initials` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `description_snowball` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_type_currency`
--

CREATE TABLE `catalog_type_currency` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_type_movement`
--

CREATE TABLE `catalog_type_movement` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_type_transaction`
--

CREATE TABLE `catalog_type_transaction` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `catalog_broker`
--

CREATE TABLE `catalog_broker` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_type_currency` int(11) DEFAULT NULL,
  `acronym` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_catalog_broker_type_currency` (`id_type_currency`),
  CONSTRAINT `FK_catalog_broker_type_currency` FOREIGN KEY (`id_type_currency`) REFERENCES `catalog_type_currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `broker_account`
--

CREATE TABLE `broker_account` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_broker` int(11) NOT NULL,
  `cut_day` int(11) DEFAULT NULL,
  `date_creation` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_broker_account_broker` (`id_broker`),
  CONSTRAINT `FK_broker_account_broker` FOREIGN KEY (`id_broker`) REFERENCES `catalog_broker` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `movements_issue`
--

CREATE TABLE `movements_issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_issue` int(11) NOT NULL,
  `id_broker_account` int(11) NOT NULL,
  `quantity_issues` int(11) NOT NULL,
  `price_issue_unity` NUMERIC(15,4) NOT NULL,
  `price_total` NUMERIC(15,4) NOT NULL,
  `comision_total` NUMERIC(15,4) DEFAULT NULL,
  `id_type_movement` int(11) NOT NULL,
  `date_transaction` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movements_issue_issue` (`id_issue`),
  KEY `FK_movements_issue_broker_account` (`id_broker_account`),
  KEY `FK_movements_issue_type_movement` (`id_type_movement`),
  CONSTRAINT `FK_movements_issue_broker_account` FOREIGN KEY (`id_broker_account`) REFERENCES `broker_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_movements_issue_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_movements_issue_type_movement` FOREIGN KEY (`id_type_movement`) REFERENCES `catalog_type_movement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `movements_money`
--

CREATE TABLE `movements_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_issue` int(11) DEFAULT NULL,
  `id_broker_account` int(11) NOT NULL,
  `amount` NUMERIC(15,4) NOT NULL,
  `amount_mxn` NUMERIC(15,4) NOT NULL,
  `id_type_transaction` int(11) NOT NULL,
  `date_transaction` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movements_money_issue` (`id_issue`),
  KEY `FK_movements_money_broker_account` (`id_broker_account`),
  KEY `FK_movements_money_type_transaction` (`id_type_transaction`),
  CONSTRAINT `FK_movements_money_broker_account` FOREIGN KEY (`id_broker_account`) REFERENCES `broker_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_movements_money_issue` FOREIGN KEY (`id_issue`) REFERENCES `catalog_issue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_movements_money_type_transaction` FOREIGN KEY (`id_type_transaction`) REFERENCES `catalog_type_transaction` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4;