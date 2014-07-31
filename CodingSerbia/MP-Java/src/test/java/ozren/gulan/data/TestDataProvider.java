package ozren.gulan.data;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import ozren.gulan.RatingWritable;

public class TestDataProvider {

    public static final LongWritable STUDENT_ID = new LongWritable(1L);
    public static final LongWritable STUDENT_ID_FILTERED_OUT = new LongWritable(2L);

    // mapper input, data as expected to be from the HDFS
    public static final Text STUDENT_INFO = new Text("1,Ozren,Gulan,Serbia,Novi Sad,PMF,Softversko Inzenjerstvo");
    public static final Text RATING_INFO = new Text("1,10.0");

    public static final Text STUDENT_INFO_FILTERED_OUT = new Text("2,Petar,Petrovic,Serbia,Novi Sad,FTN,Informacioni Sistemi");
    public static final Text RATING_INFO_FILTERED_OUT = new Text("2,7.53");

    // mapper output, data expected to go out of the mapper class, i.e. reducer input
    public static final RatingWritable STUDENT_INFO_VALUE = new RatingWritable();
    public static final RatingWritable RATING_INFO_VALUE = new RatingWritable();

    public static final RatingWritable STUDENT_INFO_VALUE_FILTERED_OUT = new RatingWritable();
    public static final RatingWritable RATING_INFO_VALUE_FILTERED_OUT = new RatingWritable();

    static {
        STUDENT_INFO_VALUE.setFirstName(new Text("Ozren"));
        STUDENT_INFO_VALUE.setLastName(new Text("Gulan"));
        STUDENT_INFO_VALUE.setCountry(new Text("Serbia"));
        STUDENT_INFO_VALUE.setCity(new Text("Novi Sad"));
        STUDENT_INFO_VALUE.setUniversity(new Text("PMF"));
        STUDENT_INFO_VALUE.setCourse(new Text("Softversko Inzenjerstvo"));
        STUDENT_INFO_VALUE.setRating(new FloatWritable(0F));

        RATING_INFO_VALUE.setRating(new FloatWritable(10.0F));

        STUDENT_INFO_VALUE_FILTERED_OUT.setFirstName(new Text("Petar"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setLastName(new Text("Petrovic"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setCountry(new Text("Serbia"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setCity(new Text("Novi Sad"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setUniversity(new Text("FTN"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setCourse(new Text("Informacioni Sistemi"));
        STUDENT_INFO_VALUE_FILTERED_OUT.setRating(new FloatWritable(0F));

        RATING_INFO_VALUE_FILTERED_OUT.setRating(new FloatWritable(7.53F));
    }

    // reducer output, i.e. map/reduce final result
    public static final Text RESULT_TUPPLE_TEXT = new Text(STUDENT_INFO_VALUE.getFirstName() + "\t" + STUDENT_INFO_VALUE.getLastName() + "\t" + STUDENT_INFO_VALUE.getCourse() + "\t" + RATING_INFO_VALUE.getRating().get());

}
