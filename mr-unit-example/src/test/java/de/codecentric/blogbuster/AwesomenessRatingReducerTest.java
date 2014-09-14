package de.codecentric.blogbuster;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.codecentric.blogbuster.data.AwesomeTestDataProvider;

public class AwesomenessRatingReducerTest {

    private ReduceDriver<LongWritable, AwesomenessRatingWritable, LongWritable, Text> reduceDriver;

    @Before
    public void setUp() {
        AwesomenessRatingReducer reducer = new AwesomenessRatingReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testReducerWithManualAssertions() throws Exception {
        ImmutableList<AwesomenessRatingWritable> values = ImmutableList.of(AwesomeTestDataProvider.USER_INFO_VALUE,
                        AwesomeTestDataProvider.RATING_INFO_VALUE);
        ImmutableList<AwesomenessRatingWritable> valuesFilteredOut = ImmutableList.of(
                        AwesomeTestDataProvider.USER_INFO_VALUE_FILTERED_OUT, AwesomeTestDataProvider.RATING_INFO_VALUE_FILTERED_OUT);

        reduceDriver.withInput(AwesomeTestDataProvider.USER_ID, values);
        reduceDriver.withInput(AwesomeTestDataProvider.USER_ID_FILTERED_OUT, valuesFilteredOut);

        Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(AwesomeTestDataProvider.USER_ID,
                        AwesomeTestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = reduceDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(1).containsExactly(expectedTupple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testReducerWithAutoAssertions() throws Exception {
        ImmutableList<AwesomenessRatingWritable> values = ImmutableList.of(AwesomeTestDataProvider.USER_INFO_VALUE,
                        AwesomeTestDataProvider.RATING_INFO_VALUE);
        ImmutableList<AwesomenessRatingWritable> valuesFilteredOut = ImmutableList.of(
                        AwesomeTestDataProvider.USER_INFO_VALUE_FILTERED_OUT, AwesomeTestDataProvider.RATING_INFO_VALUE_FILTERED_OUT);

        reduceDriver.withInput(AwesomeTestDataProvider.USER_ID, values);
        reduceDriver.withInput(AwesomeTestDataProvider.USER_ID_FILTERED_OUT, valuesFilteredOut);

        reduceDriver.withOutput(new Pair<LongWritable, Text>(AwesomeTestDataProvider.USER_ID,
                        AwesomeTestDataProvider.RESULT_TUPPLE_TEXT));

        reduceDriver.runTest();
    }

}
