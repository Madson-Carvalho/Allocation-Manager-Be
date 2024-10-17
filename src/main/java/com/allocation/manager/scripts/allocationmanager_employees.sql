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
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` binary(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `job_role` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `specializations` varchar(100) DEFAULT NULL,
  `wage` double NOT NULL,
  `work_in_seconds` bigint NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (_binary 'Ñ\ﬂ\€\√\≈@Kî¯√ï]î\»X','alice.johnson@example.com','Project Manager','Alice Johnson','Master\'s in Business Administration','Agile Management, Risk Assessment',75000,3600),(_binary '>•âp¸K:°£n_ıΩ\Õm','sophia.wilson@example.com','DevOps Engineer','Sophia Wilson','Bachelor\'s in Information Technology','Cloud Services, CI/CD Pipelines',70000,1800),(_binary 'UM\Á≤K9é∞ ˙ç\…\‡','michael.brown@example.com','UI/UX Designer','Michael Brown','Bachelor\'s in Design','User Research, Prototyping',65000,5400),(_binary 'dIÉüO≤Ω´\"˜a+N\ÿ','emily.davis@example.com','Data Analyst','Emily Davis','Bachelor\'s in Statistics','Data Visualization, Predictive Analytics',55000,7200),(_binary '\€¡òõ}N´ãcyÜxìÛ','james.taylor@example.com','Systems Administrator','James Taylor','Bachelor\'s in Network Administration','Server Management, Network Security',58000,3600),(_binary '\ÊDí|\ÊA˝∞çπïv\‰+','john.smith@example.com','Software Engineer','John Smith','Bachelor\'s in Computer Science','Machine Learning, Web Development',60000,0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
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
