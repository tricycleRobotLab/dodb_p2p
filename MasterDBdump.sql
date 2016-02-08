-- MySQL dump 10.13  Distrib 5.6.21, for Win32 (x86)
--
-- Host: localhost    Database: p2p_test
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `chunk_list`
--

DROP TABLE IF EXISTS `chunk_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chunk_list` (
  `chunk_name` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `port` int(5) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  KEY `index1` (`hash`),
  CONSTRAINT `chunk_list_ibfk_1` FOREIGN KEY (`hash`) REFERENCES `file_list` (`hash`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chunk_list`
--

LOCK TABLES `chunk_list` WRITE;
/*!40000 ALTER TABLE `chunk_list` DISABLE KEYS */;
INSERT INTO `chunk_list` VALUES ('C:\\Users\\BiNI\\Downloads\\Documents\\Android Fragments.pdf.chunk0','127.0.0.1',52137,'84fb2cf00654318d5a8324892847af80'),('C:\\Users\\BiNI\\Downloads\\Documents\\Android Fragments.pdf.chunk1','127.0.0.1',52137,'84fb2cf00654318d5a8324892847af80'),('C:\\Users\\BiNI\\Downloads\\Documents\\Android Fragments.pdf.chunk2','127.0.0.1',52137,'84fb2cf00654318d5a8324892847af80'),('C:\\Users\\BiNI\\Downloads\\Documents\\Android Fragments.pdf.chunk3','127.0.0.1',52137,'84fb2cf00654318d5a8324892847af80');
/*!40000 ALTER TABLE `chunk_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_list`
--

DROP TABLE IF EXISTS `file_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_list` (
  `file_name` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `port` int(5) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `file_size` int(10) DEFAULT NULL,
  KEY `hash` (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_list`
--

LOCK TABLES `file_list` WRITE;
/*!40000 ALTER TABLE `file_list` DISABLE KEYS */;
INSERT INTO `file_list` VALUES ('test','0.0.0.0',0,'ccc',0),('C:\\Users\\BiNI\\Downloads\\Documents\\Android Fragments.pdf','127.0.0.1',52137,'84fb2cf00654318d5a8324892847af80',1588);
/*!40000 ALTER TABLE `file_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-08 17:34:14
