package com.codingserbia;

import java.util.List;

import junit.framework.Assert;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
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
        reduceDriver.withInput(new LongWritable(6L), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_6);
        reduceDriver.withInput(new LongWritable(8L), CustomerTestDataProvider.CUSTOMER_RECORDS_FOR_REDUCER_INPUT_CATEGORY_8);

        // Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(AwesomeTestDataProvider.USER_ID, AwesomeTestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = reduceDriver.run();

        // Assertions.assertThat(result).isNotNull().hasSize(1).containsExactly(expectedTupple);

        Assert.fail("To be implemented");
    }

}
