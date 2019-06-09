package com.greengiant.website.global;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResultBean handleNullPointerException(NullPointerException ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.EXCEPTION.getCode(), StatusCodeEnum.EXCEPTION.getMsg());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultBean handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.EXCEPTION.getCode(), StatusCodeEnum.EXCEPTION.getMsg());
    }

}

