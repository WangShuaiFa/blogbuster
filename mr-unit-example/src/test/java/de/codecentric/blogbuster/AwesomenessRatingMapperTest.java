package de.codecentric.blogbuster;

import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AwesomenessRatingMapperTest {

    private MapDriver<LongWritable, Text, LongWritable, AwesomenessRatingWritable> mapDriver;

    @Before
    public void setUp() {
        AwesomenessRatingMapper mapper = new AwesomenessRatingMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    // testing style: tell the input, assert the output
    @Test
    public void testWithInput() throws Exception {
        mapDriver.withInput(new LongWritable(0L), new Text("1,Ozren,Gulan,Serbia,Novi Sad,codecentric"));
        mapDriver.withInput(new LongWritable(1L), new Text("1,1000"));

        AwesomenessRatingWritable userInfoValue = new AwesomenessRatingWritable();
        userInfoValue.setFirstName(new Text("Ozren"));
        userInfoValue.setLastName(new Text("Gulan"));
        userInfoValue.setCountry(new Text("Serbia"));
        userInfoValue.setCity(new Text("Novi Sad"));
        userInfoValue.setCompany(new Text("codecentric"));
        userInfoValue.setRating(new LongWritable(0L));
        Pair<LongWritable, AwesomenessRatingWritable> userInfoTuple = new Pair<LongWritable, AwesomenessRatingWritable>(new LongWritable(1L), userInfoValue);

        AwesomenessRatingWritable ratingInfoValue = new AwesomenessRatingWritable();
        ratingInfoValue.setRating(new LongWritable(1000L));
        Pair<LongWritable, AwesomenessRatingWritable> ratingInfoTuple = new Pair<LongWritable, AwesomenessRatingWritable>(new LongWritable(1L), ratingInfoValue);

        List<Pair<LongWritable, AwesomenessRatingWritable>> result = mapDriver.run();
        Assertions.assertThat(result).isNotNull().hasSize(2).contains(userInfoTuple, ratingInfoTuple);
    }

    // testing style: tell the input and output, let the framework do the assertions
    @Test
    public void testWithKnownInputAndOutput() throws Exception {
        mapDriver.withInput(new LongWritable(0L), new Text("1,Ozren,Gulan,Serbia,Novi Sad,codecentric"));
        mapDriver.withInput(new LongWritable(1L), new Text("1,1000"));

        LongWritable outputKey = new LongWritable(1L);
        AwesomenessRatingWritable userInfoValue = new AwesomenessRatingWritable();
        userInfoValue.setFirstName(new Text("Ozren"));
        userInfoValue.setLastName(new Text("Gulan"));
        userInfoValue.setCountry(new Text("Serbia"));
        userInfoValue.setCity(new Text("Novi Sad"));
        userInfoValue.setCompany(new Text("codecentric"));
        userInfoValue.setRating(new LongWritable(0L));
        mapDriver.withOutput(outputKey, userInfoValue);

        AwesomenessRatingWritable ratingInfoValue = new AwesomenessRatingWritable();
        ratingInfoValue.setRating(new LongWritable(1000L));
        mapDriver.withOutput(outputKey, ratingInfoValue);

        mapDriver.runTest();
    }

}
