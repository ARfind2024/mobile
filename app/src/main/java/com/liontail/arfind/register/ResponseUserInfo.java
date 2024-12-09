package com.liontail.arfind.register;

public class ResponseUserInfo {
    private String message;
    private String userId;

    public ResponseUserInfo(){
    }
    public ResponseUserInfo(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
