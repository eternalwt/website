package com.greengiant.infrastructure.utils;

import com.greengiant.website.enums.StatusCodeEnum;
import com.greengiant.website.pojo.ResultBean;

public class ResultUtils {

    private ResultUtils() {}

    public static ResultBean success() {
        return success(null);
    }

    public static ResultBean success(Object data) {
        ResultBean result = new ResultBean(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMsg(), data);
        return result;
    }

    public static ResultBean fail() {
        ResultBean result = new ResultBean(StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getMsg(), null);
        return result;
    }

    public static ResultBean paramError() {
        ResultBean result = new ResultBean(StatusCodeEnum.PARAM_ERROR.getCode(), StatusCodeEnum.PARAM_ERROR.getMsg(), null);
        return result;
    }

    public static ResultBean fail(int code, String msg) {
        ResultBean result = new ResultBean(code, msg, null);
        return result;
    }
}
