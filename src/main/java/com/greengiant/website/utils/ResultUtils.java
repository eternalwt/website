package com.greengiant.website.utils;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;

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

    public static ResultBean fail(int code, String msg) {
        ResultBean result = new ResultBean(code, msg, null);
        return result;
    }
}
