package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.Type;
import com.shuwa.treefrog.exception.TypeNameException;

import java.util.Date;
import java.util.List;

/**
 * 类型 接口
 */
public interface ITypeService {
    /**
     * 增加 类型
     * @param typeName
     * @param createTime
     * @return
     */
    boolean addType(String typeName, Date createTime) throws TypeNameException;

    /**
     * 删除类型
     * @param id
     * @return
     */
    boolean deleteType( Integer id);

    /**
     * 更新类型
     * @param type
     * @return
     */
    boolean updateType(Type type);

    /**
     * 查询所有类型
     * @return
     */
    List<Type> findAll();

    /**
     * 分页查询类型
     * @return
     */
    PageInfo<Type> typePageQuery(Integer currentPage, Integer limit);

    /**
     * 查询是否有同名的类型
     * @param typeName
     * @return
     */
    String returnTypeName(String typeName);
}
