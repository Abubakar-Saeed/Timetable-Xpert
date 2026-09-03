-- Generated from time_table_automation_backup.sql for the bundled MariaDB engine.
-- Sanitised: collation -> utf8mb4_general_ci, DEFINER clauses removed,
-- SQL_SAFE_UPDATES toggles removed, admin row reset to admin/admin.
CREATE DATABASE IF NOT EXISTS `time_table_automation` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `time_table_automation`;

-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: time_table_automation
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `admintable`
--

DROP TABLE IF EXISTS `admintable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admintable` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(150) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admintable`
--

LOCK TABLES `admintable` WRITE;
/*!40000 ALTER TABLE `admintable` DISABLE KEYS */;
INSERT INTO `admintable` VALUES (1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin@timetablexpert.local',NULL);
/*!40000 ALTER TABLE `admintable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `all_subjects_view`
--

DROP TABLE IF EXISTS `all_subjects_view`;
/*!50001 DROP VIEW IF EXISTS `all_subjects_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `all_subjects_view` AS SELECT 
 1 AS `programSemesterSubjectID`,
 1 AS `SSTitle`,
 1 AS `programSemesterID`,
 1 AS `LabID`,
 1 AS `Title`,
 1 AS `ProgramSemesterisActive`,
 1 AS `ProgramID`,
 1 AS `Program`,
 1 AS `SemesterID`,
 1 AS `Semester`,
 1 AS `SemesterIsActive`,
 1 AS `Capacity`,
 1 AS `lectureSubjectID`,
 1 AS `SubjectTitle`,
 1 AS `lectureID`,
 1 AS `professorName`,
 1 AS `courseID`,
 1 AS `courseCode`,
 1 AS `creditHours`,
 1 AS `roomTypeID`,
 1 AS `courseTitle`,
 1 AS `SessionID`,
 1 AS `Session`,
 1 AS `timetabletypeID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `alllabtimetable`
--

DROP TABLE IF EXISTS `alllabtimetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alllabtimetable` (
  `ROOMID` int DEFAULT NULL,
  `ROOMNAME` varchar(300) DEFAULT NULL,
  `TIME` varchar(300) DEFAULT NULL,
  `MONDAY` varchar(300) DEFAULT NULL,
  `TUESDAY` varchar(300) DEFAULT NULL,
  `WEDNESDAY` varchar(300) DEFAULT NULL,
  `THURSDAY` varchar(300) DEFAULT NULL,
  `FRIDAY` varchar(300) DEFAULT NULL,
  `SATURDAY` varchar(300) DEFAULT NULL,
  `SUNDAY` varchar(300) DEFAULT NULL,
  `programID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alllabtimetable`
--

LOCK TABLES `alllabtimetable` WRITE;
/*!40000 ALTER TABLE `alllabtimetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `alllabtimetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allroomtimetable`
--

DROP TABLE IF EXISTS `allroomtimetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allroomtimetable` (
  `ROOMID` int DEFAULT NULL,
  `ROOMNAME` varchar(300) DEFAULT NULL,
  `TIME` varchar(300) DEFAULT NULL,
  `MONDAY` varchar(300) DEFAULT NULL,
  `TUESDAY` varchar(300) DEFAULT NULL,
  `WEDNESDAY` varchar(300) DEFAULT NULL,
  `THURSDAY` varchar(300) DEFAULT NULL,
  `FRIDAY` varchar(300) DEFAULT NULL,
  `SATURDAY` varchar(300) DEFAULT NULL,
  `SUNDAY` varchar(300) DEFAULT NULL,
  `programID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allroomtimetable`
--

LOCK TABLES `allroomtimetable` WRITE;
/*!40000 ALTER TABLE `allroomtimetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `allroomtimetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allsemestertimetable`
--

DROP TABLE IF EXISTS `allsemestertimetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allsemestertimetable` (
  `timeTableID` int DEFAULT NULL,
  `semester` varchar(300) DEFAULT NULL,
  `timee` varchar(300) DEFAULT NULL,
  `MONDAY` varchar(300) DEFAULT NULL,
  `TUESDAY` varchar(300) DEFAULT NULL,
  `WEDNESDAY` varchar(300) DEFAULT NULL,
  `THURSDAY` varchar(300) DEFAULT NULL,
  `FRIDAY` varchar(300) DEFAULT NULL,
  `SATURDAY` varchar(300) DEFAULT NULL,
  `SUNDAY` varchar(300) DEFAULT NULL,
  `programID` int DEFAULT NULL,
  `season` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allsemestertimetable`
--

LOCK TABLES `allsemestertimetable` WRITE;
/*!40000 ALTER TABLE `allsemestertimetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `allsemestertimetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allteachertimetable`
--

DROP TABLE IF EXISTS `allteachertimetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allteachertimetable` (
  `TEACHERID` int DEFAULT NULL,
  `TEACHERNAME` varchar(300) DEFAULT NULL,
  `TIME` varchar(300) DEFAULT NULL,
  `MONDAY` varchar(300) DEFAULT NULL,
  `TUESDAY` varchar(300) DEFAULT NULL,
  `WEDNESDAY` varchar(300) DEFAULT NULL,
  `THURSDAY` varchar(300) DEFAULT NULL,
  `FRIDAY` varchar(300) DEFAULT NULL,
  `SATURDAY` varchar(300) DEFAULT NULL,
  `SUNDAY` varchar(300) DEFAULT NULL,
  `programID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allteachertimetable`
--

LOCK TABLES `allteachertimetable` WRITE;
/*!40000 ALTER TABLE `allteachertimetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `allteachertimetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursetable`
--

DROP TABLE IF EXISTS `coursetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursetable` (
  `courseID` int NOT NULL AUTO_INCREMENT,
  `courseCode` varchar(15) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `crHrs` int DEFAULT NULL,
  `roomTypeID` int DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  `programID` int DEFAULT NULL,
  `semesterID` int DEFAULT NULL,
  `courseViewID` int DEFAULT NULL,
  PRIMARY KEY (`courseID`),
  KEY `courseViewID` (`courseViewID`),
  CONSTRAINT `coursetable_ibfk_1` FOREIGN KEY (`courseViewID`) REFERENCES `courseview` (`courseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursetable`
--

LOCK TABLES `coursetable` WRITE;
/*!40000 ALTER TABLE `coursetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `coursetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseview`
--

DROP TABLE IF EXISTS `courseview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courseview` (
  `courseID` int NOT NULL AUTO_INCREMENT,
  `courseCode` varchar(30) DEFAULT NULL,
  `courseTitle` varchar(200) DEFAULT NULL,
  `creditHours` int DEFAULT NULL,
  `program` varchar(50) DEFAULT NULL,
  `semester` varchar(50) DEFAULT NULL,
  `programID` int DEFAULT NULL,
  PRIMARY KEY (`courseID`),
  KEY `programID` (`programID`),
  CONSTRAINT `courseview_ibfk_1` FOREIGN KEY (`programID`) REFERENCES `programtable` (`programID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseview`
--

LOCK TABLES `courseview` WRITE;
/*!40000 ALTER TABLE `courseview` DISABLE KEYS */;
/*!40000 ALTER TABLE `courseview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daytable`
--

DROP TABLE IF EXISTS `daytable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daytable` (
  `dayID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`dayID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daytable`
--

LOCK TABLES `daytable` WRITE;
/*!40000 ALTER TABLE `daytable` DISABLE KEYS */;
INSERT INTO `daytable` VALUES (1,'MONDAY',_binary ''),(2,'TUESDAY',_binary ''),(3,'WEDNESDAY',_binary ''),(4,'THURSDAY',_binary ''),(5,'FRIDAY',_binary '');
/*!40000 ALTER TABLE `daytable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daytimeslottable`
--

DROP TABLE IF EXISTS `daytimeslottable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daytimeslottable` (
  `dayTimeSlotID` int NOT NULL AUTO_INCREMENT,
  `slotTitle` varchar(50) NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `dayID` int NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  `timetabletypeID` int NOT NULL,
  `weight` int DEFAULT NULL,
  PRIMARY KEY (`dayTimeSlotID`),
  KEY `dayID` (`dayID`),
  KEY `dayTimeSlotIndex` (`slotTitle`,`weight`),
  KEY `slot_idx` (`slotTitle`),
  KEY `type_idx` (`timetabletypeID`),
  KEY `weight_idx` (`weight`),
  CONSTRAINT `daytimeslottable_ibfk_1` FOREIGN KEY (`dayID`) REFERENCES `daytable` (`dayID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daytimeslottable`
--

LOCK TABLES `daytimeslottable` WRITE;
/*!40000 ALTER TABLE `daytimeslottable` DISABLE KEYS */;
INSERT INTO `daytimeslottable` VALUES (1,'08:00 - 09:00','08:00:00','09:00:00',1,_binary '',1,6),(2,'09:00 - 10:00','09:00:00','10:00:00',1,_binary '',1,6),(3,'10:00 - 11:00','10:00:00','11:00:00',1,_binary '',1,6),(4,'11:00 - 12:00','11:00:00','12:00:00',1,_binary '',1,4),(5,'12:00 - 01:00','12:00:00','13:00:00',1,_binary '',1,3),(6,'08:00 - 09:00','08:00:00','09:00:00',2,_binary '',1,NULL),(7,'09:00 - 10:00','09:00:00','10:00:00',2,_binary '',1,NULL),(8,'10:00 - 11:00','10:00:00','11:00:00',2,_binary '',1,NULL),(9,'11:00 - 12:00','11:00:00','12:00:00',2,_binary '',1,NULL),(10,'12:00 - 01:00','12:00:00','13:00:00',2,_binary '',1,NULL),(11,'08:00 - 09:00','08:00:00','09:00:00',3,_binary '',1,NULL),(12,'09:00 - 10:00','09:00:00','10:00:00',3,_binary '',1,NULL),(13,'10:00 - 11:00','10:00:00','11:00:00',3,_binary '',1,NULL),(14,'11:00 - 12:00','11:00:00','12:00:00',3,_binary '',1,NULL),(15,'12:00 - 01:00','12:00:00','13:00:00',3,_binary '',1,NULL),(16,'08:00 - 09:00','08:00:00','09:00:00',4,_binary '',1,NULL),(17,'09:00 - 10:00','09:00:00','10:00:00',4,_binary '',1,NULL),(18,'10:00 - 11:00','10:00:00','11:00:00',4,_binary '',1,NULL),(19,'11:00 - 12:00','11:00:00','12:00:00',4,_binary '',1,NULL),(20,'12:00 - 01:00','12:00:00','13:00:00',4,_binary '',1,NULL),(21,'08:00 - 09:00','08:00:00','09:00:00',5,_binary '',1,NULL),(22,'09:00 - 10:00','09:00:00','10:00:00',5,_binary '',1,NULL),(23,'10:00 - 11:00','10:00:00','11:00:00',5,_binary '',1,NULL),(24,'11:00 - 12:00','11:00:00','12:00:00',5,_binary '',1,NULL),(25,'12:00 - 01:00','12:00:00','13:00:00',5,_binary '',1,NULL),(26,'11:00 - 12:00','11:00:00','12:00:00',1,_binary '',2,3),(27,'12:00 - 01:00','12:00:00','13:00:00',1,_binary '',2,4),(28,'01:00 - 02:00','13:00:00','14:00:00',1,_binary '',2,6),(29,'02:00 - 03:00','14:00:00','15:00:00',1,_binary '',2,6),(30,'03:00 - 04:00','15:00:00','16:00:00',1,_binary '',2,6),(31,'11:00 - 12:00','11:00:00','12:00:00',2,_binary '',2,NULL),(32,'12:00 - 01:00','12:00:00','13:00:00',2,_binary '',2,NULL),(33,'01:00 - 02:00','13:00:00','14:00:00',2,_binary '',2,NULL),(34,'02:00 - 03:00','14:00:00','15:00:00',2,_binary '',2,NULL),(35,'03:00 - 04:00','15:00:00','16:00:00',2,_binary '',2,NULL),(36,'11:00 - 12:00','11:00:00','12:00:00',3,_binary '',2,NULL),(37,'12:00 - 01:00','12:00:00','13:00:00',3,_binary '',2,NULL),(38,'01:00 - 02:00','13:00:00','14:00:00',3,_binary '',2,NULL),(39,'02:00 - 03:00','14:00:00','15:00:00',3,_binary '',2,NULL),(40,'03:00 - 04:00','15:00:00','16:00:00',3,_binary '',2,NULL),(41,'11:00 - 12:00','11:00:00','12:00:00',4,_binary '',2,NULL),(42,'12:00 - 01:00','12:00:00','13:00:00',4,_binary '',2,NULL),(43,'01:00 - 02:00','13:00:00','14:00:00',4,_binary '',2,NULL),(44,'02:00 - 03:00','14:00:00','15:00:00',4,_binary '',2,NULL),(45,'03:00 - 04:00','15:00:00','16:00:00',4,_binary '',2,NULL),(46,'11:00 - 12:00','11:00:00','12:00:00',5,_binary '',2,NULL),(47,'12:00 - 01:00','12:00:00','13:00:00',5,_binary '',2,NULL),(48,'01:00 - 02:00','13:00:00','14:00:00',5,_binary '',2,NULL),(49,'02:00 - 03:00','14:00:00','15:00:00',5,_binary '',2,NULL),(50,'03:00 - 04:00','15:00:00','16:00:00',5,_binary '',2,NULL);
/*!40000 ALTER TABLE `daytimeslottable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daytimetabledetails`
--

DROP TABLE IF EXISTS `daytimetabledetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daytimetabledetails` (
  `RowNo` int DEFAULT NULL,
  `TimeTableID` int DEFAULT NULL,
  `ProgramSemesterSubjectID` int DEFAULT NULL,
  `SubjectTitle` varchar(400) DEFAULT NULL,
  `RoomID` int DEFAULT NULL,
  `LabID` int DEFAULT NULL,
  `DayTimeSlotID` int DEFAULT NULL,
  `SlotTitle` varchar(200) DEFAULT NULL,
  `DayTitle` varchar(80) DEFAULT NULL,
  `LectureID` int DEFAULT NULL,
  `DayID` int DEFAULT NULL,
  `IsActive` bit(1) DEFAULT NULL,
  KEY `TimeTableID` (`TimeTableID`),
  KEY `ProgramSemesterSubjectID` (`ProgramSemesterSubjectID`),
  KEY `RoomID` (`RoomID`),
  KEY `LabID` (`LabID`),
  KEY `DayTimeSlotID` (`DayTimeSlotID`),
  KEY `DayID` (`DayID`),
  KEY `LectureID` (`LectureID`),
  CONSTRAINT `daytimetabledetails_ibfk_1` FOREIGN KEY (`TimeTableID`) REFERENCES `timetbltable` (`timeTableID`),
  CONSTRAINT `daytimetabledetails_ibfk_2` FOREIGN KEY (`ProgramSemesterSubjectID`) REFERENCES `programsemestersubjecttable` (`programSemesterSubjectID`),
  CONSTRAINT `daytimetabledetails_ibfk_3` FOREIGN KEY (`RoomID`) REFERENCES `roomtable` (`roomID`),
  CONSTRAINT `daytimetabledetails_ibfk_4` FOREIGN KEY (`LabID`) REFERENCES `labtable` (`labID`),
  CONSTRAINT `daytimetabledetails_ibfk_5` FOREIGN KEY (`DayTimeSlotID`) REFERENCES `daytimeslottable` (`dayTimeSlotID`),
  CONSTRAINT `daytimetabledetails_ibfk_6` FOREIGN KEY (`DayID`) REFERENCES `daytable` (`dayID`),
  CONSTRAINT `daytimetabledetails_ibfk_7` FOREIGN KEY (`LectureID`) REFERENCES `lecturetable` (`lectureID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daytimetabledetails`
--

LOCK TABLES `daytimetabledetails` WRITE;
/*!40000 ALTER TABLE `daytimetabledetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `daytimetabledetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixedslotstable`
--

DROP TABLE IF EXISTS `fixedslotstable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixedslotstable` (
  `slotID` int NOT NULL AUTO_INCREMENT,
  `TimeTableID` int NOT NULL,
  `lectureID` int NOT NULL,
  `teacherName` varchar(255) NOT NULL,
  `dayID` int NOT NULL,
  `dayTimeSlotID` int NOT NULL,
  `slotTitle` varchar(255) NOT NULL,
  `slotKey` varchar(255) NOT NULL DEFAULT '',
  `course` varchar(255) NOT NULL,
  `courseKey` varchar(255) NOT NULL DEFAULT '',
  `TimeTableTypeID` int NOT NULL,
  `programID` int NOT NULL,
  `roomID` int NOT NULL,
  `labID` int NOT NULL,
  `isreserved` bit(1) DEFAULT b'1',
  PRIMARY KEY (`slotID`),
  -- One composite index covering the conflict probes (all filter dayID first),
  -- replacing the 11 single-column indexes that could not serve the multi-column
  -- WHERE clauses and slowed every INSERT.
  KEY `idx_probe` (`dayID`,`slotKey`,`lectureID`,`TimeTableTypeID`,`TimeTableID`,`programID`,`roomID`,`labID`,`courseKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixedslotstable`
--

LOCK TABLES `fixedslotstable` WRITE;
/*!40000 ALTER TABLE `fixedslotstable` DISABLE KEYS */;
/*!40000 ALTER TABLE `fixedslotstable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labtable`
--

DROP TABLE IF EXISTS `labtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `labtable` (
  `labID` int NOT NULL AUTO_INCREMENT,
  `labNo` varchar(100) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `programID` int DEFAULT NULL,
  `programName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`labID`),
  KEY `programID` (`programID`),
  KEY `idx_LAB_program` (`labID`,`labNo`,`programID`,`programName`),
  CONSTRAINT `labtable_ibfk_1` FOREIGN KEY (`programID`) REFERENCES `programtable` (`programID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labtable`
--

LOCK TABLES `labtable` WRITE;
/*!40000 ALTER TABLE `labtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `labtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `lecture_view`
--

DROP TABLE IF EXISTS `lecture_view`;
/*!50001 DROP VIEW IF EXISTS `lecture_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `lecture_view` AS SELECT 
 1 AS `lectureSubjectID`,
 1 AS `title`,
 1 AS `lectureID`,
 1 AS `courseID`,
 1 AS `professorName`,
 1 AS `courseCode`,
 1 AS `courseTitle`,
 1 AS `creditHours`,
 1 AS `roomTypeID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `lecturesubjecttable`
--

DROP TABLE IF EXISTS `lecturesubjecttable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturesubjecttable` (
  `lectureSubjectID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `lectureID` int NOT NULL,
  `courseID` int NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  `programSemesterID` int DEFAULT NULL,
  `programSemesterSubjectViewID` int DEFAULT NULL,
  PRIMARY KEY (`lectureSubjectID`),
  KEY `lectureID` (`lectureID`),
  KEY `courseID` (`courseID`),
  CONSTRAINT `lecturesubjecttable_ibfk_1` FOREIGN KEY (`lectureID`) REFERENCES `lecturetable` (`lectureID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturesubjecttable`
--

LOCK TABLES `lecturesubjecttable` WRITE;
/*!40000 ALTER TABLE `lecturesubjecttable` DISABLE KEYS */;
/*!40000 ALTER TABLE `lecturesubjecttable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturetable`
--

DROP TABLE IF EXISTS `lecturetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturetable` (
  `lectureID` int NOT NULL AUTO_INCREMENT,
  `fullName` varchar(150) DEFAULT NULL,
  `contactNo` varchar(50) DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  `programID` int DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `programName` varchar(50) DEFAULT NULL,
  `type` bit(1) DEFAULT NULL,
  PRIMARY KEY (`lectureID`),
  KEY `programID` (`programID`),
  CONSTRAINT `lecturetable_ibfk_1` FOREIGN KEY (`programID`) REFERENCES `programtable` (`programID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturetable`
--

LOCK TABLES `lecturetable` WRITE;
/*!40000 ALTER TABLE `lecturetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `lecturetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programsemestersubjecttable`
--

DROP TABLE IF EXISTS `programsemestersubjecttable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programsemestersubjecttable` (
  `programSemesterSubjectID` int NOT NULL AUTO_INCREMENT,
  `programSemesterID` int DEFAULT NULL,
  `lectureSubjectID` int DEFAULT NULL,
  `title` varchar(300) DEFAULT NULL,
  `isSubjectActive` bit(1) DEFAULT b'1',
  `labID` int DEFAULT '0',
  `lab` varchar(100) DEFAULT ' ',
  `programSemesterSubjectViewID` int DEFAULT NULL,
  `timetableTypeID` int DEFAULT NULL,
  `programID` int DEFAULT NULL,
  PRIMARY KEY (`programSemesterSubjectID`),
  KEY `lectureSubjectID` (`lectureSubjectID`),
  CONSTRAINT `programsemestersubjecttable_ibfk_1` FOREIGN KEY (`lectureSubjectID`) REFERENCES `lecturesubjecttable` (`lectureSubjectID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programsemestersubjecttable`
--

LOCK TABLES `programsemestersubjecttable` WRITE;
/*!40000 ALTER TABLE `programsemestersubjecttable` DISABLE KEYS */;
/*!40000 ALTER TABLE `programsemestersubjecttable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programsemestersubjecttableview`
--

DROP TABLE IF EXISTS `programsemestersubjecttableview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programsemestersubjecttableview` (
  `ProgramSemesterSubjectViewID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(400) DEFAULT NULL,
  `Lab` varchar(200) DEFAULT ' ',
  `Section` varchar(100) DEFAULT NULL,
  `timeTableTypeID` int DEFAULT NULL,
  `programName` varchar(100) DEFAULT NULL,
  `semester` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ProgramSemesterSubjectViewID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programsemestersubjecttableview`
--

LOCK TABLES `programsemestersubjecttableview` WRITE;
/*!40000 ALTER TABLE `programsemestersubjecttableview` DISABLE KEYS */;
/*!40000 ALTER TABLE `programsemestersubjecttableview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programsemestertable`
--

DROP TABLE IF EXISTS `programsemestertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programsemestertable` (
  `programSemesterID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `SemesterID` int DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `capacity` int DEFAULT NULL,
  `timetabletypeID` int DEFAULT NULL,
  `programName` varchar(100) DEFAULT NULL,
  `totalCreditHours` int NOT NULL,
  `programID` int DEFAULT NULL,
  `sessionID` int DEFAULT NULL,
  PRIMARY KEY (`programSemesterID`),
  KEY `idx_programSemesterID` (`programSemesterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programsemestertable`
--

LOCK TABLES `programsemestertable` WRITE;
/*!40000 ALTER TABLE `programsemestertable` DISABLE KEYS */;
/*!40000 ALTER TABLE `programsemestertable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programtable`
--

DROP TABLE IF EXISTS `programtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programtable` (
  `programID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`programID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programtable`
--

LOCK TABLES `programtable` WRITE;
/*!40000 ALTER TABLE `programtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `programtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomtable`
--

DROP TABLE IF EXISTS `roomtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomtable` (
  `roomID` int NOT NULL AUTO_INCREMENT,
  `roomNo` varchar(100) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `programID` int DEFAULT NULL,
  `programName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`roomID`),
  KEY `programID` (`programID`),
  KEY `roomtable` (`roomNo`),
  KEY `idx_room_program` (`roomID`,`roomNo`,`programID`,`programName`),
  CONSTRAINT `roomtable_ibfk_1` FOREIGN KEY (`programID`) REFERENCES `programtable` (`programID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomtable`
--

LOCK TABLES `roomtable` WRITE;
/*!40000 ALTER TABLE `roomtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomtypetable`
--

DROP TABLE IF EXISTS `roomtypetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomtypetable` (
  `roomTypeID` int NOT NULL,
  `typeName` varchar(20) NOT NULL,
  KEY `roomTypeID_index` (`roomTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomtypetable`
--

LOCK TABLES `roomtypetable` WRITE;
/*!40000 ALTER TABLE `roomtypetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomtypetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sectiontable`
--

DROP TABLE IF EXISTS `sectiontable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sectiontable` (
  `sectionID` int NOT NULL AUTO_INCREMENT,
  `sectionTitle` varchar(150) NOT NULL,
  `programSemesterID` int NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  `sectionCapacity` int NOT NULL DEFAULT '40',
  PRIMARY KEY (`sectionID`),
  KEY `programSemesterID` (`programSemesterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sectiontable`
--

LOCK TABLES `sectiontable` WRITE;
/*!40000 ALTER TABLE `sectiontable` DISABLE KEYS */;
/*!40000 ALTER TABLE `sectiontable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semestertable`
--

DROP TABLE IF EXISTS `semestertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semestertable` (
  `semesterID` int NOT NULL AUTO_INCREMENT,
  `semesterName` varchar(50) NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`semesterID`),
  KEY `idx_semester_name` (`semesterName`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semestertable`
--

LOCK TABLES `semestertable` WRITE;
/*!40000 ALTER TABLE `semestertable` DISABLE KEYS */;
INSERT INTO `semestertable` VALUES (1,'1st Semester',_binary ''),(2,'2nd Semester',_binary ''),(3,'3rd Semester',_binary ''),(4,'4th Semester',_binary ''),(5,'5th Semester',_binary ''),(6,'6th Semester',_binary ''),(7,'7th Semester',_binary ''),(8,'8th Semester',_binary '');
/*!40000 ALTER TABLE `semestertable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessionprogramtable`
--

DROP TABLE IF EXISTS `sessionprogramtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessionprogramtable` (
  `sessionProgramID` int NOT NULL AUTO_INCREMENT,
  `sessionProgramName` varchar(200) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `programID` int DEFAULT NULL,
  `sessionID` int DEFAULT NULL,
  PRIMARY KEY (`sessionProgramID`),
  KEY `sessionID` (`sessionID`),
  KEY `programID` (`programID`),
  CONSTRAINT `sessionprogramtable_ibfk_1` FOREIGN KEY (`sessionID`) REFERENCES `sessiontable` (`sessionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sessionprogramtable_ibfk_2` FOREIGN KEY (`programID`) REFERENCES `programtable` (`programID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessionprogramtable`
--

LOCK TABLES `sessionprogramtable` WRITE;
/*!40000 ALTER TABLE `sessionprogramtable` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessionprogramtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessiontable`
--

DROP TABLE IF EXISTS `sessiontable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessiontable` (
  `sessionID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `isActive` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`sessionID`),
  KEY `idx_session_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessiontable`
--

LOCK TABLES `sessiontable` WRITE;
/*!40000 ALTER TABLE `sessiontable` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessiontable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studenttable`
--

DROP TABLE IF EXISTS `studenttable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studenttable` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(150) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studenttable`
--

LOCK TABLES `studenttable` WRITE;
/*!40000 ALTER TABLE `studenttable` DISABLE KEYS */;
/*!40000 ALTER TABLE `studenttable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetabledetailstable`
--

DROP TABLE IF EXISTS `timetabledetailstable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timetabledetailstable` (
  `timeTableDetailID` int NOT NULL AUTO_INCREMENT,
  `timeTableID` int DEFAULT NULL,
  `programSemesterSubjectID` int DEFAULT NULL,
  `subjectTitle` varchar(400) DEFAULT NULL,
  `roomID` int DEFAULT NULL,
  `labID` int DEFAULT NULL,
  `dayTimeSlotID` int DEFAULT NULL,
  `lectureID` int DEFAULT NULL,
  `dayID` int DEFAULT NULL,
  `IsActive` bit(1) NOT NULL DEFAULT b'1',
  `SessionID` int DEFAULT NULL,
  `SessionTitle` varchar(250) DEFAULT NULL,
  `slotTitle` varchar(200) DEFAULT NULL,
  `dayTitle` varchar(50) DEFAULT NULL,
  `lectureSubjectTitle` varchar(200) DEFAULT NULL,
  `crHrs` int DEFAULT NULL,
  `courseCode` varchar(50) DEFAULT NULL,
  `timetableTypeID` int DEFAULT NULL,
  `programID` int DEFAULT NULL,
  PRIMARY KEY (`timeTableDetailID`),
  KEY `timeTableID` (`timeTableID`),
  KEY `programSemesterSubjectID` (`programSemesterSubjectID`),
  KEY `roomID` (`roomID`),
  KEY `labID` (`labID`),
  KEY `dayTimeSlotID` (`dayTimeSlotID`),
  KEY `FK_LectureID` (`lectureID`),
  KEY `FK_DayID` (`dayID`),
  KEY `FK_SessionID` (`SessionID`),
  KEY `FK_SessionTitle` (`SessionTitle`),
  CONSTRAINT `FK_DayID` FOREIGN KEY (`dayID`) REFERENCES `daytable` (`dayID`),
  CONSTRAINT `fk_labID` FOREIGN KEY (`labID`) REFERENCES `labtable` (`labID`),
  CONSTRAINT `fk_lectureID` FOREIGN KEY (`lectureID`) REFERENCES `lecturetable` (`lectureID`),
  CONSTRAINT `fk_roomID` FOREIGN KEY (`roomID`) REFERENCES `roomtable` (`roomID`),
  CONSTRAINT `timetabledetailstable_ibfk_1` FOREIGN KEY (`timeTableID`) REFERENCES `timetbltable` (`timeTableID`),
  CONSTRAINT `timetabledetailstable_ibfk_5` FOREIGN KEY (`dayTimeSlotID`) REFERENCES `daytimeslottable` (`dayTimeSlotID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetabledetailstable`
--

LOCK TABLES `timetabledetailstable` WRITE;
/*!40000 ALTER TABLE `timetabledetailstable` DISABLE KEYS */;
/*!40000 ALTER TABLE `timetabledetailstable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetabletypetable`
--

DROP TABLE IF EXISTS `timetabletypetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timetabletypetable` (
  `timeTableTypeID` int DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetabletypetable`
--

LOCK TABLES `timetabletypetable` WRITE;
/*!40000 ALTER TABLE `timetabletypetable` DISABLE KEYS */;
INSERT INTO `timetabletypetable` VALUES (1,'Morning'),(2,'Replica');
/*!40000 ALTER TABLE `timetabletypetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetbltable`
--

DROP TABLE IF EXISTS `timetbltable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timetbltable` (
  `timeTableID` int NOT NULL AUTO_INCREMENT,
  `programSemesterID` int NOT NULL,
  `sessionID` int NOT NULL,
  `TimeTableTitle` varchar(400) NOT NULL,
  `SemesterTitle` varchar(300) NOT NULL,
  `SessionTitle` varchar(300) NOT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `TimeTableTypeID` int DEFAULT NULL,
  `programName` varchar(50) DEFAULT NULL,
  `season` varchar(50) DEFAULT NULL,
  `programID` int DEFAULT NULL,
  PRIMARY KEY (`timeTableID`),
  KEY `programSemesterID` (`programSemesterID`),
  KEY `FK_SessionTable_sessionID` (`sessionID`),
  CONSTRAINT `FK_SessionTable_sessionID` FOREIGN KEY (`sessionID`) REFERENCES `sessiontable` (`sessionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetbltable`
--

LOCK TABLES `timetbltable` WRITE;
/*!40000 ALTER TABLE `timetbltable` DISABLE KEYS */;
/*!40000 ALTER TABLE `timetbltable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_allactivetimeslots`
--

DROP TABLE IF EXISTS `v_allactivetimeslots`;
/*!50001 DROP VIEW IF EXISTS `v_allactivetimeslots`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_allactivetimeslots` AS SELECT 
 1 AS `DayTimeSlotID`,
 1 AS `SlotTitle`,
 1 AS `StartTime`,
 1 AS `DayID`,
 1 AS `Name`,
 1 AS `EndTime`,
 1 AS `DayStatus`,
 1 AS `timetabletypeID`,
 1 AS `SlotStatus`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_programsemesteractivelist`
--

DROP TABLE IF EXISTS `v_programsemesteractivelist`;
/*!50001 DROP VIEW IF EXISTS `v_programsemesteractivelist`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_programsemesteractivelist` AS SELECT 
 1 AS `ProgramSemesterID`,
 1 AS `Title`,
 1 AS `ProgramSemesterIsActive`,
 1 AS `ProgramID`,
 1 AS `Program`,
 1 AS `ProgramIsActive`,
 1 AS `SemesterID`,
 1 AS `Semester`,
 1 AS `SemesterIsActive`,
 1 AS `Capacity`,
 1 AS `SessionID`,
 1 AS `Session`,
 1 AS `timeTabletypeID`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'time_table_automation'
--
/*!50003 DROP PROCEDURE IF EXISTS `GenerateTimeTableForAllSessions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `GenerateTimeTableForAllSessions`(OUT Message varchar(100))
BEGIN
   -- -------------------- Step's : Genetic Algorithem  -------------------=====================================================================
 SET autocommit=0; 
   SET @currentMonth = MONTH(CURDATE());
    SET @currentYear = YEAR(CURDATE()); 

    if @currentMonth IN (9, 10, 11, 12) THEN
        SET @season = CONCAT('Finalized Timetable Fall ', @currentYear);
    ELSEIF @currentMonth IN (1,2, 3, 4, 5, 6) THEN
        SET @season = CONCAT('Finalized Timetable Spring ', @currentYear);
    ELSEIF @currentMonth IN (7, 8) THEN
        SET @season = CONCAT('Finalized Timetable Summer ', @currentYear);
    END IF;


-- First Step (Here we are getting Chromosomes/Gene)
-- 	Polulation is All Time Slots in Time Table
-- 	Chromosomes is All Subjects
-- 	Gene is one Subject
-- Get All Sujects -- All Subject here Chromosomes, and one subject is Gen
    
-- Get All Subjects -- Chromosomes

  drop temporary table if exists FixedSlotsTable;
 
-- DROP INDEX  auth ON fixedslotstable;

alter table timetabledetailstable auto_increment = 1;
delete from FixedSlotsTable ; 


drop temporary table if exists SUBJECTSEMESTERTABLE;
    CREATE TEMPORARY TABLE SUBJECTSEMESTERTABLE(
    
		rowNo int,
        programSemesterID int,
        crHrs int,
        courseCode varchar(50),
        labID int,
        programSemesterSubjectID int,
        ssTitle varchar(300),
        title varchar(200),
        roomTypeID int,
        lectureID int,
        sessionID int,
        sessionTitle varchar(200),
        timeTableTypeID int
        
    );

drop temporary table if exists Header;
CREATE TEMPORARY TABLE Header (

    TimeTableID INT,
    SessionID INT,
    SessionTitle varchar(500),
    ProgramSemesterID INT,
    TimeTableTitle varchar(200),
    SemesterTitle varchar(200),
    TimeTableTypeID int,
	programName varchar(50),
    season varchar (50),
    programID int

);
-- Create temporary table for Details
drop temporary table if exists Details;
CREATE TEMPORARY TABLE Details (

    TimeTableID INT,
    SessionID INT,
    SessionTitle varchar(500),
    ProgramSemesterSubjectID INT,
    SubjectTitle varchar(400),
    crHrs int,
	courseCode varchar(50),
    RoomID INT,
    LabID INT,
    DayTimeSlotID INT,
    LectureID INT,
    DayID INT,
    slotTitle varchar(200),
    dayTitle varchar(200),
    isActive bit default 1,
    timeTableTypeID int,
    programID int
    
);
drop temporary table if exists AllSemester;
CREATE TEMPORARY TABLE AllSemester (

    RowNo INT PRIMARY KEY AUTO_INCREMENT,
    ProgramSemesterID INT,
    Title VARCHAR(300),
    SessionID INT,
    SessionTitle VARCHAR(500),
    TimeTableTypeID int,
    programName varchar(50),
    season varchar (50),
    programID int
);

set @semesterStart = (SELECT MIN(DISTINCT programSemesterID) FROM programsemestersubjecttable  limit 1);
set @semesterEnd = (SELECT MAX(DISTINCT programSemesterID) FROM programsemestersubjecttable  limit 1);
While @semesterStart <= @semesterEnd    do
Begin
    
SET @creditHours := (select sum(creditHours) from all_subjects_view where programSemesterID = @semesterStart limit 1);
SET @totalCreditHours := (SELECT totalCreditHours FROM programSemesterTable where programSemesterID = @semesterStart limit 1)  ;
set @title = (select title from programsemestertable where programSemesterID = @semesterStart limit 1);
set @section = (select timetabletypeID from programsemestertable where programSemesterID = @semesterStart limit 1);

IF @creditHours < @TotalCreditHours  THEN


	if @section = 1 then
     SET @errorMessage = CONCAT('Please allocate more courses! ',@title, ' has not allocated enough credit hours.');
	else
	SET @errorMessage = CONCAT('Please allocate more courses! ',@title, ' (Replica) ', ' has not allocated enough credit hours.');
    end if;
    
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;
    	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;


ELSE
    SET @Message := 'Credit Hours are enough';
end if;

set @semesterStart = @semesterStart + 1;

END;
END While;

set @programStart = (select MIN(programID) from all_subjects_view limit 1);
set @programEnd = (select MAX(programID) from all_subjects_view limit 1);

IF @programStart is null then

   SET @errorMessage = CONCAT('Please Add Classes first! ');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT =  @errorMessage;
    	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;


end if;
While @programStart <= @programEnd    do
	Begin
    
SET @TotalRequiredLabRooms := (SELECT COUNT(DISTINCT labID) FROM programsemestersubjecttable where programID = @programStart  and timeTableTypeID = 1 and labID > 0);
SET @TotalRequiredRooms := (SELECT COUNT(DISTINCT programSemesterID) FROM programsemestersubjecttable where programID = @programStart   and timeTableTypeID = 1 )  ;
SET @AvailableRooms := (select count(*) from roomtable  where programID = @programStart  );
SET @AvailableLabs := (select count(*) from labtable  where programID = @programStart );

set @programTitle= (select name from programtable  where programID = @programStart limit 1);



IF @AvailableRooms < @TotalRequiredRooms  THEN

	     SET @errorMessage = CONCAT('Please Add more Rooms! ', @programTitle, 'has less rooms then practical classes.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT =  @errorMessage;
    	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;


ELSE
    SET @Message := 'Room are enough';
END IF;

IF @AvailableLabs < @TotalRequiredLabRooms  THEN

	     SET @errorMessage = CONCAT('Please Add more lab! ',@programTitle, ' has less lab then classes.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT =  @errorMessage;
            	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;


ELSE
    SET @Message := 'Labs are enough';
END IF;
    end;
    
    set @programStart = @programStart + 1;
    
END While;


set @programStart = (select MIN(programID) from all_subjects_view limit 1);
set @programEnd = (select MAX(programID) from all_subjects_view limit 1);
IF @creditHours < @TotalCreditHours  THEN

	set @programStart = 0;
	set @programEnd = -1;
    
end if;
IF @AvailableRooms < @TotalRequiredRooms  THEN
	set @programStart = 0;
	set @programEnd = -1;
	    
END IF;

IF @AvailableLabs < @TotalRequiredLabRooms  THEN

	set @programStart = 0;
	set @programEnd = -1;
		
END IF;

While @programStart <= @programEnd    do
set @proID = (select programID from all_subjects_view where programID = @programStart limit 1);

if @proID is not null then

delete from AllSemester;

INSERT INTO SubjectSemesterTable (
    rowNo, 
    programSemesterID, 
    crHrs,
    courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle,
    timetabletypeID
)
SELECT 
    (@row_number := @row_number + 1) AS rowNo, 
    programSemesterID,
    creditHours,
    courseCode, 
    LabID, 
    ProgramSemesterSubjectID, 
    SSTitle, 
    Title, 
    roomTypeID,
    LectureID,
    SessionID,
    Session,
    timeTableTypeID
FROM 
    (SELECT @row_number := 0) AS init, 
    all_subjects_view
WHERE 
    ProgramID = @programStart;
        
drop temporary table if exists ROOMSSLOTS;
CREATE TEMPORARY TABLE ROOMSSLOTS(
        
			rowNo int,
            dayTimeSlotID int,
            slotTitle varchar(200),
            startTime time,
            endTime time,
            dayID int,
            dayTitle varchar(200),
            roomID int,
            roomNo varchar(200),
            capacity int,
            timeTableTypeID int
		);
		drop temporary table if exists LABSLOTS;
        CREATE TEMPORARY TABLE LABSLOTS(
        
			rowNo int,
            dayTimeSlotID int,
            slotTitle varchar(200),
            startTime time,
            endTime time,
            dayID int,
            dayTitle varchar(200),
            labID int,
            labNo varchar(200),
            capacity int,
			timeTableTypeID int

		);
		
        drop temporary table if exists EVENINGROOMSLOTS;
        CREATE TEMPORARY TABLE EVENINGROOMSLOTS(
        
			rowNo int,
            dayTimeSlotID int,
            slotTitle varchar(200),
            startTime time,
            endTime time,
            dayID int,
            dayTitle varchar(200),
            roomID int,
            roomNo varchar(200),
            capacity int,
            timeTableTypeID int
		);
		drop temporary table if exists EVENINGLABSLOTS;
        CREATE TEMPORARY TABLE EVENINGLABSLOTS(
        
			rowNo int,
            dayTimeSlotID int,
            slotTitle varchar(200),
            startTime time,
            endTime time,
            dayID int,
            dayTitle varchar(200),
            labID int,
            labNo varchar(200),
            capacity int,
			timeTableTypeID int

		);
Set @TimeTableTypeID = (select timeTableTypeID from programSemesterTable where programsemesterID = @OneByOne  limit 1) ;

set @row_number = 0;
INSERT INTO RoomsSlots (

    rowNo, 
    dayTimeSlotID, 
    slotTitle, 
    startTime, 
    endTime, 
    dayID, 
    dayTitle, 
    roomID, 
    roomNo, 
    capacity,
	timeTableTypeID 

    
)
SELECT  
    (@row_number := @row_number + 1) AS RowNo, 
    DayTimeSlotID, 
    SlotTitle, 
    StartTime, 
    EndTime, 
    DayID, 
    Name, 
    RoomID, 
    RoomNo, 
    Capacity,
	timeTableTypeID

FROM 
    v_AllActiveTimeSlots
 cross JOIN 
    (SELECT * FROM RoomTable) RT 
WHERE 
        ProgramID = @programStart AND timetabletypeID = 1
order by 
		RT.roomID;
SET @row_number = 0;

INSERT INTO EVENINGRoomSlots (

    rowNo, 
    dayTimeSlotID, 
    slotTitle, 
    startTime, 
    endTime, 
    dayID, 
    dayTitle, 
    roomID, 
    roomNo, 
    capacity,
	timeTableTypeID 

    
)
SELECT  
    (@row_number := @row_number + 1) AS RowNo, 
    DayTimeSlotID, 
    SlotTitle, 
    StartTime, 
    EndTime, 
    DayID, 
    Name, 
    RoomID, 
    RoomNo, 
    Capacity,
	timeTableTypeID

FROM 
    v_AllActiveTimeSlots
 cross JOIN 
    (SELECT * FROM RoomTable) RT 
WHERE 
        ProgramID = @programStart AND timetabletypeID = 2
order by 
		RT.roomID;
 
drop temporary table if exists ALLONESUBJECTSEMESTERTABLE;
        
    CREATE TEMPORARY TABLE ALLONESUBJECTSEMESTERTABLE (
    
        rowNo int,
        repeatedRowNo int,
        programSemesterID int,
        crHrs int,
		courseCode varchar(50),
		labID int,
        programSemesterSubjectID int,
        ssTitle varchar(300),
        title varchar(200),
        roomTypeID int,
        lectureID int,
        sessionID int,
        sessionTitle varchar(200),
		timeTableTypeID int

    );
    
drop temporary table if exists ONESUBJECTSEMESTERTABLE;
CREATE TEMPORARY TABLE ONESUBJECTSEMESTERTABLE(
    
		rowNo int,
        programSemesterID int,
        crHrs int,
        courseCode varchar(50),
		labID int,
        programSemesterSubjectID int,
        ssTitle varchar(300),
        title varchar(200),
        roomTypeID int,
        lectureID int,
        sessionID int,
        sessionTitle varchar(200),
		timeTableTypeID int

        
    );
     drop temporary table if exists SUBJECTTABLE;
 CREATE TEMPORARY TABLE SUBJECTTABLE(
		
        programSemesterID int,
        crHrs int,
        courseCode varchar(50),
		labID int,
        programSemesterSubjectID int,
        ssTitle varchar(300),
        title varchar(200),
        roomTypeID int,
        lectureID int,
        sessionID int,
        sessionTitle varchar(300)
        
        );
	  
      drop temporary table if exists RANDOMSUBJECTTABLE;
      CREATE TEMPORARY TABLE RANDOMSUBJECTTABLE(
      
		rowNo int,
        programSemesterID int,
        crHrs int,
        courseCode varchar(50),
		labID int,
        programSemesterSubjectID int,
        ssTitle varchar(300),
        title varchar(200),
        roomTypeID int,
        lectureID int,
        sessionID int,
        sessionTitle varchar(200)
        
        );
-- Getting All Labs Time Slots   
-- It's Lab Slots Population
SET @row_number = 0;

INSERT INTO LabSlots (

    rowNo, 
    dayTimeSlotID, 
    slotTitle, 
    startTime, 
    endTime, 
    dayID, 
    dayTitle, 
    labID, 
    labNo, 
    capacity,
	timeTableTypeID

)
SELECT 
    (@row_number := @row_number + 1) AS rowNo, 
    DayTimeSlotID, 
    SlotTitle, 
    StartTime, 
    EndTime, 
    DayID, 
    Name, 
    LabID, 
    LabNo, 
    Capacity,
	timeTableTypeID

FROM 
    v_AllActiveTimeSlots
CROSS JOIN 
    (SELECT * FROM LabTable) LT
WHERE 
        ProgramID = @programStart AND  timeTabletypeID = 1;


SET @row_number = 0;

INSERT INTO EVENINGLabSlots (
    rowNo, 
    dayTimeSlotID, 
    slotTitle, 
    startTime, 
    endTime, 
    dayID, 
    dayTitle, 
    labID, 
    labNo, 
    capacity,
	timeTableTypeID

)
SELECT 
    (@row_number := @row_number + 1) AS rowNo, 
    DayTimeSlotID, 
    SlotTitle, 
    StartTime, 
    EndTime, 
    DayID, 
    Name, 
    LabID, 
    LabNo, 
    Capacity,
	timeTableTypeID

FROM 
    v_AllActiveTimeSlots
CROSS JOIN 
    (SELECT * FROM LabTable) LT
WHERE 
        ProgramID = @programStart AND  timeTabletypeID = 2;

-- Checking Special Cases

SET @TTID = 1;
SET @OneByOne = (select MIN(programSemesterID) from subjectsemestertable   LIMIT 1);  
SET @EndOneByOne  = (select MAX(programSemesterID) from subjectsemestertable   LIMIT 1);   
SET @TotalSlots = (select count(*) from dayTimeSlotTable where dayID = 2  limit 1 );
SET @TotalDay = (select count(*) from daytable where isActive = 1);
set @allslots = (select count(*) from daytimeslottable where slotTitle is NOT null);
SET @TotalTimeSlots = @TotalSlots * @TotalDay;
-- Slot-window invariants: these depend only on the (stable) day/slot configuration,
-- so compute them once per program instead of once per semester (see below).
set @eveningStart = Floor(@allslots/2);
set @diffi = Floor(@eveningStart/@totalDay);
set @SlotID = (select dayTimeSlotID from daytimeslottable where dayTimeSlotID = Floor((@eveningStart + 1)) limit 1);
 Set @roomCounter := 1;
Set @EveningCounter := 1;






 -- outer first loop will start from here......
WHILE @OneByOne  <= @EndOneByOne DO


set @semID = (select programsemesterID from subjectsemestertable where programsemesterID = @OneByOne limit 1) ;

 IF @semID is not null then 

	delete from ONESUBJECTSEMESTERTABLE;
	delete from ALLONESUBJECTSEMESTERTABLE;
	delete from subjecttable;
	delete from randomsubjecttable;
    
INSERT INTO ONESUBJECTSEMESTERTABLE (
  
	rowNo,
    programSemesterID,
	crHrs,
    courseCode,
    labID,
    programSemesterSubjectID,
    ssTitle,
    title,
    roomTypeID,
    lectureID,
    sessionID,
    sessionTitle,
	timeTableTypeID

)
SELECT 
    rowNo,
    programSemesterID,
	crHrs,
	courseCode,
    labID,
    programSemesterSubjectID,
    ssTitle,
    title,
    roomTypeID,
    lectureID,
    sessionID,
    sessionTitle,
	timeTableTypeID

FROM 
    SubjectSemesterTable
WHERE 
    programSemesterID = @OneByOne;    -- will use loop variable for one by one semester
    
-- splitting crhrs in to one slot    


SET @IndexNo := 1;
SET @RowNo := 1;
SET @CountRecord := 0;

SELECT COUNT(*) INTO @CountRecord FROM SubjectSemesterTable LIMIT 1;

WHILE @IndexNo <= @CountRecord DO
	SET @RepeatPrint = 0;
    SELECT CrHrs INTO @RepeatPrint FROM SubjectSemesterTable WHERE RowNo = @IndexNo;
    
     SET @CountCrHrs := 1;
    WHILE @CountCrHrs <= @RepeatPrint DO
    
        INSERT INTO ALLONESUBJECTSEMESTERTABLE (rowNo, repeatedRowNo, programSemesterID, crHrs, courseCode,labID, programSemesterSubjectID, ssTitle, title, roomTypeID, lectureID, sessionID, sessionTitle,timeTableTypeID)
        SELECT @RowNo, rowNo, programSemesterID, crHrs,  courseCode,labID,programSemesterSubjectID, ssTitle, title, roomTypeID, lectureID, sessionID, sessionTitle,timeTableTypeID
        FROM ONESUBJECTSEMESTERTABLE
        WHERE RowNo = @IndexNo;
        
        SET @CountCrHrs = @CountCrHrs + 1;
        SET @RowNo = @RowNo + 1;
    END WHILE;
    
    SET @IndexNo = @IndexNo + 1;

END WHILE;
        
INSERT INTO SUBJECTTABLE (
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
)
SELECT 
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
FROM 
    ALLONESUBJECTSEMESTERTABLE 
ORDER BY 
    RAND();


-- for shuffling  practical subjects

SET @row_number = 0;

Set @TimeTableTypeID = (select timeTableTypeID from programsemestertable where programsemesterID = @OneByOne  limit 1) ;

if @TimeTableTypeID  = 1 then

INSERT INTO RandomSubjectTable (

    rowNo, 
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
    
)
SELECT 
    (@row_number := @row_number + 1) AS rowNo, 
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
FROM 
    SUBJECTTABLE   
    order by labID desc;
    
else 

    INSERT INTO RandomSubjectTable (

    rowNo, 
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    title, 
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
    
)
SELECT 
    (@row_number := @row_number + 1) AS rowNo, 
    programSemesterID, 
    crHrs, 
     courseCode,
    labID,
    programSemesterSubjectID, 
    ssTitle, 
    concat(title, ' (Replica)'),
    roomTypeID, 
    lectureID, 
    sessionID, 
    sessionTitle
FROM 
    SUBJECTTABLE
    
    order by labID desc;

end if;    


-- set @IDM := 0;
INSERT INTO AllSemester( ProgramSemesterID, Title, SessionID, SessionTitle,timetabletypeID,programName,season,programID) 
SELECT  ProgramSemesterID, Title, SessionID, SessionTitle ,@TimeTableTypeID,(select name from programtable WHERE programID = @programStart),@season,@programStart
FROM (
    SELECT ProgramSemesterID, Title, SessionID, SessionTitle
    FROM allonesubjectsemestertable
    GROUP BY ProgramSemesterID, Title, SessionID, SessionTitle
) AS ALFF; 

-- Create Time Table for one by one Semester

SET @CAllSemester := (SELECT COUNT(*) FROM AllSemester LIMIT 1);

    -- Get SEMESTER HEADER
    SET @TTID = (SELECT RowNo FROM AllSemester WHERE programSemesterID = @OneByOne limit 1);
    SET @SessionTitle := (SELECT SessionTitle FROM AllSemester WHERE programSemesterID = @OneByOne );
    SET @SessionID := (SELECT SessionID FROM AllSemester WHERE programSemesterID = @OneByOne);
    SET @ProgramSemesterID := (SELECT ProgramSemesterID FROM AllSemester WHERE programSemesterID = @OneByOne);
    SET @Title := (SELECT Title FROM AllSemester WHERE programSemesterID = @OneByOne);
    SET @TimeTableTitle := (SELECT Title FROM AllSemester WHERE programSemesterID = @OneByOne);
	SET @program := (SELECT programName FROM AllSemester WHERE programSemesterID = @OneByOne);
    SET @season := (SELECT season FROM AllSemester WHERE programSemesterID = @OneByOne);
    set @PID = (SELECT programID FROM AllSemester WHERE programSemesterID = @OneByOne ) ;

    IF @ProgramSemesterID is not null then 
		IF @TimeTableTypeID = 2 THEN 
			 INSERT INTO Header (TimeTableID, SessionID, SessionTitle, ProgramSemesterID, TimeTableTitle, SemesterTitle,timetabletypeID,programName,season,programID)
			VALUES (@TTID, @SessionID, @SessionTitle, @ProgramSemesterID, concat(@TimeTableTitle, ' (Replica)'), concat(@Title, ' (Replica)'),@TimeTableTypeID,@program,@season,@PID);
		ELSE	
			INSERT INTO Header (TimeTableID, SessionID, SessionTitle, ProgramSemesterID, TimeTableTitle, SemesterTitle,timetabletypeID,programName,season,programID)
			VALUES (@TTID, @SessionID, @SessionTitle, @ProgramSemesterID, @TimeTableTitle, @Title,@TimeTableTypeID,@program,@season,@PID);
  END if;  
  END IF;
    
SET @Message := 'Time Table Header Inatilize';
-- DECLARE Time Slot Validation Variables
-- (@eveningStart / @diffi / @SlotID are now computed once per program, above)
SET @SlotNo = 1 ;
Set @TimeTableTypeID = (select timeTableTypeID from programsemestertable where programsemesterID = @OneByOne  limit 1) ;


SET @OneByOneSubject = 1;
SET @SemesterTitle := (SELECT Title FROM RandomSubjectTable WHERE RowNo = @OneByOne LIMIT 1);
SET @totalCreditHours := (SELECT totalCreditHours FROM programSemesterTable where programSemesterID = @OneByOne limit 1)  ;

WHILE @OneByOneSubject <=  @eveningStart DO


SET @SubjectSessionTitle := (SELECT SessionTitle FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @SubjectSessionID := (SELECT SessionID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @ProgramSemesterID := (SELECT ProgramSemesterID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @SemesterTitle := (SELECT Title FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @TimeTableID := (SELECT TimeTableID FROM Header WHERE ProgramSemesterID = @ProgramSemesterID AND SemesterTitle = @SemesterTitle LIMIT 1);
SET @ProgramSemesterSubjectID := (SELECT ProgramSemesterSubjectID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @SubjectTitle := (SELECT SSTitle FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @RoomTypeID := (SELECT RoomTypeID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @CourseCode := (SELECT courseCode FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
SET @CreditHours := (SELECT crHrs FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);

SET @LectureID := (SELECT LectureID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
-- Normalised subject key + this teacher's total load for the semester: both are
-- constant for the whole placement search below, so compute them once per subject
-- instead of on every retry.
SET @courseKey := UPPER(REPLACE(TRIM(@SubjectTitle), ' ', ''));
SET @crHrs := (select sum(crHrs) from subjectsemestertable where lectureID = @LectureID and programsemesterID = @ProgramSemesterID);
SET @TimeTableTitle := (SELECT TimeTableTitle FROM Header WHERE programsemesterID = @OneByOne LIMIT 1);
SET @DayTimeSlotID := (SELECT DayTimeSlotID FROM LabSlots WHERE DayTimeSlotID = @SlotNo  LIMIT 1);
SET @DayTimeSlotTitle := (SELECT slotTitle FROM LabSlots WHERE DayTimeSlotID = @SlotNo  LIMIT 1);
SET @DayID := (SELECT DayID FROM LabSlots WHERE RowNo = @SlotNo AND timeTableTypeID = @TimeTableTypeID LIMIT 1);

IF @RoomTypeID = 4 then
	SET @LabID := (SELECT LabID FROM RandomSubjectTable WHERE RowNo = @OneByOneSubject LIMIT 1);
end if;
IF @TimeTableTypeID = 1 then 
	SET @roomID := (SELECT roomID FROM roomsSlots WHERE RowNo = (@roomCounter *  @eveningStart) LIMIT 1);
end if;
IF @TimeTableTypeID = 2 then 

SET @roomID := (SELECT roomID FROM eveningroomSlots WHERE RowNo = (@EveningCounter *  @eveningStart) LIMIT 1);
    IF @roomID IS NULL THEN
    
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'room ID is null in evening counter';
        
    END IF;
end if;
    
   SET @ValidCombinationFound = FALSE;
   -- Safety cap: this is a random search with no backtracking, so a fully
   -- constrained subject could otherwise spin here forever. After @MaxTries
   -- failed attempts we stop and raise a clear message instead of hanging.
   SET @Tries = 0;
   SET @MaxTries = 800;

   IF @TimeTableID is null
   then
		   SET @ValidCombinationFound = true;

    END IF;


    -- Loop until a valid combination is found (or the safety cap is hit)
    WHILE (NOT @ValidCombinationFound) AND (@Tries < @MaxTries)
DO
        SET @Tries = @Tries + 1;
		IF @TimeTableTypeID = 1 then
			
			SET @DayTimeSlotTitle = (SELECT slotTitle FROM DayTimeSlotTable WHERE DayTimeSlotID <= @diffi ORDER BY RAND() * weight DESC  LIMIT 1);
        else
			SET @DayTimeSlotTitle = (SELECT slotTitle FROM DayTimeSlotTable WHERE DayTimeSlotID >= @SlotID AND DayTimeSlotID <= FLOOR((@eveningStart + @diffi)) ORDER BY RAND() * weight DESC LIMIT 1);
        end if;	
       SET @DayID = (SELECT DayID FROM daytable ORDER BY RAND() LIMIT 1);

        -- All six conflict probes below filtered on "DayID = @DayID"; the original
        -- code issued them as six separate SELECT COUNT(*) round-trips per retry.
        -- One scan of the day's rows with conditional SUMs is equivalent and cuts
        -- the query count per attempt from ~7 to ~1. (course / slot comparisons
        -- use the pre-normalised courseKey / slotKey columns so the composite
        -- index can be used instead of a per-row REPLACE/TRIM/LOWER scan.)
        SET @slotKey := UPPER(REPLACE(TRIM(@DayTimeSlotTitle), ' ', ''));
        SELECT
            COALESCE(SUM(slotKey = @slotKey AND LectureID = @LectureID), 0),
            COALESCE(SUM(LectureID = @LectureID AND TimeTableTypeID = @TimeTableTypeID), 0),
            COALESCE(SUM(LectureID = @LectureID AND TimeTableID = @TimeTableID
                        AND TimeTableTypeID = @TimeTableTypeID), 0),
            COALESCE(SUM(LectureID = @LectureID AND courseKey = @courseKey
                        AND TimeTableID = @TimeTableID AND TimeTableTypeID = @TimeTableTypeID), 0),
            COALESCE(SUM(slotKey = @slotKey AND roomID = @roomID AND programID = @proID), 0),
            COALESCE(SUM(TimeTableID = @TimeTableID AND slotKey = @slotKey
                        AND programID = @proID AND TimeTableTypeID = @TimeTableTypeID), 0)
        INTO @AllocatedCount, @TeacherPerDay, @NumMCourse, @NumCourse, @AllowRoom, @SameCount
        FROM FixedSlotsTable
        WHERE DayID = @DayID;

         IF @AllocatedCount = 0 THEN
			SET @ValidCombinationFound = True;
         end if;
        -- If not allocated, set the flag to true and exit the loop
		
		IF @NumMCourse = 1 AND @crHrs <= 10 and @AllocatedCount = 0 Then  -- 3 lectures credit hour should  continusly if teacher assign more than 10 credit hours to same class
	
			SET @ValidCombinationFound = True;

         elseif @NumMCourse = 2 AND @crHrs > 10  and @AllocatedCount = 0 then
			SET @ValidCombinationFound = True;
            
         elseif  @NumMCourse = 3 then 
			
				SET @ValidCombinationFound = False;
                
		elseif @NumMCourse = 2  Then  -- 2 lectures credit hour should  continusly if teacher assign more than 4 credit hours to same class
			SET @ValidCombinationFound = FALSE;

		end if;
        
       
        IF  @SameCount > 0 Then 
			SET @ValidCombinationFound = FALSE;
		end if;
        
        
        IF  @TeacherPerDay = 3 then
			SET @ValidCombinationFound = FALSE;
        END IF;    
	  
        
		 IF @NumCourse = 2  Then  -- 3 lectures credit hour should  continusly if teacher assign more than 10 credit hours to same class

            SET @ValidCombinationFound = false;

		 end if;
         -- (@AllowRoom already computed in the combined probe above; no rows have
         --  been inserted since, so the re-query here was redundant.)

IF @RoomTypeID = 3 then
IF @TimeTableTypeID = 1 then 
IF LOWER(REPLACE(TRIM(@DayTimeSlotTitle), ' ', '')) IN (LOWER(REPLACE(TRIM('11:00 - 12:00'), ' ', '')), LOWER(REPLACE(TRIM('12:00 - 01:00'), ' ', '')))   THEN
        IF @AllowRoom > 0  and @ValidCombinationFound = true then
	
    SET @roomID = (SELECT roomID FROM roomTable WHERE programID = @proID Order by rand()  LIMIT 1);
       set @AllowRoom =  (
        -- for not overlapping rooms
            SELECT COUNT(*)
            FROM FixedSlotsTable
            WHERE DayID = @DayID
                AND slotTitle =  @DayTimeSlotTitle
                AND roomID = @roomID
                AND programID = @proID
               
        );
        
        IF @AllowRoom > 0 then
			
			set @ValidCombinationFound = false;
        end if;
        end if;
     else
     
     SET @roomID := (SELECT roomID FROM roomsSlots WHERE RowNo = (@roomCounter *  @eveningStart) LIMIT 1);
		
		
        end if;
        end if;
     
     end if;

IF @RoomTypeID = 3 then
IF @TimeTableTypeID = 2 then 
IF LOWER(REPLACE(TRIM(@DayTimeSlotTitle), ' ', '')) IN (LOWER(REPLACE(TRIM('11:00 - 12:00'), ' ', '')), LOWER(REPLACE(TRIM('12:00 - 01:00'), ' ', '')))   THEN
		
        IF @AllowRoom > 0  and @ValidCombinationFound = true then

    SET @roomID = (SELECT roomID FROM roomTable WHERE programID = @proID Order by rand()  LIMIT 1);
       set @AllowRoom =  (
        -- for not overlapping rooms
            SELECT COUNT(*)
            FROM FixedSlotsTable
            WHERE DayID = @DayID
                AND slotTitle =  @DayTimeSlotTitle
                AND roomID = @roomID
                AND programID = @proID
               
        );
        
        IF @AllowRoom > 0 then
        
			set @ValidCombinationFound = false;
        end if;
        end if;
     else
     
     SET @roomID := (SELECT roomID FROM eveningroomSlots WHERE RowNo = (@EveningCounter *  @eveningStart) LIMIT 1);
		
		
        end if;
        end if;
      
			

end if;

        IF @RoomTypeID = 4 then

        set @AllowLab =  (
                -- for not overlapping labs
            SELECT COUNT(*)
            FROM FixedSlotsTable
            WHERE DayID = @DayID
			AND slotKey =  @slotKey
			AND labID = @LabID
			AND programID = @proID

        );
        
           IF @AllowLab > 0 then
			set @ValidCombinationFound = false;
        end if;
    
        
        end if;

END WHILE;

   -- Hit the safety cap without placing this class hour: stop with a clear
   -- message rather than inserting a clashing slot or looping forever.
   IF (@TimeTableID is not null) AND (NOT @ValidCombinationFound) THEN
       SET @errorMessage = CONCAT('Could not fit "', @SubjectTitle,
           '" into ', @TimeTableTitle,
           ' after ', @MaxTries, ' attempts. Add more rooms/labs for this program, ',
           'reduce the teacher''s load, or free up time slots, then generate again.');
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @errorMessage;
   END IF;

   IF @TimeTableID is not null
   then

           IF @RoomTypeID = 3 Then
			INSERT INTO FixedSlotsTable (TimeTableID,lectureID,teacherName ,dayID, dayTimeSlotID, slotTitle, slotKey, isreserved,course, courseKey, TimeTableTypeID,programID,roomID,labID)
			VALUES (@TimeTableID,@LectureID,(SELECT fullName FROM lectureTable WHERE lectureID = @LectureID LIMIT 1), @DayID, @DayTimeSlotID,@DayTimeSlotTitle , @slotKey, 1,@SubjectTitle, @courseKey, @TimeTableTypeID,@proID,@roomID,0);
         ELSE
			INSERT INTO FixedSlotsTable (TimeTableID,lectureID,teacherName ,dayID, dayTimeSlotID, slotTitle, slotKey, isreserved,course, courseKey, TimeTableTypeID,programID,roomID,labID)
			VALUES (@TimeTableID,@LectureID,(SELECT fullName FROM lectureTable WHERE lectureID = @LectureID LIMIT 1), @DayID, @DayTimeSlotID,@DayTimeSlotTitle , @slotKey, 1,@SubjectTitle, @courseKey, @TimeTableTypeID,@proID,0,@LabID);
         END IF;
            IF @RoomTypeID = 3
            Then
				 INSERT INTO Details (TimeTableID, SessionTitle, SessionID, ProgramSemesterSubjectID, SubjectTitle, RoomID, LabID, DayTimeSlotID, LectureID, DayID,slotTitle,dayTitle,crHrs,courseCode,timeTableTypeID,programID)
				VALUES (@TimeTableID, @SubjectSessionTitle, @SubjectSessionID, @ProgramSemesterSubjectID, @SubjectTitle, @RoomID, 0, @DayTimeSlotID, @LectureID, @DayID,@DayTimeSlotTitle,(select name from daytable where dayID = @DayID limit 1),@creditHours,@courseCode,@TimeTableTypeID,@programStart);
			 ELSE 
				 INSERT INTO Details (TimeTableID, SessionTitle, SessionID, ProgramSemesterSubjectID, SubjectTitle, RoomID, LabID, DayTimeSlotID, LectureID, DayID,slotTitle,dayTitle,crHrs,courseCode,timeTableTypeID,programID)
					VALUES (@TimeTableID, @SubjectSessionTitle, @SubjectSessionID, @ProgramSemesterSubjectID, @SubjectTitle, 0, @LabID, @DayTimeSlotID, @LectureID, @DayID,@DayTimeSlotTitle,(select name from daytable where dayID = @DayID limit 1),@creditHours,@courseCode,@TimeTableTypeID,@programStart);
            END IF;
           
				IF @BreakDurationNo = 1 THEN
					select 'break executed';
					INSERT INTO Details (TimeTableID, SessionTitle, SessionID, ProgramSemesterSubjectID, SubjectTitle, RoomID, LabID, DayTimeSlotID, LectureID, DayID)
					VALUES (@TimeTableID, '', 0, 0, 'Break', 0, @LabID, @DayTimeSlotID, 0, @DayID);
				END IF;    
                
          
END IF;

    SET @OneByOneSubject = @OneByOneSubject + 1;

END WHILE;
end if;
 IF @semID is not null then 
IF @TimeTableTypeID = 2 then 
	Set @EveningCounter = @EveningCounter + 1;
ELSE 
	SET @roomCounter = @roomCounter + 1;
END IF;
end if;

SET @OneByOne = @OneByOne + 1;




END WHILE;

END IF;

SET @programStart = @programStart + 1;

 delete from labslots;
 delete from roomsslots;
 delete from Eveningroomslots;
 delete from Eveninglabslots;
delete from subjectsemestertable;

END WHILE;   

   SET FOREIGN_KEY_CHECKS = 0;
    delete from TimeTblTable; 
   INSERT INTO TimeTblTable (TimeTableID, ProgramSemesterID, SessionID,TimeTableTitle,SemesterTitle,SessionTitle,TimeTableTypeID,programName,season,programID)
SELECT 
    TimeTableID,
    ProgramSemesterID,
    SessionID,
    TimeTableTitle,
	SUBSTR(SemesterTitle, 11) ,
    SessionTitle,
    TimeTableTypeID,
    programName,
    season,
    programID
FROM (
    SELECT 
        TimeTableID,
        ProgramSemesterID,
        SessionID,
        TimeTableTitle,
        SemesterTitle,
        SessionTitle,
        TimeTableTypeID,
        programName,
        season,
        programID
    FROM 
        Header
) AS tmp;

 delete from TimeTableDetailsTable; 
 INSERT INTO TimeTableDetailsTable (TimeTableID, SessionID, SessionTitle, ProgramSemesterSubjectID, SubjectTitle, RoomID, LabID, DayTimeSlotID, LectureID, DayID,slotTitle,dayTitle, IsActive,crHrs,courseCode,timeTableTypeID,programID)
SELECT 
    TimeTableID, 
    SessionID, 
    SessionTitle, 
    ProgramSemesterSubjectID, 
    SubjectTitle, 
    RoomID, 
    LabID, 
    DayTimeSlotID, 
    LectureID, 
    DayID, 
    slotTitle,
    dayTitle,
    IsActive,
    crHrs,
    courseCode,
    timeTableTypeID,
    programID
FROM 
    (SELECT 
        TimeTableID, 
        SessionID, 
        SessionTitle, 
        ProgramSemesterSubjectID, 
        SubjectTitle, 
        RoomID, 
        LabID, 
        DayTimeSlotID, 
        LectureID, 
        DayID, 
        slotTitle,
		dayTitle,
        IsActive,
        crHrs,
		courseCode,
        timeTabletypeID,
        programID
    FROM 
        Details) AS tmp
    Where TimeTableID is not null;
    
    SET foreign_key_checks = 1;
set @Message = "Time Table Created Successfully";
Select @Message;
 SET autocommit=1; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_PrintLabwiseTimeTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `sp_PrintLabwiseTimeTables`()
BEGIN


    DELETE FROM AllLabTimeTable;

    DROP TEMPORARY TABLE IF EXISTS TimeSlotTimeTable;
    CREATE TEMPORARY TABLE TimeSlotTimeTable (
        RowNo INT,
        SlotTitle VARCHAR(200)
    );

    DROP TEMPORARY TABLE IF EXISTS LabTimeTableDetails;
    CREATE TEMPORARY TABLE LabTimeTableDetails (
        RowNo INT,
        TimeTableID INT,
        ProgramSemesterSubjectID INT,
        SubjectTitle VARCHAR(400),
        RoomID INT,
        LabID INT,
        DayTimeSlotID INT,
        SlotTitle VARCHAR(200),
        DayTitle VARCHAR(80),
        LectureID INT,
        LectureName VARCHAR(200),
        DayID INT,
        IsActive BIT,
        timeTableTypeID INT,
        programID INT
    );

    DROP TEMPORARY TABLE IF EXISTS AllLabs;
    CREATE TEMPORARY TABLE AllLabs (
        RowNo INT,
        LabID INT,
        LabName VARCHAR(200)
    );

    DROP TEMPORARY TABLE IF EXISTS LabTimeTable;
    CREATE TEMPORARY TABLE LabTimeTable (
        LABID INT,
        LABNAME VARCHAR(300),
        TIME VARCHAR(300),
        MONDAY VARCHAR(300),
        TUESDAY VARCHAR(300),
        WEDNESDAY VARCHAR(300),
        THURSDAY VARCHAR(300),
        FRIDAY VARCHAR(300),
        SATURDAY VARCHAR(300),
        SUNDAY VARCHAR(300),
        programID INT
    );

    DELETE FROM AllLabs;

    INSERT INTO AllLabs (RowNo, LabID, LabName)
    SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo,
           LT.LabID,
           LT.LabNo
    FROM (SELECT LabID, LabNo FROM LabTable) LT
    WHERE LT.LabID > 0
    GROUP BY LT.LabID, LT.LabNo;

    SET @CountTotalLabs = (SELECT MAX(LabID) FROM AllLabs);
    SET @GETTimeTableOneByOne = 1;

    WHILE @GETTimeTableOneByOne <= @CountTotalLabs DO

        SET @LabTimeTableTitle = (SELECT LabName FROM AllLabs WHERE RowNo = @GETTimeTableOneByOne);

        DELETE FROM LabTimeTable;
        DELETE FROM TimeSlotTimeTable;

        INSERT INTO TimeSlotTimeTable (RowNo, SlotTitle)
        SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo, SlotTitle
        FROM (SELECT SlotTitle, StartTime, EndTime
              FROM DayTimeSlotTable
              WHERE ISActive = 1
              GROUP BY SlotTitle, StartTime, EndTime) DTST
        ORDER BY StartTime;

        SET @COUNTTIMEROWSTIMETABLE = (SELECT COUNT(*) FROM TimeSlotTimeTable);
        SET @CREATESLOTSVARIABLE = 1;

        WHILE @CREATESLOTSVARIABLE <= @COUNTTIMEROWSTIMETABLE DO

            SET @TIMETITLE = (SELECT SlotTitle FROM TimeSlotTimeTable WHERE RowNo = @CREATESLOTSVARIABLE);

            INSERT INTO LabTimeTable (LABID, LABNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
            VALUES (0, NULL, @TIMETITLE, 'Break', 'Break', 'Break', 'Break', 'Break', 'Break', 'Break');

            SET @CREATESLOTSVARIABLE = @CREATESLOTSVARIABLE + 1;

        END WHILE;

        DELETE FROM LabTimeTableDetails;

        SET @row_number_tt := 0;

        -- Initialize row number variable
        SET @row_number_tt := 0;

        -- Insert data into LabTimeTableDetails
        INSERT INTO LabTimeTableDetails (
            RowNo,
            TimeTableID,
            ProgramSemesterSubjectID,
            SubjectTitle,
            RoomID,
            LabID,
            DayTimeSlotID,
            SlotTitle,
            DayTitle,
            LectureID,
            LectureName,
            DayID,
            IsActive,
            timeTableTypeID
        )
        SELECT
            @row_number_tt := @row_number_tt + 1 AS RowNo,
            TTD.TimeTableID,
            TTD.ProgramSemesterSubjectID,
            TTD.SubjectTitle,
            TTD.RoomID,
            TTD.LabID,
            TTD.DayTimeSlotID,
            TTD.SlotTitle,
            TTD.DayTitle,
            TTD.LectureID,
            (SELECT fullName FROM LectureTable WHERE LectureID = TTD.LectureID) AS LectureName,
            TTD.DayID,
            TTD.IsActive,
            TTD.timeTableTypeID
        FROM
            TimeTableDetailsTable AS TTD
        WHERE
            TTD.LabID = (SELECT LabID FROM AllLabs WHERE RowNo = @GETTimeTableOneByOne)
        ORDER BY
            TTD.DayTimeSlotID;

        SET @LabID = (SELECT LabID FROM LabTimeTableDetails LIMIT 1);
        SET @LabName = (SELECT LabName FROM AllLabs WHERE RowNo = @GETTimeTableOneByOne);
        SET @LabName = CONCAT(@LabName, ' - Time Table');

        UPDATE LabTimeTable SET LABID = @LabID, LABNAME = @LabName;

        SET @LocationTitleTimeTable = NULL;
        SET @SemsterTitleTimeTable = NULL;
        SET @SubjectTitleTimeTable = NULL;

        SET @CountTimeSlotTimeTable = (SELECT COUNT(*) FROM LabTimeTableDetails);
        SET @AddOnebyOne = 1;

        WHILE @AddOnebyOne <= @CountTimeSlotTimeTable DO

            SET @GETProgramSemesterSubjectID = (SELECT ProgramSemesterSubjectID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            IF @GETProgramSemesterSubjectID > 0 THEN
            
                SET @LectureSUBJECTID = (SELECT LectureSubjectID FROM ProgramSemesterSubjectTable WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID);
                SET @programSemesterSubjectID = (SELECT ProgramSemesterSubjectID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
                SET @programSemesterTitle = (SELECT SUBSTR(Title, 11) FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @professorName = (SELECT ProfessorName FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @courseTitle = (SELECT CourseTitle FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @programID = (SELECT ProgramID FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @SubjectTitleTimeTable = (SELECT SubjectTitle FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETRoomID = (SELECT RoomID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETLabID = (SELECT LabID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETDayTimeSlotID = (SELECT DayTimeSlotID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETLectureID = (SELECT LectureID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETTimeSlotName = (SELECT SlotTitle FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETDayTitle = (SELECT DayTitle FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @timeTableTypeID := (SELECT timeTableTypeID FROM LabTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);

                IF @GETRoomID > 0 THEN
                
                    SET @LocationTitleTimeTable = (SELECT RoomNo FROM RoomTable WHERE RoomID = @GETRoomID LIMIT 1);
                    
                END IF;

                IF @GETLabID > 0 THEN
                
                    SET @LocationTitleTimeTable = (SELECT LabNo FROM LabTable WHERE LabID = @GETLabID LIMIT 1);
                END IF;

                IF @timeTableTypeID = 2 THEN
                    SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle), ' (Replica)\n', @courseTitle, '\n(', @professorName,')');
                ELSE
                    SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle), '\n', @courseTitle, '\n(', @professorName,')');
                END IF;

                UPDATE LabTimeTable SET programID = @programID;
                IF @GETDayTitle = 'MONDAY' THEN
                    UPDATE LabTimeTable SET MONDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'TUESDAY' THEN
                    UPDATE LabTimeTable SET TUESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'WEDNESDAY' THEN
                    UPDATE LabTimeTable SET WEDNESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'THURSDAY' THEN
                    UPDATE LabTimeTable SET THURSDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'FRIDAY' THEN
                    UPDATE LabTimeTable SET FRIDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'SATURDAY' THEN
                    UPDATE LabTimeTable SET SATURDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'SUNDAY' THEN
                    UPDATE LabTimeTable SET SUNDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                END IF;
            END IF;

            SET @AddOnebyOne = @AddOnebyOne + 1;

        END WHILE;

        SET @GETTimeTableOneByOne = @GETTimeTableOneByOne + 1;

        INSERT INTO AllLabTimeTable (ROOMID, ROOMNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, programID)
        SELECT LABID AS ROOMID, LABNAME AS ROOMNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, programID
        FROM LabTimeTable;

    END WHILE;

    SELECT * FROM AllLabTimeTable WHERE ROOMNAME IS NOT NULL;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_PrintRoomwiseTimeTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `sp_PrintRoomwiseTimeTables`()
BEGIN


    DELETE FROM AllRoomTimeTable;

    DROP TEMPORARY TABLE IF EXISTS TimeSlotTimeTable;
    CREATE TEMPORARY TABLE TimeSlotTimeTable (
        RowNo INT,
        SlotTitle VARCHAR(200)
    );

    DROP TEMPORARY TABLE IF EXISTS RoomTimeTableDetails;
    CREATE TEMPORARY TABLE RoomTimeTableDetails (
        RowNo INT,
        TimeTableID INT,
        ProgramSemesterSubjectID INT,
        SubjectTitle VARCHAR(400),
        RoomID INT,
        LabID INT,
        DayTimeSlotID INT,
        SlotTitle VARCHAR(200),
        DayTitle VARCHAR(80),
        LectureID INT,
        LectureName VARCHAR(200),
        DayID INT,
        IsActive BIT,
        timeTableTypeID INT,
        programID INT
    );

    DROP TEMPORARY TABLE IF EXISTS AllRooms;
    CREATE TEMPORARY TABLE AllRooms (
        RowNo INT,
        RoomID INT,
        RoomName VARCHAR(200)
    );

    DROP TEMPORARY TABLE IF EXISTS RoomTimeTable;
    CREATE TEMPORARY TABLE RoomTimeTable (
        ROOMID INT,
        ROOMNAME VARCHAR(300),
        TIME VARCHAR(300),
        MONDAY VARCHAR(300),
        TUESDAY VARCHAR(300),
        WEDNESDAY VARCHAR(300),
        THURSDAY VARCHAR(300),
        FRIDAY VARCHAR(300),
        SATURDAY VARCHAR(300),
        SUNDAY VARCHAR(300),
        programID INT
    );

    DELETE FROM AllRooms;

    INSERT INTO AllRooms (RowNo, RoomID, RoomName)
    SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo,
           RT.RoomID,
           RT.RoomNo
    FROM (SELECT RoomID, RoomNo FROM RoomTable) RT
    WHERE RT.RoomID > 0
    GROUP BY RT.RoomID, RT.RoomNo;

    SET @CountTotalRooms = (SELECT MAX(RoomID) FROM AllRooms);
    SET @GETTimeTableOneByOne = 1;

    WHILE @GETTimeTableOneByOne <= @CountTotalRooms DO

        SET @RoomTimeTableTitle = (SELECT RoomName FROM AllRooms WHERE RowNo = @GETTimeTableOneByOne);

        DELETE FROM RoomTimeTable;
        DELETE FROM TimeSlotTimeTable;

        INSERT INTO TimeSlotTimeTable (RowNo, SlotTitle)
        SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo, SlotTitle
        FROM (SELECT SlotTitle, StartTime, EndTime
              FROM DayTimeSlotTable
              WHERE ISActive = 1
              GROUP BY SlotTitle, StartTime, EndTime) DTST
        ORDER BY StartTime;

        SET @COUNTTIMEROWSTIMETABLE = (SELECT COUNT(*) FROM TimeSlotTimeTable);
        SET @CREATESLOTSVARIABLE = 1;

        WHILE @CREATESLOTSVARIABLE <= @COUNTTIMEROWSTIMETABLE DO

            SET @TIMETITLE = (SELECT SlotTitle FROM TimeSlotTimeTable WHERE RowNo = @CREATESLOTSVARIABLE);

            INSERT INTO RoomTimeTable (ROOMID, ROOMNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
            VALUES (0, NULL, @TIMETITLE, 'Break', 'Break', 'Break', 'Break', 'Break', 'Break', 'Break');

            SET @CREATESLOTSVARIABLE = @CREATESLOTSVARIABLE + 1;

        END WHILE;

        DELETE FROM RoomTimeTableDetails;

        SET @row_number_tt := 0;

        -- Initialize row number variable
        SET @row_number_tt := 0;

        -- Insert data into RoomTimeTableDetails
        INSERT INTO RoomTimeTableDetails (
            RowNo,
            TimeTableID,
            ProgramSemesterSubjectID,
            SubjectTitle,
            RoomID,
            LabID,
            DayTimeSlotID,
            SlotTitle,
            DayTitle,
            LectureID,
            LectureName,
            DayID,
            IsActive,
            timeTableTypeID
        )
        SELECT
            @row_number_tt := @row_number_tt + 1 AS RowNo,
            TTD.TimeTableID,
            TTD.ProgramSemesterSubjectID,
            TTD.SubjectTitle,
            TTD.RoomID,
            TTD.LabID,
            TTD.DayTimeSlotID,
            TTD.SlotTitle,
            TTD.DayTitle,
            TTD.LectureID,
            (SELECT fullName FROM LectureTable WHERE LectureID = TTD.LectureID) AS LectureName,
            TTD.DayID,
            TTD.IsActive,
            TTD.timeTableTypeID
        FROM
            TimeTableDetailsTable AS TTD
        WHERE
            TTD.RoomID = (SELECT RoomID FROM AllRooms WHERE RowNo = @GETTimeTableOneByOne)
        ORDER BY
            TTD.DayTimeSlotID;

        SET @RoomID = (SELECT RoomID FROM RoomTimeTableDetails LIMIT 1);
        SET @RoomName = (SELECT RoomName FROM AllRooms WHERE RowNo = @GETTimeTableOneByOne);
        SET @RoomName = CONCAT(@RoomName, ' - Time Table');

        UPDATE RoomTimeTable SET ROOMID = @RoomID, ROOMNAME = @RoomName;

        SET @LocationTitleTimeTable = NULL;
        SET @SemsterTitleTimeTable = NULL;
        SET @SubjectTitleTimeTable = NULL;

        SET @CountTimeSlotTimeTable = (SELECT COUNT(*) FROM RoomTimeTableDetails);
        SET @AddOnebyOne = 1;

        WHILE @AddOnebyOne <= @CountTimeSlotTimeTable DO

            SET @GETProgramSemesterSubjectID = (SELECT ProgramSemesterSubjectID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            IF @GETProgramSemesterSubjectID > 0 THEN
            
                SET @LectureSUBJECTID = (SELECT LectureSubjectID FROM ProgramSemesterSubjectTable WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID);
                SET @programSemesterSubjectID = (SELECT ProgramSemesterSubjectID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
                SET @programSemesterTitle = (SELECT SUBSTR(Title, 11) FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @professorName = (SELECT ProfessorName FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @courseTitle = (SELECT CourseTitle FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @programID = (SELECT ProgramID FROM all_subjects_view WHERE ProgramSemesterSubjectID = @programSemesterSubjectID LIMIT 1);
                SET @SubjectTitleTimeTable = (SELECT SubjectTitle FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETRoomID = (SELECT RoomID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETLabID = (SELECT LabID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETDayTimeSlotID = (SELECT DayTimeSlotID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETLectureID = (SELECT LectureID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETTimeSlotName = (SELECT SlotTitle FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @GETDayTitle = (SELECT DayTitle FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
                SET @timeTableTypeID := (SELECT timeTableTypeID FROM RoomTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);

                IF @GETRoomID > 0 THEN
                
                    SET @LocationTitleTimeTable = (SELECT RoomNo FROM RoomTable WHERE RoomID = @GETRoomID LIMIT 1);
                    
                END IF;

                IF @GETLabID > 0 THEN
                
                    SET @LocationTitleTimeTable = (SELECT LabNo FROM LabTable WHERE LabID = @GETLabID LIMIT 1);
                END IF;

                IF @timeTableTypeID = 2 THEN
                    SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle), ' (Replica)\n', @courseTitle, '\n(', @professorName,')');
                ELSE
                    SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle), '\n', @courseTitle, '\n(', @professorName,')');
                END IF;

                UPDATE RoomTimeTable SET programID = @programID;
                IF @GETDayTitle = 'MONDAY' THEN
                    UPDATE RoomTimeTable SET MONDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'TUESDAY' THEN
                    UPDATE RoomTimeTable SET TUESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'WEDNESDAY' THEN
                    UPDATE RoomTimeTable SET WEDNESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'THURSDAY' THEN
                    UPDATE RoomTimeTable SET THURSDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'FRIDAY' THEN
                    UPDATE RoomTimeTable SET FRIDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'SATURDAY' THEN
                    UPDATE RoomTimeTable SET SATURDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                ELSEIF @GETDayTitle = 'SUNDAY' THEN
                    UPDATE RoomTimeTable SET SUNDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
                END IF;
            END IF;

            SET @AddOnebyOne = @AddOnebyOne + 1;

        END WHILE;

        SET @GETTimeTableOneByOne = @GETTimeTableOneByOne + 1;

        INSERT INTO AllRoomTimeTable (ROOMID, ROOMNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, programID)
        SELECT ROOMID, ROOMNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, programID
        FROM RoomTimeTable;

    END WHILE;

    SELECT * FROM AllRoomTimeTable WHERE ROOMNAME IS NOT NULL;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_PrintSemesterwiseTimeTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `sp_PrintSemesterwiseTimeTables`()
BEGIN
delete from AllSemesterTimeTable;

drop temporary table if exists TimeSlotTimeTable;

    CREATE TEMPORARY TABLE TimeSlotTimeTable (
    rowNo INT,
    slotTitle VARCHAR(200)
	);
    drop temporary table if exists SemesterTimeTable;
    CREATE TEMPORARY TABLE SemesterTimeTable (
    timeTableID INT,
    semester VARCHAR(300) not null,
    timee VARCHAR(300),
    MONDAY VARCHAR(300),
    TUESDAY VARCHAR(300),
    WEDNESDAY VARCHAR(300),
    THURSDAY VARCHAR(300),
    FRIDAY VARCHAR(300),
    SATURDAY VARCHAR(300),
    SUNDAY VARCHAR(300),
    programID int
	);
    
    -- Declaration of SemesterTimeTableDetails table
drop temporary table if exists SemesterTimeTableDetails;
CREATE TEMPORARY TABLE SemesterTimeTableDetails (

    RowNo INT,
    TimeTableID INT,
    ProgramSemesterSubjectID INT,
    SubjectTitle VARCHAR(400),
    RoomID INT,
    LabID INT,
    DayTimeSlotID INT,
    SlotTitle VARCHAR(200),
    DayTitle VARCHAR(80),
    LectureID INT,
    DayID INT,
    IsActive BIT,
    courseCode varchar(50),
    programID int
);

   SET @currentMonth = MONTH(CURDATE());
    SET @currentYear = YEAR(CURDATE()); 

    if @currentMonth IN (9, 10, 11, 12) THEN
        SET @season = CONCAT('Fall ', @currentYear);
    ELSEIF @currentMonth IN (1,2, 3, 4, 5, 6) THEN
        SET @season = CONCAT('Spring ', @currentYear);
    ELSEIF @currentMonth IN (7, 8) THEN
        SET @season = CONCAT('Summer ', @currentYear);
    END IF;

set @countTotalSemester = (select count(*) from timetbltable);
set @getTimeTableOneByOne = 1;
select @SemesterTimeTableTitle;
WHILE @getTimeTableOneByOne  <= @countTotalSemester DO

	set @SemesterTimeTableTitle = (select SemesterTitle from timetbltable where timeTableID = @getTimeTableOneByOne limit 1);
	set @TimeTableTypeID = (select TimeTableTypeID from timetbltable where timeTableID = @getTimeTableOneByOne limit 1);
    
-- clear table 
	delete from semestertimetable;
 -- clear table   
    delete from TimeSlotTimeTable;
		-- Insert data into the temporary table
	SET @row_number := 0;

	INSERT INTO TimeSlotTimeTable (RowNo, SlotTitle)
	SELECT (@row_number := @row_number + 1) AS RowNo, SlotTitle
	FROM (
    
		SELECT DISTINCT SlotTitle, StartTime, EndTime
		FROM DayTimeSlotTable
		WHERE timetabletypeID = @TimeTableTypeID
		ORDER BY StartTime
        
	) AS DTST;
    
    SET @COUNTTIMEROWSTIMETABLE := (SELECT COUNT(*) FROM TimeSlotTimeTable);
	SET @CREATESLOTSVARIABLE := 1;
    -- Loop until CREATESLOTSVARIABLE is less than or equal to COUNTTIMEROWSTIMETABLE
WHILE @CREATESLOTSVARIABLE <= 5 DO
    -- Get the SlotTitle from TimeSlotTimeTable for the current RowNo
		SET @TIMETITLE := (SELECT SlotTitle FROM TimeSlotTimeTable WHERE RowNo = @CREATESLOTSVARIABLE );


    INSERT INTO SemesterTimeTable (TimeTableID, semester,TIMEE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,programID)
    VALUES (0, @SemesterTimeTableTitle,@TIMETITLE, 'Break', 'Break', 'Break', 'Break', 'Break', 'Break', 'Break',0);
    
    -- Increment CREATESLOTSVARIABLE
    SET @CREATESLOTSVARIABLE = @CREATESLOTSVARIABLE + 1;
END WHILE;

DELETE FROM SemesterTimeTableDetails;

-- Initialize variable for row numbering
SET @row_number := 0;

-- Insert data into SemesterTimeTableDetails with row numbering
INSERT INTO SemesterTimeTableDetails (

    RowNo, 
    TimeTableID,
    ProgramSemesterSubjectID, 
    SubjectTitle, 
    RoomID, 
    LabID, 
    DayTimeSlotID, 
    SlotTitle,
    DayTitle,
    LectureID, 
    DayID, 
    IsActive,
    courseCode,
    programID
)
SELECT 
    (@row_number := @row_number + 1) AS RowNo,
    TTD.TimeTableID,
    TTD.ProgramSemesterSubjectID, 
    TTD.lectureSubjectTitle, 
    TTD.RoomID, 
    TTD.LabID, 
    TTD.DayTimeSlotID,
    TTD.slotTitle, 
    TTD.dayTitle,
    TTD.LectureID, 
    TTD.DayID, 
    TTD.IsActive,
    TTD.courseCode,
    TTD.programID
from 
	timetabledetailstable as TTD
WHERE 
    TTD.TimeTableID = @GETTimeTableOneByOne 
ORDER BY 
    DayTimeSlotID;
    
    -- Print SELECT Semester Class 
	 -- SELECT * FROM SemesterTimeTableDetails;
      set @timeTableID = (select timeTableID FROM SemesterTimeTableDetails limit 1);
      UPDATE SemesterTimeTable SET TimeTableID = @TimeTableID;
		
        SET @LocationTitleTimeTable := NULL;
		SET @SemesterTitleTimeTable := NULL;
		SET @SubjectTitleTimeTable := NULL;
        

		SELECT COUNT(*) INTO @CountTimeSlotTimeTable FROM SemesterTimeTableDetails;
		SET @AddOnebyOne := 1;
		WHILE @AddOnebyOne <= @CountTimeSlotTimeTable DO
			SET @GETProgramSemesterSubjectID := (SELECT ProgramSemesterSubjectID FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
			IF @GETProgramSemesterSubjectID > 0 THEN
				SET @SubjectTitleTimeTable := (SELECT SubjectTitle FROM all_subjects_view WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID LIMIT 1);
				SET @courseCode := (SELECT courseCode FROM all_subjects_view WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID LIMIT 1);
                SET @courseTitle := (SELECT courseTitle FROM all_subjects_view WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID LIMIT 1);
                SET @programID := (SELECT programID FROM all_subjects_view WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID LIMIT 1);
               set @semesterTitle := (SELECT semester FROM all_subjects_view WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID LIMIT 1);
              
              
			
              set @practicalHours := (select crHrs from coursetable where title = @courseTitle and roomTypeID = 4 limit 1);
			  set @nonPracticalHours := (select crHrs from coursetable where title = @courseTitle and roomTypeID = 3 limit 1);
				IF @practicalHours is null then
					 set @practicalHours = 0;
                 end if;    
               IF @nonPracticalHours is null then
					 set @nonPracticalHours = 0;
                 end if;    
                set @courseTitle = (select courseTitle from all_subjects_view where programsemestersubjectID =  @GETProgramSemesterSubjectID LIMIT 1);
                set @professorName = (select professorName from all_subjects_view where programsemestersubjectID =  @GETProgramSemesterSubjectID LIMIT 1);
				Set @totalCreditHours =  @practicalHours  + @nonPracticalHours ;
                SET @GETRoomID := (SELECT RoomID FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
				SET @GETLabID := (SELECT LabID FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
				SET @GETDayTimeSlotID := (SELECT DayTimeSlotID FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
				SET @GETLectureID := (SELECT LectureID FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
				SET @GETTimeSlotName := (SELECT SlotTitle FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
				SET @GETDayTitle := (SELECT DayTitle FROM SemesterTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 LIMIT 1);
		IF @GETRoomID > 0 THEN
        
		SET @LocationTitleTimeTable := (SELECT RoomNo FROM RoomTable WHERE RoomID = @GETRoomID LIMIT 1);
		ELSE IF @GETLabID > 0 THEN
			SET @LocationTitleTimeTable := (SELECT LabNo FROM LabTable WHERE LabID = @GETLabID LIMIT 1);
	END IF;
        END IF;

IF @GETLabID > 0 THEN
    SET @LocationTitleTimeTable := (SELECT LabNo FROM LabTable WHERE LabID = @GETLabID LIMIT 1);
END IF;

SET @SubjectTitleTimeTable := CONCAT(@courseTitle, '\n',Concat( '(',@courseCode,')  \\  ',@totalCreditHours,'(',@nonPracticalHours,'-',@practicalHours,')'),'\n(',@professorName ,')\n',@LocationTitleTimeTable);

UPDATE SemesterTimeTable set  programID = @programID WHERE SUNDAY = 'Break';


CASE @GETDayTitle
    WHEN 'MONDAY' THEN
        UPDATE SemesterTimeTable SET MONDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'TUESDAY' THEN
        UPDATE SemesterTimeTable SET TUESDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'WEDNESDAY' THEN
        UPDATE SemesterTimeTable SET WEDNESDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'THURSDAY' THEN
        UPDATE SemesterTimeTable SET THURSDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'FRIDAY' THEN
        UPDATE SemesterTimeTable SET FRIDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'SATURDAY' THEN
        UPDATE SemesterTimeTable SET SATURDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
    WHEN 'SUNDAY' THEN
        UPDATE SemesterTimeTable SET SUNDAY = @SubjectTitleTimeTable, SEMESTER = @SemesterTimeTableTitle WHERE TIMEE = @GETTimeSlotName;
        ELSE
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid day title encountered.';
END CASE;
			END IF;

	SET @AddOnebyOne := @AddOnebyOne + 1;
END WHILE;
	SET @GETTimeTableOneByOne = @GETTimeTableOneByOne + 1;
	INSERT INTO AllSemesterTimeTable (TimeTableID, SEMESTER, TIMEE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,programID,season)
	SELECT TimeTableID, SEMESTER, TIMEE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,programID,@season FROM 
    SemesterTimeTable;

END WHILE;
SELECT * FROM AllSemesterTimeTable;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_PrintTeacherwiseTimeTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `sp_PrintTeacherwiseTimeTables`()
BEGIN


delete from AllTeacherTimeTable;

DROP TEMPORARY TABLE IF EXISTS TimeSlotTimeTable;
 CREATE TEMPORARY TABLE TimeSlotTimeTable (
        RowNo INT,
        SlotTitle VARCHAR(200)
    );
    
 DROP TEMPORARY TABLE IF EXISTS TeacherTimeTableDetails;
 CREATE TEMPORARY TABLE TeacherTimeTableDetails (
    
        RowNo INT,
        TimeTableID INT,
        ProgramSemesterSubjectID INT,
        SubjectTitle VARCHAR(400),
        RoomID INT,
        LabID INT,
        DayTimeSlotID INT,
        SlotTitle VARCHAR(200),
        DayTitle VARCHAR(80),
        LectureID INT,
        LectureName VARCHAR(200),
        DayID INT,
        IsActive BIT,
        timeTableTypeID int,
        programID int
    );

 DROP TEMPORARY TABLE IF EXISTS AllTeachers;
CREATE TEMPORARY TABLE AllTeachers (
    RowNo INT,
    LectureID INT,
    LectureName VARCHAR(200)
);
 DROP TEMPORARY TABLE IF EXISTS TeacherTimeTable;
CREATE TEMPORARY TABLE TeacherTimeTable (
        TEACHERID INT,
        TEACHERNAME VARCHAR(300),
        TIME VARCHAR(300),
        MONDAY VARCHAR(300),
        TUESDAY VARCHAR(300),
        WEDNESDAY VARCHAR(300),
        THURSDAY VARCHAR(300),
        FRIDAY VARCHAR(300),
        SATURDAY VARCHAR(300),
        SUNDAY VARCHAR(300),
        programID int
    );



DELETE FROM AllTeachers;

INSERT INTO AllTeachers (RowNo, LectureID, LectureName)
SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo,
       TTD.LectureID,
       TTD.FullName
FROM (SELECT TD.LectureID, FullName
      FROM TimeTableDetailsTable TD
               INNER JOIN LectureTable LEC ON TD.LectureID = LEC.LectureID) TTD
WHERE TTD.LectureID > 0
GROUP BY TTD.LectureID, TTD.FullName;


SET @CountTotalTeacher = (SELECT max(LectureID) FROM AllTeachers);
SET @GETTimeTableOneByOne = 1;

WHILE @GETTimeTableOneByOne <= @CountTotalTeacher DO

    SET @TeacherTimeTableTitle = (SELECT LectureName FROM AllTeachers WHERE RowNo = @GETTimeTableOneByOne);



    DELETE FROM TeacherTimeTable;
    DELETE FROM TimeSlotTimeTable;

    INSERT INTO TimeSlotTimeTable (RowNo, SlotTitle)
    SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS RowNo, SlotTitle
    FROM (SELECT SlotTitle, StartTime, EndTime
          FROM DayTimeSlotTable
          WHERE ISActive = 1
          GROUP BY SlotTitle, StartTime, EndTime) DTST
    ORDER BY StartTime;

    SET @COUNTTIMEROWSTIMETABLE = (SELECT COUNT(*) FROM TimeSlotTimeTable);
    SET @CREATESLOTSVARIABLE = 1;

    WHILE @CREATESLOTSVARIABLE <= @COUNTTIMEROWSTIMETABLE DO

        SET @TIMETITLE = (SELECT SlotTitle FROM TimeSlotTimeTable WHERE RowNo = @CREATESLOTSVARIABLE);

        INSERT INTO TeacherTimeTable (TEACHERID, TEACHERNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
        VALUES (0, NULL, @TIMETITLE, 'Break', 'Break', 'Break', 'Break', 'Break', 'Break', 'Break');

        SET @CREATESLOTSVARIABLE = @CREATESLOTSVARIABLE + 1;

    END WHILE;

   

    DELETE FROM TeacherTimeTableDetails;

    SET @row_number_tt := 0;

-- Initialize row number variable
SET @row_number_tt := 0;

-- Insert data into TeacherTimeTableDetails
INSERT INTO TeacherTimeTableDetails (
    RowNo, 
    TimeTableID, 
    ProgramSemesterSubjectID, 
    SubjectTitle, 
    RoomID, 
    LabID, 
    DayTimeSlotID, 
    SlotTitle, 
    DayTitle, 
    LectureID, 
    LectureName, 
    DayID, 
    IsActive,
    timeTableTypeID
    )
SELECT 
    @row_number_tt := @row_number_tt + 1 AS RowNo,
    TTD.TimeTableID,
    TTD.ProgramSemesterSubjectID,
    TTD.SubjectTitle,
    TTD.RoomID,
    TTD.LabID,
    TTD.DayTimeSlotID,
    TTD.SlotTitle,
    TTD.DayTitle,
    TTD.LectureID,
    (SELECT fullName FROM lectureTable WHERE lectureID = TTD.LectureID) AS LectureName,
    TTD.DayID,
    TTD.IsActive,
    TTD.timeTableTypeID
FROM 
    TimeTableDetailsTable AS TTD
WHERE 
    TTD.LectureID = @GETTimeTableOneByOne
ORDER BY 
    TTD.DayTimeSlotID;


    SET @TeacherID = (SELECT LectureID FROM TeacherTimeTableDetails LIMIT 1);
    SET @TeacherName = (SELECT LectureName FROM TeacherTimeTableDetails LIMIT 1);
    SET @TeacherName = CONCAT(@TeacherName, ' - Time Table');

    UPDATE TeacherTimeTable SET TEACHERID = @TeacherID, TEACHERNAME = @TeacherName;

    SET @LocationTitleTimeTable = null;
    SET @SemsterTitleTimeTable = null;
    SET @SubjectTitleTimeTable = null;

    SET @CountTimeSlotTimeTable = (SELECT COUNT(*) FROM TeacherTimeTableDetails);
    SET @AddOnebyOne = 1;

    WHILE @AddOnebyOne <= @CountTimeSlotTimeTable DO

        SET @GETProgramSemesterSubjectID = (SELECT ProgramSemesterSubjectID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
        IF @GETProgramSemesterSubjectID > 0 THEN
            SET @LectureSUBJECTID = (SELECT LectureSubjectID FROM ProgramSemesterSubjectTable WHERE ProgramSemesterSubjectID = @GETProgramSemesterSubjectID);
           set @programSemesterSubjectID = (SELECT programsemestersubjectID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1 limit 1);
           set @programSemesterTitle = (select substr(Title,11) from all_subjects_view where programsemestersubjectID = @programSemesterSubjectID limit 1) ;
           set @professorName = (select professorName from all_subjects_view where programsemestersubjectID = @programSemesterSubjectID limit 1);
			set @courseTitle = (select courseTitle from all_subjects_view where programsemestersubjectID = @programSemesterSubjectID limit 1);
         set @programID = (select programID from all_subjects_view where programsemestersubjectID = @programSemesterSubjectID limit 1);
         SET @SubjectTitleTimeTable = (SELECT SubjectTitle FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETRoomID = (SELECT RoomID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETLabID = (SELECT LabID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETDayTimeSlotID = (SELECT DayTimeSlotID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETLectureID = (SELECT LectureID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETTimeSlotName = (SELECT SlotTitle FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            SET @GETDayTitle = (SELECT DayTitle FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
			set @timeTableTypeID := (SELECT timeTabletypeID FROM TeacherTimeTableDetails WHERE RowNo = @AddOnebyOne AND IsActive = 1);
            
            IF @GETRoomID > 0 THEN
                SET @LocationTitleTimeTable = (SELECT RoomNo FROM RoomTable WHERE RoomID = @GETRoomID LIMIT 1);
            END IF;

            IF @GETLabID > 0 THEN
                SET @LocationTitleTimeTable = (SELECT LabNo FROM LabTable WHERE LabID = @GETLabID LIMIT 1);
            END IF;

			IF @timeTableTypeID = 2 then
                        SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle),' (Replica)\n', @courseTitle,'\n',@LocationTitleTimeTable);
			else
                        SET @SubjectTitleTimeTable = CONCAT((@programSemesterTitle), '\n ', @courseTitle,'\n',@LocationTitleTimeTable);
            end if;
            
            Update TeacherTimeTable set programID  = @programID;
            IF @GETDayTitle = 'MONDAY' THEN
                UPDATE TeacherTimeTable SET MONDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'TUESDAY' THEN
                UPDATE TeacherTimeTable SET TUESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'WEDNESDAY' THEN
                UPDATE TeacherTimeTable SET WEDNESDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'THURSDAY' THEN
                UPDATE TeacherTimeTable SET THURSDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'FRIDAY' THEN
                UPDATE TeacherTimeTable SET FRIDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'SATURDAY' THEN
                UPDATE TeacherTimeTable SET SATURDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            ELSEIF @GETDayTitle = 'SUNDAY' THEN
                UPDATE TeacherTimeTable SET SUNDAY = @SubjectTitleTimeTable WHERE TIME = @GETTimeSlotName;
            END IF;
        END IF;

        SET @AddOnebyOne = @AddOnebyOne + 1;

    END WHILE;

    SET @GETTimeTableOneByOne = @GETTimeTableOneByOne + 1;

    INSERT INTO AllTeacherTimeTable (TEACHERID, TEACHERNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,programID)
    SELECT TEACHERID, TEACHERNAME, TIME, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,programID
    FROM TeacherTimeTable;

END WHILE;

SELECT * FROM AllTeacherTimeTable WHERE TEACHERNAME IS NOT NULL;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `all_subjects_view`
--

/*!50001 DROP VIEW IF EXISTS `all_subjects_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `all_subjects_view` AS select `psst`.`programSemesterSubjectID` AS `programSemesterSubjectID`,`psst`.`title` AS `SSTitle`,`psst`.`programSemesterID` AS `programSemesterID`,`psst`.`labID` AS `LabID`,`psal`.`Title` AS `Title`,`psal`.`ProgramSemesterIsActive` AS `ProgramSemesterisActive`,`psal`.`ProgramID` AS `ProgramID`,`psal`.`Program` AS `Program`,`psal`.`SemesterID` AS `SemesterID`,`psal`.`Semester` AS `Semester`,`psal`.`SemesterIsActive` AS `SemesterIsActive`,`psal`.`Capacity` AS `Capacity`,`psst`.`lectureSubjectID` AS `lectureSubjectID`,`lv`.`title` AS `SubjectTitle`,`lv`.`lectureID` AS `lectureID`,`lv`.`professorName` AS `professorName`,`lv`.`courseID` AS `courseID`,`lv`.`courseCode` AS `courseCode`,`lv`.`creditHours` AS `creditHours`,`lv`.`roomTypeID` AS `roomTypeID`,`lv`.`courseTitle` AS `courseTitle`,`psal`.`SessionID` AS `SessionID`,`psal`.`Session` AS `Session`,`psal`.`timeTabletypeID` AS `timetabletypeID` from ((`programsemestersubjecttable` `psst` join `v_programsemesteractivelist` `psal` on((`psal`.`ProgramSemesterID` = `psst`.`programSemesterID`))) join `lecture_view` `lv` on((`psst`.`lectureSubjectID` = `lv`.`lectureSubjectID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `lecture_view`
--

/*!50001 DROP VIEW IF EXISTS `lecture_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `lecture_view` AS select `lst`.`lectureSubjectID` AS `lectureSubjectID`,`lst`.`title` AS `title`,`lst`.`lectureID` AS `lectureID`,`lst`.`courseID` AS `courseID`,`lt`.`fullName` AS `professorName`,`ct`.`courseCode` AS `courseCode`,`ct`.`title` AS `courseTitle`,`ct`.`crHrs` AS `creditHours`,`ct`.`roomTypeID` AS `roomTypeID` from ((`lecturesubjecttable` `lst` join `lecturetable` `lt` on((`lst`.`lectureID` = `lt`.`lectureID`))) join `coursetable` `ct` on((`lst`.`courseID` = `ct`.`courseID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_allactivetimeslots`
--

/*!50001 DROP VIEW IF EXISTS `v_allactivetimeslots`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `v_allactivetimeslots` AS select `dts`.`dayTimeSlotID` AS `DayTimeSlotID`,`dts`.`slotTitle` AS `SlotTitle`,`dts`.`startTime` AS `StartTime`,`dts`.`dayID` AS `DayID`,`dt`.`name` AS `Name`,`dts`.`endTime` AS `EndTime`,`dt`.`isActive` AS `DayStatus`,`dts`.`timetabletypeID` AS `timetabletypeID`,`dts`.`isActive` AS `SlotStatus` from (`daytable` `dt` join `daytimeslottable` `dts` on((`dt`.`dayID` = `dts`.`dayID`))) where ((`dt`.`isActive` = 1) and (`dts`.`isActive` = 1)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_programsemesteractivelist`
--

/*!50001 DROP VIEW IF EXISTS `v_programsemesteractivelist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `v_programsemesteractivelist` AS select `programsemestertable`.`programSemesterID` AS `ProgramSemesterID`,concat(`sessiontable`.`title`,' ',`programtable`.`name`,' ',`semestertable`.`semesterName`) AS `Title`,`programsemestertable`.`isActive` AS `ProgramSemesterIsActive`,`programsemestertable`.`programID` AS `ProgramID`,`programtable`.`name` AS `Program`,`programtable`.`isActive` AS `ProgramIsActive`,`programsemestertable`.`SemesterID` AS `SemesterID`,`semestertable`.`semesterName` AS `Semester`,`semestertable`.`isActive` AS `SemesterIsActive`,`programsemestertable`.`capacity` AS `Capacity`,`programsemestertable`.`sessionID` AS `SessionID`,`sessiontable`.`title` AS `Session`,`programsemestertable`.`timetabletypeID` AS `timeTabletypeID` from (((`programsemestertable` join `programtable` on((`programsemestertable`.`programID` = `programtable`.`programID`))) join `semestertable` on((`programsemestertable`.`SemesterID` = `semestertable`.`semesterID`))) join `sessiontable` on((`programsemestertable`.`sessionID` = `sessiontable`.`sessionID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-13  7:29:53
