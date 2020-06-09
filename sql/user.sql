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

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(18) NOT NULL COMMENT '用户名',
  `password` varchar(18) NOT NULL COMMENT '密码',
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `birth` varbinary(20) DEFAULT NULL COMMENT '出生日期',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) NOT NULL COMMENT '手机',
  `permission` int(1) DEFAULT NULL COMMENT '权限',
  `description` varbinary(300) DEFAULT NULL COMMENT '描述',
  `via` varchar(300) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`realName`,`sex`,`birth`,`email`,`phone`,`permission`,`description`,`via`) values (1,'admin','123456789','韦成建','男','1999-03-15','18377860349@163.com','18177860349',NULL,'快乐先生',NULL),(2,'shuwa','Jian1999','韦成建','on','1999-03-15','1921776332@qq.com','18377860349',1,'一个码农日常生活1111','/userVia/git常用命令.jpg'),(4,'wcj1999','1234567',NULL,NULL,NULL,NULL,'18177860349',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
