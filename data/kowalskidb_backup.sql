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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
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
  `role` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`k_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kowalskiuser`
--

LOCK TABLES `kowalskiuser` WRITE;
/*!40000 ALTER TABLE `kowalskiuser` DISABLE KEYS */;
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
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FKq8iq8wxrf07xn4hyy8vc6sklf` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FKq8iq8wxrf07xn4hyy8vc6sklf` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
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
  `activity_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `kowalskiuser_k_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tr_id`),
  KEY `FKeg1jp1khhjw1qsnw0weyb4wb8` (`activity_id`),
  KEY `FKtb1ncnb6o0vs4nimts9ik5unt` (`project_id`),
  KEY `FK1tw2v4volharkb6exkroqvexr` (`task_id`),
  KEY `FKfhb4aecc0t3ixuuggf8yhdonp` (`kowalskiuser_k_user_id`),
  CONSTRAINT `FK1tw2v4volharkb6exkroqvexr` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKeg1jp1khhjw1qsnw0weyb4wb8` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`),
  CONSTRAINT `FKfhb4aecc0t3ixuuggf8yhdonp` FOREIGN KEY (`kowalskiuser_k_user_id`) REFERENCES `kowalskiuser` (`k_user_id`),
  CONSTRAINT `FKtb1ncnb6o0vs4nimts9ik5unt` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timerecord`
--

LOCK TABLES `timerecord` WRITE;
/*!40000 ALTER TABLE `timerecord` DISABLE KEYS */;
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

-- Dump completed on 2018-03-22 17:12:57
