package com.hzxc.framework.domain.course.response;

import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.framework.domain.course.response
 * @ClassName: CoursePublishResult
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/27 18:46
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class CoursePublishResult extends ResponseResult {
    private String previewUrl;

    public CoursePublishResult(ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
