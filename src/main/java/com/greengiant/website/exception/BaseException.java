package com.greengiant.website.exception;

public class BaseException extends Exception {
    // todo 公司里面的代码加了 serialVersionUID 和 isPropertiesKey，有什么用？
    private int errorCode;

    public BaseException(int errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
