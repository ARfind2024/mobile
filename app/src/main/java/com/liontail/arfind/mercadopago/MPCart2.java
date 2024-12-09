package com.liontail.arfind.mercadopago;

public class MPCart2 {

    private String idProducto;
    private String idPlan;
    private String userId;

    public MPCart2(){}

    public MPCart2(String idProducto, String idPlan, String userId) {
        this.idProducto = idProducto;
        this.idPlan = idPlan;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
