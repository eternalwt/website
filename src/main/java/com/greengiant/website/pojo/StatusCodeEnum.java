package com.greengiant.website.pojo;

/**
 * @author fei.gao
 */
public enum StatusCodeEnum {
    //成功失败
    SUCCESS("success", 1),
    FAIL("fail", -1),
    EXCEPTION("System Exception", -2),

    USER_EXISTS("用户已存在", 10001),
    PASSWORD_INCORRECT("密码错误", 10002),
    TOO_MANY_INCORRECT_PASSWORD(null, 10003),
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
