package com.shuwa.treefrog.service.impl;

import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = userDao.login(username,password);
        if(user == null){
            return null;
        }
        if(user.getUsername().equals(username) && user.getPassword().equals(password)){
            return user;
        }
        else {
            return null;
        }
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * 根据Id获取用户对像
     * @param id
     * @return
     */
    @Override
    public User get(Integer id) {
        return userDao.get(id);
    }
}
