package stubs;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvgWordLength extends Configured implements Tool{

  public static void main(String[] args) throws Exception {
	
	// use toolrunner to run the job
    int exitCode = ToolRunner.run(new Configuration(), new AvgWordLength(), args); 
    System.exit(exitCode); 
  }

  public int run(String[] args) throws Exception {
	// TODO Auto-generated method stub
	/*
     * Validate that two arguments were passed from the command line.
     */
    if (args.length != 2) {
      System.out.printf("Usage: AvgWordLength <input dir> <output dir>\n", getClass().getSimpleName());
      return -1;
    }

    /*
     * Instantiate a Job object for your job's configuration. 
     */
    Job job = new Job(getConf());
    
    /*
     * Specify the jar file that contains your driver, mapper, and reducer.
     * Hadoop will transfer this jar file to nodes in your cluster running 
     * mapper and reducer tasks.
     */
    job.setJarByClass(AvgWordLength.class);
    
    /*
     * Specify an easily-decipherable name for the job.
     * This job name will appear in reports and logs.
     */
    job.setJobName("Average Word Length");

    /*
     * Specify the paths to the input and output data based on the
     * command-line arguments.
     */
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    /*
     * Specify the mapper and reducer classes.
     */
    job.setMapperClass(LetterMapper.class);
    job.setReducerClass(AverageReducer.class);
    
    /*
     * Specify the mapper key and value output type
     */
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    
    /*
     * Specify the job key and value output type
     */
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(DoubleWritable.class);
    
    /*
     * Start the MapReduce job and wait for it to finish.
     * If it finishes successfully, return 0. If not, return 1.
     */
    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }
}

