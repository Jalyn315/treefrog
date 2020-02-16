/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.12 : Database - treefrog
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
  `download_times` int(11) NOT NULL DEFAULT '0' COMMENT '下载次数',
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `file` */

insert  into `file`(`id`,`name`,`suffix`,`local_url`,`visit_url`,`size`,`create_time`,`description`,`check_times`,`download_times`,`tag`,`user_id`,`category_id`,`is_downloadable`,`is_uploadable`,`is_visible`,`is_deletable`,`is_updatable`,`last_modify_time`) values (1,'db8b6d4f-2a72-4211-8c8d-78e018c70519_文件上传.xmind','xmind','G:\\treefrogFile\\7\\0\\','',22478,'2019-12-25 14:40:24','上传流程图',0,0,'文件',2,0,0,0,0,0,0,'2019-12-25 14:40:24'),(2,'a7043d06-b943-48d3-9cf4-f4bde79bb885_DFS.doc','doc','G:\\treefrogFile\\0\\0\\','',12800,'2019-12-25 14:59:27','发',0,0,'凤飞飞',2,0,0,0,0,0,0,'2019-12-25 14:59:27'),(3,'894ee522-cd08-4f3f-b93e-52c03cb38ba7_DFS.doc','doc','G:\\treefrogFile\\12\\0\\','',12800,'2019-12-25 16:02:06','5555555',0,0,'图片',2,0,0,0,0,0,0,'2019-12-25 16:02:06'),(4,'43959965-b7f0-4787-ae2e-46e591afb423_新建文本文档.txt','txt','G:\\treefrogFile\\4\\0\\','',571,'2019-12-26 21:13:29','55',0,0,'文件',2,0,0,0,0,0,0,'2019-12-26 21:13:29'),(5,'5c762c51-29bf-4969-bb7c-1b4ca2c2e67f_DFS.doc','doc','G:\\treefrogFile\\7\\0\\','',12800,'2019-12-27 00:07:18','是',0,0,'图片',2,0,0,0,0,0,0,'2019-12-27 00:07:18'),(6,'ffdbf85a-d920-4b27-ab71-11a6ba17fd52_usingthymeleaf.pdf','pdf','G:\\treefrogFile\\4\\0\\','',677697,'2019-12-27 01:21:05','thymeleaf使用文档',0,0,'文件',2,0,0,0,0,0,0,'2019-12-27 01:21:05'),(7,'cbc9b637-ef39-49e4-ae86-015ccafeb18c_jdk api 1.8_google.chw','chw','G:\\treefrogFile\\7\\0\\','',381209,'2019-12-27 01:25:15','jaca',0,0,'文档',2,0,0,0,0,0,0,'2019-12-27 01:25:15'),(8,'1ade05e8-f249-4141-b32c-be59410c01d1_G-DRAGON、金钟万 - Today.kgm','kgm','G:\\treefrogFile\\12\\0\\','',8885769,'2019-12-27 02:35:02','我的最爱',0,0,'音乐',1,0,0,0,0,0,0,'2019-12-27 02:35:02'),(9,'d0b9e7cc-f5f1-45b3-b343-4bc38a465db2_pl.mp3','mp3','G:\\treefrogFile\\8\\0\\','',3873507,'2019-12-27 10:54:07','一首歌',0,0,'音乐',2,0,0,0,0,0,0,'2019-12-27 10:54:07');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
