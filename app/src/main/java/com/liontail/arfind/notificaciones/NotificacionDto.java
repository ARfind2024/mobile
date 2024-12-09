package com.liontail.arfind.notificaciones;

import com.google.firebase.Timestamp;

public class NotificacionDto {
    private String id;
    private String mensaje;
    private String tipo_notificacion_id;
    private Timestamp fecha_envio;
    private String id_usuario;
    private String id_dispositivo;

    public NotificacionDto(){}

    public NotificacionDto(String id, String mensaje, String tipo_notificacion_id, Timestamp fecha_envio, String id_usuario, String id_dispositivo) {
        this.id = id;
        this.mensaje = mensaje;
        this.tipo_notificacion_id = tipo_notificacion_id;
        this.fecha_envio = fecha_envio;
        this.id_usuario = id_usuario;
        this.id_dispositivo = id_dispositivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo_notificacion_id() {
        return tipo_notificacion_id;
    }

    public void setTipo_notificacion_id(String tipo_notificacion_id) {
        this.tipo_notificacion_id = tipo_notificacion_id;
    }

    public Timestamp getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Timestamp fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    @Override
    public String toString() {
        return "NotificacionDto{" +
                "id='" + id + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", tipo_notificacion_id='" + tipo_notificacion_id + '\'' +
                ", fecha_envio=" + fecha_envio +
                ", id_usuario='" + id_usuario + '\'' +
                ", id_dispositivo='" + id_dispositivo + '\'' +
                '}';
    }
}

