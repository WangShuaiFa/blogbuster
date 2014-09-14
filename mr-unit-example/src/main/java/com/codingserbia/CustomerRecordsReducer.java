package com.codingserbia;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CustomerRecordsReducer extends Reducer<LongWritable, Text, LongWritable, Text> {

    public CustomerRecordsReducer() {
        super();
    }

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        System.out.println("Reducer");
        for (Text value : values) {
            System.out.println(key.toString() + "###" + value.toString());
        }
    }

}
