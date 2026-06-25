package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 * @RestControllerAdvice 声明这个类是全局异常处理器,其 @ExceptionHandler 方法对所有 Controller 生效
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler // 表明这个方法是处理异常的方法
    public  Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        String msg = "操作失败";
        if (message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            msg = split[2] + MessageConstant.ACCOUNT_ALREADY_EXISTS;
        }
        return Result.error(msg);

    }

}
