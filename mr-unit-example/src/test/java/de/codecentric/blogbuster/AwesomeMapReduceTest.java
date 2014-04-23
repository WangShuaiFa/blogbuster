package de.codecentric.blogbuster;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;

public class AwesomeMapReduceTest {

    private MapDriver<LongWritable, Text, LongWritable, AwesomenessRatingWritable> mapDriver;
    private ReduceDriver<LongWritable, AwesomenessRatingWritable, LongWritable, Text> reduceDriver;
    private MapReduceDriver<LongWritable, Text, LongWritable, AwesomenessRatingWritable, LongWritable, Text> mapReduceDriver;

    @Before
    public void setUp() {
        AwesomenessRatingMapper mapper = new AwesomenessRatingMapper();
        mapDriver = MapDriver.newMapDriver(mapper);

        AwesomenessRatingReducer reducer = new AwesomenessRatingReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);

        AwesomeMapReduce mapReduce = new AwesomeMapReduce();
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

}
