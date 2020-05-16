package com.shuwa.treefrog.util;


import com.shuwa.treefrog.entity.File;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;

/**
 * 对文件上传进行处理
 */

public class WebUtils {

    @Autowired
    private File file;

    public static File doFileLoad(HttpServletRequest request) throws FileUploadBase.FileSizeLimitExceededException {


        return null;
    }


}
