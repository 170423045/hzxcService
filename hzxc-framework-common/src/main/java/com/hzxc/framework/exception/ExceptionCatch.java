package com.hzxc.framework.exception;

import com.hzxc.framework.model.response.CommonCode;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.framework.exception
 * @ClassName: ExceptionCatch
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/9 16:54
 * @Version: 1.0
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);
    /**
     * google提供的一个map集合
     * 特点：一旦生成则无法更改，线程安全
     *作用（本项目）:指定非自定义异常的一些错误提示代码
     * */
    protected static ImmutableMap<Class<? extends Throwable>, ResultCode> immutableMap;
    /**
     * ImmutableMap：用够构造ImmutableMap
     * */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode>
            builder=ImmutableMap.builder();
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        LOGGER.error("catch CustomException");
        return new ResponseResult(customException.getResultCode());
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        LOGGER.error("catch Exception",exception.getMessage());
        //如果容器没有被创建过则构建容器
        if(immutableMap==null){
            immutableMap=builder.build();
        }
        //如果容器中包含此异常则返还指定Code，如果无则返回服务器异常
        if(immutableMap.containsKey(exception.getClass())){
            return new ResponseResult(immutableMap.get(exception.getClass()));
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
