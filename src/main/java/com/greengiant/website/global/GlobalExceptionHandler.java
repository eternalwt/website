package com.greengiant.website.global;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.enums.StatusCodeEnum;
import com.greengiant.website.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
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

    // todo login方法会抛出AuthenticationException，再确认一下
    // todo 结合Objects.requiresNotNull以及@Transactional的rollback一起确认一下
    // AccountException
    // AuthenticationException

    @ExceptionHandler(UnknownAccountException.class)
    public ResultBean handleUnknownAccountException(UnknownAccountException ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.PASSWORD_INCORRECT.getCode(), ex.getMessage());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultBean handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.PASSWORD_INCORRECT.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ExcessiveAttemptsException.class)
    public ResultBean handleExcessiveAttemptsException(ExcessiveAttemptsException ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.TOO_MANY_INCORRECT_PASSWORD.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultBean handleException(Exception ex) {
        log.error(ex.getMessage(),ex);
        return ResultUtils.fail(StatusCodeEnum.EXCEPTION.getCode(), StatusCodeEnum.EXCEPTION.getMsg());
    }

}

