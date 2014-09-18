package com.codingserbia;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.codingserbia.data.CustomerTestDataProvider;
import com.codingserbia.writable.CustomerSessionWritable;

public class CustomerRecordsReducerTest {

    private ReduceDriver<LongWritable, CustomerSessionWritable, LongWritable, Text> reduceDriver;

    @Before
    public void setUp() {
        CustomerRecordsReducer reducer = new CustomerRecordsReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testReducerWithManualAssertions() throws Exception {
        reduceDriver.withInput(new LongWritable(4L), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_4);
        reduceDriver.withInput(new LongWritable(5L), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_5);
        reduceDriver.withInput(new LongWritable(8L), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_8);

        Pair<LongWritable, Text> category4Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_4_KEY, CustomerTestDataProvider.CATEGORY_4_MAPREDUCE_OUTPUT);
        Pair<LongWritable, Text> category5Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_5_KEY, CustomerTestDataProvider.CATEGORY_5_MAPREDUCE_OUTPUT);
        Pair<LongWritable, Text> category8Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_8_KEY, CustomerTestDataProvider.CATEGORY_8_MAPREDUCE_OUTPUT);

        List<Pair<LongWritable, Text>> result = reduceDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(3).contains(category4Tuple, category5Tuple, category8Tuple);
    }
}
