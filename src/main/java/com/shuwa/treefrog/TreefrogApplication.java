package com.shuwa.treefrog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shuwa.treefrog.dao")
public class TreefrogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreefrogApplication.class, args);

    }

}
