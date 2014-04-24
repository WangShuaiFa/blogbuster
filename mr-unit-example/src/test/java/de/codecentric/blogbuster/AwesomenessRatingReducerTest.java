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

import de.codecentric.blogbuster.data.TestDataProvider;

public class AwesomenessRatingReducerTest {

    private ReduceDriver<LongWritable, AwesomenessRatingWritable, LongWritable, Text> reduceDriver;

    @Before
    public void setUp() {
        AwesomenessRatingReducer reducer = new AwesomenessRatingReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testWithInput() throws Exception {
        ImmutableList<AwesomenessRatingWritable> values = ImmutableList.of(TestDataProvider.USER_INFO_VALUE,
                        TestDataProvider.RATING_INFO_VALUE);
        reduceDriver.withInput(TestDataProvider.USER_ID, values);

        Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(TestDataProvider.USER_ID,
                        TestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = reduceDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(1).containsExactly(expectedTupple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testWithInputAndOutput() throws Exception {
        ImmutableList<AwesomenessRatingWritable> values = ImmutableList.of(TestDataProvider.USER_INFO_VALUE,
                        TestDataProvider.RATING_INFO_VALUE);
        reduceDriver.withInput(TestDataProvider.USER_ID, values);

        reduceDriver.withOutput(new Pair<LongWritable, Text>(TestDataProvider.USER_ID,
                        TestDataProvider.RESULT_TUPPLE_TEXT));

        reduceDriver.runTest();
    }

}
