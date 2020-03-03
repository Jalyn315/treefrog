package com.shuwa.treefrog.servicetest;

import com.shuwa.treefrog.model.DownloadRecord;
import com.shuwa.treefrog.service.impl.DownloadRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadRecordServiceTest {
    @Autowired
    private DownloadRecordService downloadRecordService;

    private static Integer id = 1;
    private static String userName = "admin";
    private static String fileName = "fileName";
    private static String fileUrl = "fileUrl";
    private static Integer fileSize = 20;
    private static Date time;

    @Test
    public void addDownloadRecord() {
        DownloadRecord downloadRecord = new DownloadRecord();
        downloadRecord.setUserName(userName);
        downloadRecord.setFileName(fileName);
        downloadRecord.setFileUrl(fileUrl);
        downloadRecord.setFileSize(fileSize);
        downloadRecord.setDownloadTime(new Date());
        System.out.println(downloadRecordService.addDownloadRecord(downloadRecord));
    }

    @Test
    public void deleteDownloadRecord() {
        System.out.println(downloadRecordService.deleteDownloadRecord(id));
    }

    @Test
    public void getDownloadRecordS() {
        Integer currentPage = 0;
        Integer limit = 2;
        System.out.println(downloadRecordService.getDownloadRecordS(currentPage, limit));
    }
}
