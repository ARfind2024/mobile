package com.liontail.arfind.mercadopago;

public class MPCart {
    private String idProducto;
    private String idPlan;

    public MPCart(){}

    public MPCart(String idProducto, String idPlan) {
        this.idProducto = idProducto;
        this.idPlan = idPlan;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }
}
