package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.entity.File;
import com.shuwa.treefrog.model.UploadedRecord;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IFileService {

    /**
     * 获取文件的上传信息
     *
     * @param uploadedRecord
     */
    boolean uploaded(MultipartFile file, UploadedRecord uploadedRecord);

    /**
     * 获取文件资源信息
     *
     * @return
     */
    List<File> getFileList();

    /**
     * 文件下载。
     *
     * @param response 相应对象
     * @param id       文件id
     * @return
     */
    boolean downloadFile(HttpServletResponse response, long id, String username);

    /**
     * 跟前用户id获取用户的所有上传文件
     *
     * @param id
     * @return
     */
    List<File> getMyFileList(Integer id);

    /**
     * 根据id查询文件
     *
     * @param id
     * @return
     */

    File findOne(Integer id);

    /**
     * 跟前文件名称查询文件
     *
     * @param name
     * @return
     */

    File findByName(String name);

    /**
     * 根据上传用户查询文件
     *
     * @param userName
     * @return
     */
    File findByUserName(String userName);

    /**
     * 分页查询
     *
     * @param page  当前页
     * @param limit 每页显示多少条数据
     * @return
     */
    PageInfo<File> listFiles(Integer page, Integer limit);

    /**
     * 后台文件管理分页查询
     *
     * @param currentPage
     * @param limit
     * @return
     */
    PageInfo<File> filePageQuery(Integer currentPage, Integer limit);

    /**
     * 根据id数组删除多个文件
     *
     * @param id
     * @return
     */
    boolean removeFile(Long[] id);

    /**
     * m模糊查询
     *
     * @param keyword
     * @return
     */
    List<File> fuzzyQuery(String keyword);

    /**
     * 查询全部
     *
     * @return
     */
    List<File> findAll();

    /**
     * 添加收藏
     *
     * @param fileId
     * @param userId
     * @return
     */
    boolean addCollect(Integer fileId, Integer userId);

    /**
     * 获取全部收藏
     *
     * @param userId
     * @return
     */
    List<File> findAllToCollect(Integer userId);

    /**
     * 移出个人收藏
     *
     * @param fileId
     * @return
     */
    boolean removeCollect(Integer fileId, Integer userId);

    /**
     * 是否已被收藏
     *
     * @param fileId
     * @param userId
     * @return
     */
    boolean isCollected(Integer fileId, Integer userId);
}
