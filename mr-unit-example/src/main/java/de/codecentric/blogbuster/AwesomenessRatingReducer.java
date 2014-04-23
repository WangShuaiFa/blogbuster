package de.codecentric.blogbuster;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AwesomenessRatingReducer extends Reducer<LongWritable, AwesomenessRatingWritable, LongWritable, Text> {

    public AwesomenessRatingReducer() {
        super();
    }

    @Override
    protected void reduce(LongWritable key, Iterable<AwesomenessRatingWritable> values, Context context)
                    throws IOException, InterruptedException {
        Text firstName = null;
        Text lastName = null;
        Text country = null;
        Text city = null;
        Text company = null;
        LongWritable rating = null;

        for (AwesomenessRatingWritable value : values) {
            if (value.isUserInformation()) {
                firstName = new Text(value.getFirstName());
                lastName = new Text(value.getLastName());
                country = new Text(value.getCountry());
                city = new Text(value.getCity());
                company = new Text(value.getCompany());
            } else {
                rating = new LongWritable(value.getRating().get());
            }
        }

        // filter out users with awesomenessRating <= 150
        if (rating != null && rating.get() > 150L) {
            StringBuffer stringValue = prepareOutput(firstName, lastName, country, city, company, rating);
            context.write(key, new Text(stringValue.toString()));
        }
    }

    private StringBuffer prepareOutput(Text firstName, Text lastName, Text country, Text city, Text company,
                    LongWritable rating) {
        StringBuffer stringValue = new StringBuffer(firstName != null ? firstName.toString() : "");
        // stringValue.append("\t" + (lastName != null ? lastName.toString() : ""));
        stringValue.append("\t" + (country != null ? country.toString() : ""));
        // stringValue.append("\t" + (city != null ? city.toString() : ""));
        // stringValue.append("\t" + (company != null ? company.toString() : ""));
        stringValue.append("\t" + (rating != null ? rating.toString() : "0"));

        return stringValue;
    }
}
