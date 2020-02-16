package com.shuwa.treefrog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UploadedDao {


    /**
     * 根据用户id获取用户名。
     * @param id
     * @return
     */
    @Select("select username from user where id = #{id}")
    String getUserName(int id);




}
