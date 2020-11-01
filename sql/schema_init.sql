CREATE DATABASE  IF NOT EXISTS `calorie_composer` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `calorie_composer`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: calorie_composer
-- ------------------------------------------------------
-- Server version	8.0.18

-- Drop in specific order to appease FK constraints
DROP TABLE IF EXISTS `plan`;
DROP TABLE IF EXISTS `combo_food_constituent`;
DROP TABLE IF EXISTS `combo_food_conversion_ratio`;
DROP TABLE IF EXISTS `combo_food`;
DROP TABLE IF EXISTS `nutrient`;
DROP TABLE IF EXISTS `conversion_ratio`;
DROP TABLE IF EXISTS `food`;
DROP TABLE IF EXISTS `user`;

--
-- Table structure for table `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `food`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `fdc_id` varchar(10) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `brand_owner` varchar(100) DEFAULT NULL,
  `ingredients` varchar(1000) DEFAULT NULL,
  `ssr_display_unit` varchar(45) DEFAULT NULL,
  `csr_display_unit` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `food_user_id_idx` (`user_id`),
  CONSTRAINT `food_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conversion_ratio`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversion_ratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_id` int(11) DEFAULT NULL,
  `amount_a` decimal(5,2) DEFAULT NULL,
  `unit_a` varchar(45) DEFAULT NULL,
  `free_form_value_a` varchar(45) DEFAULT NULL,
  `amount_b` decimal(5,2) DEFAULT NULL,
  `unit_b` varchar(45) DEFAULT NULL,
  `free_form_value_b` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `conversion_ratio_food_id_idx` (`food_id`),
  CONSTRAINT `conversion_ratio_food_id` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nutrient`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nutrient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_id` int(11) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `unit` varchar(45) NOT NULL,
  `amount` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `nutrient_food_id_idx` (`food_id`),
  CONSTRAINT `nutrient_food_id` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `combo_food`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `is_draft` int(11) NOT NULL,
  `draft_of_combo_food_id` int(11) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `combo_food_user_id_idx` (`user_id`),
  CONSTRAINT `combo_food_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `combo_food_draft_of_combo_food_id` FOREIGN KEY (`draft_of_combo_food_id`) REFERENCES `combo_food` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `combo_food_conversion_ratio`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_food_conversion_ratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `combo_food_id` int(11) DEFAULT NULL,
  `amount_a` decimal(5,2) DEFAULT NULL,
  `unit_a` varchar(45) DEFAULT NULL,
  `amount_b` decimal(5,2) DEFAULT NULL,
  `unit_b` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `combo_food_conversion_ratio_combo_food_id_idx` (`combo_food_id`),
  CONSTRAINT `combo_food_conversion_ratio_combo_food_id` FOREIGN KEY (`combo_food_id`) REFERENCES `combo_food` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `combo_food_constituent`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_food_constituent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `combo_food_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL,
  `amount` decimal(5,2) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `combo_food_constituent_combo_food_id_idx` (`combo_food_id`),
  KEY `combo_food_constituent_food_id_idx` (`food_id`),
  CONSTRAINT `combo_food_constituent_combo_food_id` FOREIGN KEY (`combo_food_id`) REFERENCES `combo_food` (`id`) ON DELETE CASCADE,
  CONSTRAINT `combo_food_constituent_food_id` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


