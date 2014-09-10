package de.codecentric.blogbuster;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class AwesomeMapReduce {

    public static void main(String[] args) throws Exception {
        runJob(new Path("/some/path/students.txt"), new Path("/some/path/ratings.txt"), new Path("/some/path/students_output_mr"), new Configuration());
    }

    protected static void runJob(Path usersPath, Path awesomenessRatingsPath, Path outputPath, Configuration config) throws Exception {
        Job job = new Job(config);
        job.setJarByClass(AwesomeMapReduce.class);
        job.setJobName("Awesome MapReduce job");

        MultipleInputs.addInputPath(job, usersPath, TextInputFormat.class);
        MultipleInputs.addInputPath(job, awesomenessRatingsPath, TextInputFormat.class);

        job.setMapperClass(AwesomenessRatingMapper.class);
        job.setReducerClass(AwesomenessRatingReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(AwesomenessRatingWritable.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileOutputFormat.setOutputPath(job, outputPath);

        if (!job.waitForCompletion(true)) {
            throw new Exception("Awesome MapReduce job failed.");
        }
    }

}
