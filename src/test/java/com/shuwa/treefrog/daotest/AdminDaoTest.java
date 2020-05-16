package com.shuwa.treefrog.daotest;

import com.shuwa.treefrog.dao.AdminDao;
import com.shuwa.treefrog.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试 AdminDao 类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminDaoTest {
    @Autowired
    AdminDao adminDao;

    @Autowired
    Admin admin;

    private static String userName = "吕长旭";
    private static String password = "1315";
    private static Integer id = 1;

    @Test
    public void addAdminTest() {
        System.out.println("添加管理员是否成功：" + adminDao.addAdmin(userName, password));
    }

    @Test
    public void deleteAdminTest() {
        System.out.println("删除管理员是否成功：" + adminDao.deleteAdmin(id));
    }

    @Test
    public void updateAdminTest() {
        admin.setIdAdmin(id);
        admin.setUserNameAdmin(userName);
        admin.setPasswordAdmin(password);
        System.out.println("更新管理员是否成功：" + adminDao.updateAdmin(admin));
    }

    @Test
    public void getAdminTest() {
        System.out.println("获取到的管理员信息：" + adminDao.getAdminById(id));
    }

    @Test
    public void listAdminTest() {
        System.out.println("获取到的所有管理员信息：" + adminDao.listAdmin());
    }

    @Test
    public void getAdminByUserNameAndPasswordTest() {
        System.out.println("获取到的管理员信息：" + adminDao.getAdminByUserNameAndPassword(userName, password));
    }

    @Test
    public void getUserName() {
        System.out.println("获取到的用户名：" + adminDao.getUserName(userName));
    }


}
