package com.shuwa.treefrog.service;

import com.shuwa.treefrog.entity.User;

public interface IUserService {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 用户嘻嘻更新
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 跟前用户ID获取用户对象
     * @param id
     * @return
     */
    User get(Integer id);

}
