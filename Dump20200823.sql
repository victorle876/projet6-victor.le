-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: Sites
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `commentaire`
--
/* */
DROP TABLE IF EXISTS `commentaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `commentaire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `zone_commentaire` varchar(255) DEFAULT NULL,
  `site_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7poewyx0vupiexwj3o3v5r746` (`site_id`),
  KEY `FK6932n1nb2xh52evf8xlmjajxr` (`utilisateur_id`),
  CONSTRAINT `FK6932n1nb2xh52evf8xlmjajxr` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`),
  CONSTRAINT `FK7poewyx0vupiexwj3o3v5r746` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentaire`
--

LOCK TABLES `commentaire` WRITE;
/*!40000 ALTER TABLE `commentaire` DISABLE KEYS */;
INSERT INTO `commentaire` VALUES (1,'2020-08-12 12:45:24','2020-08-12 12:45:52','Demande info sur voie xxx?',1,1),(2,NULL,'2020-08-12 13:52:12','test Commentaire - v1a',2,2),(4,'2020-08-17 19:04:16','2020-08-17 19:04:16','Pouvez vous des infos supplementaires sur les sites, svp?',3,3),(5,'2020-08-17 19:04:39','2020-08-17 19:04:39','Demande de reservation',1,3);
/*!40000 ALTER TABLE `commentaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (5),(5),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `persistent_logins` (
  `username` varchar(100) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `topo_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr6bnvop3b5pi14dydgrog353c` (`topo_id`),
  KEY `FKs1a8of5joke6m74bmeqtrtkac` (`utilisateur_id`),
  CONSTRAINT `FKr6bnvop3b5pi14dydgrog353c` FOREIGN KEY (`topo_id`) REFERENCES `topologie` (`id`),
  CONSTRAINT `FKs1a8of5joke6m74bmeqtrtkac` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,NULL,'Libre','2020-08-12 14:10:47',1,2),(2,NULL,'En cours','2020-08-17 19:05:41',2,3);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ACTUATOR'),(1,'ADMIN'),(3,'UTILISATEUR'),(4,'VISITEUR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secteur`
--

DROP TABLE IF EXISTS `secteur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `secteur` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `nom_secteur` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `site_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc5crh1gx05vjasl4jusc1mgh2` (`site_id`),
  KEY `FKcgovew7b10bo45pox2ypg7v28` (`utilisateur_id`),
  CONSTRAINT `FKc5crh1gx05vjasl4jusc1mgh2` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`),
  CONSTRAINT `FKcgovew7b10bo45pox2ypg7v28` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secteur`
--

LOCK TABLES `secteur` WRITE;
/*!40000 ALTER TABLE `secteur` DISABLE KEYS */;
INSERT INTO `secteur` VALUES (1,'2020-08-12 11:44:39','Langenfeld','2020-08-12 11:44:39',1,1),(2,NULL,'Schibegütsch - Nord','2020-08-12 13:44:57',2,2),(3,'2020-08-12 13:49:36','Schibegütsch - Sud','2020-08-12 13:49:36',2,2),(4,'2020-08-17 19:00:22','Preddvor','2020-08-17 19:00:22',3,3);
/*!40000 ALTER TABLE `secteur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `isofficiel` bit(1) NOT NULL,
  `nom_site` varchar(255) DEFAULT NULL,
  `nombre_secteur` varchar(255) DEFAULT NULL,
  `pays` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `site_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkivq07k01ft0xg75b5mye8ufy` (`site_id`),
  KEY `FKh43jprr0a65puxwkwrbj1knh7` (`utilisateur_id`),
  CONSTRAINT `FKh43jprr0a65puxwkwrbj1knh7` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`),
  CONSTRAINT `FKkivq07k01ft0xg75b5mye8ufy` FOREIGN KEY (`site_id`) REFERENCES `topologie` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

LOCK TABLES `site` WRITE;
/*!40000 ALTER TABLE `site` DISABLE KEYS */;
INSERT INTO `site` VALUES (1,'2020-08-12 10:48:38',_binary '','Oberried','3','Autriche','2020-08-12 11:31:05',1,1),(2,'2020-08-12 13:24:51',_binary '','Schibegütsch - Est','2','Suisse','2020-08-12 13:34:55',2,2),(3,'2020-08-17 18:57:43',_binary '\0','Preddvor','2','Slovenie','2020-08-17 19:05:14',3,3);
/*!40000 ALTER TABLE `site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topologie`
--

DROP TABLE IF EXISTS `topologie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `topologie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `created_at` date NOT NULL,
  `isavailable` bit(1) DEFAULT NULL,
  `ispublic` bit(1) DEFAULT NULL,
  `nom_topologie` varchar(255) DEFAULT NULL,
  `updated_at` date NOT NULL,
  `reservation_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtg9us1oqtasrdyh6w93dw2h9u` (`reservation_id`),
  KEY `FK9ot1rq5kn1jkr2k55c24j4ist` (`utilisateur_id`),
  CONSTRAINT `FK9ot1rq5kn1jkr2k55c24j4ist` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`),
  CONSTRAINT `FKtg9us1oqtasrdyh6w93dw2h9u` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topologie`
--

LOCK TABLES `topologie` WRITE;
/*!40000 ALTER TABLE `topologie` DISABLE KEYS */;
INSERT INTO `topologie` VALUES (1,'Ce topo contient tous les infos du Tyrol1','2020-08-12',_binary '',_binary '','Les balades du Tyrol1','2020-08-12',NULL,1),(2,'Ce topo contient les sites d\'escalade du canton de St Gall','2020-08-12',_binary '\0',_binary '','Schibegütsch','2020-08-17',NULL,2),(3,'Ce top contient les secteurs de Slovenie','2020-08-17',NULL,_binary '\0','Les balades de Solvenie','2020-08-17',NULL,3);
/*!40000 ALTER TABLE `topologie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  KEY `FKacbokoiwo2ikh8fg67gs42rbx` (`user_id`),
  CONSTRAINT `FKacbokoiwo2ikh8fg67gs42rbx` FOREIGN KEY (`user_id`) REFERENCES `utilisateurs` (`id`),
  CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utilisateurs` (
  `id` bigint(20) NOT NULL,
  `age` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateurs`
--

LOCK TABLES `utilisateurs` WRITE;
/*!40000 ALTER TABLE `utilisateurs` DISABLE KEYS */;
INSERT INTO `utilisateurs` VALUES (1,23,'2020-08-12 10:26:01','donovan.mitchell@yahoo.us','$2a$10$W9w2ud5xGqYrXgJfbwmskOhlvEKuBMdA5ppk7dszDvO081DSkM3im','Donovan','2020-08-12 10:26:01','Mitchell'),(2,45,'2020-08-12 13:20:20','mitch.richmond@yahoo.us','$2a$10$qmuqV8F17at8Sk5vhGjQ4ORQZhVbABm0sFtD.NdYfZR/5xSxz68m6','Mitch','2020-08-12 13:20:20','Richmond'),(3,45,'2020-08-17 18:30:30','jacques.macronis@live.fr','$2a$10$ySxlFZafFkG5PL7h6CwQ1e2eM5Um2.0dyK8j/EhvgrmqfD8iKCAF6','Jacques','2020-08-17 18:30:30','Macronis'),(4,34,'2020-08-17 18:31:01','jens.dutcher@yahoo.de','$2a$10$2mqetT91YjuDt..uiB/deeVvIL3qDrFKVJ45.xT8IJ2gx0P0CPGAi','Jens','2020-08-17 18:31:01','Dutcher');
/*!40000 ALTER TABLE `utilisateurs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voie`
--

DROP TABLE IF EXISTS `voie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `distance` int(11) NOT NULL,
  `cotation` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `hauteur` int(11) NOT NULL,
  `nom_voie` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `secteur_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqlo061c6fgkuosv9chywpgn8t` (`secteur_id`),
  KEY `FKg4qlxbx3tt3sp46sh6bbm5b7l` (`utilisateur_id`),
  CONSTRAINT `FKg4qlxbx3tt3sp46sh6bbm5b7l` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateurs` (`id`),
  CONSTRAINT `FKqlo061c6fgkuosv9chywpgn8t` FOREIGN KEY (`secteur_id`) REFERENCES `secteur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voie`
--

LOCK TABLES `voie` WRITE;
/*!40000 ALTER TABLE `voie` DISABLE KEYS */;
INSERT INTO `voie` VALUES (1,1200,'8a','2020-08-12 12:07:49',456,'Langenfeld','2020-08-12 12:32:09',1,1),(2,1378,'7d',NULL,567,'Baltschiederklause - voie 1','2020-08-12 13:38:49',2,2),(3,1276,'8b','2020-08-12 13:56:26',1284,'voie x1','2020-08-12 13:56:26',2,2),(4,1200,'7a','2020-08-17 19:00:48',890,'Preddvor - partie Nord','2020-08-17 19:00:48',4,3);
/*!40000 ALTER TABLE `voie` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-23 10:32:32
