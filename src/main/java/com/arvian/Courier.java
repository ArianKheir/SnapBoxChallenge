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
    public static double dist(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371;
        double radLat = Math.toRadians(lat2 - lat1);
        double radLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(radLat / 2) * Math.sin(radLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(radLng / 2) * Math.sin(radLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    public static double calcSpeed(Courier a, Courier b) {
        double distanceKm = dist(a.lat, a.lng, b.lat, b.lng);
        long timeSec = Math.abs(b.timestamp - a.timestamp);
        return (distanceKm / timeSec) * 3600.0;
    }
    public String getId() {
        return id;
    }
}
