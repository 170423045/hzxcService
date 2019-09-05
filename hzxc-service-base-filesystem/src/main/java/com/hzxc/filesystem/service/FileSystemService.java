package com.hzxc.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.hzxc.filesystem.mapper.FileSystemRepository;
import com.hzxc.framework.domain.filesystem.FileSystem;
import com.hzxc.framework.domain.filesystem.response.FileSystemCode;
import com.hzxc.framework.domain.filesystem.response.UploadFileResult;
import com.hzxc.framework.exception.ExceptionCast;
import com.hzxc.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;

import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;


@Service
public class FileSystemService {


    @Value("${hzxc.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${hzxc.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${hzxc.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${hzxc.fastdfs.charset}")
    String charset;

    @Autowired
    private FileSystemRepository fileSystemRepository;

    public UploadFileResult upload(MultipartFile multipartFile, String fileTag, String businesskey, String metadata) {
        //1.将文件上传到fastdfs
        if (multipartFile == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //1.1初始化上传环境

        //1.2获取StorageClient
        StorageClient1 storageClient1 = this.getStorageClient1();
        //1.3进行上传
        String fileId = this.uploadFile(multipartFile, storageClient1);
        //2将文件信息存入到mongodb
        if (fileId == null || StringUtils.isEmpty(fileId)) ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        //2.1创建文件信息包装类
        FileSystem fileSystem = new FileSystem();
        //2.2封装文件信息
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystem.setFiletag(fileTag);
        if (metadata != null || StringUtils.isEmpty(metadata)) {
            HashMap hashMap = JSON.parseObject(metadata, HashMap.class);
            fileSystem.setMetadata(hashMap);
        }
        //2.3将对象保存进数据库
        fileSystemRepository.save(fileSystem);

        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    //将文件上传到fastdfs
    private String uploadFile(MultipartFile multipartFile, StorageClient1 storageClient1) {
        //获取文件类型
        String filename = multipartFile.getOriginalFilename();
        String type = filename.substring(filename.lastIndexOf(".") + 1);
        //获取文件字节
        try {
            byte[] bytes = multipartFile.getBytes();
            //进行上传
           return storageClient1.upload_file1(bytes, type, null);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        return null;

    }


    private StorageClient1 getStorageClient1() {

        this.FastdfsInit();
        //创建tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        //建立连接获取trackerServer

        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过trackerClient获取storageServer
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            return new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        return null;
        //返回StorageClient1
    }

    private void FastdfsInit() {
        try {
            String path = new ClassPathResource("config/fastdfs-client.properties").getFile().getAbsolutePath();
            ClientGlobal.init(path);
           /* ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);*/
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }

    }
}
