package com.hzxc.framework.exception;

import com.hzxc.framework.model.response.ResultCode;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.framework.exception
 * @ClassName: ExceptionCast
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/9 16:49
 * @Version: 1.0
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
