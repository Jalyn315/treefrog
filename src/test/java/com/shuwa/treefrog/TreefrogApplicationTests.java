package com.shuwa.treefrog;

import com.shuwa.treefrog.constant.ConfigConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TreefrogApplicationTests {




    @Test
    void contextLoads() {

        System.out.println(ConfigConstant.MAX_SIZE);
    }

}
