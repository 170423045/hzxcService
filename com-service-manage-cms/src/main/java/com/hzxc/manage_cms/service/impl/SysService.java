package com.hzxc.manage_cms.service.impl;

import com.hzxc.framework.domain.system.SysDictionary;
import com.hzxc.manage_cms.mapper.SysDictionaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.service.impl
 * @ClassName: SysService
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/23 14:47
 * @Version: 1.0
 */
@Service
public class SysService {
    @Autowired
    SysDictionaryRepository sysDictionaryRepository;
    public SysDictionary getDictionaryByDType(String dType) {
        return sysDictionaryRepository.findByDType(dType);
    }
}
