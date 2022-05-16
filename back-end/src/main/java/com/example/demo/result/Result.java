package com.example.demo.result;

public class Result {
    private int code;
    private String mesg;

    public Result(int code) {
        this.code = code;
    }
    public Result(int code,String message){
        this.code=code;
        mesg=message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
