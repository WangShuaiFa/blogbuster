package ozren.gulan;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RatingReducer extends Reducer<LongWritable, RatingWritable, LongWritable, Text> {

    public RatingReducer() {
        super();
    }

    @Override
    protected void reduce(LongWritable key, Iterable<RatingWritable> values, Context context)
                    throws IOException, InterruptedException {
        Text firstName = null;
        Text lastName = null;
        Text country = null;
        Text city = null;
        Text university = null;
        Text course = null;
        FloatWritable rating = null;

        for (RatingWritable value : values) {
            if (value.isUserInformation()) {
                firstName = new Text(value.getFirstName());
                lastName = new Text(value.getLastName());
                country = new Text(value.getCountry());
                city = new Text(value.getCity());
                university = new Text(value.getUniversity());
                course = new Text(value.getCourse());
            } else {
                rating = new FloatWritable(value.getRating().get());
            }
        }

        // filter out students with rating > 8.5
        if (rating != null && rating.get() > 8.5F) {
            StringBuffer stringValue = prepareOutput(firstName, lastName, country, city, university, course, rating);
            context.write(key, new Text(stringValue.toString()));
        }
    }

    private StringBuffer prepareOutput(Text firstName, Text lastName, Text country, Text city, Text university, Text course, FloatWritable rating) {
        StringBuffer stringValue = new StringBuffer(firstName != null ? firstName.toString() : "");
        stringValue.append("\t" + (lastName != null ? lastName.toString() : ""));
        stringValue.append("\t" + (course != null ? course.toString() : ""));
        stringValue.append("\t" + (rating != null ? rating.toString() : "0"));

        return stringValue;
    }
}
