package com.hzxc.manage_cms.controller;

import com.hzxc.api.cms.CmsConfigControllerApi;
import com.hzxc.framework.domain.cms.CmsConfig;
import com.hzxc.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.controller
 * @ClassName: CmsConfigController
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/10 11:34
 * @Version: 1.0
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    CmsConfigService cmsConfigService;
    /**
     *
     * 根据ID获取数据模型
     *http://localhost:31001/cms/config/getModel/5d245ee488b63a3954029a58
     * */
    @Override
    @GetMapping("/getModel/{id}")
    public CmsConfig getModel(@PathVariable("id") String id) {
        return cmsConfigService.getModel(id);
    }
}
