
# SnappBox Delivery Fare Estimation

#### This project implements a Delivery Fare Estimation tool for SnappBox couriers. The goal is to filter out erroneous GPS data and accurately calculate the fare for each delivery based on the courier's GPS coordinates and timestamps, using the Haversine formula. This program handles large datasets efficiently by leveraging concurrency features.

## Problem Overview
##### SnappBox couriers perform thousands of deliveries daily, and ensuring accurate fare calculation is essential for maintaining trust and efficiency. The challenge involves filtering out invalid GPS data and calculating delivery fares based on filtered coordinates and timestamps.

### Key Requirements:
1. Filtering Invalid Data: Filter out GPS data where the calculated speed exceeds 100 km/h, as this indicates inaccurate data.
2. Fare Calculation:
    * . A standard fare of 1.30 is charged at the start of each delivery.
    * .The minimum fare for each delivery is 3.47.
3. Distance Calculation: The Haversine formula is used to compute the distance between two consecutive GPS points.
4. Concurrency: The solution leverages concurrency to ensure that it processes large datasets efficiently.
5. Handling Large Datasets: The program should be capable of ingesting and processing several gigabytes of sample data.

### Algorithm and Design

1. Reading the Data: The program reads the input file using a buffered reader to handle large datasets.
2. Filtering Invalid Segments: For each delivery, the program filters out segments where the speed between two points exceeds 100 km/h.
3. Fare Calculation:
    * The fare is calculated by summing up the distances between valid GPS points.
    * A base charge of 1.30 is added, and the final fare is rounded up to a minimum of 3.47 if needed.
4. Concurrency: The fare calculation for multiple deliveries is done in parallel to improve performance when dealing with large datasets.

### Setup and Usage
#### Prerequisites
* Java 8 or higher.
* Maven or another Java build tool for running tests and managing dependencies.

### Input
The input data is provided as a CSV file with the following format:

`id_delivery,lat,lng,timestamp`

Each row represents a courier's location at a specific point in time during a delivery.

### output
The output will be a CSV file in the following format:

`id_delivery,fare_estimate`

where `fare_estimate` represents the total fare for the respective delivery.
### Code Structure
* Main.java: The entry point of the program. It handles reading the input, processing the data, and writing the output.
* Courier.java: Represents a delivery with methods to calculate distances, speeds, and fares.
* CourierTest.java: Unit tests for individual methods like distance and fare calculation.
* end-to-endTest Documentry:an end-to-end test for the whole project to check if everything integrates and works well together.

### End-to-end testing
you should do some changes in Main.java:

`19 private static final String inputFilePath = "src/main/resources/End-to-end Test/input.csv";`

`20 private static final String outputFilePath = "src/main/resources/End-to-end Test/output.csv";`

the correct output for this Test should be this:

`id_delivery,fare_estimate`

`1,217.004614738`

After you've tested the code you can go back to the original sample-data testing by undoing the changes.

`19 private static final String inputFilePath = "src/main/resources/SampleData/sample-data.csv";`

`20 private static final String outputFilePath = "src/main/resources/SampleData/output.csv";`