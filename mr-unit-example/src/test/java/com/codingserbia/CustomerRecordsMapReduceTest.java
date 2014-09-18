package com.codingserbia;

import java.util.List;

import junit.framework.Assert;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.codingserbia.data.CustomerTestDataProvider;
import com.codingserbia.writable.CustomerSessionWritable;

public class CustomerRecordsMapReduceTest extends AbstractMapReduceTest {

    public static String CUSTOMER_CATEGORIES_FILE_PATH = "file://target/test-classes/input/customer_categories.db";

    private MapReduceDriver<LongWritable, Text, LongWritable, CustomerSessionWritable, LongWritable, Text> mapReduceDriver;

    @Override
    @Before
    public void setUp() {
        super.setUp();
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

        Pair<LongWritable, Text> category4Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_4_KEY, CustomerTestDataProvider.CATEGORY_4_MAPREDUCE_OUTPUT);
        Pair<LongWritable, Text> category5Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_5_KEY, CustomerTestDataProvider.CATEGORY_5_MAPREDUCE_OUTPUT);
        Pair<LongWritable, Text> category8Tuple = new Pair<LongWritable, Text>(CustomerTestDataProvider.CATEGORY_8_KEY, CustomerTestDataProvider.CATEGORY_8_MAPREDUCE_OUTPUT);

        List<Pair<LongWritable, Text>> result = mapReduceDriver.run();

        Assert.assertEquals(3, result.size());
        Assertions.assertThat(result).contains(category4Tuple, category5Tuple, category8Tuple);
    }

}
