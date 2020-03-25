package com.shuwa.treefrog.service.impl;

import com.shuwa.treefrog.dao.UserDao;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = userDao.login(username, password);
        if (user == null) {
            return null;
        }
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * 根据Id获取用户对像
     *
     * @param id
     * @return
     */
    @Override
    public User get(Integer id) {
        return userDao.get(id);
    }

    @Override
    public boolean register(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean isAllowRegister(String username, String phone) {
        if (username.equals(userDao.isUserNameExist(username)) && phone.equals(userDao.isPhoneExist(phone))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPhoneExist(String phone) {
        return phone.equals(userDao.isPhoneExist(phone));
    }

    @Override
    public boolean isUserNameExist(String username) {
        return username.equals(userDao.isUserNameExist(username));
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    /**
     * 重置用户密码
     *
     * @param password 新密码
     * @param id       用户id
     * @return
     */
    @Override
    public boolean resetassword(String password, Integer id) {
        return userDao.updataPasswordById(password, id);
    }


    @Override
    public boolean verifyPassword(String password, Integer id) {
        if(password.equals(userDao.getpasswordById(id))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getEmail(String email) {
        return userDao.getEmail(email);
    }

    @Override
    public boolean resetPasswordByEmail(String password, String email) {
        return userDao.resetPasswordByEmail(password, email);
    }

    @Override
    public String getUserNameByEmail(String email) {
        return userDao.getUserNameByEmail(email);
    }
}
