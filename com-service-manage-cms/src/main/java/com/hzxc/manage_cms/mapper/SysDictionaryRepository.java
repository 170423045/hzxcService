package com.hzxc.manage_cms.mapper;

import com.hzxc.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.dao
 * @ClassName: SysDictionaryRepository
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/23 14:26
 * @Version: 1.0
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findByDType(String dType);
}
