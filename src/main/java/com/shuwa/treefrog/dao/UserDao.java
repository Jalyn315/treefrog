package com.shuwa.treefrog.dao;

import com.shuwa.treefrog.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserDao {

    /**
     * 用户登入
     *
     * @param name
     * @param password
     * @return
     */
    @Select("select * from user where (username=#{name}) and password=#{password}")
    User login(@RequestParam("username") String name, @RequestParam("password") String password);


    /**
     * 用户注册-添加用户
     *
     * @return
     */
    @Insert("insert into user(user_name,password,phone) values(#{userName},#{password},#{phone})")
    boolean addUser(User user);

    boolean updataPasswordById(int id);

    boolean insertUser(User user);

    /**
     * 注册验证-查看数据库中是否有相同用户名
     *
     * @param username
     * @return 存在-返回用户名 不存在-null
     */
    @Select("select username from user where username=#{username}")
    String isUserNameExist(@Param("username") String username);

    /**
     * 注册验证-查看数据库中是否有相同手机号
     *
     * @param phone
     * @return 存在-true 不存在-false
     */
    @Select("select phone from user where phone=#{phone}")
    String isPhoneExist(@Param("phone") String phone);

    /**
     * 修改密码验证-查看数据库中是否有相同邮箱
     *
     * @param email
     * @return 存在-true 不存在-false
     */
    @Select("select email from user where email=#{email}")
    String isEmailExist(@Param("email") String email);

    /**
     * 用户功能-用户重置密码
     *
     * @param password
     * @param id
     * @return
     */
    @Update("update user set password=#{password} where id = #{id}")
    boolean updataPasswordById(@Param("password") String password, @Param("id") int id);

    /**
     * 更新数据库中的用户信息
     *
     * @param user
     * @return
     */
    @Update("update user set realName=#{realName},sex=#{sex},birth=#{birth},email=#{email},phone=#{phone},description=#{description} where id = #{id}")
    boolean update(User user);

    /**
     * 管理员功能-查看全部用户信息
     *
     * @return
     */
    @Select("select * from user")
    List<User> getAllUser();

    /**
     * 删除用户
     * 管理员功能
     *
     * @param id
     * @return
     */
    @Delete("delete from user where id = #{id}")
    boolean deleteUser(@Param("id") int id);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Insert("insert into user(username,password,realName,sex,birth,email,phone,permission,description) values" +
            "(#{username},#{password},#{realName},#{sex},#{birth},#{email},#{phone},#{permission},#{description})")
    boolean saveUser(User user);

    /**
     * 根据用户id获取数据库中的用户
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User get(Integer id);

    /**
     * 根据Id获取用户名
     *
     * @param id
     * @return
     */
    @Select("select username from user where id = #{id}")
    String getByUserName(Integer id);

    /**
     * 根据Id获取邮箱信息
     *
     * @param id
     * @return
     */
    @Select("select email from user where id = #{id}")
    String getByEmail(Integer id);

}

