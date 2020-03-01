package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.dao.TypeDao;
import com.shuwa.treefrog.entity.Type;
import com.shuwa.treefrog.exception.TypeNameException;
import com.shuwa.treefrog.service.ITypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 实现 ITypeService 接口
 */
@Service
public class TypeService implements ITypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public boolean addType(String typeName, Date createTime) throws TypeNameException {
        if ("".equals(typeName) || typeName == null) {
            throw new TypeNameException("添加的类型名为空！");
        }
        if (typeName.equals(typeDao.returnTypeName(typeName))) {
            throw new TypeNameException("添加的类型名重复");
        }
        return typeDao.addType(typeName,createTime);
    }

    @Override
    public boolean deleteType(Integer id) {
        return typeDao.deleteType(id);
    }

    @Override
    public boolean updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public List<Type> findAll() {
        return typeDao.findAll();
    }

    @Override
    public PageInfo<Type> typePageQuery(Integer currentPage, Integer limit) {
        PageHelper.startPage(currentPage,limit);
        return new PageInfo<>(typeDao.typePageQuery());
    }

    @Override
    public String returnTypeName(String typeName) {
        return typeDao.returnTypeName(typeName);
    }
}
