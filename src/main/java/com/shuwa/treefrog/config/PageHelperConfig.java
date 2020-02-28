package com.shuwa.treefrog.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {

    @Bean   //spring容器自动配置
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        /**
         * 该属性默认未false
         * 设置未true时，会将rowbounds第一个参数offset当成pageNum使用
         * 和starPage中的pageNum效果一样
         */
        properties.setProperty("offsetAsPageNum","true");
        /**
         * 该参数默认未false
         * 设置未true时，使用RowBounds分页会进行count查询
         *
         */
        properties.setProperty("rowBoundsWithCount","true");
        /**
         * 分页查询合理化
         * 启用合理化时，如果pageNum<1会查询第一页，如果pageNum > pages会查询最后一页
         * 禁用合理化，如果pageNum <1 或者 pageNum > pages 会返回空数据值
         */
        properties.setProperty("reasonable","true");
        /**
         *
         */
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
