package com.liontail.arfind.dispositivos;

import com.google.firebase.Timestamp;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DispositivoDto {
    @SerializedName("id")
    private String id;

    @SerializedName("tipo_producto")
    private String tipoProducto;

    @SerializedName("bateria")
    private Object bateria;

    @SerializedName("fecha_creacion")
    private Timestamp fechaCreacion;

    @SerializedName("ubicacion")
    private GeoPoint ubicacion;

    @SerializedName("usuario_id")
    private String usuarioId;

    @SerializedName("plan_id")
    private String planId;

    @SerializedName("usuarios_invitados")
    private List<String> usuariosInvitados;

    @SerializedName("ult_actualizacion")
    private Timestamp ultActualizacion;

    @SerializedName("numero_telefonico")
    private String numeroTelefonico;

    @SerializedName("codigo_invitado")
    private String codigoInvitado;

    @SerializedName("apodo")
    private String apodo;

    private int refresco;
    private String imagen;

    @SerializedName("detalles_usuarios_invitados")
    private List<DetalleUsuario> detallesUsuariosInvitados;

    public DispositivoDto(){}

    public DispositivoDto(String id, String tipoProducto, Object bateria, Timestamp fechaCreacion, GeoPoint ubicacion, String usuarioId, String planId, List<String> usuariosInvitados, Timestamp ultActualizacion, String numeroTelefonico, String codigoInvitado, String apodo, int refresco, String imagen, List<DetalleUsuario> detallesUsuariosInvitados) {
        this.id = id;
        this.tipoProducto = tipoProducto;
        this.bateria = bateria;
        this.fechaCreacion = fechaCreacion;
        this.ubicacion = ubicacion;
        this.usuarioId = usuarioId;
        this.planId = planId;
        this.usuariosInvitados = usuariosInvitados;
        this.ultActualizacion = ultActualizacion;
        this.numeroTelefonico = numeroTelefonico;
        this.codigoInvitado = codigoInvitado;
        this.apodo = apodo;
        this.refresco = refresco;
        this.imagen = imagen;
        this.detallesUsuariosInvitados = detallesUsuariosInvitados;
    }

    public int getRefresco() {
        return refresco;
    }

    public void setRefresco(int refresco) {
        this.refresco = refresco;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<DetalleUsuario> getDetallesUsuariosInvitados() {
        return detallesUsuariosInvitados;
    }

    public void setDetallesUsuariosInvitados(List<DetalleUsuario> detallesUsuariosInvitados) {
        this.detallesUsuariosInvitados = detallesUsuariosInvitados;
    }

    // Getters y Setters para todos los campos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Object getBateria() {
        return bateria;
    }

    public void setBateria(Object bateria) {
        this.bateria = bateria;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public List<String> getUsuariosInvitados() {
        return usuariosInvitados;
    }

    public void setUsuariosInvitados(List<String> usuariosInvitados) {
        this.usuariosInvitados = usuariosInvitados;
    }

    public Timestamp getUltActualizacion() {
        return ultActualizacion;
    }

    public void setUltActualizacion(Timestamp ultActualizacion) {
        this.ultActualizacion = ultActualizacion;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public void setCodigoInvitado(String codigoInvitado) {
        this.codigoInvitado = codigoInvitado;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }



    public static class DetalleUsuario {
        private String id;
        private String nombre;

        public DetalleUsuario(){}

        public DetalleUsuario(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }

}
