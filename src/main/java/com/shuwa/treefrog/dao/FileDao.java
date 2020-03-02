package com.shuwa.treefrog.dao;


import com.shuwa.treefrog.entity.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao {


    /**
     * 通过编号获取文件
     *
     * @param id
     * @return
     */
    @Select("select * form file where id = #{id}")
    File getById(long id);

    /**
     * 通过文件id获取文件路径
     * @param id 文件id
     * @return
     */
    @Select("select local_url form file where id = #{id}")
    String getLocalUrlById(long id);

    /**
     * 通过id删除文件
     *
     * @param id
     * @return
     */
    @Delete("delete from file where id = #{id}")
    boolean removeById(long id);

    /**
     * 通过本地文件获取文件ID
     *
     * @param localUrl 本地路径
     * @return
     */
    @Select("select id form file where local_url=#{localUrl}")
    long  getIdByVisitUrl(String localUrl);

    /**
     * 通过访问路径获取本地文件路径
     * @param visitUrl
     * @return
     */
    @Select("select local_url from file where visit_url=#{visitUrl}")
    long getIdByLocalUrl(String visitUrl);

    /**
     * t通过访问路径删除文
     * @param visitUrl 访问路径
     * @return
     */
    @Delete("delete from file where visit_url=#{visitUrl}")
    boolean removeByVisitUrl(String visitUrl);

    /**
     * 通过本地路径删除文件
     * @param localUrl 本地路径
     * @return
     */
    @Delete("delete from file where local_url=#{localUrl}")
    boolean removeByLocalUrl(String localUrl);

    /**
     * 检查本地路径
     * @param localUrl
     * @return
     */
    @Select("select count(*) from file where local_url=#{localUrl}")
    int checkLocalUrl(String localUrl);

    /***
     * 检查访问路径
     * @param visitUrl 访问路径
     * @return
     */
    @Select("select count(*) from file where visit_url=#{visitUrl}")
    int checkVisitUrl(String visitUrl);

    /**
     * 添加一个文件
     * @param file
     * @return
     */
    @Insert("insert into file(name,suffix,local_url,visit_url,size,description,tag,user_id,category_id," +
            "is_downloadable,is_uploadable,is_deletable,is_updatable,is_visible) values(#{name},#{suffix}," +
            "#{localUrl},#{visitUrl},#{size},#{description},#{tag},#{userId},#{categoryId},#{isDownloadable}," +
            "#{isUploadable},#{isDeletable},#{isUpdatable},#{isVisible})")
    boolean insertFile(File file);

    /**
     * 删除一个文件
     * @param id 文件编号
     */
    @Delete("delete form file where id=#{id}")
    void deleteFileById(int id);

    /**
     * 根据用户ID删除文件
     * @param userId
     */
    @Delete("delete from file where user_id=#{userId}")
    void deleteFileByUserID(int userId);

    /**
     * 删除文件
     * @param categoryId 分类编号
     */
    @Delete("delete form file where category_id=#{categoryId}")
    void deleteFileByCategoryId(int categoryId);

    /**
     * 更新文件基本信息
     *
     * @param file 文件
     *
     * @return 是否更新成功
     */
    @Update("update file set name=#{name},suffix=#{suffix},local_url=#{localUrl},visit_url=#{visitUrl}," +
            "description=#{description},tag=#{tag},category_id=#{categoryId},last_modify_time=current_timestamp " +
            "where" + " id=#{id}")
    boolean updateFileInfo(File file);


    /**
     * 更新文件名
     *
     * @param id 编号
     * @param name 文件名
     * @param suffix 后缀名
     */
    @Update("update file set name=#{name},suffix=#{suffix},last_modify_time=current_timestamp where id=#{id}")
    void updateFileNameById(@Param("id") int id, @Param("name") String name, @Param("suffix") String suffix);


    /**
     * 更新文件修改时间
     *
     * @param id 编号
     */
    @Update("update file set last_modify_time=current_timestamp where id=#{id}")
    void updateLastModifyTimeById(int id);

    /**
     * 更新文件本地路径
     *
     * @param id 编号
     * @param localUrl 本地路径
     */
    @Update("update file set local_url=#{localUrl} where id=#{id}")
    void updateLocalUrlById(@Param("id") int id, @Param("localUrl") String localUrl);


    /**
     * 更新文件访问路径
     *
     * @param id 编号
     * @param visitUrl 访问链接
     */
    @Update("update file set visit_url=#{visitUrl} where id=#{id}")
    void updateVisitUrlById(@Param("id") int id, @Param("visitUrl") String visitUrl);

    /**
     * 更新文件描述
     *
     * @param id 文件编号
     * @param description 描述
     */
    @Update("update file set description=#{description} where id=#{id}")
    void updateDescriptionById(@Param("id") int id, @Param("description") String description);

    /**
     * 更新文件查看次数
     *
     * @param id 编号
     */
    @Update("update file set check_times=check_times+1 where id=#{id}")
    void updateCheckTimesById(int id);

    /**
     * 更新文件下载次数
     *
     * @param id 编号
     */
    @Update("update file set download_times=download_times+1 where id=#{id}")
    void updateDownloadTimesById(long id);

    /**
     * 更新文件标签
     *
     * @param id 编号
     * @param tag 标签
     */
    @Update("update file set tag=#{tag} where id=#{id}")
    void updateTagById(@Param("id") int id, @Param("tag") String tag);

    /**
     * 更新文件分类
     *
     * @param id 编号
     * @param categoryId 分类编号
     */
    @Update("update file set category_id=#{categoryId} where id=#{id}")
    void updateCategoryById(@Param("id") int id, @Param("categoryId") int categoryId);

    /**
     * 获取文件信息
     *
     * @param visitUrl 访问链接
     *
     * @return {@link File}
     */
    @Select("select * from file where visit_url=#{visitUrl}")
    File getFileByVisitUrl(String visitUrl);

    /**
     * 查询系统中的所有文件
     * @return
     */
    @Select("select * from file")
    List<File> findAll();

    /**
     * 根据文件id获取文件
     * @param id
     * @return
     */
    @Select("select * from file where id = #{id}")
    File getFile(long id);

    /**
     * 根据用户Id获取用户上传的文件
     *
     * @param userId
     * @return
     */
    @Select("select * from file where user_id = #{userId}")
    List<File> getMyFile(Integer userId);

    @Select("select * from file where ")
    File findByname(String name);

    /**
     * 查询所有的 类型
     *
     * @return
     */
    @Select("select * from file")
    List<File> filePageQuery();
}
