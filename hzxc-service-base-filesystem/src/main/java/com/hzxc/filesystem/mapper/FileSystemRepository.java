package com.hzxc.filesystem.mapper;

import com.hzxc.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: hzxcService
 * @Package: com.xuecheng.filesystem.mapper
 * @ClassName: FileSystemRepostory
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/25 19:51
 * @Version: 1.0
 */
@Repository
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
