package com.codingserbia;

import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.codingserbia.data.JsonTestDataProvider;
import com.codingserbia.writable.CustomerSessionWritable;

public class CustomerRecordsMapperTest {

    public static String CUSTOMER_CATEGORIES_FILE_PATH = "file://target/test-classes/input/customer_categories.db";

    private MapDriver<LongWritable, Text, LongWritable, CustomerSessionWritable> mapDriver;

    private Configuration config;

    @Before
    public void setUp() {
        config = new Configuration();
        config.set("customer_categories.file.path", CUSTOMER_CATEGORIES_FILE_PATH);

        CustomerRecordsMapper mapper = new CustomerRecordsMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testMapperWithManualAssertions() throws Exception {
        Context ctx = mapDriver.getContext();
        Mockito.when(ctx.getConfiguration()).thenReturn(config);

        for (int i = 0; i < JsonTestDataProvider.CUSTOMER_RECORDS.length; i++) {
            mapDriver.withInput(new LongWritable(i), JsonTestDataProvider.CUSTOMER_RECORDS[i]);
        }

        List<Pair<LongWritable, CustomerSessionWritable>> result = mapDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(2);

        LongWritable expectedCategoryId = new LongWritable(5L);
        for (Iterator<Pair<LongWritable, CustomerSessionWritable>> iterator = result.iterator(); iterator.hasNext();) {
            Pair<LongWritable, CustomerSessionWritable> pair = iterator.next();
            Assertions.assertThat(expectedCategoryId.equals(pair.getFirst()));
            Assertions.assertThat(expectedCategoryId.equals(pair.getSecond().categoryId));
        }
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testMapperWithAutoAssertions() throws Exception {
        // mapDriver.withInput(new LongWritable(0L), AwesomeTestDataProvider.USER_INFO);
        // mapDriver.withInput(new LongWritable(1L), AwesomeTestDataProvider.RATING_INFO);
        //
        // mapDriver.withOutput(AwesomeTestDataProvider.USER_ID, AwesomeTestDataProvider.USER_INFO_VALUE);
        // mapDriver.withOutput(AwesomeTestDataProvider.USER_ID, AwesomeTestDataProvider.RATING_INFO_VALUE);
        //
        // mapDriver.runTest();
    }

}
