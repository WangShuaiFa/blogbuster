package de.codecentric.blogbuster.data;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import de.codecentric.blogbuster.AwesomenessRatingWritable;

public class AwesomeTestDataProvider {

    public static final LongWritable USER_ID = new LongWritable(1L);
    public static final LongWritable USER_ID_FILTERED_OUT = new LongWritable(2L);

    // mapper input, data as expected to be from the HDFS
    public static final Text USER_INFO = new Text("1,Ozren,Gulan,Serbia,Novi Sad,FTN,E2");
    public static final Text RATING_INFO = new Text("1,8.98");

    public static final Text USER_INFO_FILTERED_OUT = new Text("2,Dusan,Zamurovic,Serbia,Novi Sad,PMF,Maths");
    public static final Text RATING_INFO_FILTERED_OUT = new Text("2,5.78");

    // mapper output, data expected to go out of the mapper class, i.e. reducer input
    public static final AwesomenessRatingWritable USER_INFO_VALUE = new AwesomenessRatingWritable();
    public static final AwesomenessRatingWritable RATING_INFO_VALUE = new AwesomenessRatingWritable();

    public static final AwesomenessRatingWritable USER_INFO_VALUE_FILTERED_OUT = new AwesomenessRatingWritable();
    public static final AwesomenessRatingWritable RATING_INFO_VALUE_FILTERED_OUT = new AwesomenessRatingWritable();

    static {
        USER_INFO_VALUE.setFirstName(new Text("Ozren"));
        USER_INFO_VALUE.setLastName(new Text("Gulan"));
        USER_INFO_VALUE.setCountry(new Text("Serbia"));
        USER_INFO_VALUE.setCity(new Text("Novi Sad"));
        USER_INFO_VALUE.setFaculty(new Text("FTN"));
        USER_INFO_VALUE.setDepartment(new Text("E2"));
        USER_INFO_VALUE.setRating(new FloatWritable(0L));

        RATING_INFO_VALUE.setRating(new FloatWritable(8.98f));

        USER_INFO_VALUE_FILTERED_OUT.setFirstName(new Text("Dusan"));
        USER_INFO_VALUE_FILTERED_OUT.setLastName(new Text("Zamurovic"));
        USER_INFO_VALUE_FILTERED_OUT.setCountry(new Text("Serbia"));
        USER_INFO_VALUE_FILTERED_OUT.setCity(new Text("Novi Sad"));
        USER_INFO_VALUE_FILTERED_OUT.setFaculty(new Text("PMF"));
        USER_INFO_VALUE_FILTERED_OUT.setDepartment(new Text("Maths"));
        USER_INFO_VALUE_FILTERED_OUT.setRating(new FloatWritable(0L));

        RATING_INFO_VALUE_FILTERED_OUT.setRating(new FloatWritable(5.78f));
    }

    // reducer output, i.e. map/reduce final result
    public static final Text RESULT_TUPPLE_TEXT = new Text(USER_INFO_VALUE.getFirstName() + "\t" + USER_INFO_VALUE.getLastName() + "\t" + USER_INFO_VALUE.getCountry() + "\t"
                    + USER_INFO_VALUE.getCity() + "\t" + USER_INFO_VALUE.getFaculty() + "\t" + USER_INFO_VALUE.getDepartment() + "\t" + RATING_INFO_VALUE.getRating().get());

}
