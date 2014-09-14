package com.codingserbia;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CustomerCategoriesMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

    public CustomerCategoriesMapper() {
        super();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println(key.toString());
        System.out.println(value.toString());
    }

}
