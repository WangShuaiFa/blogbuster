package ozren.gulan;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RatingMapper extends Mapper<LongWritable, Text, LongWritable, RatingWritable> {

    public RatingMapper() {
        super();
    }

    @Override
    protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {
        String value = text.toString();
        String[] tokens = value.split(",");

        if (tokens.length == 2) {
            mapRating(tokens, context);
        } else if (tokens.length == 7) {
            mapStudents(tokens, context);
        }
    }

    private void mapStudents(String[] tokens, Context context) throws IOException, InterruptedException {
        LongWritable valueKey = new LongWritable(Long.parseLong(tokens[0]));

        RatingWritable writable = new RatingWritable();
        writable.setFirstName(new Text(tokens[1]));
        writable.setLastName(new Text(tokens[2]));
        writable.setCountry(new Text(tokens[3]));
        writable.setCity(new Text(tokens[4]));
        writable.setUniversity(new Text(tokens[5]));
        writable.setCourse(new Text(tokens[6]));

        context.write(valueKey, writable);
    }

    private void mapRating(String[] tokens, Context context) throws IOException, InterruptedException {
        LongWritable key = new LongWritable(Long.parseLong(tokens[0]));

        RatingWritable writable = new RatingWritable();
        writable.setRating(new FloatWritable(Float.parseFloat(tokens[1])));
        context.write(key, writable);
    }

}
