package com.greengiant.website.global;

import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    private final ResultMap resultMap;

//    @Autowired
//    public ExceptionController(ResultMap resultMap) {
//        this.resultMap = resultMap;
//    }

    // 捕捉 CustomRealm 抛出的异常
    @ExceptionHandler(AccountException.class)
    public String handleShiroException(Exception ex) {
        //return resultMap.fail().message(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex) {
        //todo ResultUtils.fail();
        return ex.getMessage();
    }
}

