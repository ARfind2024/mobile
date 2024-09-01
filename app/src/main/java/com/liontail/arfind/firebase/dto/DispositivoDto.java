package com.liontail.arfind.firebase.dto;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class DispositivoDto {
    private String id;
    private Timestamp fecha_creacion;
    private String plan;
    private String telefono;
    private GeoPoint ubicacion;
    private String usuario;

    public DispositivoDto(){}

    public DispositivoDto(Timestamp fecha_creacion, String plan, String telefono, GeoPoint ubicacion, String usuario) {
        this.fecha_creacion = fecha_creacion;
        this.plan = plan;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.usuario = usuario;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
