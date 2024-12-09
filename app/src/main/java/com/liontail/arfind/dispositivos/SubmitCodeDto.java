package com.liontail.arfind.dispositivos;

public class SubmitCodeDto {
    private String codigo_invitado;

    public SubmitCodeDto(){}
    public SubmitCodeDto(String codigo_invitado) {
        this.codigo_invitado = codigo_invitado;
    }

    public String getCodigo_invitado() {
        return codigo_invitado;
    }

    public void setCodigo_invitado(String codigo_invitado) {
        this.codigo_invitado = codigo_invitado;
    }
}
