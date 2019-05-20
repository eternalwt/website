package com.greengiant.website.utils;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;

public class ResultUtils {

    public static ResultBean success(Object data) {
        ResultBean result = new ResultBean();
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setMsg(StatusCodeEnum.SUCCESS.getMsg());
        result.setData(data);

        return result;
    }

    public static ResultBean fail(int code, String msg) {
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
