package com.codingserbia;

import java.util.List;

import junit.framework.Assert;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import com.codingserbia.data.CustomerTestDataProvider;
import com.codingserbia.writable.CustomerSessionWritable;

public class CustomerRecordsMapReduceTest {

    public static String CUSTOMER_CATEGORIES_FILE_PATH = "file://target/test-classes/input/customer_categories.db";

    private MapReduceDriver<LongWritable, Text, LongWritable, CustomerSessionWritable, LongWritable, Text> mapReduceDriver;

    @Before
    public void setUp() {
        CustomerRecordsMapper mapper = new CustomerRecordsMapper();
        CustomerRecordsReducer reducer = new CustomerRecordsReducer();

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        mapReduceDriver.getConfiguration().set("customer_categories.file.path", CUSTOMER_CATEGORIES_FILE_PATH);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testMapReduce() throws Exception {
        for (int i = 0; i < CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_MAP_REDUCE_INPUT.size(); i++) {
            mapReduceDriver.withInput(new LongWritable(i), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_MAP_REDUCE_INPUT.get(i));
        }

        // Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(AwesomeTestDataProvider.USER_ID, AwesomeTestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = mapReduceDriver.run();
        System.out.println(result);

        Assert.fail("To be implemented");
    }

}
