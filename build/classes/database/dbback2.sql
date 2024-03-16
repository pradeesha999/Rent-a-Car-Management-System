-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: coursework
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `AdminID` varchar(10) NOT NULL,
  `AdminName` varchar(50) DEFAULT NULL,
  `Username` varchar(100) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`AdminID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('A001','John Doe','john_admin','password123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `BookingID` varchar(10) NOT NULL,
  `CustomerID` varchar(10) DEFAULT NULL,
  `VehicleID` varchar(10) DEFAULT NULL,
  `PickupDate` date DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `Purpose` varchar(255) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `TotalPrice` decimal(10,2) DEFAULT NULL,
  `BookDate` date DEFAULT NULL,
  PRIMARY KEY (`BookingID`),
  KEY `CustomerID` (`CustomerID`),
  KEY `VehicleID` (`VehicleID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES ('B001','CUS001','V001','2023-08-05','2023-08-09','Trip','Confirmed',8000.00,'2023-08-02'),('B002','CUS002','V002','2023-08-02','2023-08-09','Trip','Confirmed',7500.00,'2023-08-01'),('B003','CUS003','V001','2023-08-20','2023-08-26','Wedding','Pending',21000.00,'2023-08-19'),('B004','CUS003','V002','2023-08-20','2023-08-22','','Pending',4000.00,'2023-08-19'),('B005','CUS003','V002','2023-08-21','2023-08-21','Party','Pending',2000.00,'2023-08-19'),('B006','CUS001','V002','2023-08-22','2023-08-25','vacation','Pending',12000.00,'2023-08-20');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `CustomerID` varchar(10) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `ContactNumber` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `NICNumber` varchar(20) DEFAULT NULL,
  `Username` varchar(100) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`),
  UNIQUE KEY `NICNumber` (`NICNumber`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('CUS001','Yellow','0777772879','yellow@example.com','987654321X','yello','password123'),('CUS002','John','0778872879','john@example.com','987654322X','john','password123'),('CUS003','Sony','0112568172','sony@gmail.com','857454322v','sony123','password123');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `VehicleID` varchar(10) NOT NULL,
  `RegistrationPlateNumber` varchar(15) DEFAULT NULL,
  `VehicleType` varchar(20) DEFAULT NULL,
  `Model` varchar(100) DEFAULT NULL,
  `SeatingCapacity` int(11) DEFAULT NULL,
  `KmPerLiter` decimal(5,2) DEFAULT NULL,
  `FuelType` varchar(20) DEFAULT NULL,
  `Availability` tinyint(1) DEFAULT NULL,
  `DailyPrice` decimal(10,2) DEFAULT NULL,
  `OverduePrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`VehicleID`),
  UNIQUE KEY `unique_registration_plate` (`RegistrationPlateNumber`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES ('V001','CAD-2546','Car','Toyota Corolla',5,7.00,'Petrol',1,2000.00,2500.00),('V002','CAD_2244','Car','Toyota Supra',2,10.00,'Petrol',1,3000.00,3500.00),('V003','CAD-1253','Car','TATA Nano',4,5.00,'Petrol',1,1500.00,2000.00),('V004','EAD-2548','MiniVan','Suzuki Every',5,6.80,'Petrol',1,3500.00,4000.00);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-20 13:18:30
