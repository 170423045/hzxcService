package com.hzxc.manage_cms.service.impl;

import com.hzxc.framework.domain.cms.CmsConfig;
import com.hzxc.manage_cms.mapper.CmsConfigMapper;
import com.hzxc.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.service.impl
 * @ClassName: CmsConfigServiceImpl
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 11:33
 * @Version: 1.0
 */
@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    @Autowired
    CmsConfigMapper cmsConfigMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public CmsConfig getModel(String id) {
        Optional<CmsConfig> cmsConfig = cmsConfigMapper.findById(id);
        if(cmsConfig.isPresent()){
            return cmsConfig.get();
        }
        return null;
    }
}
