package de.codecentric.blogbuster;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import de.codecentric.blogbuster.data.TestDataProvider;

public class AwesomenessRatingMapperTest {

    private MapDriver<LongWritable, Text, LongWritable, AwesomenessRatingWritable> mapDriver;

    @Before
    public void setUp() {
        AwesomenessRatingMapper mapper = new AwesomenessRatingMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testMapperWithManualAssertions() throws Exception {
        mapDriver.withInput(new LongWritable(0L), TestDataProvider.USER_INFO);
        mapDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);

        Pair<LongWritable, AwesomenessRatingWritable> userInfoTuple = new Pair<LongWritable, AwesomenessRatingWritable>(
                        TestDataProvider.USER_ID, TestDataProvider.USER_INFO_VALUE);
        Pair<LongWritable, AwesomenessRatingWritable> ratingInfoTuple = new Pair<LongWritable, AwesomenessRatingWritable>(
                        TestDataProvider.USER_ID, TestDataProvider.RATING_INFO_VALUE);

        List<Pair<LongWritable, AwesomenessRatingWritable>> result = mapDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(2).contains(userInfoTuple, ratingInfoTuple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testMapperWithAutoAssertions() throws Exception {
        mapDriver.withInput(new LongWritable(0L), TestDataProvider.USER_INFO);
        mapDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);

        mapDriver.withOutput(TestDataProvider.USER_ID, TestDataProvider.USER_INFO_VALUE);
        mapDriver.withOutput(TestDataProvider.USER_ID, TestDataProvider.RATING_INFO_VALUE);

        mapDriver.runTest();
    }

}
