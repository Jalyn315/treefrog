package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 用户信息更新
     *
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 跟前用户ID获取用户对象
     *
     * @param id
     * @return
     */
    User get(Integer id);

    /**
     * 用户注册
     *
     * @return
     */
    boolean register(User user);

    /**
     * 将传递过来的用户名、手机号码检查是否在数据库中有相同值
     *
     * @param username
     * @param phone
     * @return
     */
    boolean isAllowRegister(String username, String phone);

    /**
     * 检查手机号是否已注册
     *
     * @param phone 手机号
     * @return
     */
    boolean isPhoneExist(String phone);


    /**
     * 检查用户名是否存在
     *
     * @param username
     * @return
     */
    boolean isUserNameExist(String username);

    /**
     * 用户删除-管理员功能
     *
     * @param id
     * @return
     */
    boolean deleteUser(int id);

    /**
     * 得到全部用户
     *
     * @return
     */
    List<User> getAllUser();

    /**
     * 根据用户id更改密码
     *
     * @param id       用户id
     * @param password 新密码
     * @return
     */
    boolean resetassword(String password, Integer id);

    /**
     * 根据邮箱查询邮箱是否存在于数据库
     *
     * @param email
     * @return
     */
    String getEmail(String email);

    /**
     * 根据邮箱重置密码
     *
     * @param password
     * @param email
     * @return
     */
    boolean resetPasswordByEmail(String password, String email);

    /**
     * 根据邮箱得到 用户名
     *
     * @param email
     * @return
     */
    String getUserNameByEmail(String email);

    /**
     * 上传用户头像
     *
     * @param password
     * @param id
     * @return
     */
    boolean verifyPassword(String password, Integer id);

    String getVia(Integer id);

//    /**
//     * 根据 type 名称获取所有的文件
//     * @param typeName
//     * @return
//     */
//    PageInfo<File> getFileByTypeName(String typeName);
//

    /**
     * 分页查询文件
     *
     * @param typeName
     * @param currentPage
     * @param limit
     * @return
     */
    PageInfo<File> getFileByTypeNamePageQuery(String typeName, Integer currentPage, Integer limit);


    boolean uploadUserVia(MultipartFile file, Integer id) throws Exception;
}