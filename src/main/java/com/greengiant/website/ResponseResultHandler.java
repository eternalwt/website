package com.greengiant.website;

import com.greengiant.website.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
//  todo 这里ControllerAdvice没有配合其他惯用注解，要理解
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> convertType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 判断请求，是否有包装标记
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);

        return (responseResultAnn != null);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // todo 异常处理
//        if (body instanceof ErrorResult) {
////            ErrorResult errorResult = (ErrorResult) body;
////            return ResultBean.failure(errorResult.getCode(), errorResult.getMessage(), errorResult.getErrors());
//
//        }

        log.info("重写返回值……");
        return ResultUtils.success(body);
    }

}
