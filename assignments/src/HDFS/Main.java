package HDFS;

import HDFS.HdfsParallel;
import HDFS.HdfsReader;
import HDFS.HdfsWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        // SINGLE THREADED WRITE
//        HdfsWriter _hdfswriter = null;
//        try {
//            _hdfswriter = new HdfsWriter(1);
//            Thread ioWriterThread = new Thread(_hdfswriter);
//            ioWriterThread.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // SINGLE THREADED READ
//            HdfsReader _hdfsreader = null;
//            try {
//                _hdfsreader = new HdfsReader(1);
//                Thread ioReaderThread = new Thread(_hdfsreader);
//                ioReaderThread.start();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

        // Starting with Five Threads
        //for (int i=0;i<=5;i++) {

            // HDFS WRITER MULTITHREADED
//            HdfsWriter _hdfswriter = null;
//            try {
//                _hdfswriter = new HdfsWriter(i);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Thread ioWriterThread = new Thread(_hdfswriter);
//            ioWriterThread.start();

//            // HDFS READER MULTITHREADED
//            HdfsReader _hdfsreader = null;
//            try {
//                _hdfsreader = new HdfsReader(i);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Thread ioReaderThread = new Thread(_hdfsreader);
//            ioReaderThread.start();

        //}

        HdfsParallel hdfsParallel = new HdfsParallel();
        hdfsParallel.run();

    }
}

