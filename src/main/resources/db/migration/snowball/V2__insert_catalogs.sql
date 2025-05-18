--
-- Dumping data for table `catalog_type_currency`
--

INSERT INTO `catalog_type_currency` VALUES (1,'MXN'),(2,'USD');

--
-- Dumping data for table `catalog_type_movement`
--

INSERT INTO `catalog_type_movement` VALUES (1,'Buy'),(2,'Sell'),(3,'Buy Market Secundary'),(4,'Buy Market Secundary Cancelled');

--
-- Dumping data for table `catalog_type_transaction`
--

INSERT INTO `catalog_type_transaction` VALUES (1,'Deposit'),(2,'Withdraw'),(3,'Dividend');

--
-- Dumping data for table `catalog_broker`
--

INSERT INTO `catalog_broker` VALUES (1,'Snow Ball',1,'SB'),(2,'GBM Homebroker',1,'GBM');

--
-- Dumping data for table `broker_account`
--

INSERT INTO `broker_account` VALUES (1,'Snow Ball Main',1,-1,'2021-01-01 00:00:00');