package com.hzxc.manage_cms.controller;

import com.hzxc.framework.web.BaseController;
import com.hzxc.manage_cms.service.CmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.controller
 * @ClassName: CmsPagePreviewController
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 9:26
 * @Version: 1.0
 */
@Controller
public class CmsPagePreviewController extends BaseController {
    @Autowired
    private CmsService cmsService;
    @RequestMapping(value = "/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String id) throws IOException {
        String html = cmsService.getHtml(id);

        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-type","text/html;charset=utf-8");
        outputStream.write(html.getBytes("utf-8"));
    }

}
