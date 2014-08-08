package ozren.gulan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class MapReduce {

    public static void main(String[] args) throws Exception {
        runJob(new Path("/some/path/students_L.txt"), new Path("/some/path/ratings_L.txt"), new Path(
                        "/results/ratings_java_L"), new Configuration());
    }

    @SuppressWarnings("deprecation")
    protected static void runJob(Path studentsPath, Path ratingsPath, Path outputPath, Configuration config)
                    throws Exception {
        Job job = new Job(config);
        job.setJarByClass(MapReduce.class);
        job.setJobName("MapReduce job");

        MultipleInputs.addInputPath(job, studentsPath, TextInputFormat.class);
        MultipleInputs.addInputPath(job, ratingsPath, TextInputFormat.class);

        job.setMapperClass(RatingMapper.class);
        job.setReducerClass(RatingReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(RatingWritable.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        FileOutputFormat.setOutputPath(job, outputPath);

        if (!job.waitForCompletion(true)) {
            throw new Exception("MapReduce job failed.");
        }
    }

}
