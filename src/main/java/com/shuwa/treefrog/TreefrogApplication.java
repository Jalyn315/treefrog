package com.shuwa.treefrog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shuwa.treefrog.dao")
public class TreefrogApplication {

    public static void main(String[] args) {
//        SpringApplication newRun = new SpringApplication(TreefrogApplication.class);
//        newRun.setBannerMode(Banner.Mode.OFF);
//        newRun.run(args);
        SpringApplication.run(TreefrogApplication.class, args);
    }

}
