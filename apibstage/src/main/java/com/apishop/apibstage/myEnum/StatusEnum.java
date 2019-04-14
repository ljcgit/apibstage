package com.apishop.apibstage.myEnum;

public enum StatusEnum {

    TOKEN_NOT_EXIST("300"),
    AUTH_FAIL("404"),
    SUCCESS("200"),
    USER_NOT_EXIST("203"),
    ROLE_NOT_EXIST("204"),
    LOGIN_FAIL("302"),
    ADD_FAIL("303"),
    QUERY_FAIL("304"),
    UPDATE_FAIL("305"),
    DELETE_FAIL("500"),
    DELETE_ROLE_FAIL("502"),
    ALLOT_FAIL("503");
    private String code;
    private StatusEnum(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
