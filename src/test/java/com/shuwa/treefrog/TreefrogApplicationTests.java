package com.shuwa.treefrog;

import com.shuwa.treefrog.constant.ConfigConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;

@SpringBootTest
class TreefrogApplicationTests {


    @Test
    void contextLoads() throws Exception {

        String path = ResourceUtils.getURL("classpath:static/userVia").getPath();
        System.out.println(path);
    }

}
