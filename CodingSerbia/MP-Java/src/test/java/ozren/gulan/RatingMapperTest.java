package ozren.gulan;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import ozren.gulan.data.TestDataProvider;

public class RatingMapperTest {

    private MapDriver<LongWritable, Text, LongWritable, RatingWritable> mapDriver;

    @Before
    public void setUp() {
        RatingMapper mapper = new RatingMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testMapperWithManualAssertions() throws Exception {
        mapDriver.withInput(new LongWritable(0L), TestDataProvider.STUDENT_INFO);
        mapDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);

        Pair<LongWritable, RatingWritable> studentInfoTuple = new Pair<LongWritable, RatingWritable>(TestDataProvider.STUDENT_ID, TestDataProvider.STUDENT_INFO_VALUE);
        Pair<LongWritable, RatingWritable> ratingInfoTuple = new Pair<LongWritable, RatingWritable>(TestDataProvider.STUDENT_ID, TestDataProvider.RATING_INFO_VALUE);

        List<Pair<LongWritable, RatingWritable>> result = mapDriver.run();

        Assertions.assertThat(result).isNotNull().hasSize(2).contains(studentInfoTuple, ratingInfoTuple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testMapperWithAutoAssertions() throws Exception {
        mapDriver.withInput(new LongWritable(0L), TestDataProvider.STUDENT_INFO);
        mapDriver.withInput(new LongWritable(1L), TestDataProvider.RATING_INFO);

        mapDriver.withOutput(TestDataProvider.STUDENT_ID, TestDataProvider.STUDENT_INFO_VALUE);
        mapDriver.withOutput(TestDataProvider.STUDENT_ID, TestDataProvider.RATING_INFO_VALUE);

        mapDriver.runTest();
    }

}
