package HDFS;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

public class HdfsDriver {

    public static Configuration configuration;

    public HdfsDriver() throws IOException {
        configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        System.out.println("Config Successful");
    }

}
