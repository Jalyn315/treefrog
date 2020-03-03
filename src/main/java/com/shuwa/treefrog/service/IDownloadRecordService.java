package com.shuwa.treefrog.service;

import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.model.DownloadRecord;

/**
 * 下载记录的接口定义
 */
public interface IDownloadRecordService {
    /**
     * 添加一次下载记录
     *
     * @param downloadRecord
     * @return
     */
    boolean addDownloadRecord(DownloadRecord downloadRecord);

    /**
     * 删除一次下载记录
     *
     * @param id
     * @return
     */
    boolean deleteDownloadRecord(Integer id);

    /**
     * 查询多条记录
     *
     * @return
     */
    PageInfo<DownloadRecord> getDownloadRecordS(Integer currentPage, Integer limit);
}
