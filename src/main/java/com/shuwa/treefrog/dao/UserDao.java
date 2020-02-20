package com.shuwa.treefrog.dao;

import com.shuwa.treefrog.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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
    User login(@RequestParam("username") String name,@RequestParam("password") String password);

    boolean updataPasswordById(int id);
    boolean insertUser(User user);

    /**
     * 更新数据库中的用户信息
     * @param user
     * @return
     */
    @Update("update user set realName=#{realName},sex=#{sex},birth=#{birth},email=#{email},phone=#{phone},description=#{description} where id = #{id}")
    boolean update( User user);

    /**
     * 保存用户
     * @param user
     * @return
     */
    @Insert("insert into user(username,password,realName,sex,birth,email,phone,permission,description) values" +
            "(#{username},#{password},#{realName},#{sex},#{birth},#{email},#{phone},#{permission},#{description})")
    User saveUser(User user);

    /**
     * 根据用户id获取数据库中的用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User get(Integer id);

    /**
     * 根据Id获取用户名
     * @param id
     * @return
     */
    @Select("select username from user where id = #{id}")
    String  getByUserName(Integer id);

    /**
     * 根据Id获取邮箱信息
     * @param id
     * @return
     */
    @Select("select email from user where id = #{id}")
    String getByEmail(Integer id);

}

