package com.liontail.arfind.dispositivos;

public class EliminarInvitadoResponse {
    private String message;
    public EliminarInvitadoResponse(){}
    public EliminarInvitadoResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
