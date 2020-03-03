package com.shuwa.treefrog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuwa.treefrog.dao.DownloadRecordDao;
import com.shuwa.treefrog.model.DownloadRecord;
import com.shuwa.treefrog.service.IDownloadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对 IDownloadRecordService 接口的实现
 */
@Service
public class DownloadRecordService implements IDownloadRecordService {

    @Autowired
    private DownloadRecordDao downloadRecordDao;

    @Override
    public boolean addDownloadRecord(DownloadRecord downloadRecord) {
        return downloadRecordDao.addDownloadRecord(downloadRecord);
    }

    @Override
    public boolean deleteDownloadRecord(Integer id) {
        return downloadRecordDao.deleteDownloadRecord(id);
    }

    @Override
    public PageInfo<DownloadRecord> getDownloadRecordS(Integer currentPage, Integer limit) {
        PageHelper.startPage(currentPage, limit);
        return new PageInfo<>(downloadRecordDao.getDownloadRecordS());
    }
}
