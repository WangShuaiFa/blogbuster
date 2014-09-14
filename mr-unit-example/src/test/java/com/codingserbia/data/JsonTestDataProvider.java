package com.codingserbia.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.hadoop.io.Text;

public class JsonTestDataProvider {

    public static Text[] CUSTOMER_CATEGORIES;
    public static Text[] CUSTOMER_RECORDS;

    static {
        CUSTOMER_CATEGORIES = initCustomerCategories();
        CUSTOMER_RECORDS = initCustomerRecords();
    }

    private static Text[] initCustomerCategories() {
        return new Text[] { new Text("1\t13-18\tmale"), new Text("2\t18-24\tmale"), new Text("3\t24-30\tmale"), new Text("4\t30-40\tmale"), new Text("5\t40+\tmale") };
    }

    private static Text[] initCustomerRecords() {
        Text[] categoriesTestData = new Text[3];

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("target/test-classes/input/customer_records_S.json")));
            String line;
            int idx = 0;
            while ((line = br.readLine()) != null) {
                categoriesTestData[idx] = new Text(line);
                idx++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoriesTestData;
    }

}
