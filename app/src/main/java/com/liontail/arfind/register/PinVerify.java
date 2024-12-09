package com.liontail.arfind.register;

public class PinVerify {

    private String message;
    private Boolean result;

    public PinVerify(){}

    public PinVerify(String message, Boolean result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
