package HDFS;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class HdfsReader implements Runnable {

    private int threadNumber;

    private HdfsDriver driver;

    private FileSystem fileSystem;

    public HdfsReader(int threadNumber) throws IOException {
        this.threadNumber = threadNumber;
        this.driver = new HdfsDriver();
        fileSystem = FileSystem.get(this.driver.configuration);
        System.out.println("HDFS Connection Successful - "+ this.fileSystem);
    }


    @Override
    public void run() {

            try {
                // File path
                String fileName = "output"+".txt";

                Path hdfsReadPath = new Path(fileSystem.getHomeDirectory()+"/shyam/"+fileName);
                //Initialize HDFS input stream
                FSDataInputStream inputStream = fileSystem.open(hdfsReadPath);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line = null;
                while ((line=bufferedReader.readLine())!=null){
                        System.out.println(line);
                }
                inputStream.close();
                fileSystem.close();
                System.out.println("Reading on Thread with ThreadNumber -- "+this.threadNumber);
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e){
                System.out.println(e.getMessage());
            }
    }
}
