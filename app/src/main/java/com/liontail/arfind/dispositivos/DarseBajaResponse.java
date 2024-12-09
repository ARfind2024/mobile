package com.liontail.arfind.dispositivos;

public class DarseBajaResponse {
    private String message;
    public DarseBajaResponse(){}
    public DarseBajaResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
