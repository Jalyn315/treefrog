package com.shuwa.treefrog.constant;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.Property;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class ConfigConstant {

    //本地路径
    public static String UPLOAD_PATH ;
    //文件大小
    public static long MAX_SIZE;


    @Value("${file.maxSize}")
    public  void setMaxSize(long maxSize) {
        ConfigConstant.MAX_SIZE = maxSize;
    }

    @Value("${file.uploadPath}")
    public void setUploadPath(String uploadPath) {
        ConfigConstant.UPLOAD_PATH = uploadPath;
    }


}
