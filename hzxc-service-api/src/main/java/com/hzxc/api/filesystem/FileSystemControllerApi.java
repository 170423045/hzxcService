package com.hzxc.api.filesystem;

import com.hzxc.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.api.filesystem
 * @ClassName: FileSystemControllerApi
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/25 19:38
 * @Version: 1.0
 */
@Api(value = "文件系统服务",description = "对文件上传下载的管理",tags = {"文件系统"})
public interface FileSystemControllerApi {
    @ApiOperation("上传文件")
    UploadFileResult upload(MultipartFile multipartFile,
                            String fileTag,
                            String businesskey,
                            String metadata);
}
