package com.liontail.arfind.dispositivos;

public class ChangeApodoDto {

    private String deviceId;
    private String apodo;

    public ChangeApodoDto(){}

    public ChangeApodoDto(String deviceId, String apodo) {
        this.deviceId = deviceId;
        this.apodo = apodo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
}
