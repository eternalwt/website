package com.greengiant.website.global;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    // 捕捉 CustomRealm 抛出的异常
//    @ExceptionHandler(AccountException.class)
//    public String handleShiroException(Exception ex) {
//        //return resultMap.fail().message(ex.getMessage());
//        return ex.getMessage();
//    }

    @ExceptionHandler(NullPointerException.class)
    public ResultBean handleNullPointerException(NullPointerException ex) {
        //todo ResultUtils.fail();
        //todo 如何判断是哪个地方抛出的？怎么打日志？

        log.error(ex.getMessage(),ex);

        return ResultUtils.fail(StatusCodeEnum.EXCEPTION.getCode(), StatusCodeEnum.EXCEPTION.getMsg());
    }



}

