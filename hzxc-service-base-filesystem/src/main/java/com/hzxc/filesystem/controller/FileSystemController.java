package com.hzxc.filesystem.controller;

import com.hzxc.api.filesystem.FileSystemControllerApi;
import com.hzxc.filesystem.service.FileSystemService;
import com.hzxc.framework.domain.filesystem.response.UploadFileResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("filesystem")
public class FileSystemController implements FileSystemControllerApi {

    @Autowired
    FileSystemService fileSystemService;

    @Override
    @PostMapping("upload")
    public UploadFileResult upload(MultipartFile multipartFile, String fileTag, String businesskey, String metadata) {
        return fileSystemService.upload(multipartFile,fileTag,businesskey,metadata);
    }
}
