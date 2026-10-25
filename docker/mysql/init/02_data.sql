-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: carebridge
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
-- Dumping data for table `donation_plan_table`
--

LOCK TABLES `donation_plan_table` WRITE;
/*!40000 ALTER TABLE `donation_plan_table` DISABLE KEYS */;
INSERT INTO `donation_plan_table` VALUES ('DOP00000001','HOP00000001','Children\'s Education Support','Provides financial assistance for children requiring long-term medical treatment and education support.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000002','HOP00000001','Cancer Treatment Assistance','Supports patients undergoing cancer treatment through charitable contributions.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000003','HOP00000002','Cardiac Care Program','Helps patients receive life-saving cardiac treatments and surgeries.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000004','HOP00000002','Dialysis Support Initiative','Provides dialysis treatment support for financially challenged patients.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000005','HOP00000003','Maternal Care Program','Supports healthcare services for expecting mothers and newborns.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000006','HOP00000003','Child Nutrition Initiative','Provides nutrition and healthcare assistance for children.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000007','HOP00000004','Emergency Relief Fund','Provides emergency medical financial assistance to patients.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000008','HOP00000004','Rural Healthcare Outreach','Supports healthcare camps and treatment in rural communities.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000009','HOP00000005','Senior Citizen Wellness','Provides healthcare support for elderly patients.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03'),('DOP00000010','HOP00000005','Organ Transplant Support','Provides financial assistance for organ transplant patients.',1,'SYSTEM','2026-06-29 06:49:03','SYSTEM','2026-06-29 06:49:03');
/*!40000 ALTER TABLE `donation_plan_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employee_table`
--

LOCK TABLES `employee_table` WRITE;
/*!40000 ALTER TABLE `employee_table` DISABLE KEYS */;
INSERT INTO `employee_table` VALUES ('EMP00000001','CBG100001','Rahul Sharma','rahul.sharma@example.com','+919999900001',45000.00,1,'SYSTEM','2026-06-29 06:36:15','SYSTEM','2026-07-27 09:08:05'),('EMP00000002','CBG100002','Nair','priya.nair@example.com','+919999900002',62000.00,1,'SYSTEM','2026-06-29 06:36:15','SYSTEM','2026-07-01 07:18:05'),('EMP00000003','CBG100003','Arjun Kumar','arjun.kumar@example.com','9999900001',38000.00,1,'SYSTEM','2026-06-29 06:36:15','SYSTEM','2026-07-28 07:36:44'),('EMP00000004','CBG100004','Sneha Reddy','sneha.reddy@example.com','+919999900004',75000.00,1,'SYSTEM','2026-06-29 06:36:15','SYSTEM','2026-07-27 08:49:05'),('EMP00000005','CBG100005','Vikram Singh','vikram.singh@example.com','+919999900005',55000.00,1,'SYSTEM','2026-06-29 06:36:15','SYSTEM','2026-06-29 06:36:15'),('EMP00000006','CBG100006','UpdateEmployee','updateemployee@example.com','+919999900115',65000.00,1,'SYSTEM','2026-06-30 08:09:10','SYSTEM','2026-07-27 08:47:51'),('EMP00000007','CBG100007','Yashwanth Kumar M N','yashwanthkumar.mn@gmail.com','7397797991',25000.00,1,'SYSTEM','2026-07-10 06:59:19','SYSTEM','2026-07-10 06:59:19'),('EMP00000009','CBG100008','Yashwanth Kumar','rohit1@example.com','+919999900015',1000000.00,1,'SYSTEM','2026-07-10 07:22:22','SYSTEM','2026-07-27 08:59:17'),('EMP00000010','CBG100009','Yash','yash@example.com','+919799979313',100000.00,1,'SYSTEM','2026-07-16 08:25:45','SYSTEM','2026-07-16 08:25:45'),('EMP00000011','CBG100010','PayrollAdmin','payrolladmin@gmail.com','+919000001234',50000.00,1,'SYSTEM','2026-07-17 13:57:12','SYSTEM','2026-07-17 13:57:12'),('EMP00000012','asdfasfd','asdfdfasasdfasdf','asdfasdfsad@gmail.com','7399998713',1000.00,0,'SYSTEM','2026-07-27 14:08:07','SYSTEM','2026-07-27 15:08:16'),('EMP00000013','sdafasdf','sdfasdfa','temp@gmail.com','1111111111',10001.00,1,'SYSTEM','2026-07-27 14:43:03','SYSTEM','2026-07-27 14:43:03'),('EMP00000015','CBG100013','New Employee','newemployee@gmail.com','7456632718',10000.00,0,'SYSTEM','2026-07-27 15:10:53','SYSTEM','2026-07-27 15:18:08'),('EMP00000016','qweqweqwe','qeqweqweqweq','sdafasdf@gmail.com','1111223345',12441.00,1,'SYSTEM','2026-07-28 06:09:18','SYSTEM','2026-07-28 06:09:18'),('EMP00000017','tempnumber','After Mainlayout','layout@gmail.com','7388876717',100000.00,1,'SYSTEM','2026-08-03 17:32:55','SYSTEM','2026-08-03 17:32:55'),('EMP00000018','CBG100011','New Employee','newemployee1@gmail.com','9999988717',300000.00,1,'SYSTEM','2026-09-02 13:58:24','SYSTEM','2026-09-02 13:58:24');
/*!40000 ALTER TABLE `employee_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hospital_table`
--

LOCK TABLES `hospital_table` WRITE;
/*!40000 ALTER TABLE `hospital_table` DISABLE KEYS */;
INSERT INTO `hospital_table` VALUES ('HOP00000001','HopeCare Hospital','Providing medical assistance to underprivileged families.',1,'SYSTEM','2026-06-29 06:40:09','SYSTEM','2026-06-29 06:40:09'),('HOP00000002','LifeBridge Medical Center','Supporting critical healthcare treatments and patient care.',1,'SYSTEM','2026-06-29 06:40:09','SYSTEM','2026-06-29 06:40:09'),('HOP00000003','Sunrise Community Hospital','Focused on maternal, child, and rural healthcare services.',1,'SYSTEM','2026-06-29 06:40:09','SYSTEM','2026-06-29 06:40:09'),('HOP00000004','Unity Health Foundation','Delivering affordable healthcare and emergency medical support.',1,'SYSTEM','2026-06-29 06:40:09','SYSTEM','2026-06-29 06:40:09'),('HOP00000005','Green Valley Hospital','Dedicated to improving community health through charitable initiatives.',1,'SYSTEM','2026-06-29 06:40:09','SYSTEM','2026-06-29 06:40:09');
/*!40000 ALTER TABLE `hospital_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `id_sequence`
--

LOCK TABLES `id_sequence` WRITE;
/*!40000 ALTER TABLE `id_sequence` DISABLE KEYS */;
INSERT INTO `id_sequence` VALUES ('IDS00000001','Employee','EMP',1,19,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-09-02 13:58:24'),('IDS00000002','Hospital','HOP',1,5,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-06-29 06:40:28'),('IDS00000003','Donation Plan','DOP',1,10,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-06-29 06:52:18'),('IDS00000004','Donation Request','DOR',1,50,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-10-20 02:06:09'),('IDS00000005','Donation Transaction','DTR',1,57,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-09-14 10:49:06'),('IDS00000006','Role','ROL',1,3,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-06-29 06:27:28'),('IDS00000007','Application User','USR',1,12,1,'SYSTEM','2026-06-29 06:27:28','SYSTEM','2026-09-02 13:58:53'),('IDS00000008','Payroll Run','PRL',1,20,1,'SYSTEM','2026-07-07 12:45:16','SYSTEM','2026-09-14 10:49:06');
/*!40000 ALTER TABLE `id_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role_table`
--

LOCK TABLES `role_table` WRITE;
/*!40000 ALTER TABLE `role_table` DISABLE KEYS */;
INSERT INTO `role_table` VALUES ('ROL00000001','ADMIN',1,'SYSTEM','2026-06-29 06:23:23',NULL,NULL),('ROL00000002','EMPLOYEE',1,'SYSTEM','2026-06-29 06:23:23',NULL,NULL),('ROL00000003','PAYROLL_ADMIN',1,'SYSTEM','2026-06-29 06:23:23',NULL,NULL);
/*!40000 ALTER TABLE `role_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- =============================================
-- Seed Admin Employee
-- =============================================

INSERT INTO employee_table (
    employee_id,
    employee_number,
    employee_name,
    employee_email,
    employee_phone_number,
    basic_salary,
    is_active,
    created_by,
    created_on,
    modified_by,
    modified_on
)
VALUES (
    'EMP00000019',
    'CBG100012',
    'ADMIN',
    'admin@gmail.com',
    '9999999898',
    1000000.00,
    TRUE,
    'SYSTEM',
    NOW(),
    'SYSTEM',
    NOW()
);

-- =============================================
-- Seed Admin Application User
-- =============================================

INSERT INTO application_user_table (
    user_id,
    employee_id,
    role_id,
    username,
    password,
    last_login,
    is_active,
    created_by,
    created_on,
    modified_by,
    modified_on
)
VALUES (
    'USR00000013',
    'EMP00000019',
    'ROL00000001',
    'admin01',
    '$2a$10$WwWUW/eO.yH229Bor5hl4.0fwyDep6jaVF4LmIGFFahYVxfdkvpv6',
    NULL,
    TRUE,
    'SYSTEM',
    NOW(),
    'SYSTEM',
    NOW()
);


-- Dump completed on 2026-10-20 14:02:36
