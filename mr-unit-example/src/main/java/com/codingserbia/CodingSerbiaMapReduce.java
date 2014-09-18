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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodingSerbiaMapReduce {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodingSerbiaMapReduce.class);

    protected String inputPath = "";
    protected String outputPath = "";

    public static void main(String[] args) throws Exception {
        CodingSerbiaMapReduce mr = new CodingSerbiaMapReduce();

        if (mr.validateAndParseInput(args)) {
            mr.runJob(new Configuration());
        }
    }

    protected boolean validateAndParseInput(String[] args) {
        if (args == null || args.length < 2) {
            LOGGER.error("Two arguments are required: path to input data and path to desired output directory.");
            return false;
        }

        if (args.length > 2) {
            LOGGER.error("Too many arguments. Only two arguments are required: path to input data and path to desired output directory.");
            return false;
        }

        inputPath = args[0];
        outputPath = args[1];

        return true;
    }

    private void runJob(Configuration config) throws Exception {
        config.set("mapreduce.output.textoutputformat.separator", "\t");

        Job job = new Job(config);
        job.setJarByClass(CodingSerbiaMapReduce.class);
        job.setJobName("CodingSerbia MapReduce job");

        MultipleInputs.addInputPath(job, new Path(inputPath), TextInputFormat.class, CustomerRecordsMapper.class);

        job.setReducerClass(CustomerRecordsReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        if (!job.waitForCompletion(true)) {
            throw new Exception("CodingSerbia MapReduce job failed.");
        }
    }

}
