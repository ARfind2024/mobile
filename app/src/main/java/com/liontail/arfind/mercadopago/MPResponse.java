package com.liontail.arfind.mercadopago;

public class MPResponse {

    private String url;
    private Double precioTotal;

    public MPResponse(){}

    public MPResponse(String url, Double precioTotal) {
        this.url = url;
        this.precioTotal = precioTotal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
