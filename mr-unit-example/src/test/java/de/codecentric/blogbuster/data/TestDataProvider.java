package de.codecentric.blogbuster.data;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.types.Pair;

import de.codecentric.blogbuster.AwesomenessRatingWritable;

public class TestDataProvider {

    public static final LongWritable USER_ID = new LongWritable(1L);

    // mapper input, data as expected to be from the HDFS
    public static final Text USER_INFO = new Text("1,Ozren,Gulan,Serbia,Novi Sad,codecentric");
    public static final Text RATING_INFO = new Text("1,1000");

    // mapper output, data expected to go out of the mapper class, i.e. reducer input
    public static final AwesomenessRatingWritable USER_INFO_VALUE = new AwesomenessRatingWritable();
    public static final AwesomenessRatingWritable RATING_INFO_VALUE = new AwesomenessRatingWritable();

    static {
        USER_INFO_VALUE.setFirstName(new Text("Ozren"));
        USER_INFO_VALUE.setLastName(new Text("Gulan"));
        USER_INFO_VALUE.setCountry(new Text("Serbia"));
        USER_INFO_VALUE.setCity(new Text("Novi Sad"));
        USER_INFO_VALUE.setCompany(new Text("codecentric"));
        USER_INFO_VALUE.setRating(new LongWritable(0L));

        RATING_INFO_VALUE.setRating(new LongWritable(1000L));
    }

    // reducer output, i.e. map/reduce final result
    public static final Text RESULT_TUPPLE_TEXT = new Text(USER_INFO_VALUE.getFirstName() + "\t"
                    + USER_INFO_VALUE.getCountry() + "\t" + RATING_INFO_VALUE.getRating().get());

    public static Pair<LongWritable, AwesomenessRatingWritable> getUserInfoTupple() {
        return new Pair<LongWritable, AwesomenessRatingWritable>(USER_ID, USER_INFO_VALUE);
    }

    public static Pair<LongWritable, AwesomenessRatingWritable> getRatingInfoTupple() {
        return new Pair<LongWritable, AwesomenessRatingWritable>(USER_ID, RATING_INFO_VALUE);
    }

}
