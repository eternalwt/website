package com.greengiant.website.pojo;

/**
 * @author fei.gao
 */
public enum StatusCodeEnum {
    SUCCESS("success", 1),
    FAIL("fail", 10000);

    // 成员变量
    private int code;
    private String msg;

    // 构造方法
    private StatusCodeEnum(String msg, int code) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
