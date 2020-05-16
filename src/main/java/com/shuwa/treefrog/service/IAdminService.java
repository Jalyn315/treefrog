package com.shuwa.treefrog.service;


import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.Admin;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.exception.LoginException;
import com.shuwa.treefrog.exception.RegisterException;

/**
 * 管理员接口
 */
public interface IAdminService {
    /**
     * 管理员登录
     *
     * @param userName
     * @param password
     * @return
     */
    Admin login(String userName, String password) throws LoginException;

    /**
     * 管理员注册
     *
     * @param userName
     * @param password
     * @return
     */
    boolean register(String userName, String password) throws RegisterException;

    /**
     * 判断管理员是否重名
     *
     * @param userName
     * @return
     */
    String getUserName(String userName);

    /**
     * 分页查询得到全部用户
     *
     * @param currentPage 当前页数
     * @param limit       每页显示多少条数据
     * @return
     */
    PageInfo<User> getAllUserByPageingQuery(Integer currentPage, Integer limit);

}
