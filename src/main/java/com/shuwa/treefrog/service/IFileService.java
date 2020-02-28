package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.UploadedRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IFileService {

    /**
     * 获取文件的上传信息
     * @param uploadedRecord
     */
     boolean  uploaded(MultipartFile file, UploadedRecord uploadedRecord);

    /**
     * 获取文件资源信息
     * @return
     */
     List<File> getFileList();

    /**
     * 文件下载。
     * @param response 相应对象
     * @param id 文件id
     * @return
     */
     boolean downloadFile(HttpServletResponse response,long id);

    /**
     * 跟前用户id获取用户的所有上传文件
     * @param id
     * @return
     */
     List<File> getMyFileList(Integer id);

    /**
     * 根据id查询文件
     * @param id
     * @return
     */

     File findOne(Integer id);

    /**
     * 跟前文件名称查询文件
     * @param name
     * @return
     */

     File findByName(String name);

    /**
     * 根据上传用户查询文件
     * @param userName
     * @return
     */
     File findByUserName(String userName);

    /**
     * 分页查询
     * @param page 当前页
     * @param limit 每页显示多少条数据
     * @return
     */
     PageInfo<File> listFiles(Integer page, Integer limit);
}
