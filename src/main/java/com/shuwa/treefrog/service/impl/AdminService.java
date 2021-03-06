package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.dao.AdminDao;
import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.Admin;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.exception.LoginException;
import com.shuwa.treefrog.exception.RegisterException;
import com.shuwa.treefrog.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现 IAdminService 接口
 */
@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Admin login(String userName, String password) throws LoginException {
        Admin admin = adminDao.getAdminByUserNameAndPassword(userName, password);
        if (admin != null) {
            return admin;
        } else {
            throw new LoginException("用户名或密码错误！");
        }
    }

    @Override
    public boolean register(String userName, String password) throws RegisterException {
        if (userName != null && !userName.equals(getUserName(userName))) {
            adminDao.addAdmin(userName, password);
            return true;
        }
        throw new RegisterException("用户名重复！");
    }


    @Override
    public String getUserName(String userName) {
        return adminDao.getUserName(userName);
    }

    @Override
    public PageInfo<User> getAllUserByPageingQuery(Integer currentPage, Integer limit) {
        PageHelper.startPage(currentPage, limit);
        List<User> userList = userDao.getAllUser();
        return new PageInfo<>(userList);
    }
}
