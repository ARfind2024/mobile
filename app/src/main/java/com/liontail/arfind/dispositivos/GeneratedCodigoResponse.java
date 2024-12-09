package com.liontail.arfind.dispositivos;

public class GeneratedCodigoResponse {
    private String  message;
    private String  codigo_invitado;

    public GeneratedCodigoResponse(){}

    public GeneratedCodigoResponse(String message, String codigo_invitado) {
        this.message = message;
        this.codigo_invitado = codigo_invitado;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodigo_invitado() {
        return codigo_invitado;
    }

    public void setCodigo_invitado(String codigo_invitado) {
        this.codigo_invitado = codigo_invitado;
    }
}
