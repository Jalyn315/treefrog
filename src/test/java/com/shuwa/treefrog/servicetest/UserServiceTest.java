package com.shuwa.treefrog.servicetest;

import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 对 UserService 类进行测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    public static String username = "summerlv";
    public static String password = "1314";
    public static String phone = "13340975823";
    public static String email = "174412205@QQ.com";
    public static Integer id = 1;
    public static String realName = "吕长旭";
    public static String sex = "男";
    public static String birth = "1999-03-15";
    public static String description = "一个程序员";
    public static int permission = 1;

    /**
     * 测试 login 方法
     */
    @Test
    public void loginTest() {
        System.out.println("用户登录：" + userService.login(username, password));
    }

    /**
     * 测试 update 方法
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

        boolean isSuccess = userService.update(user);
        System.out.println("更新用户是否成功：" + isSuccess);
    }

    /**
     * 测试 get 方法
     */
    @Test
    public void getTest() {
        System.out.println("根据用户 id 获取用户信息：" + userService.get(id));
    }

    /**
     * 测试 register 方法
     */
    @Test
    public void registerTest() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        boolean isSuccess = userService.register(user);
        System.out.println("是否注册新用户成功：" + isSuccess);
    }

    /**
     * 测试 isAllowRegister 方法
     */
    @Test
    public void isAllowRegisterTest() {
        boolean isSuccess = userService.isAllowRegister(username, phone);
        System.out.println("是否允许用户注册：" + isSuccess);
    }

    /**
     * 测试 deleteUser 方法
     */
    @Test
    public void deleteUserTest() {
        boolean isSuccess = userService.deleteUser(id);
        System.out.println("删除用户是否成功：" + isSuccess);
    }

    /**
     * 测试 getAllUser 方法
     */
    @Test
    public void getAllUserTest() {
        List<User> allUser = userService.getAllUser();
        System.out.println("所有用户信息：" + allUser);
    }


}
