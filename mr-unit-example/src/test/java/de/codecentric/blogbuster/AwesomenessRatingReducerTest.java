package de.codecentric.blogbuster;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;

public class AwesomenessRatingReducerTest {

    private ReduceDriver<LongWritable, AwesomenessRatingWritable, LongWritable, Text> reduceDriver;

    @Before
    public void setUp() {
        AwesomenessRatingReducer reducer = new AwesomenessRatingReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }

}
