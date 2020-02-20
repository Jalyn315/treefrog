package com.shuwa.treefrog.daotest;

import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试 UserDao 各个功能是否正确
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    public static String username = "admin";
    public static String password = "1314";
    public static String phone = "15244812873";
    public static String email = "174412205@QQ.com";
    public static Integer id = 1;
    public static String realName = "为城建";
    public static String sex = "男";
    public static String birth = "1999-03-15";
    public static String description = "一个程序员";
    public static int permission = 1;


    /**
     * 测试登录功能
     */
    @Test
    public void loginTest() {
        User user = userDao.login(username, password);
        System.out.println("user:" + user);
    }

    /**
     * 测试验证用户名是否存在功能
     *
     * @return
     */
    @Test
    public void isUserNameExistTest() {
        String userName = userDao.isUserNameExist(username);
        System.out.println("用户名：" + userName);
    }

    /**
     * 测试验证用户手机是否存在
     *
     * @return
     */
    @Test
    public void isPhoneExistTest() {
        String isPhone = userDao.isPhoneExist(phone);
        System.out.println("手机号：" + isPhone);
    }

    /**
     * 测试邮箱是否存在
     *
     * @return
     */
    @Test
    public void isEmailExistTest() {
        String isEmail = userDao.isEmailExist(email);
        System.out.println("邮箱：" + isEmail);
    }

    /**
     * 根据 id 修改 password
     *
     * @return
     */
    @Test
    public void updataPasswordByIdTest() {
        boolean isSuccess = userDao.updataPasswordById(password, 1);
        System.out.println("修改密码是否成功：" + isSuccess);
    }

    /**
     * 更新用户信息
     */
    @Test
    public void updateTest() {
        User user = new User();
        user.setId(id);
        user.setRealName(realName);
        user.setSex(sex);
        user.setBirth(birth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDescription(description);

        boolean isSuccess = userDao.update(user);
        System.out.println("更新用户是否成功：" + isSuccess);
    }

    /**
     * 测试获取所有用户
     */
    @Test
    public void getAllUserTest() {
        List<User> allUser = userDao.getAllUser();
        System.out.println(allUser);
    }

    /**
     * 测试根据 id 删除 用户
     */
    public void deleteUserTest() {
        boolean isSuccess = userDao.deleteUser(id);
        System.out.println("删除用户是否成功：" + isSuccess);
    }

    /**
     * 保存用户
     */
    @Test
    public void saveUserTest() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealName(realName);
        user.setSex(sex);
        user.setBirth(birth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPermission(permission);
        user.setDescription(description);
        System.out.println("插入的用户信息是否成功：" + userDao.saveUser(user));
    }

    /**
     * 通过 id 获取 user
     */
    @Test
    public void getTest() {
        System.out.println("通过 id 获取的用户为：" + userDao.get(id));
    }

    /**
     * 通过 id 获取 用户姓名
     */
    @Test
    public void getByUserNameTest() {
        System.out.println("通过 id 获取的用户姓名为：" + userDao.getByUserName(id));
    }

    /**
     * 通过 id 获取用户的 邮箱
     */
    @Test
    public void getByEmailTest() {
        System.out.println("通过 id 获取的邮箱账户为：" + userDao.getByEmail(id));
    }

}
