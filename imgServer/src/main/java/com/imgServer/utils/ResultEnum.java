package com.imgServer.utils;

public enum ResultEnum {

    success(200,"success"),
    error(500,"fail");

    public Integer code;
    public String stauts;

    ResultEnum(Integer code, String stauts) {
        this.code = code;
        this.stauts = stauts;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}
