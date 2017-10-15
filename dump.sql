-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: websitename
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activities` (
  `id` varchar(36) NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
INSERT INTO `activities` VALUES ('1','Amusement Park','amusement_park'),('10','Shopping mall','shopping_mall'),('11','Spa','spa'),('12','Zoo','zoo'),('13','Park','park'),('2','Art Gallery','art_gallery'),('3','Bar','bar'),('4','Bowling Alley','bowling_alley'),('5','Cafe','cafe'),('6','Museum','museum'),('7','Night Club','night_club'),('8','Casino','casino'),('9','Clothing Store','clothing_store');
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `friend_user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_fk` (`user_id`),
  KEY `friend_user_id_fk` (`friend_user_id`),
  CONSTRAINT `friend_user_id_fk` FOREIGN KEY (`friend_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES ('PNG2f11e-bcb0-47d4-a17b-30e54f00101c','PNG04842-7b14-4c0d-8f78-4ae6a3b6a701','PNG7a5c3-2ff5-4f24-a269-d6347b06dbfc'),('PNGeed34-bcb5-4cd3-8211-4b0ebb316791','PNG7a5c3-2ff5-4f24-a269-d6347b06dbfc','PNG04842-7b14-4c0d-8f78-4ae6a3b6a701');
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itinerary`
--

DROP TABLE IF EXISTS `itinerary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itinerary` (
  `id` varchar(36) NOT NULL,
  `plan_id` varchar(36) NOT NULL,
  `date` varchar(36) NOT NULL,
  `sequence` int(3) NOT NULL,
  `location_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `plan_id_fk` (`plan_id`),
  KEY `location_id_fk` (`location_id`),
  CONSTRAINT `it_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `it_plan_id_fk` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itinerary`
--

