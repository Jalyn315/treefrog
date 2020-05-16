package com.shuwa.treefrog.servicetest;

import com.shuwa.treefrog.exception.LoginException;
import com.shuwa.treefrog.exception.RegisterException;
import com.shuwa.treefrog.service.impl.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    private String userName = "吕长旭";
    private String password = "1315";

    @Test
    public void loginTest() {
        try {
            System.out.println("管理员登录：" + adminService.login(userName, password));
        } catch (LoginException e) {
            System.err.println(e);
        }
    }

    @Test
    public void registerTest() {
        try {
            System.out.println("管理员注册：" + adminService.register(userName, password));
        } catch (RegisterException e) {
            System.err.println(e);
        }
    }

}
