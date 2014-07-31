package ozren.gulan;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import ozren.gulan.RatingReducer;
import ozren.gulan.RatingWritable;
import ozren.gulan.data.TestDataProvider;

import com.google.common.collect.ImmutableList;


public class RatingReducerTest {

    private ReduceDriver<LongWritable, RatingWritable, LongWritable, Text> reduceDriver;

    @Before
    public void setUp() {
        RatingReducer reducer = new RatingReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testReducerWithManualAssertions() throws Exception {
        ImmutableList<RatingWritable> values = ImmutableList.of(TestDataProvider.STUDENT_INFO_VALUE, TestDataProvider.RATING_INFO_VALUE);
        ImmutableList<RatingWritable> valuesFilteredOut = ImmutableList.of(TestDataProvider.STUDENT_INFO_VALUE_FILTERED_OUT, TestDataProvider.RATING_INFO_VALUE_FILTERED_OUT);

        reduceDriver.withInput(TestDataProvider.STUDENT_ID, values);
        reduceDriver.withInput(TestDataProvider.STUDENT_ID_FILTERED_OUT, valuesFilteredOut);

        Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(TestDataProvider.STUDENT_ID, TestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = reduceDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(1).containsExactly(expectedTupple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testReducerWithAutoAssertions() throws Exception {
        ImmutableList<RatingWritable> values = ImmutableList.of(TestDataProvider.STUDENT_INFO_VALUE, TestDataProvider.RATING_INFO_VALUE);
        ImmutableList<RatingWritable> valuesFilteredOut = ImmutableList.of(TestDataProvider.STUDENT_INFO_VALUE_FILTERED_OUT, TestDataProvider.RATING_INFO_VALUE_FILTERED_OUT);

        reduceDriver.withInput(TestDataProvider.STUDENT_ID, values);
        reduceDriver.withInput(TestDataProvider.STUDENT_ID_FILTERED_OUT, valuesFilteredOut);

        reduceDriver.withOutput(new Pair<LongWritable, Text>(TestDataProvider.STUDENT_ID, TestDataProvider.RESULT_TUPPLE_TEXT));

        reduceDriver.runTest();
    }

}
