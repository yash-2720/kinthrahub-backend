CREATE DATABASE  IF NOT EXISTS `kinthrahub` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kinthrahub`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: kinthrahub
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `application_user_table`
--

DROP TABLE IF EXISTS `application_user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_user_table` (
  `user_id` varchar(20) NOT NULL,
  `employee_id` varchar(20) NOT NULL,
  `role_id` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `employee_id` (`employee_id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_application_user_role` (`role_id`),
  CONSTRAINT `fk_application_user_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee_table` (`employee_id`),
  CONSTRAINT `fk_application_user_role` FOREIGN KEY (`role_id`) REFERENCES `role_table` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation_plan_table`
--

DROP TABLE IF EXISTS `donation_plan_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_plan_table` (
  `donation_plan_id` varchar(20) NOT NULL,
  `hospital_id` varchar(20) NOT NULL,
  `donation_name` varchar(150) NOT NULL,
  `donation_description` varchar(500) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`donation_plan_id`),
  KEY `fk_donation_plan_hospital` (`hospital_id`),
  CONSTRAINT `fk_donation_plan_hospital` FOREIGN KEY (`hospital_id`) REFERENCES `hospital_table` (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation_request_table`
--

DROP TABLE IF EXISTS `donation_request_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_request_table` (
  `donation_request_id` varchar(20) NOT NULL,
  `employee_id` varchar(20) NOT NULL,
  `donation_plan_id` varchar(20) NOT NULL,
  `donation_type` varchar(20) NOT NULL,
  `donation_amount` decimal(10,2) NOT NULL,
  `donation_start_date` date NOT NULL,
  `donation_end_date` date DEFAULT NULL,
  `donation_status` varchar(50) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`donation_request_id`),
  KEY `fk_donation_request_employee` (`employee_id`),
  KEY `fk_donation_request_donation_plan` (`donation_plan_id`),
  CONSTRAINT `fk_donation_request_donation_plan` FOREIGN KEY (`donation_plan_id`) REFERENCES `donation_plan_table` (`donation_plan_id`),
  CONSTRAINT `fk_donation_request_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee_table` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation_transaction_table`
--

DROP TABLE IF EXISTS `donation_transaction_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_transaction_table` (
  `donation_transaction_id` varchar(20) NOT NULL,
  `donation_request_id` varchar(20) NOT NULL,
  `payroll_period` varchar(20) NOT NULL,
  `deducted_amount` decimal(10,2) NOT NULL,
  `transaction_status` varchar(50) NOT NULL,
  `processed_on` timestamp NULL DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  `payroll_run_id` varchar(20) NOT NULL,
  PRIMARY KEY (`donation_transaction_id`),
  KEY `fk_donation_transaction_donation_request` (`donation_request_id`),
  KEY `fk_donation_transaction_payroll_run` (`payroll_run_id`),
  CONSTRAINT `fk_donation_transaction_donation_request` FOREIGN KEY (`donation_request_id`) REFERENCES `donation_request_table` (`donation_request_id`),
  CONSTRAINT `fk_donation_transaction_payroll_run` FOREIGN KEY (`payroll_run_id`) REFERENCES `payroll_run_table` (`payroll_run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_table`
--

DROP TABLE IF EXISTS `employee_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_table` (
  `employee_id` varchar(20) NOT NULL,
  `employee_number` varchar(20) NOT NULL,
  `employee_name` varchar(150) NOT NULL,
  `employee_email` varchar(150) NOT NULL,
  `employee_phone_number` varchar(15) NOT NULL,
  `basic_salary` decimal(10,2) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_number` (`employee_number`),
  UNIQUE KEY `employee_email` (`employee_email`),
  UNIQUE KEY `employee_phone_number` (`employee_phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hospital_table`
--

DROP TABLE IF EXISTS `hospital_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_table` (
  `hospital_id` varchar(20) NOT NULL,
  `hospital_name` varchar(150) NOT NULL,
  `hospital_description` varchar(500) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `id_sequence`
--

DROP TABLE IF EXISTS `id_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `id_sequence` (
  `sequence_id` varchar(20) NOT NULL,
  `entity_name` varchar(50) NOT NULL,
  `code` varchar(5) NOT NULL,
  `start_value` bigint NOT NULL,
  `current_value` bigint NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sequence_id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payroll_run_table`
--

DROP TABLE IF EXISTS `payroll_run_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll_run_table` (
  `payroll_run_id` varchar(20) NOT NULL,
  `payroll_month` tinyint NOT NULL,
  `payroll_year` smallint NOT NULL,
  `run_status` varchar(30) NOT NULL,
  `processed_on` timestamp NULL DEFAULT NULL,
  `processed_by` varchar(50) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`payroll_run_id`),
  UNIQUE KEY `uk_payroll_run_month_year` (`payroll_month`,`payroll_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_table`
--

DROP TABLE IF EXISTS `role_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_table` (
  `role_id` varchar(20) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_on` timestamp NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-20 13:53:22
