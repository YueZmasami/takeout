package com.yueZhai.reggie.common;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
/**
 * 异常处理方法
 */
@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
    log.error(ex.getMessage());
    // 告诉用户具体错误是什么
    if(ex.getMessage().contains("Duplicate entry")){
        String[] split=ex.getMessage().split(" ");
        String s = split[2]+"已存在";
        return R.error(s);

    }
    return R.error("未知错误");
}

    @ExceptionHandler(CustomerException.class)
    public R<String> customerExceptionHandler(CustomerException e){
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }









}
