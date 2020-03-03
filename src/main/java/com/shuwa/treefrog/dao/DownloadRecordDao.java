package com.shuwa.treefrog.dao;

import com.shuwa.treefrog.model.DownloadRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 下载记录的 dao 层
 */
@Repository
public interface DownloadRecordDao {
    /**
     * 添加一条下载记录
     *
     * @param downloadRecord
     * @return
     */
    @Insert("insert into download(user_name,file_name,file_url,file_size,time) values(#{userName},#{fileName},#{fileUrl},#{fileSize},#{downloadTime})")
    boolean addDownloadRecord(DownloadRecord downloadRecord);

    /**
     * 删除一条下载记录
     *
     * @param id
     * @return
     */
    @Delete("delete from download where id = #{id}")
    boolean deleteDownloadRecord(@Param("id") Integer id);

    /**
     * 获取多条下载记录
     *
     * @return
     */
    @Select("select * from download")
    List<DownloadRecord> getDownloadRecordS();


}
