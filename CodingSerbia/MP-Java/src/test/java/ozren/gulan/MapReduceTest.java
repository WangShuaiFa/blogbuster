package ozren.gulan;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import ozren.gulan.RatingMapper;
import ozren.gulan.RatingReducer;
import ozren.gulan.RatingWritable;
import ozren.gulan.data.TestDataProvider;


public class MapReduceTest {

    private MapReduceDriver<LongWritable, Text, LongWritable, RatingWritable, LongWritable, Text> mapReduceDriver;

    @Before
    public void setUp() {
        RatingMapper mapper = new RatingMapper();
        RatingReducer reducer = new RatingReducer();

        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testMapReduceWithManualAssertions() throws Exception {
        mapReduceDriver.withInput(new LongWritable(0L), TestDataProvider.STUDENT_INFO);
        mapReduceDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);
        mapReduceDriver.withInput(new LongWritable(3L), TestDataProvider.STUDENT_INFO_FILTERED_OUT);
        mapReduceDriver.withInput(new LongWritable(4L), TestDataProvider.RATING_INFO_FILTERED_OUT);

        Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(TestDataProvider.STUDENT_ID,
                        TestDataProvider.RESULT_TUPPLE_TEXT);

        List<Pair<LongWritable, Text>> result = mapReduceDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(1).containsExactly(expectedTupple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testMapReduceWithAutoAssertions() throws Exception {
        mapReduceDriver.withInput(new LongWritable(0L), TestDataProvider.STUDENT_INFO);
        mapReduceDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);
        mapReduceDriver.withInput(new LongWritable(3L), TestDataProvider.STUDENT_INFO_FILTERED_OUT);
        mapReduceDriver.withInput(new LongWritable(4L), TestDataProvider.RATING_INFO_FILTERED_OUT);

        Pair<LongWritable, Text> expectedTupple = new Pair<LongWritable, Text>(TestDataProvider.STUDENT_ID,
                        TestDataProvider.RESULT_TUPPLE_TEXT);
        mapReduceDriver.withOutput(expectedTupple);

        mapReduceDriver.runTest();
    }

}
