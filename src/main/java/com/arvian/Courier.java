package com.arvian;

public class Courier {
    private static final double stdFlag = 1.30;
    private static final double minimumFare = 3.47;
    private String id;
    private double lat, lng;
    private long timestamp;

    public Courier(String id, double lat, double lng, long timestamp) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }
    public String getId() {
        return id;
    }
}
