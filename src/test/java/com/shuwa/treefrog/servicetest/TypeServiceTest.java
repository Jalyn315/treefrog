package com.shuwa.treefrog.servicetest;

import com.shuwa.treefrog.entity.Type;
import com.shuwa.treefrog.exception.TypeNameException;
import com.shuwa.treefrog.service.impl.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeServiceTest {
    @Autowired
    private TypeService typeService;

    private static Integer id = 2;
    private static String typeName = "文件";
    private static Date createTime;

    @Test
    public void addTypeTest() {
        try {
            createTime = new Date();
            System.out.println(typeService.addType(typeName, createTime));
        } catch (TypeNameException e) {
            e.getMessage();
        }
    }

    @Test
    public void deleteTypeTest() {
        System.out.println(typeService.deleteType(id));
    }

    @Test
    public void updateTypeTest() {
        Type type = new Type();
        createTime = new Date();
        type.setId(id);
        type.setTypeName(typeName);
        type.setCreateTime(createTime);
        System.out.println(typeService.updateType(type));
    }

    @Test
    public void findAllTest() {
        System.out.println(typeService.findAll());
    }

    @Test
    public void typePageQueryTest() {
        Integer currentPage = 1;
        Integer limit = 2;
        System.out.println(typeService.typePageQuery(currentPage, limit));
    }
}
