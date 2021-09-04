package com.greengiant.website.enums;

public enum EntityTypeEnum {

    ROLE(1, "ROLE");

    private int code;

    private String name;

    EntityTypeEnum(int code, String name) {
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
