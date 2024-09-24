package com.arvian;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public static double calcFare(ArrayList<Courier> arr){
        if(arr.size() == 1 || arr.isEmpty()){
            return minimumFare;
        }
        double ans = stdFlag;
        for(int i = 1 ; i < arr.size() ; i ++){
            double tmp = dist(arr.get(i - 1).lat,arr.get(i - 1).lng,
                    arr.get(i).lat, arr.get(i).lng);
            LocalDateTime fareTime1 = (new Timestamp(arr.get(i - 1).timestamp * 1000l)).toLocalDateTime();
            LocalDateTime fareTime2 = (new Timestamp(arr.get(i).timestamp * 1000l)).toLocalDateTime();
            double speed = calcSpeed(arr.get(i - 1), arr.get(i));
            if(speed > 10) {
                if (fareTime1.getHour() < 5 && fareTime2.getHour() < 5) {
                    tmp = tmp * 1.30;
                }
                else{
                    tmp = tmp * 0.74;
                }
            }else{
                tmp = (Math.abs(arr.get(i).timestamp - arr.get(i - 1).timestamp) / 3600.0) * 11.90;
            }
            ans += tmp;
        }
        return Math.max(ans, minimumFare);
    }
    public String getId() {
        return id;
    }
}
