package com.shuwa.treefrog.dao;

import com.shuwa.treefrog.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员 Dao 层，基本的增删改查
 */
@Repository
public interface AdminDao {
    /**
     * 添加 管理员
     * @param userName
     * @param password
     * @return
     */
    @Insert("insert into admin(user_name_admin,password_admin) values(#{userName},#{password})")
    boolean addAdmin(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据管理员 id 来删除管理员
     * @param id
     * @return
     */
    @Delete("delete from admin where id_admin = #{id}")
    boolean deleteAdmin(@Param("id") Integer id);

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @Update("update admin set user_name_admin=#{userNameAdmin}, password_admin=#{passwordAdmin} where id_admin=#{idAdmin}")
    boolean updateAdmin(Admin admin);


    /**
     *
     * @param id
     * @return
     */
    @Select("select * from admin where id_admin=#{id}")
    Admin getAdminById(@Param("id") Integer id);

    /**
     * 列出所有的管理员
     * @return
     */
    @Select("select * from admin")
    List<Admin> listAdmin();

    /**
     * 根据 用户名 和 密码 来查询管理员
     * @param userName
     * @param password
     * @return
     */
    @Select("select * from admin where user_name_admin=#{userName} and password_admin=#{password}")
    Admin getAdminByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 返回 userName
     * @param userName
     * @return
     */
    @Select("select user_name_admin from admin where user_name_admin=#{userName}")
    String getUserName(@Param("userName") String userName);

}
