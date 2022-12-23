package HDFS;


import java.io.IOException;

public class HdfsParallel {


    public void run() {
        try {


            HdfsWriter hdfsWriter = new HdfsWriter(1);
            Thread _hdfsWriterThread = new Thread(hdfsWriter);
            _hdfsWriterThread.start();



            HdfsReader reader = new HdfsReader(1);
            Thread _hdfsReaderThread = new Thread(reader);
            _hdfsReaderThread.start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


