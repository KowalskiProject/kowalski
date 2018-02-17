-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: kowalskidb
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  `project_project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `FK293388c3wn9eufn7m1mhii4nk` (`kowalskiuser_k_user_id`),
  KEY `FKkl5wciey44062wo324m1ohqtv` (`project_project_id`),
  CONSTRAINT `FK293388c3wn9eufn7m1mhii4nk` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`),
  CONSTRAINT `FKkl5wciey44062wo324m1ohqtv` FOREIGN KEY (`project_project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'Pra lá , depois divoltis porris, paradis. Atirei o pau no gatis, per gatis num morreus. Quem num gosta di mé, boa gentis num é. Admodum accumsan disputationi eu sit. Vide electram sadipscing et per.','2019-01-01 00:00:00','Depois divoltis porris, paradis','2018-01-01 00:00:00','New',NULL,1),(2,'Aenean aliquam molestie leo, vitae iaculis nisl. Aenean aliquam molestie leo, vitae iaculis nisl. Cevadis im ampola pa arma uma pindureta. Per aumento de cachacis, eu reclamis.','2019-01-01 00:00:00','Mauris nec dolor in eros commodo tempor','2018-01-01 00:00:00','New',NULL,1),(3,'Si u mundo tá muito paradis? Toma um mé que o mundo vai girarzis! Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Per aumento de cachacis, eu reclamis.','2019-01-01 00:00:00','Atirei o pau no gatis, per gatis num morreus','2018-01-01 00:00:00','New',NULL,1),(4,'Praesent malesuada urna nisi, quis volutpat erat hendrerit non. Nam vulputate dapibus. Interagi no mé, cursus quis, vehicula ac nisi.','2019-01-01 00:00:00','Diuretics paradis num copo é motivis de denguis','2018-01-01 00:00:00','New',NULL,1),(5,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Quem num gosta di mé, boa gentis num é','2018-01-01 00:00:00','New',NULL,1);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_activity_group`
--

DROP TABLE IF EXISTS `activity_activity_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_activity_group` (
  `activity_activity_id` int(11) NOT NULL,
  `activitygroup_activity_group_id` int(11) NOT NULL,
  PRIMARY KEY (`activity_activity_id`,`activitygroup_activity_group_id`),
  KEY `FKhvq4dyk6xo5gjwt15b7bgdgby` (`activitygroup_activity_group_id`),
  CONSTRAINT `FK9vmed4iyqb5yyh4pp96xox9gv` FOREIGN KEY (`activity_activity_id`) REFERENCES `activity` (`activity_id`),
  CONSTRAINT `FKhvq4dyk6xo5gjwt15b7bgdgby` FOREIGN KEY (`activitygroup_activity_group_id`) REFERENCES `activitygroup` (`activity_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_activity_group`
--

LOCK TABLES `activity_activity_group` WRITE;
/*!40000 ALTER TABLE `activity_activity_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_activity_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_kowalski_user`
--

DROP TABLE IF EXISTS `activity_kowalski_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_kowalski_user` (
  `activity_activity_id` int(11) NOT NULL,
  `kowalskiuser_k_user_id` int(11) NOT NULL,
  PRIMARY KEY (`activity_activity_id`,`kowalskiuser_k_user_id`),
  KEY `FKncdcao3a1kf8t0ei6dfa2cov9` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FKhcrv3km1jsu7tb9t9cuwh7lyt` FOREIGN KEY (`activity_activity_id`) REFERENCES `activity` (`activity_id`),
  CONSTRAINT `FKncdcao3a1kf8t0ei6dfa2cov9` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_kowalski_user`
--

LOCK TABLES `activity_kowalski_user` WRITE;
/*!40000 ALTER TABLE `activity_kowalski_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_kowalski_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_tasks`
--

DROP TABLE IF EXISTS `activity_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_tasks` (
  `activity_activity_id` int(11) NOT NULL,
  `tasks_task_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pnuurhiy9veur42vum15bym2s` (`tasks_task_id`),
  KEY `FKk9c13tgfkdvojy7o7bnjtoxmu` (`activity_activity_id`),
  CONSTRAINT `FKk9c13tgfkdvojy7o7bnjtoxmu` FOREIGN KEY (`activity_activity_id`) REFERENCES `activity` (`activity_id`),
  CONSTRAINT `FKqwcvdoqixjhg2nbwgmkkxlt9e` FOREIGN KEY (`tasks_task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_tasks`
--

LOCK TABLES `activity_tasks` WRITE;
/*!40000 ALTER TABLE `activity_tasks` DISABLE KEYS */;
INSERT INTO `activity_tasks` VALUES (1,1),(1,2),(1,3),(1,4),(1,5);
/*!40000 ALTER TABLE `activity_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activitygroup`
--

DROP TABLE IF EXISTS `activitygroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitygroup` (
  `activity_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`activity_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitygroup`
--

LOCK TABLES `activitygroup` WRITE;
/*!40000 ALTER TABLE `activitygroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitygroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kowalskiuser`
--

DROP TABLE IF EXISTS `kowalskiuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kowalskiuser` (
  `k_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`k_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kowalskiuser`
--

LOCK TABLES `kowalskiuser` WRITE;
/*!40000 ALTER TABLE `kowalskiuser` DISABLE KEYS */;
INSERT INTO `kowalskiuser` VALUES (1,'2018-02-17 17:22:52','didimoco@sonrisal.com','Didi Mocó','didimoco','didimoco'),(2,'2018-02-17 17:24:21','dedesantana@disfarca.com','Dedé Santana','dedesantana','dedesantana'),(3,'2018-02-17 17:24:50','mussum@branquinha.com','Mussum','mussumzis','mussumzis'),(4,'2018-02-17 17:25:12','zacarias@didiiiiiii.com','Zacarias','zacarias','zacarias');
/*!40000 ALTER TABLE `kowalskiuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FKq8iq8wxrf07xn4hyy8vc6sklf` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FKq8iq8wxrf07xn4hyy8vc6sklf` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'PROJ1','Mussum Ipsum, cacilds vidis litro abertis. Quem num gosta di mim que vai caçá sua turmis! Suco de cevadiss deixa as pessoas mais interessantis. Si num tem leite então bota uma pinga aí cumpadi! Per aumento de cachacis, eu reclamis.','2019-01-01 00:00:00','Mussum Ipsum, cacilds vidis litro abertis','2018-01-01 00:00:00',NULL),(2,'PROJ2','Copo furadis é disculpa de bebadis, arcu quam euismod magna. Si u mundo tá muito paradis? Toma um mé que o mundo vai girarzis! Suco de cevadiss deixa as pessoas mais interessantis. A ordem dos tratores não altera o pão duris.','2019-01-01 00:00:00','Copo furadis é disculpa de bebadis','2018-01-01 00:00:00',NULL),(3,'PROJ3','Pra lá , depois divoltis porris, paradis. Atirei o pau no gatis, per gatis num morreus. Quem num gosta di mé, boa gentis num é. Admodum accumsan disputationi eu sit. Vide electram sadipscing et per.','2019-01-01 00:00:00','Depois divoltis porris, paradis','2018-01-01 00:00:00',NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_activities`
--

DROP TABLE IF EXISTS `project_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_activities` (
  `project_project_id` int(11) NOT NULL,
  `activities_activity_id` int(11) NOT NULL,
  UNIQUE KEY `UK_95obedw40tc7ecek9edbruddf` (`activities_activity_id`),
  KEY `FK2c25x9vbnunxleehodai5djnu` (`project_project_id`),
  CONSTRAINT `FK2c25x9vbnunxleehodai5djnu` FOREIGN KEY (`project_project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `FK50wf19qssvlx9pjwsmubmc21f` FOREIGN KEY (`activities_activity_id`) REFERENCES `activity` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_activities`
--

LOCK TABLES `project_activities` WRITE;
/*!40000 ALTER TABLE `project_activities` DISABLE KEYS */;
INSERT INTO `project_activities` VALUES (1,1),(1,2),(1,3),(1,4),(1,5);
/*!40000 ALTER TABLE `project_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_kowalski_user`
--

DROP TABLE IF EXISTS `project_kowalski_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_kowalski_user` (
  `project_project_id` int(11) NOT NULL,
  `kowalskiuser_k_user_id` int(11) NOT NULL,
  PRIMARY KEY (`project_project_id`,`kowalskiuser_k_user_id`),
  KEY `FKhws03qmurcq4nvp2l6orbb4f6` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FKhws03qmurcq4nvp2l6orbb4f6` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`),
  CONSTRAINT `FKqjk9w67vsnlyofdp4f8qsyvok` FOREIGN KEY (`project_project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_kowalski_user`
--

LOCK TABLES `project_kowalski_user` WRITE;
/*!40000 ALTER TABLE `project_kowalski_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_kowalski_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  `activity_activity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FK58r0butye5knj6ey0epce67b5` (`kowalskiuser_k_user_id`),
  KEY `FK5y0qhxknesnsaf7nwtjwqwpxd` (`activity_activity_id`),
  CONSTRAINT `FK58r0butye5knj6ey0epce67b5` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`),
  CONSTRAINT `FK5y0qhxknesnsaf7nwtjwqwpxd` FOREIGN KEY (`activity_activity_id`) REFERENCES `activity` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Vivamus sit amet nibh non tellus tristique interdum','2018-01-01 00:00:00','New',NULL,1),(2,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Delegadis gente finis, bibendum egestas augue arcu ut est','2018-01-01 00:00:00','New',NULL,1),(3,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Per aumento de cachacis, eu reclamis','2018-01-01 00:00:00','New',NULL,1),(4,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Cevadis im ampola pa arma uma pindureta','2018-01-01 00:00:00','New',NULL,1),(5,'Detraxit consequat et quo num tendi nada. Aenean aliquam molestie leo, vitae iaculis nisl. Casamentiss faiz malandris se pirulitá.','2019-01-01 00:00:00','Mé faiz elementum girarzis, nisi eros vermeio','2018-01-01 00:00:00','New',NULL,1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timerecord`
--

DROP TABLE IF EXISTS `timerecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timerecord` (
  `tr_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `reported_day` date DEFAULT NULL,
  `reported_time` time DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `task_task_id` int(11) DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tr_id`),
  KEY `FKgpecbronb7vwevqp86hv8qumi` (`task_task_id`),
  KEY `FKfhb4aecc0t3ixuuggf8yhdonp` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FKfhb4aecc0t3ixuuggf8yhdonp` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`),
  CONSTRAINT `FKgpecbronb7vwevqp86hv8qumi` FOREIGN KEY (`task_task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timerecord`
--

LOCK TABLES `timerecord` WRITE;
/*!40000 ALTER TABLE `timerecord` DISABLE KEYS */;
INSERT INTO `timerecord` VALUES (1,'Viva Forevis aptent taciti sociosqu ad litora torquent. Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis. Casamentiss faiz malandris se pirulitá.','2018-02-17 17:38:10','2017-12-15','08:00:00',0,1,1),(2,'Vivamus sit amet nibh non tellus tristique interdum. Casamentiss faiz malandris se pirulitá. Nec orci ornare consequat.','2018-02-17 17:38:49','2017-12-16','08:00:00',0,2,2),(3,'Sed non consequat odio. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.','2018-02-17 17:39:11','2017-12-17','08:00:00',0,3,3),(4,'Casamentiss faiz malandris se pirulitá. Nec orci ornare consequat.','2018-02-17 17:39:45','2017-12-18','08:00:00',0,4,4),(5,'Casamentiss faiz malandris se pirulitá. Nec orci ornare consequat.','2018-02-17 17:40:07','2017-12-16','08:00:00',0,1,1),(6,'Casamentiss faiz malandris se pirulitá. Nec orci ornare consequat.','2018-02-17 17:40:20','2017-12-17','08:00:00',0,1,2),(7,'Casamentiss faiz malandris se pirulitá. Nec orci ornare consequat.','2018-02-17 17:40:33','2017-12-18','08:00:00',0,1,3),(8,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:41:38','2018-02-19','08:00:00',0,1,1),(9,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:41:48','2018-02-20','08:00:00',0,2,1),(10,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:41:58','2018-02-21','08:00:00',0,3,1),(11,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:42:05','2018-02-22','08:00:00',0,4,1),(12,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:42:17','2018-02-23','04:00:00',0,1,1),(13,'Sapien in monti palavris qui num significa nadis i pareci latim.','2018-02-17 17:42:22','2018-02-23','04:00:00',0,2,1);
/*!40000 ALTER TABLE `timerecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timerecord_reviews`
--

DROP TABLE IF EXISTS `timerecord_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timerecord_reviews` (
  `time_record_tr_id` int(11) NOT NULL,
  `reviews_tr_review_id` int(11) NOT NULL,
  UNIQUE KEY `UK_qjjh3oglai0wo97k0v91tkxxu` (`reviews_tr_review_id`),
  KEY `FKfl0j8u24km5bpjs7cx2d2f04n` (`time_record_tr_id`),
  CONSTRAINT `FKfl0j8u24km5bpjs7cx2d2f04n` FOREIGN KEY (`time_record_tr_id`) REFERENCES `timerecord` (`tr_id`),
  CONSTRAINT `FKrbtghw3d4t4pqqup4x9bxqbry` FOREIGN KEY (`reviews_tr_review_id`) REFERENCES `timerecordreview` (`tr_review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timerecord_reviews`
--

LOCK TABLES `timerecord_reviews` WRITE;
/*!40000 ALTER TABLE `timerecord_reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `timerecord_reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timerecordreview`
--

DROP TABLE IF EXISTS `timerecordreview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timerecordreview` (
  `tr_review_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `next_state` int(11) DEFAULT NULL,
  `previous_state` int(11) DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  `timerecord_tr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tr_review_id`),
  KEY `FKqq88j3s2u20xumkkqs3sjamv4` (`kowalskiuser_k_user_id`),
  KEY `FKl9f55t6ajrbbyg5aelh738oya` (`timerecord_tr_id`),
  CONSTRAINT `FKl9f55t6ajrbbyg5aelh738oya` FOREIGN KEY (`timerecord_tr_id`) REFERENCES `timerecord` (`tr_id`),
  CONSTRAINT `FKqq88j3s2u20xumkkqs3sjamv4` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timerecordreview`
--

LOCK TABLES `timerecordreview` WRITE;
/*!40000 ALTER TABLE `timerecordreview` DISABLE KEYS */;
/*!40000 ALTER TABLE `timerecordreview` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-17 19:44:52
