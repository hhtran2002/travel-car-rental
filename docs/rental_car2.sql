CREATE DATABASE  IF NOT EXISTS `rental_car` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `rental_car`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: rental_car
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('active','locked') NOT NULL DEFAULT 'active',
  `created_at` datetime NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_email` (`email`) USING BTREE,
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `account_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'honhan3062002@gmail.com','$2a$10$eMrZlmf.v11yJk/JLpaL2edzujoYUKCecr0cI2iKBHBuvAu4zzEU6','active','2025-09-07 01:49:26',1),(2,'driver@gmail.com','$2a$10$KgitEoNTCLfQtPsTZ8D4ueTkhm8yTsp8W6qwnQyxP0rcJz3fJeYLS','active','2025-09-07 01:55:17',2),(3,'a@gmail.com','$2a$10$XeSwG/CILc09ThvSwEIUluV.hzAyp6bwkN3QetVVLYDd5GN5c/EFC','active','2025-09-07 01:57:04',3),(4,'n21dccn159@student.ptithcm.edu.vn','$2a$10$OW9//mvyMn3SNqXWX/hgoOf70VPm19bzRpLQcxOSEhQygm3ExBM0u','active','2025-12-26 17:52:32',3),(5,'b@gmail.com','$2a$10$HMn7TB9UFFt5eyaCkJ5KLeSMt4iS/hfz4Rqq8yFHTmeyhdc5Zp1ES','locked','2025-12-28 23:33:18',3),(10,'c@gmail.com','$2a$10$E7xOOl.HAWVVb9EhRehh3e0h3CwYEW6VDAE3LE/W5OBYbXo7zDscS','active','2025-12-29 07:15:05',3),(13,'sonly@gmail.com','$2a$10$aCgUMCU.pLoDEjY34uQCK.nZu5R1dco4d.ZDehHfs2oNQ.njngV6u','active','2025-12-29 08:42:15',3),(14,'vantran@gmail.com','$2a$10$6sjVyVno2X03.gdpRmgn.Onggla9nDxeahijiBxTYGLXPsJxGO.gO','active','2025-12-29 15:33:44',3),(15,'anly@gmail.com','$2a$10$oWi5qlF7Yq4TEltDBFN.ee/OUEjPaMlvX09NzXNOUdo.10ChVHVHm','active','2025-12-31 14:44:50',3),(16,'thanhthach@gmail.com','$2a$10$5itCc7CftVdKVdtSdV.Ap.C14etdPA7Cy6BjqSIuFSTvdtwI3sw1a','active','2025-12-31 14:47:48',3);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `car_id` bigint(20) NOT NULL,
  `driver_id` bigint(20) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `pickup_location` varchar(255) NOT NULL,
  `dropoff_location` varchar(255) NOT NULL,
  `total_price` bigint(20) DEFAULT NULL,
  `status` enum('pending','confirmed','cancelled','completed') NOT NULL DEFAULT 'pending',
  `trip_status` enum('assigned','in_progress','completed','cancelled') NOT NULL DEFAULT 'assigned',
  `created_at` datetime DEFAULT NULL,
  `note` text DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  KEY `driver_id` (`driver_id`),
  KEY `discount_id` (`discount_id`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`),
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`),
  CONSTRAINT `booking_ibfk_4` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,3,1,1,2,'2025-10-01 07:30:00','2025-10-08 07:30:00','97 Man thiện, phường Hiệp Phú, tp. Thủ Đức, tp. Hồ Chí Minh','97 Man thiện, phường Hiệp Phú, tp. Thủ Đức, tp. Hồ Chí Minh',NULL,'pending','assigned',NULL,NULL);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `car_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plate_number` varchar(20) NOT NULL,
  `brand_id` bigint(20) NOT NULL,
  `type_id` bigint(20) NOT NULL,
  `model_name` varchar(255) NOT NULL,
  `year` int(11) DEFAULT NULL,
  `status` enum('available','maintenance','inactive') NOT NULL DEFAULT 'available',
  `rating` varchar(20) DEFAULT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `UK_plate_number` (`plate_number`),
  KEY `brand_id` (`brand_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `car_brand` (`brand_id`),
  CONSTRAINT `car_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `car_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (1,'37P-12345',1,1,'Toyota Vios',2022,'available',NULL,NULL),(2,'51H-56789',2,2,'Hyundai SantaFe',NULL,'available',NULL,NULL),(3,'72A-99999',3,2,'Kia Sorento',NULL,'available',NULL,NULL),(4,'60B-88888',4,3,'Ford Transit',NULL,'available',NULL,NULL),(5,'43A-55555',5,4,'Mercedes-Benz ',2022,'available',NULL,NULL),(6,'30F-11111',6,1,'VinFast Lux A2.0',2021,'available',NULL,NULL);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_brand`
--

DROP TABLE IF EXISTS `car_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_brand` (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) NOT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_brand`
--

LOCK TABLES `car_brand` WRITE;
/*!40000 ALTER TABLE `car_brand` DISABLE KEYS */;
INSERT INTO `car_brand` VALUES (1,'Toyota'),(2,'Hyundai'),(3,'Kia'),(4,'Ford'),(5,'Mercedes-Benz'),(6,'VinFast');
/*!40000 ALTER TABLE `car_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_image`
--

DROP TABLE IF EXISTS `car_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_image` (
  `image_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) NOT NULL,
  `car_id` bigint(20) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `car_image_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_image`
