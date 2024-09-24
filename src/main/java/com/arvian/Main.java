package com.arvian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String inputFilePath = "src/main/resources/SampleData/sample-data.csv";
    private static final String outputFilePath = "src/main/resources/SampleData/output.csv";
    public static void csvManager() throws IOException {
        ArrayList<String[]> outputData = new ArrayList<>();
        ArrayList<Courier> arr = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<String[]>> futures = new ArrayList<>();
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
                if (ls != null && !arr.isEmpty()){
                    ArrayList<Courier> arrCopy = new ArrayList<>(arr);
                    String idCopy = ls.getId();

                    CompletableFuture<String[]> future = CompletableFuture.supplyAsync(() -> {
                        double fare = Courier.calcFare(arrCopy);
                        return new String[]{idCopy, String.format("%.9f", fare)};
                    }, executorService);

                    futures.add(future);
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

        if (ls != null && !arr.isEmpty()){
            ArrayList<Courier> arrCopy = new ArrayList<>(arr);
            String idCopy = ls.getId();

            CompletableFuture<String[]> future = CompletableFuture.supplyAsync(() -> {
                double fare = Courier.calcFare(arrCopy);
                return new String[]{idCopy, String.format("%.9f", fare)};
            }, executorService);

            futures.add(future);
            arr.clear();
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            writer.write("id_delivery,fare_estimate\n");
            for (CompletableFuture<String[]> future : futures) {
                future.thenAccept(seg -> {
                    try {
                        writer.write(seg[0] + "," + seg[1] + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        try {
            csvManager();
            System.out.println("successfully Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}