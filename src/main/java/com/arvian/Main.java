package com.arvian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static final String inputFilePath = "SampleData/sample-data.csv";
    private static final String outputFilePath = "SampleData/output.csv";
    public static void csvManager() throws IOException {
        ArrayList<String[]> outputData = new ArrayList<>();
        ArrayList<Courier> arr = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath));

        String line;
        Courier ls = null;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            double lat = Double.parseDouble(parts[1]);
            double lng = Double.parseDouble(parts[2]);
            long timestamp = Long.parseLong(parts[3]);

            Courier cur = new Courier(id, lat, lng, timestamp);
            boolean okToAdd = true;
            if (ls == null || !ls.getId().equals(id)) {
                if (!arr.isEmpty()){
                    double fare = Courier.calcFare(arr);
                    outputData.add(new String[]{ls.getId(), String.format("%.16f", fare)});
                    arr.clear();
                }
                ls = null;
            }
            if(ls != null) {
                double speed = Courier.calcSpeed(ls, cur);
                if (speed > 100) {
                    okToAdd = false;
                }
            }
            if(okToAdd == true) {
                arr.add(cur);
                ls = cur;
            }
        }

        if (!arr.isEmpty()) {
            double fare = Courier.calcFare(arr);
            outputData.add(new String[]{ls.getId(), String.format("%.16f", fare)});
        }

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));
        writer.write("id_delivery,fare_estimate\n");
        for (String[] seg : outputData) {
            writer.write(seg[0] + "," + seg[1] + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {

    }
}