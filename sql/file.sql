/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28 : Database - treefrog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`treefrog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `treefrog`;

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(250) DEFAULT '' COMMENT '文件名',
  `suffix` varchar(16) NOT NULL DEFAULT '' COMMENT '文件后缀',
  `local_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '本地路径',
  `visit_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '客户端访问路径',
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件大小',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(1024) DEFAULT '' COMMENT '文件描述',
  `check_times` int(11) NOT NULL DEFAULT '0' COMMENT '查看次数',
  `download_count` int(11) NOT NULL DEFAULT '0' COMMENT '下载次数',
  `tag` varchar(45) DEFAULT '' COMMENT '文件标签',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `category_id` int(11) NOT NULL COMMENT '分类名称',
  `is_downloadable` int(11) NOT NULL DEFAULT '1' COMMENT '文件是否可下载',
  `is_uploadable` int(11) NOT NULL DEFAULT '1' COMMENT '文件夹是否可上传',
  `is_visible` int(11) NOT NULL DEFAULT '1' COMMENT '文件是否可见',
  `is_deletable` int(11) NOT NULL DEFAULT '1' COMMENT '文件是否可删除',
  `is_updatable` int(11) NOT NULL DEFAULT '1' COMMENT '文件是否可更新',
  `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `file` */

insert  into `file`(`id`,`name`,`suffix`,`local_url`,`visit_url`,`size`,`create_time`,`description`,`check_times`,`download_count`,`tag`,`user_id`,`category_id`,`is_downloadable`,`is_uploadable`,`is_visible`,`is_deletable`,`is_updatable`,`last_modify_time`) values (8,'1ade05e8-f249-4141-b32c-be59410c01d1_G-DRAGON、金钟万 - Today.kgm','kgm','G:\\treefrogFile\\12\\0\\','',8885769,'2019-12-27 02:35:02','我的最爱',0,27,'音频',1,0,1,0,1,1,1,'2020-06-05 14:56:51'),(10,'8cf8e304-cd99-4c47-9c5e-f277f7aa9ee5_t01196a8e68182b4173.jpg','jpg','G:\\treefrogFile\\10\\0\\','',17955,'2019-12-28 13:48:30','',0,2,'音频',2,0,1,0,1,1,1,'2020-06-08 22:36:42'),(14,'85c109ac-07f4-4878-b9f3-a9c7b714521a_IT行业（网上复制）.docx','docx','G:\\treefrogFile\\10\\0\\','',77845,'2020-03-27 14:59:09','iT介绍',0,1,'音频',0,0,1,0,0,1,0,'2020-06-06 14:32:26'),(16,'793b0a61-a872-490f-bb10-c7c4aa8410e9_thesapientdream - Promise.mp3','mp3','G:\\treefrogFile\\6\\0\\','',6126280,'2020-03-27 15:15:54','ssss',0,1,'音频',2,0,1,0,1,0,1,'2020-04-02 23:39:11'),(18,'5e972794-a954-4ec2-a3bb-a8105a013283_thesapientdream - Promise.mp3','mp3','G:\\treefrogFile\\13\\0\\','',6126280,'2020-04-02 15:23:09','音乐',0,0,'音频',2,0,0,0,0,0,0,'2020-04-03 00:03:42'),(19,'6981a778-d52d-49ae-a343-c60b78d44587_thesapientdream - Promise.mp3','mp3','G:\\treefrogFile\\9\\0\\','',6126280,'2020-04-02 15:23:09','音乐',0,0,'音频',2,0,1,0,1,1,1,'2020-04-02 15:23:09'),(22,'aa74b57c-159b-4ccc-9fe0-1919f8ebd367_PS2018 x64bit.zip','zip','G:\\treefrogFile\\8\\0\\','',1678283837,'2020-04-02 16:55:53','PS安装包',0,1,'文档',2,0,1,0,1,1,1,'2020-04-02 16:57:24'),(23,'eaca5818-fbdc-4060-a03c-979b7226c79d_mysql-5.5.62-winx64.msi','msi','G:\\treefrogFile\\9\\0\\','',39456768,'2020-04-02 17:12:08','mysql安装包',0,0,'文档',2,0,0,0,0,0,0,'2020-04-03 00:03:42'),(24,'ac6fc3d4-56d6-4f91-bcfe-8240c46d8368_Idea 2019.zip','zip','G:\\treefrogFile\\14\\0\\','',603767387,'2020-04-02 17:19:01','IDEA',0,1,'文档',2,0,1,0,1,1,1,'2020-06-08 22:22:12'),(28,'6d085b25-2a4b-44ef-9f33-fa5908c33488_Idea 2019.zip','zip','G:\\treefrogFile\\8\\0\\','',603767387,'2020-04-03 23:27:38','IDEA安装包',0,0,'文档',2,0,1,0,1,1,1,'2020-04-03 23:27:38'),(29,'8c15a2cf-301a-4bf0-ab53-2bf1367bedc8_注册码.txt','txt','G:\\treefrogFile\\12\\0\\','',3675,'2020-04-03 23:30:46','ssss',0,0,'音频',2,0,1,0,1,1,1,'2020-04-03 23:30:46'),(30,'6dc1d6eb-a0a5-44b9-b6ec-0e1ef555ada1_录像24.exe','exe','G:\\treefrogFile\\7\\0\\','',13385548,'2020-04-30 08:14:23','我叫吕长旭',0,1,'视频',0,0,0,0,0,0,0,'2020-06-05 15:08:45'),(31,'8cc11afc-39b9-45c8-b23c-24c722c913b5_录像24.exe','exe','G:\\treefrogFile\\15\\0\\','',13385548,'2020-04-30 08:14:45','我叫吕长旭',0,0,'视频',0,0,0,0,0,0,0,'2020-04-30 08:14:45'),(32,'75b36d82-6a74-4a26-930f-889b689a98cb_bmw6.jpg','jpg','G:\\treefrogFile\\11\\0\\','',428652,'2020-05-16 16:28:33','宝马',0,0,'图片',2,0,1,0,1,1,1,'2020-05-16 16:28:33'),(33,'2ee2e08b-0dc6-4d58-bab5-e72c465bc90b_git常用命令.jpg','jpg','G:\\treefrogFile\\6\\0\\','',291840,'2020-06-08 22:21:05','这是git的常用命令',0,2,'图片',2,0,1,0,1,1,1,'2020-06-08 22:37:23');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
