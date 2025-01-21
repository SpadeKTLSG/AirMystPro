package org.spc.base.common.handle;


import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.FrontClient;
import org.spc.base.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局自定义异常处理
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @Autowired
    FrontClient frontClient;


    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e) {

        if (e instanceof BaseException) { // 自定义异常
            log.error("自定义异常 -> ", e);
            //发送异常消息到前端
            frontClient.sendException(e.getMessage());
        } else {
            log.error(e.toString(), e);
        }

    }

}
