package com.liontail.arfind.dispositivos;

import com.google.gson.annotations.SerializedName;

public class GeoPoint {
        @SerializedName("_latitude")
        private double latitude;

        @SerializedName("_longitude")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
}

