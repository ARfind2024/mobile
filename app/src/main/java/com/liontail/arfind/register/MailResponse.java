package com.liontail.arfind.register;

public class MailResponse {
    private String message;
    public MailResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