--

LOCK TABLES `car_image` WRITE;
/*!40000 ALTER TABLE `car_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_type`
--

DROP TABLE IF EXISTS `car_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  `seat_count` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_type`
--

LOCK TABLES `car_type` WRITE;
/*!40000 ALTER TABLE `car_type` DISABLE KEYS */;
INSERT INTO `car_type` VALUES (1,'Sedan',4,'Sedan 4 chỗ ngồi'),(2,'Sedan',5,'Sedan 5 chỗ ngồi'),(3,'SUV',5,'SUV 5 chỗ ngồi, gầm cao'),(4,'SUV',7,'SUV 7 chỗ ngồi, gầm cao, rộng rãi'),(5,'Minivan',16,'Xe khách 16 chỗ'),(6,'Limousine',9,'Xe Limousine cao cấp, 9 chỗ'),(7,'Bus',29,'Xe bus 29 chỗ '),(8,'Bus',45,'Xe bus lớn 45 chỗ');
/*!40000 ALTER TABLE `car_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `contract_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `booking_id` bigint(20) NOT NULL,
  `return_date` datetime DEFAULT NULL,
  `total_price` bigint(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `details` text DEFAULT NULL,
  `status` enum('pending','confirmed','active','completed','paid','cancelled') NOT NULL DEFAULT 'pending',
  `signed_by` varchar(255) NOT NULL,
  PRIMARY KEY (`contract_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,1,NULL,NULL,'2025-09-07 03:26:33',NULL,'pending','');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `discount_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `discount_code` varchar(20) NOT NULL,
  `discount_name` varchar(255) NOT NULL,
  `discount_type` enum('percent','amount') NOT NULL,
  `discount_value` int(11) NOT NULL,
  `status` enum('active','inactive','expired') NOT NULL,
  `min_order_value` int(11) NOT NULL,
  `max_discount_value` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`discount_id`),
  UNIQUE KEY `UK_discount_code` (`discount_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (1,'SUMMER2025','Khuyến mãi mùa hè 2025','percent',20,'expired',1000,20000,'2025-06-01 00:00:00','2025-08-31 00:00:00'),(2,'NEWCUSTOMER100','Giảm 100k cho khách hàng mới','amount',100000,'active',0,100000,'2025-01-01 00:00:00','2028-12-31 00:00:00'),(3,'TET2026','Khuyến mãi Tết 2026','percent',15,'inactive',1000000,500000,'2025-12-25 00:00:00','2026-01-15 00:00:00');
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `driver_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `exp_years` int(11) DEFAULT NULL,
  `rating` varchar(20) DEFAULT NULL,
  `status` enum('available','busy','inactive') NOT NULL DEFAULT 'available',
  `license_num` varchar(20) NOT NULL,
  `license_type` enum('B','D1','D2','D') NOT NULL,
  `expiry_date` date NOT NULL,
  PRIMARY KEY (`driver_id`),
  UNIQUE KEY `UK_license_num` (`license_num`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (1,2,'Quận 7, tp. Hồ Chí Minh',2,NULL,'available','123456789','B','2028-09-01');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset`
--

DROP TABLE IF EXISTS `password_reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires_at` datetime NOT NULL,
  `used` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_token` (`token`),
  CONSTRAINT `fk_password_reset_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset`
--

LOCK TABLES `password_reset` WRITE;
/*!40000 ALTER TABLE `password_reset` DISABLE KEYS */;
INSERT INTO `password_reset` VALUES (10,4,'$2a$10$3oeoEbZG6sA3ALzLMEezouw9a6ojEw8XOEwFj2D0tsT27F8Ex1wUS','2025-12-27 20:37:18',1,'2025-12-27 20:27:18'),(11,4,'$2a$10$siK3oLUTOhnUQxcIeJ2pCOheA1HEqnvPDQYBeDygnccQlze6al82q','2025-12-27 21:19:57',1,'2025-12-27 21:09:57'),(12,4,'$2a$10$ltYFtSzyQKCvj5onTJ6LROMzuA70sJ3H7yDkBhftbNd1tx444j0t6','2025-12-29 06:30:50',1,'2025-12-29 06:20:50'),(13,4,'$2a$10$lbWkIlDOxjmisajk56epqutQoQzy.yHa2I88Fjne9NNPECBsYfSqu','2025-12-29 09:12:55',1,'2025-12-29 09:02:55'),(14,4,'$2a$10$S.hXADWxL//6VLS849P.ruLxXudkaUbM.Ok7OTi4c1xGeobBYYy3.','2025-12-29 09:41:14',1,'2025-12-29 09:31:14');
/*!40000 ALTER TABLE `password_reset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_id` bigint(20) NOT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `payment_method` enum('cash','online') NOT NULL,
  `status` enum('pending','completed','failed','refunded') NOT NULL DEFAULT 'pending',
  PRIMARY KEY (`payment_id`),
  KEY `contract_id` (`contract_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,NULL,'cash','pending');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pricing_rule`
--

DROP TABLE IF EXISTS `pricing_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pricing_rule` (
  `pricing_rule_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) NOT NULL,
  `type_id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `effective_from` datetime NOT NULL,
  `effective_to` datetime NOT NULL,
  `status` enum('active','inactive') NOT NULL DEFAULT 'inactive',
  `price_per_hour` int(11) NOT NULL,
  `price_per_day` int(11) NOT NULL,
  `overtime_rate` int(11) NOT NULL,
  `holiday_surcharge` int(11) NOT NULL,
  `weekend_surcharge` int(11) NOT NULL,
  PRIMARY KEY (`pricing_rule_id`),
  KEY `brand_id` (`brand_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `pricing_rule_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `car_brand` (`brand_id`),
  CONSTRAINT `pricing_rule_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `car_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pricing_rule`
--

LOCK TABLES `pricing_rule` WRITE;
/*!40000 ALTER TABLE `pricing_rule` DISABLE KEYS */;
INSERT INTO `pricing_rule` VALUES (1,1,1,'2025-09-07 02:52:55','2025-09-08 00:00:00','2025-09-30 00:00:00','inactive',150000,1000000,10,20,5),(2,4,3,'2025-09-07 02:52:55','2025-09-08 00:00:00','2026-12-31 00:00:00','inactive',250000,1500000,10,20,10);
/*!40000 ALTER TABLE `pricing_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'driver'),(3,'customer');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_user_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Hồ Đức Nhân',NULL,NULL,NULL,NULL),(2,'hdndriver',NULL,NULL,NULL,NULL),(3,'Nguyễn Văn A',NULL,NULL,NULL,NULL),(4,'Thạch An','0912356787',NULL,NULL,NULL),(5,'Nguyễn Văn B (Update)','0909123123','2003-10-10','male',NULL),(10,'Nguyễn Văn c','0909000222','2003-12-11','male',NULL),(13,'Lý Sơn','0912356799',NULL,NULL,NULL),(14,'Trần Thị Vân',NULL,NULL,NULL,NULL),(15,'Thạch Ly An',NULL,NULL,NULL,NULL),(16,'Thạch Văn Thanh','0912356789',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'rental_car'
--

--
-- Dumping routines for database 'rental_car'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-07 15:38:45
