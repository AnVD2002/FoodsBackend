-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: foodshop
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `decentralization`
--

DROP TABLE IF EXISTS `decentralization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `decentralization` (
  `decentralization_id` int NOT NULL AUTO_INCREMENT,
  `decentralization_name` enum('USER','ADMIN') DEFAULT NULL,
  PRIMARY KEY (`decentralization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decentralization`
--

LOCK TABLES `decentralization` WRITE;
/*!40000 ALTER TABLE `decentralization` DISABLE KEYS */;
/*!40000 ALTER TABLE `decentralization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_categories`
--

DROP TABLE IF EXISTS `food_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_categories` (
  `food_category_id` int NOT NULL AUTO_INCREMENT,
  `food_category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`food_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_categories`
--

LOCK TABLES `food_categories` WRITE;
/*!40000 ALTER TABLE `food_categories` DISABLE KEYS */;
INSERT INTO `food_categories` VALUES (3,'Mì'),(4,'Cơm'),(5,'Pizza');
/*!40000 ALTER TABLE `food_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_details`
--

DROP TABLE IF EXISTS `food_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_details` (
  `food_detail_id` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `food_detail_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`food_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_details`
--

LOCK TABLES `food_details` WRITE;
/*!40000 ALTER TABLE `food_details` DISABLE KEYS */;
INSERT INTO `food_details` VALUES (15,20,10,'Pizza Margherita,Size S,  Cay ít, Thịt'),(16,20,10,'Pizza Pepperoni, Size S, Cay it, Thịt'),(17,20,10,'Pizza Quattro Stagioni, Size S, Cay it, Thịt'),(18,20,10,'Pizza Quattro Formaggi, Size S, Cay it, Thịt'),(19,20,10,'Pizza Hawaiiana, Size S, Cay it, Thịt'),(20,20,10,'Pizza Diavola, Size S, Cay it, Thịt'),(21,20,10,'Pizza Funghi, Size S, Cay it, Thịt'),(22,20,10,'Pizza Capricciosa, Size S, Cay it, Thịt'),(23,20,10,'Pizza Napoletana, Size S, Cay it, Thịt'),(24,20,10,'Pizza Bianca, Size S, Cay it, Thịt'),(25,25,10,'Pizza Margherita, Size M, Cay ít, Thịt'),(26,30,10,'Pizza Margherita, Size L, Cay ít, Thịt'),(27,25,10,NULL);
/*!40000 ALTER TABLE `food_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_details_property_details`
--

DROP TABLE IF EXISTS `food_details_property_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_details_property_details` (
  `food_details_property_details_id` int NOT NULL AUTO_INCREMENT,
  `food_detail_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `property_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`food_details_property_details_id`),
  KEY `FK51b8c6dt6uyf5xdbx958sqrmu` (`food_id`),
  KEY `FKs3i2yqkj23qyd5ogw0w5ria1y` (`food_detail_id`),
  KEY `FKmbbrgbgdrbxh8h104knyvthdn` (`property_detail_id`),
  CONSTRAINT `FK51b8c6dt6uyf5xdbx958sqrmu` FOREIGN KEY (`food_id`) REFERENCES `foods` (`food_id`),
  CONSTRAINT `FKmbbrgbgdrbxh8h104knyvthdn` FOREIGN KEY (`property_detail_id`) REFERENCES `property_details` (`property_detail_id`),
  CONSTRAINT `FKs3i2yqkj23qyd5ogw0w5ria1y` FOREIGN KEY (`food_detail_id`) REFERENCES `food_details` (`food_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_details_property_details`
--

LOCK TABLES `food_details_property_details` WRITE;
/*!40000 ALTER TABLE `food_details_property_details` DISABLE KEYS */;
INSERT INTO `food_details_property_details` VALUES (1,15,3,1),(2,15,3,4),(3,15,3,7),(4,16,4,1),(5,16,4,4),(6,16,4,7),(7,17,5,1),(8,17,5,4),(9,17,5,7),(10,18,6,1),(11,18,6,4),(12,18,6,7),(13,19,7,1),(14,19,7,4),(15,19,7,7),(16,20,8,1),(17,20,8,4),(18,20,8,7),(19,21,9,1),(20,21,9,4),(21,21,9,7),(22,22,10,1),(23,22,10,4),(24,22,10,7),(25,23,11,1),(26,23,11,4),(27,23,11,7),(28,24,12,1),(29,24,12,4),(30,24,12,7),(31,25,3,2),(32,25,3,4),(33,25,3,7),(34,26,3,3),(35,26,3,4),(36,26,3,7),(37,27,4,2),(38,27,4,4),(39,27,4,7);
/*!40000 ALTER TABLE `food_details_property_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_properties`
--

DROP TABLE IF EXISTS `food_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_properties` (
  `food_property_id` int NOT NULL AUTO_INCREMENT,
  `food_id` int DEFAULT NULL,
  `property_id` int DEFAULT NULL,
  PRIMARY KEY (`food_property_id`),
  KEY `FK9um2qx6liosnjcslbiy7iu3le` (`food_id`),
  KEY `FKabu607payn418sdlwlba7sfmy` (`property_id`),
  CONSTRAINT `FK9um2qx6liosnjcslbiy7iu3le` FOREIGN KEY (`food_id`) REFERENCES `foods` (`food_id`),
  CONSTRAINT `FKabu607payn418sdlwlba7sfmy` FOREIGN KEY (`property_id`) REFERENCES `properties` (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_properties`
--

LOCK TABLES `food_properties` WRITE;
/*!40000 ALTER TABLE `food_properties` DISABLE KEYS */;
INSERT INTO `food_properties` VALUES (1,3,1),(2,3,2),(3,3,3),(4,4,1),(5,4,2),(6,4,3),(7,5,1),(8,5,2),(9,5,3),(10,6,1),(11,6,2),(12,6,3),(13,7,1),(14,7,2),(15,7,3),(16,8,1),(17,8,2),(18,8,3),(19,9,1),(20,9,2),(21,9,3),(22,10,1),(23,10,2),(24,10,3),(25,11,1),(26,11,2),(27,11,3),(28,12,1),(29,12,2),(30,12,3);
/*!40000 ALTER TABLE `food_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foods`
--

DROP TABLE IF EXISTS `foods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foods` (
  `food_id` int NOT NULL AUTO_INCREMENT,
  `food_category_id` int DEFAULT NULL,
  `food_name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`food_id`),
  KEY `FK8kpsrgxfhc4wjdmd7vs1j4n60` (`food_category_id`),
  CONSTRAINT `FK8kpsrgxfhc4wjdmd7vs1j4n60` FOREIGN KEY (`food_category_id`) REFERENCES `food_categories` (`food_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foods`
--

LOCK TABLES `foods` WRITE;
/*!40000 ALTER TABLE `foods` DISABLE KEYS */;
INSERT INTO `foods` VALUES (3,5,'Pizza Margherita',NULL),(4,5,'Pizza Pepperoni',NULL),(5,5,'Pizza Quattro Stagioni',NULL),(6,5,'Pizza Quattro Formaggi',NULL),(7,5,'Pizza Hawaiiana',NULL),(8,5,'Pizza Diavola',NULL),(9,5,'Pizza Funghi',NULL),(10,5,'Pizza Capricciosa',NULL),(11,5,'Pizza Napoletana',NULL),(12,5,'Pizza Bianca',NULL);
/*!40000 ALTER TABLE `foods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `food_detail_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `FK2lt45iptuccl2eorgafmuppg9` (`food_detail_id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  CONSTRAINT `FK2lt45iptuccl2eorgafmuppg9` FOREIGN KEY (`food_detail_id`) REFERENCES `food_details` (`food_detail_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_date` date DEFAULT NULL,
  `total_price` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `payment_date` varchar(255) DEFAULT NULL,
  `payment_method_id` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `FKlouu98csyullos9k25tbpk4va` (`order_id`),
  KEY `FKjii2n6db6cje3km5ybsbgo8cl` (`payment_method_id`),
  CONSTRAINT `FKjii2n6db6cje3km5ybsbgo8cl` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`payment_method_id`),
  CONSTRAINT `FKlouu98csyullos9k25tbpk4va` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_details` (
  `payment_detail_id` int NOT NULL AUTO_INCREMENT,
  `card_number` varchar(255) DEFAULT NULL,
  `payment_id` int DEFAULT NULL,
  PRIMARY KEY (`payment_detail_id`),
  KEY `FKfgfv48ydd9rybgntm9oko2uva` (`payment_id`),
  CONSTRAINT `FKfgfv48ydd9rybgntm9oko2uva` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `payment_method_id` int NOT NULL AUTO_INCREMENT,
  `payment_method_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_method_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `properties` (
  `property_id` int NOT NULL AUTO_INCREMENT,
  `property-name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1,'Size'),(2,'Spicy'),(3,'Topping');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_details`
--

DROP TABLE IF EXISTS `property_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_details` (
  `property_detail_id` int NOT NULL AUTO_INCREMENT,
  `property_detail_name` varchar(255) DEFAULT NULL,
  `property_id` int DEFAULT NULL,
  PRIMARY KEY (`property_detail_id`),
  KEY `FKt7e2jb9xyx1l8h01ccfi3xxk5` (`property_id`),
  CONSTRAINT `FKt7e2jb9xyx1l8h01ccfi3xxk5` FOREIGN KEY (`property_id`) REFERENCES `properties` (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_details`
--

LOCK TABLES `property_details` WRITE;
/*!40000 ALTER TABLE `property_details` DISABLE KEYS */;
INSERT INTO `property_details` VALUES (1,'Size S',1),(2,'Size M',1),(3,'Size L',1),(4,'Cay ít',2),(5,'Cay vừa',2),(6,'Cay cực độ',2),(7,'Thịt',3),(8,'Cá',3),(9,'Rau',3);
/*!40000 ALTER TABLE `property_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `decentralization_id` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `number_phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `date_update` date DEFAULT NULL,
  `update_password_token` varchar(255) DEFAULT NULL,
  `update_password_token_expiry` date DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  KEY `FKbxl6b2is2srrj6l4e61ojioyh` (`decentralization_id`),
  CONSTRAINT `FKbxl6b2is2srrj6l4e61ojioyh` FOREIGN KEY (`decentralization_id`) REFERENCES `decentralization` (`decentralization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-12 20:31:54
