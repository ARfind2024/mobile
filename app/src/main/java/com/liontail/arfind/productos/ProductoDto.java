package com.liontail.arfind.productos;

public class ProductoDto {
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String apodo;

    private Double precio;

    public ProductoDto(){

    }

    public ProductoDto(String id,String titulo, String descripcion, String imagen,String apodo,Double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.apodo = apodo;
        this.precio = precio;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getApodo() {return apodo;}
    public void setApodo(String apodo) {this.apodo = apodo;}

    public Double getPrecio() {return precio;}

    public void setPrecio(Double precio) {this.precio = precio;}
}

