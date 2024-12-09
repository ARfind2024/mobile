package com.liontail.arfind.planes;

public class PlanesDto {

    private String id;
    private Integer cantidad_compartidos;
    private String descripcion;
    private String imagen;
    private String nombre;
    private Double precio;
    private Integer refresco;

    // Constructor
    public PlanesDto(String id, int cantidad_compartidos, String descripcion, String imagen, String nombre, double precio, int refresco) {
        this.id = id;
        this.cantidad_compartidos = cantidad_compartidos;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.refresco = refresco;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad_compartidos() {
        return cantidad_compartidos;
    }

    public void setCantidad_compartidos(int cantidad_compartidos) {
        this.cantidad_compartidos = cantidad_compartidos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getRefresco() {
        return refresco;
    }

    public void setRefresco(int refresco) {
        this.refresco = refresco;
    }

    // Método toString para imprimir el objeto de manera legible
    @Override
    public String toString() {
        return "Plan{" +
                "cantidadCompartidos=" + cantidad_compartidos +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", refresco=" + refresco +
                '}';
    }
}

