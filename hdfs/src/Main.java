import HDFS.HdfsReader;
import HDFS.HdfsWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // Starting with Five Threads
        for (int i=0;i<=5;i++) {

            // SINGLE THREADED WRITE
            HdfsWriter _hdfswriter = null;
            try {
                _hdfswriter = new HdfsWriter(i);
                Thread ioWriterThread = new Thread(_hdfswriter);
                ioWriterThread.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // SINGLE THREADED READ
//            HdfsReader _hdfsreader = null;
//            try {
//                _hdfsreader = new HdfsReader(i);
//                Thread ioReaderThread = new Thread(_hdfsreader);
//                ioReaderThread.run();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            // HDFS WRITER MULTITHREADED
//            HdfsWriter _hdfswriter = null;
//            try {
//                _hdfswriter = new HdfsWriter(i);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Thread ioWriterThread = new Thread(_hdfswriter);
//            ioWriterThread.start();

            // HDFS READER MULTITHREADED
//            HdfsReader _hdfsreader = null;
//            try {
//                _hdfsreader = new HdfsReader(i);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Thread ioReaderThread = new Thread(_hdfsreader);
//            ioReaderThread.start();
        }
    }
}