LOCK TABLES `itinerary` WRITE;
/*!40000 ALTER TABLE `itinerary` DISABLE KEYS */;
INSERT INTO `itinerary` VALUES ('PNG43f62-496c-424e-a95b-1ade37cc3338','PNGa7afe-428a-40df-af36-52df9829919f','05/01/2017',1,'PNG7d79e-348a-4b41-ab96-01fded0bb828'),('PNG606d1-845c-40fa-bb49-3ef112852bac','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/01/2017',1,'PNGba693-d137-4406-b3da-be586094a478'),('PNG6370f-3023-4359-94e7-b4e5dd8c4160','PNGa7afe-428a-40df-af36-52df9829919f','05/03/2017',1,'PNG4c5da-95c0-4929-aece-3367c072a376'),('PNG9550d-40bb-4593-91fa-2e15e4aea987','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/02/2017',1,'PNG798cd-db0d-4ccb-bbd8-d0593080c53d'),('PNG95a7c-f8aa-4522-970e-ca2842887429','PNGa7afe-428a-40df-af36-52df9829919f','05/02/2017',1,'PNGc3d41-be39-47d1-8d7a-842f54bbd66f'),('PNGb361e-e0f1-42b9-929e-b4ed1c850cfa','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/01/2017',1,'PNG9a944-ead3-45cf-941a-17fc6805bc70'),('PNGb6195-fed5-40ca-83e8-7ffe17f253dd','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/02/2017',1,'PNG3646f-749b-4232-a675-e588abf4a31e'),('PNGbe882-fe88-4010-a8b4-d40bc9a362e1','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/01/2017',1,'PNG0f292-a23f-4567-b0ba-8de2aa8c5ef0'),('PNGc335b-3af1-4691-9a12-d920a58f3e8b','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/03/2017',1,'PNG9db87-76d8-44fa-8d12-e047146711d6'),('PNGe1be9-3847-4b9b-b287-e3e360bdee59','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/02/2017',1,'PNG6dd97-2d20-4417-bcdb-b9113d6f643d'),('PNGeabdc-96e0-46aa-a425-94a4194eff0f','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/03/2017',1,'PNGc81f6-7962-45d9-a0df-fc3e3c195d32'),('PNGf2fcb-711a-46ac-9525-576ef0ebb0dd','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','05/03/2017',1,'PNG64934-a1cf-470e-ad1a-26760f09a980');
/*!40000 ALTER TABLE `itinerary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` varchar(36) NOT NULL,
  `time` varchar(20) DEFAULT NULL,
  `duration` float(5,1) DEFAULT NULL,
  `name` text NOT NULL,
  `details` text,
  `image` text,
  `place_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES ('PNG0f292-a23f-4567-b0ba-8de2aa8c5ef0',NULL,3.0,'Shops at Billerica',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAACapj_sx6q2cRlQSHywneDWC3Xryl4vbOYNyjJ_vPT2CQelmmDnePJBWVT7cMC2r-WXg-XsZ_4ESYOQO4JVKo2pzCVBflUxGaLuFVwfUnAxT4_0y4MhwrjPJn_OrfIuH7NRubCIjBgZeInfHJ-PEIdsK4NANJ7zVTYEplHYO4Y7sEhCWbPyYx5Pt82ovFjtH2uDFGhRF8Y4vsKbeFB6hSSHwx6Dy88_geQ&maxheight=1000','ChIJb3imKsCh44kRFhKnOyV4eJE'),('PNG3646f-749b-4232-a675-e588abf4a31e',NULL,3.0,'Canobie Lake Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAJoj_aDqKd7EnYgsPdutH98ewAMsQv-K9d7Yd3l4xhkFQE-JP5iVI9s-nD2OaJJn9tEqoNIH8ZMXwLjMFzhoODzOFw6B6m4J5dgGoPADKDb5VbxImm2pCXtA-QLmCodvMq_8-R96as3j1-Fk-iCOpBF1EkIp6SsGchpiOYdUPirDEhCJJmVu237VLW0kR-dWSnCHGhRhgAltVlooLADtQU8molwdkw5cnQ&maxheight=1000','ChIJtdlg_66r44kRt2Oz4B8aQJM'),('PNG4c5da-95c0-4929-aece-3367c072a376',NULL,3.0,'Santa\'s Village AZoosment Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAANcJg_G9HrD3GGIPKxtc1oW7K-caOg06paDypl2-d3Mloexp398MdIkryfVb5G5AQoecqL8IpIFYRdjvQsFxLEeE_K8Wo6OW-GZXPbD-n6ibWxytb5-U6BpxG3XJJkrlNGT6esrJjj11UAhUWtrTDlqAJuUSOcmihx2Op13eGK0oEhC_qUyuDl4VUehh-BaX5Ld7GhT7i-GqrprH3cPrev3CDmSRDdfnqQ&maxheight=1000','ChIJkbOqwTsPD4gRbgvrDzlU_m0'),('PNG64934-a1cf-470e-ad1a-26760f09a980',NULL,2.0,'Franklin Park Zoo',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAE6MJHjOjDqrwUAKwXAwhWNu4r1PlzR1faZFMmUbTOg7UOjXME5iN4RmqDl-zCEokncQ70MLETwYpigZYOoWUAKtgkrUVD3cZ_ISNTDY8WAuMxZizfH7BtBKDysIBa8AwK0HarzJFd1ObkVC0X5wozwmBNk2rlb-J-N-4xnaPLa1EhClRq1FLnZfxl12eaySOpqRGhSU4G_2QMs2Q_ICGy8FE0xPEqL4Ww&maxheight=1000','ChIJme0fDdx744kRkewtCrKcph0'),('PNG6dd97-2d20-4417-bcdb-b9113d6f643d',NULL,3.0,'Colonial Shopping Mall',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAEZxjlFinn7qUdVrQjgm6gVDa5BM0CXkJDreXZJVXhNw6LSqHAqXFiZVYhf1_75stmphkBYieeEn9irhAsV93aOUHqrx_FC69MrqOKUD-0xHkRFY2i9GHHGoeS1vMfZO_e7GL3qvHV4cb5kbO_H6Cg-8T3yMDIBdWbSQGD1KmSaWEhDHi8ig8gKqO5p32fLbU3s4GhSa__oZiiw77snjx1R0uQFpMt7tXQ&maxheight=1000','ChIJy4HFyseC44kRwi9WRJ4YWa4'),('PNG798cd-db0d-4ccb-bbd8-d0593080c53d',NULL,3.0,'Launch Trampoline Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAADgook1KzXkhzK3iHYKzm78kal8yPuF-GXwldX4N5mmmzZbhGr6_7frKOVecoVwFT53ncahIL6WwKqYHXXS7taZryOtS1qJw2jpdQ9YdPCQ8mOjh0u40ezO5wJvxrwCNltpshusegLRFGOl5LNmxpPfTtDBR6Ctdvun6EWP3Yl7LEhAJ13xpyopaz-OikFEv8cw0GhRpie9-QYnm3VFMmTHR3t5qClzvsA&maxheight=1000','ChIJr3CuOLiC44kRpynUMcdcfEw'),('PNG7d79e-348a-4b41-ab96-01fded0bb828',NULL,5.0,'Odyssey Fun World',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAA5KBTNMTKgu5yBq-NLkkj7yB1LMLiqyLY1lj8Okps2JwT27uCbyIGGqd-nSJV4EtGVeBqLVKvVXXDNKwqC7t_7i-U4bz19zQ4DRRK1VPENbEFtI_hpK44W1eax8IEIGQR3LWi6tKv4B_osC1kgTSC1hUCq8lOTizrQ0j0X1FzLaEhCcAYmxV_7ot-qUGcenSPysGhQdr5danEPKJd3sUNUekgAzPgQuVw&maxheight=1000','ChIJk3f7d80VDogRqXMrI1aD3EI'),('PNG9a944-ead3-45cf-941a-17fc6805bc70',NULL,3.5,'Canobie Lake Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAJoj_aDqKd7EnYgsPdutH98ewAMsQv-K9d7Yd3l4xhkFQE-JP5iVI9s-nD2OaJJn9tEqoNIH8ZMXwLjMFzhoODzOFw6B6m4J5dgGoPADKDb5VbxImm2pCXtA-QLmCodvMq_8-R96as3j1-Fk-iCOpBF1EkIp6SsGchpiOYdUPirDEhCJJmVu237VLW0kR-dWSnCHGhRhgAltVlooLADtQU8molwdkw5cnQ&maxheight=1000','ChIJtdlg_66r44kRt2Oz4B8aQJM'),('PNG9db87-76d8-44fa-8d12-e047146711d6',NULL,3.0,'Launch Trampoline Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAADgook1KzXkhzK3iHYKzm78kal8yPuF-GXwldX4N5mmmzZbhGr6_7frKOVecoVwFT53ncahIL6WwKqYHXXS7taZryOtS1qJw2jpdQ9YdPCQ8mOjh0u40ezO5wJvxrwCNltpshusegLRFGOl5LNmxpPfTtDBR6Ctdvun6EWP3Yl7LEhAJ13xpyopaz-OikFEv8cw0GhRpie9-QYnm3VFMmTHR3t5qClzvsA&maxheight=1000','ChIJr3CuOLiC44kRpynUMcdcfEw'),('PNGba693-d137-4406-b3da-be586094a478',NULL,2.5,'Burlington Mall',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAI0d6Snsqje7LS1QJrB2jCHuhBSOgk4hQUcaakkMxIgdrwb-YWHnCcfFfufbcv741bEK7hLA7Y9BVTjirQEAcHEOn0-Okb9psoD7ixn7F9iAVlbVCYNK-0CqxUyAn-XYjslI-k3r595_FOHyUeXymAH3GbkS-z5p8lAgEBEjJhsWEhB0bNS0dhLcq4c2ZsNgyClPGhSmBfnWKoo-T_bKgiJXFV6FkAVOcQ&maxheight=1000','ChIJ20siOTie44kR9rxwj-HpBEg'),('PNGc3d41-be39-47d1-8d7a-842f54bbd66f',NULL,3.0,'Pirates\' Cove Children\'s Theme Park',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAOK3Au2XvKPPk9vWyeoISbhlHeBvnjiDHQRdxzjm5AQDh_XQQewfLB-6BcByvVCbkh6CJSQoANzkN0WnPCBYft5G6D0Bi_r7z2wWqYVJLjCnNIl9OMfQeQazFg4pa5XbVQYKr_t74RQLvtx7YqrlTXfNj4w1pt8zKz714uGJWoFnEhCsxFqK7lvHnqv-6ucpPDk3GhTMldGQdXqrWph_6pMoxnWVpVhR3A&maxheight=1000','ChIJf99_RCCuD4gRWM4L62PMUh0'),('PNGc81f6-7962-45d9-a0df-fc3e3c195d32',NULL,3.0,'Paragon Carousel',NULL,'https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBxlNWdjpL68zx09t9BCOmFlEL_Ai114X4&photoreference=CoQBdwAAAE-1AEnty1Z9-sfdTq3I3MOT9rCn9kzd7j28SplCF2AJL8qghmQw5n4dnsvVnhyPp8BuTg5FVcI6LFoPlqbL3xqik8blMp7Ep9DqAgVgA3sQbDfyRnUSI3AmPJBOcLlHcepjDvWDkBs1FzXgjf95pZjn-KsU45nxvjuOMUAf3SKsEhA84Y7IV-CliTu8H4fagtAdGhTmEq5gNetflpeQHJRqxehkxvXNSg&maxheight=1000','ChIJj_J9kAJn44kRMenr_-jRzwo');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `id` varchar(36) NOT NULL,
  `location` varchar(50) NOT NULL,
  `start_date` varchar(20) NOT NULL,
  `end_date` varchar(20) NOT NULL,
  `day_start` varchar(20) DEFAULT NULL,
  `day_end` varchar(20) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `hours` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES ('PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','Boston','05/01/2017','05/03/2017',NULL,NULL,42.3600825,-71.05888010000001,9),('PNGa7afe-428a-40df-af36-52df9829919f','Chicago','05/01/2017','05/04/2017',NULL,NULL,41.8781136,-87.62979819999998,5);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_members`
--

DROP TABLE IF EXISTS `plan_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_members` (
  `id` varchar(36) NOT NULL,
  `plan_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `isCreator` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_fk` (`user_id`),
  KEY `plan_id_fk` (`plan_id`),
  CONSTRAINT `plan_id_fk` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pm_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_members`
--

LOCK TABLES `plan_members` WRITE;
/*!40000 ALTER TABLE `plan_members` DISABLE KEYS */;
INSERT INTO `plan_members` VALUES ('PNG60d2f-ce27-4406-83be-401f5574b279','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','PNG9a1d3-192a-43b2-a593-d3307a90d468',''),('PNG6d942-d71a-4307-93ed-780a77890b1a','PNGa7afe-428a-40df-af36-52df9829919f','PNG9a1d3-192a-43b2-a593-d3307a90d468',''),('PNG73270-eb49-4df5-97bd-8a9eca0dcbce','PNG44f0a-e9a9-49d5-9ea0-75153f1684d4','PNG04842-7b14-4c0d-8f78-4ae6a3b6a701','\0');
/*!40000 ALTER TABLE `plan_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('PNG04842-7b14-4c0d-8f78-4ae6a3b6a701','Shri','Yo','goyal.pr@husky.neu.edu','u2PDePOypvnurHD7LSHk8Q=='),('PNG2219e-2667-4fd4-a464-93dd4e5ea88e','varsha','yo','varsha@gmail.com','0PBygRJo1Ww/72NO3HlYJA=='),('PNG31865-f0e6-4a36-bae7-4b409e05d995','Shreya','Yo','shreya@gmaill.com','DdxQgEa3uTA1w2nBPg/PFg=='),('PNG7a5c3-2ff5-4f24-a269-d6347b06dbfc','Pranay','Goyal','pranayg25@gmail.com','NqsgF2hWx6h6Eb1c87tfDA=='),('PNG9a1d3-192a-43b2-a593-d3307a90d468','Pranay','Goyal','pranay@gmail.com','NqsgF2hWx6h6Eb1c87tfDA==');
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

-- Dump completed on 2017-04-26  1:09:31
