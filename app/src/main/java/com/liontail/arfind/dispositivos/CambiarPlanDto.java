package com.liontail.arfind.dispositivos;

public class CambiarPlanDto {

    private String deviceId;
    private String planId;

    public CambiarPlanDto(){}

    public CambiarPlanDto(String deviceId, String planId) {
        this.deviceId = deviceId;
        this.planId = planId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
