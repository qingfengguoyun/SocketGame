/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.32-log : Database - pokegame
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pokegame` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `pokegame`;

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` bigint(255) NOT NULL,
  `content` text,
  `send_user_id` bigint(255) DEFAULT NULL,
  `receive_user_id` bigint(255) DEFAULT NULL,
  `is_broadcast` tinyint(1) DEFAULT '1',
  `replay_message_id` bigint(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_nativefile` */

DROP TABLE IF EXISTS `t_nativefile`;

CREATE TABLE `t_nativefile` (
  `id` bigint(255) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `uploader_id` bigint(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `file_suffix` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `md5` varchar(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `file_preview_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_profile_photo` */

DROP TABLE IF EXISTS `t_profile_photo`;

CREATE TABLE `t_profile_photo` (
  `id` bigint(255) NOT NULL,
  `user_id` bigint(255) DEFAULT NULL,
  `profile_photo_suffix` varchar(255) DEFAULT NULL,
  `profile_photo_url` varchar(255) DEFAULT NULL,
  `md5` varchar(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `profile_photo_org_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `un_read_message` */

DROP TABLE IF EXISTS `un_read_message`;

CREATE TABLE `un_read_message` (
  `id` bigint(20) NOT NULL,
  `send_user_id` bigint(20) DEFAULT NULL,
  `receive_user_id` bigint(20) DEFAULT NULL,
  `message_id` bigint(20) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `user_image_id` bigint(255) DEFAULT NULL,
  `user_default_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
