package com.liontail.arfind.dispositivos;

public class DarseBajaDto {
    private String deviceId;
    public DarseBajaDto(){}

    public DarseBajaDto(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
