package com.codingserbia;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class CodingSerbiaMapReduce {

    public static void main(String[] args) throws Exception {
        runJob(new Path("/customer_records.json"), new Path("/customers_output_mr"), new Configuration());
    }

    protected static void runJob(Path customers, Path outputPath, Configuration config) throws Exception {
        config.set("mapreduce.output.textoutputformat.separator", "\t");
        Job job = new Job(config);
        job.setJarByClass(CodingSerbiaMapReduce.class);
        job.setJobName("CodingSerbia MapReduce job");

        // MultipleInputs.addInputPath(job, categories, TextInputFormat.class, CustomerCategoriesMapper.class);
        MultipleInputs.addInputPath(job, customers, TextInputFormat.class, CustomerRecordsMapper.class);

        // job.setMapperClass(CustomerCategoriesMapper.class);
        job.setReducerClass(CustomerRecordsReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileOutputFormat.setOutputPath(job, outputPath);

        if (!job.waitForCompletion(true)) {
            throw new Exception("CodingSerbia MapReduce job failed.");
        }
    }

}
