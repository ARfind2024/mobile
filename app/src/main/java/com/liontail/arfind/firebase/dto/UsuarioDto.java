package com.liontail.arfind.firebase.dto;

import com.google.firebase.Timestamp;

public class UsuarioDto {
    private String id;
    private String apellido;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private Timestamp fecha_creacion;
    private Timestamp fecha_actualizacion;
    public UsuarioDto(){

    }
    public UsuarioDto(String apellido, String nombre, String correo, String telefono, String contrasena, Timestamp fecha_creacion, Timestamp fecha_actualizacion) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Timestamp getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Timestamp fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}
