package com.liontail.arfind.dispositivos;

public class SubmitCodeResponse {
    private String message;
    public SubmitCodeResponse(){}
    public SubmitCodeResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
