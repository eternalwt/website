package com.greengiant.website.enums;

public enum ResourceTypeEnum {

    MENU(1, "MENU"),
    BUTTON(2, "BUTTON");

    private int code;

    private String name;

    ResourceTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        for (ResourceTypeEnum type : ResourceTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
