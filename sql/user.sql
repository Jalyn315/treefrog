/*
<<<<<<< HEAD
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28 : Database - treefrog
=======
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.12 : Database - treefrog
>>>>>>> origin/master
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

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
<<<<<<< HEAD
  `username` varchar(18) NOT NULL COMMENT '用户名',
  `password` varchar(18) NOT NULL COMMENT '密码',
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `birth` varbinary(20) DEFAULT NULL COMMENT '出生日期',
=======
  `user_name` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(18) NOT NULL COMMENT '密码',
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出生日期',
>>>>>>> origin/master
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) NOT NULL COMMENT '手机',
  `permission` int(1) DEFAULT NULL COMMENT '权限',
  `description` varbinary(300) DEFAULT NULL COMMENT '描述',
<<<<<<< HEAD
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`realName`,`sex`,`birth`,`email`,`phone`,`permission`,`description`) values (1,'admin','123456','韦成建','男','1999-03-15','18377860349@163.com','18177860349',NULL,'快乐先生'),(2,'shuwa','123456','shuwa','男','1999-03-15','1921776332@QQ.com','18377860349',1,'一个码农的日常');
=======
  `register_time` datetime DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`password`,`real_name`,`sex`,`birth`,`email`,`phone`,`permission`,`description`,`register_time`) values (2,'shuwa','123456',NULL,'男','1999-03-15 14:00:00','1921776332@QQ.com','18377860349',1,'一个码农,hhhhhhhhhhhhhhhh','2019-12-19 22:29:51'),(3,'summer','WWWwww1314',NULL,'女','2019-12-30 02:32:03','lv.summer@qq.com','15244812874',1,'','2019-12-29 22:30:25'),(4,'summerlv','WWWwww1314','吕长旭','男','2019-12-29 06:32:34','lv@qq.com','13340975825',1,'一个程序员fffffffffffffffffffff','2019-12-05 22:30:31');
>>>>>>> origin/master

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
