package com.hzxc.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.test.fastdfs
 * @ClassName: TestFastDFS
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/25 11:42
 * @Version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {

    //上传测试
    @Test
    public void testUpload() throws IOException, MyException {
        //读取配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        //创建trackerclient
        TrackerClient trackerClient=new TrackerClient();

        //连接trackerserver
        TrackerServer trackerServer = trackerClient.getConnection();

        //获得storageserver
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);

        //创建storageclient
        StorageClient1 storageClient=new StorageClient1(trackerServer,storageServer);

        //执行上传获取文件id
        String fileid = storageClient.upload_file1("D:/1.txt", "txt", null);
        System.out.println(fileid);
    }

    //下载测试
    @Test
    public void testDownload() throws IOException, MyException {
        //读取配置文件
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        //创建tracker客户端
        TrackerClient trackerClient=new TrackerClient();
        //获取tracker服务端
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage服务端
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        //创建storane客户端
        StorageClient1 storageClient1=new StorageClient1(trackerServer,storageServer);
        //通过fileid进行下载操作
        String fileId=" ";
        byte[] file1 = storageClient1.download_file1(fileId);
        //输出流将文件写入本地磁盘
        File file = new File("e:/2.txt");


        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(file1);

    }
}
