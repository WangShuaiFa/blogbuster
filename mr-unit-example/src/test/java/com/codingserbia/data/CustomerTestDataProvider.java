package com.codingserbia.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import com.codingserbia.dto.CustomerSession;
import com.codingserbia.writable.CustomerSessionWritable;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerTestDataProvider {

    public static LongWritable CUSTOMER_CATEGORY_ID = new LongWritable(5L);

    public static Text[] CUSTOMER_CATEGORIES;
    public static List<Text> CUSTOMER_RECORDS_FOR_MAP_INPUT_ONLY;
    public static List<Text> CUSTOMER_RECORDS_FOR_MAP_REDUCE_INPUT;

    public static List<CustomerSessionWritable> CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_4;
    public static List<CustomerSessionWritable> CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_5;
    public static List<CustomerSessionWritable> CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_6;
    public static List<CustomerSessionWritable> CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_8;

    static {
        CUSTOMER_CATEGORIES = initCustomerCategories();
        CUSTOMER_RECORDS_FOR_MAP_INPUT_ONLY = parseJsonFileInput(new File("target/test-classes/input/customer_records_map_input.json"));
        CUSTOMER_RECORDS_FOR_MAP_REDUCE_INPUT = parseJsonFileInput(new File("target/test-classes/input/customer_records_map_reduce_input.json"));
        initReducerInput();
    }

    private static Text[] initCustomerCategories() {
        return new Text[] { new Text("1\t13-18\tmale"), new Text("2\t18-24\tmale"), new Text("3\t24-30\tmale"), new Text("4\t30-40\tmale"), new Text("5\t40+\tmale") };
    }

    private static List<Text> parseJsonFileInput(File json) {
        List<Text> categoriesTestData = new ArrayList<Text>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(json));
            String line;
            while ((line = br.readLine()) != null) {
                categoriesTestData.add(new Text(line));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoriesTestData;
    }

    private static void initReducerInput() {
        CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_4 = new ArrayList<CustomerSessionWritable>();
        CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_5 = new ArrayList<CustomerSessionWritable>();
        CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_6 = new ArrayList<CustomerSessionWritable>();
        CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_8 = new ArrayList<CustomerSessionWritable>();

        for (Text customerRecord : CUSTOMER_RECORDS_FOR_MAP_INPUT_ONLY) {
            ObjectMapper jsonMapper = new ObjectMapper();
            try {
                CustomerSession jsonObj = jsonMapper.readValue(customerRecord.toString(), CustomerSession.class);
                if (jsonObj.customerCategoryId == 5L) {
                    CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_5.add(new CustomerSessionWritable(jsonObj));
                } else if (jsonObj.customerCategoryId == 4L) {
                    CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_4.add(new CustomerSessionWritable(jsonObj));
                } else if (jsonObj.customerCategoryId == 6L) {
                    CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_6.add(new CustomerSessionWritable(jsonObj));
                } else if (jsonObj.customerCategoryId == 8L) {
                    CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_8.add(new CustomerSessionWritable(jsonObj));
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
