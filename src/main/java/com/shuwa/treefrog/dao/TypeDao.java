package com.shuwa.treefrog.dao;

import com.shuwa.treefrog.entity.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 类型 Dao 层
 */
@Repository
public interface TypeDao {

    /**
     * 增加一个类型
     * @param typeName
     * @param createTime
     * @return
     */
    @Insert("insert into type(type_name,create_time) values(#{type_name},#{create_time})")
    boolean addType(@Param("type_name") String typeName, @Param("create_time") Date createTime);

    /**
     * 根据 id 来删除一个类型
     * @param id
     * @return
     */
    @Delete("delete from type where id=#{id}")
    boolean deleteType(@Param("id") Integer id);


    /**
     * 更新 一个类型，比如重命名指定 id 的类型
     * @param type
     * @return
     */
    @Update("update type set type_name=#{typeName}, create_time=#{createTime} where id=#{id}")
    boolean updateType(Type type);

    /**
     * 查询所有的 类型
     * @return
     */
    @Select("select * from type")
    List<Type> findAll();

    /**
     * 查询所有的 类型
     * @return
     */
    @Select("select * from type")
    List<Type> typePageQuery();

    /**
     * 查询是否有重名的 类型 名
     *
     * @param typeName
     * @return
     */
    @Select("select type_name from type where type_name = #{type_name}")
    String returnTypeName(@Param("type_name") String typeName);

    /**
     * 根据 type_name 返回 type_name 对应的 id
     *
     * @param typeName
     * @return
     */
    @Select("select id from type where type_name = #{typeName}")
    Integer returnIdByName(String typeName);
}
