package datagenerator;

import datagenerator.csv.CsvDataGenerator;
import datagenerator.json.JsonDataGenerator;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        if (args.length == 1) {
            main.invokeDataGeneration(args[0]);
        }

    }

    private void invokeDataGeneration(String type) {
        if ("csv".equalsIgnoreCase(type)) {
            invokeCsvDataGeneration();
        } else if ("json".equalsIgnoreCase(type)) {
            invokeJsonDataGeneration();
        } else {
            System.out.println("Unrecognized data type requested.");
        }
    }

    private void invokeCsvDataGeneration() {
        Generator generator = new CsvDataGenerator();
        for (DataSize dataSize : DataSize.values()) {
            generator.generateData(dataSize);
        }
    }

    private void invokeJsonDataGeneration() {
        Generator generator = new JsonDataGenerator();
        for (DataSize dataSize : DataSize.values()) {
            generator.generateData(dataSize);
        }
    }

}
