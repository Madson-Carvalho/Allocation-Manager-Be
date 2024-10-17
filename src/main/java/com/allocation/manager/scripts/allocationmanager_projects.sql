-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: allocationmanager
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `project_id` binary(16) NOT NULL,
  `delivery_date` datetime(6) NOT NULL,
  `funding_source` varchar(255) NOT NULL,
  `initial_date` datetime(6) NOT NULL,
  `name` varchar(100) NOT NULL,
  `project_coordinator` varchar(255) NOT NULL,
  `project_hours` float NOT NULL,
  `total_project_value` float NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (_binary '!š\ì{óøHÔ–ý;|2¦nž','2025-06-01 10:00:00.000000','External Grant','2024-09-01 10:00:00.000000','Web Application Development','John Doe',200,75000),(_binary 'X\éŒI\ï\ÓLÐ”\ÞÆ™R\éŒt','2025-05-20 09:00:00.000000','Internal Budget','2024-10-05 09:00:00.000000','Cloud Infrastructure Upgrade','Sophia Martinez',250,100000),(_binary '¤ŒJ\â1#NQ§#Mûk`\'','2025-12-01 15:30:00.000000','Government Funding','2024-08-15 15:30:00.000000','Data Science Initiative','Robert Brown',300,120000),(_binary '\Ôö¯\ÊnvO\\œ¹ý\\B\n#c','2025-01-10 23:32:58.336000','Internal Budget','2024-10-10 23:32:58.336000','AI Development Project','Alice Johnson',120,50000),(_binary '\Ö8‚PbNð«òò›\\e','2025-03-30 14:00:00.000000','Private Donation','2024-09-15 14:00:00.000000','AI Research Study','Liam Williams',180,60000),(_binary '\Ö\áš^Z\ãIA´‡zŒë‰‚ÿ','2025-02-15 12:00:00.000000','Corporate Sponsorship','2024-11-01 12:00:00.000000','Mobile App Launch','Emily Smith',150,90000);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-17 20:22:18
