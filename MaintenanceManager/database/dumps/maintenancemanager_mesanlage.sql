-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: maintenancemanager
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `mesanlage`
--

DROP TABLE IF EXISTS `mesanlage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesanlage` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `prodstueck` int(11) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `anlageId` int(11) DEFAULT NULL,
  `anlage2Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_anlage_id_idx` (`anlageId`),
  CONSTRAINT `fk_anlage_id` FOREIGN KEY (`anlageId`) REFERENCES `anlage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesanlage`
--

LOCK TABLES `mesanlage` WRITE;
/*!40000 ALTER TABLE `mesanlage` DISABLE KEYS */;
INSERT INTO `mesanlage` VALUES (6,'4858 - VG 150',20885,'2017-11-30 10:17:51',19,NULL),(7,'4865 - FIAT RDM',22603,'2017-11-30 10:17:51',13,NULL),(8,'4864 - FIAT KUPPLUNG',23061,'2017-11-30 10:17:51',10,NULL),(11,'4873 - HAA350',13366,'2017-11-30 10:17:51',28,NULL),(12,'4872 - HAA550 o. Sperre',38372,'2017-11-30 10:17:51',28,NULL),(14,'4861 - LX VA',42575,'2017-11-30 10:17:51',22,NULL),(15,'4877 - VAA350',56089,'2017-11-30 10:17:51',5,NULL),(19,'4834 - Audi R4V',802,'2017-11-09 13:48:05',NULL,NULL),(20,'4827 - AGW N47',21727,'2017-11-10 07:38:36',41,NULL),(22,'4837 - Audi HL 195.T1',14893,'2017-11-30 10:17:51',9,NULL),(26,'4872 - HAA550 m. Sperre',8886,'2017-11-30 10:17:51',28,NULL),(27,'4873 - HAA550 Vormontage',47512,'2017-11-30 10:17:51',27,NULL),(29,'4873 - TTRS Prüfst.',47,'2017-11-09 11:59:52',NULL,NULL),(30,'4879 - VAA550',47147,'2017-11-30 10:17:51',12,NULL),(32,'4838 - Nissan W61G',70305,'2017-11-30 10:17:51',21,NULL),(33,'4835 - Renault W62',11977,'2017-11-30 10:17:51',14,NULL),(34,'4837 - Aktuatorvorm. ATV',46660,'2017-11-30 10:17:51',NULL,NULL),(35,'4822 - PL72 ATC',46089,'2017-11-30 10:17:51',23,NULL),(37,'4822 - PL72 Torsen',121811,'2017-11-30 10:17:51',23,NULL),(38,'4837 - Audi HL 220.T1',315,'2017-11-10 07:40:45',9,NULL),(39,'4839 - ATC 35L',146831,'2017-11-30 10:17:51',7,NULL),(42,'4883 - VAA450 neu',189892,'2017-11-30 10:17:51',20,NULL),(43,'4842 - ATC Sigma Jaguar',4292,'2017-11-30 10:17:51',8,NULL),(44,'4856 - Volvo ERAD',2852,'2017-11-30 10:17:51',32,NULL),(46,'4844 - ATC35L Omega',1821,'2017-11-10 07:44:09',35,NULL),(47,'4843 - AGW Volvo MBS',381814,'2017-11-30 10:17:51',16,NULL),(48,'4823 - AGW GM SGE_MBS_OPEL',858,'2017-11-09 11:59:52',NULL,NULL),(49,'4845 - Porsche Macan',105978,'2017-11-30 10:17:51',34,NULL),(50,'4842 - ATC Sigma Maserati',33183,'2017-11-30 10:17:51',8,NULL),(51,'4842 - ATC Sigma Hyundai',44379,'2017-11-30 10:17:51',8,NULL),(54,'4865 - Daimler MFA',121996,'2017-11-30 10:17:51',13,NULL),(55,'4864 - Daimler Kupplung',122801,'2017-11-30 10:17:51',10,NULL),(57,'4880 - Maserati FAD',1739,'2017-11-20 08:21:50',11,31),(58,'4824 - LR ITC 1-Speed',53576,'2017-11-30 10:17:51',17,NULL),(59,'4826 - LR Diff Vormontage neu',123589,'2017-11-30 10:17:51',66,NULL),(60,'4877 - VAA350+/PQ26',1838,'2017-11-20 07:01:01',5,NULL),(61,'4877 - VAA350+/PQ26 VM',2018,'2017-11-30 10:17:51',NULL,NULL),(62,'4873 - HAA350+MQB A-SUV',76110,'2017-11-30 10:17:51',28,NULL),(63,'4829 - Fert. Diff. Daimler/ITC',134315,'2017-11-30 10:17:51',NULL,NULL),(64,'4828 - Zahnradabwälzstation',1148304,'2017-11-30 10:17:51',NULL,NULL),(66,'Audi HL 600 B (HL 195.S2)',0,'2017-11-30 10:17:51',NULL,NULL),(68,'5847 - ATC Wellenfertigung',323423,'2017-11-30 10:17:51',NULL,NULL),(69,'5844 - KE PL72',42698,'2017-11-30 10:17:51',NULL,NULL),(70,'5844 - KE Macan',92637,'2017-11-30 10:17:51',NULL,NULL),(73,'4868 - CTC 50',256784,'2017-11-30 10:17:51',56,NULL),(74,'4844 - ATC 13-1',125768,'2017-11-30 10:17:51',35,NULL),(76,'5829 - CTC50 Vormontage',207736,'2017-11-30 10:17:51',70,NULL),(77,'4837 - Audi HL 195.T2',27999,'2017-11-30 10:17:51',9,NULL),(78,'4837 - Audi HL 220.T2',3928,'2017-11-30 10:17:51',9,NULL),(79,'4831 - ITC Wellenzelle',142180,'2017-11-30 10:17:52',NULL,NULL),(80,'4880 - MAS FAD Levante',29692,'2017-11-30 10:17:52',11,31),(81,'5829 - CTC50 Vormontage 2',180779,'2017-11-30 10:17:52',71,NULL),(82,'4870 - CTC50 Wellenzelle Li H.',317162,'2017-11-30 10:17:52',NULL,NULL),(83,'4821 - LR ITC NG 2-speed',123033,'2017-11-30 10:17:52',61,NULL),(84,'4879 - VAA600',2504,'2017-11-30 10:17:52',12,NULL),(85,'4869 - CTC50 Linie 2',157523,'2017-11-30 10:17:52',64,NULL),(86,'4875 - Wieser Laserbox I',367913,'2017-11-30 10:17:52',NULL,NULL),(87,'4869 - CTC52 Linie2',8134,'2017-11-30 10:17:52',64,84),(88,'4866 - ATC GIORGIO',67287,'2017-11-30 10:17:52',62,NULL),(89,'4863 - LASMA Schweißgruppe',357363,'2017-11-30 10:17:52',NULL,NULL),(92,'4870 - CTC50 Wellenzelle Re H.',64909,'2017-11-30 10:17:52',NULL,NULL),(93,'4830 - Laser Trucell Nissan',14718,'2017-11-09 13:48:06',NULL,NULL),(94,'5844 - KE Giorgio',65910,'2017-11-30 10:17:52',NULL,NULL),(95,'5844 - KE CTC50',411831,'2017-11-30 10:17:52',NULL,NULL),(96,'5844 - KE CTC52',8615,'2017-11-30 10:17:52',NULL,NULL),(97,'4918 - CTC50 Wellenz. Li H. Z3',137198,'2017-11-30 10:17:52',NULL,NULL),(99,'4831 - ITC Wellenzelle 1-Sp',41367,'2017-11-30 10:17:52',NULL,NULL),(100,'5858 - GST1 - ARD Schleifen',164809,'2017-11-30 10:17:52',NULL,NULL),(101,'5858 - GST2 - ARD Schleifen',112303,'2017-11-30 10:17:52',NULL,NULL),(102,'5858 - GST3 - ARD Schleifen',141174,'2017-11-30 10:17:52',NULL,NULL),(103,'5858 - GST4 - ARD Schleifen',20025,'2017-11-30 10:17:52',NULL,NULL),(104,'5862 - Macan Wellengr. ha.',4554,'2017-11-09 13:48:06',NULL,NULL),(105,'4867 - Fiat Wellengr. we.',32072,'2017-11-30 10:17:52',NULL,NULL),(106,'4867 - Maserati Wellengr. we.',8407,'2017-11-30 10:17:52',NULL,NULL),(107,'4867 - Giorgio Wellengr. ha.',41202,'2017-11-30 10:17:52',NULL,NULL),(108,'5829 - CTC Vormontage 3',117116,'2017-11-30 10:17:52',82,NULL),(109,'4884 - CTC 50 LINIE 3',97347,'2017-11-30 10:17:52',81,NULL),(110,'4880 - MAS FAD MY18',3560,'2017-11-30 10:17:52',11,31),(111,'4875 - Wieser Laserbox 2',105058,'2017-11-30 10:17:52',NULL,NULL),(113,'4869 - CTC45',25,'2017-11-30 10:17:52',64,NULL),(114,'4869 - CTC53',68,'2017-11-30 10:17:52',64,NULL);
/*!40000 ALTER TABLE `mesanlage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-02 16:56:11