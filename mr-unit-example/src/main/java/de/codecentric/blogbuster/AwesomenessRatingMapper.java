package de.codecentric.blogbuster;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AwesomenessRatingMapper extends Mapper<LongWritable, Text, LongWritable, AwesomenessRatingWritable> {

    public AwesomenessRatingMapper() {
        super();
    }

    @Override
    protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {
        String value = text.toString();
        String[] tokens = value.split(",");

        if (tokens.length == 2) {
            mapAwesomenessRating(tokens, context);
        } else if (tokens.length == 7) {
            mapStudents(tokens, context);
        }
    }

    private void mapStudents(String[] tokens, Context context) throws IOException, InterruptedException {
        LongWritable valueKey = new LongWritable(Long.parseLong(tokens[0]));

        AwesomenessRatingWritable writable = new AwesomenessRatingWritable();
        writable.setFirstName(new Text(tokens[1]));
        writable.setLastName(new Text(tokens[2]));
        writable.setCountry(new Text(tokens[3]));
        writable.setCity(new Text(tokens[4]));
        writable.setFaculty(new Text(tokens[5]));
        writable.setDepartment(new Text(tokens[6]));

        context.write(valueKey, writable);
    }

    private void mapAwesomenessRating(String[] tokens, Context context) throws IOException, InterruptedException {
        LongWritable key = new LongWritable(Long.parseLong(tokens[0]));

        AwesomenessRatingWritable writable = new AwesomenessRatingWritable();
        writable.setRating(new FloatWritable(Float.parseFloat(tokens[1])));
        context.write(key, writable);
    }

}
