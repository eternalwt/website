package com.greengiant.website.pojo;

/**
 * @author fei.gao
 */
public enum StatusCodeEnum {
    //成功失败
    SUCCESS("success", 1),
    FAIL("fail", -1),

    USER_EXISTS("用户已存在", 10001),
    ;

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
