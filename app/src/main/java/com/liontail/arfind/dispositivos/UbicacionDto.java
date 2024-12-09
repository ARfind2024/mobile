package com.liontail.arfind.dispositivos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UbicacionDto implements Parcelable {
    private String deviceId;
    private Coordenadas ubicacion;

    public UbicacionDto() {}

    public UbicacionDto(String deviceId, Coordenadas ubicacion) {
        this.deviceId = deviceId;
        this.ubicacion = ubicacion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Coordenadas getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Coordenadas ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Escribir los datos del objeto al parcel
    }



    public static class Coordenadas {
        private double _latitude;
        private double _longitude;

        public Coordenadas() {}

        public Coordenadas(double _latitude, double _longitude) {
            this._latitude = _latitude;
            this._longitude = _longitude;
        }

        public double getLatitude() {
            return _latitude;
        }

        public void setLatitude(double _latitude) {
            this._latitude = _latitude;
        }

        public double getLongitude() {
            return _longitude;
        }

        public void setLongitude(double _longitude) {
            this._longitude = _longitude;
        }
    }
}
