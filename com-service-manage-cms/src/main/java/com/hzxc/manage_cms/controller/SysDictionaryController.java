package com.hzxc.manage_cms.controller;

import com.hzxc.api.sys.SysDictionaryControllerApi;
import com.hzxc.framework.domain.system.SysDictionary;

import com.hzxc.manage_cms.service.impl.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.controller
 * @ClassName: SysDictionaryController
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/23 14:44
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    private SysService sysService;
    @Override
    @GetMapping("/dictionary/get/{dType}")
    public SysDictionary getDictionaryByDType(@PathVariable("dType") String dType) {
        return sysService.getDictionaryByDType(dType);
    }
}
