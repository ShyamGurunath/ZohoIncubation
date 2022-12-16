package HDFS;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class HdfsWriter implements Runnable{


    private int threadNumber;

    private HdfsDriver driver;

    private FileSystem fileSystem;

    public HdfsWriter(int threadNumber) throws IOException {
        this.threadNumber = threadNumber;
        this.driver = new HdfsDriver();
        fileSystem = FileSystem.get(this.driver.configuration);
        System.out.println("HDFS Connection Successful - "+ this.fileSystem);
    }


    @Override
    public void run() {
            try {
                //Create a path
                String fileName = "output"+this.threadNumber+".txt";

                Path homeDir = fileSystem.getHomeDirectory();
                System.out.println(homeDir);

                Path hdfsWritePath = new Path(homeDir+"/"+fileName);
                System.out.println(hdfsWritePath);

                // Intialize the HDFSOutputSream
                FSDataOutputStream fsDataOutputStream = fileSystem.create(hdfsWritePath,true);

                // BufferWriter
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
                bufferedWriter.write("HDFS Sample Data " + "- threadNumber -- " +this.threadNumber);
                bufferedWriter.newLine();
                bufferedWriter.close();

                fileSystem.close();
                System.out.println("Writing on HDFS with ThreadNumber -- "+this.threadNumber);
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
        }
    }
}

