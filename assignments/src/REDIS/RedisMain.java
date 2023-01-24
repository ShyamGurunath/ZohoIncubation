package REDIS;

import REDIS.Compression.CompressionType;
import REDIS.Configs.OperationType;

import java.util.Arrays;

public class RedisMain {

    public static void main(String[] args) {
         //Write
        Arrays.asList(1).stream().forEach((thread) -> {
            RedisClient _writeClient = new RedisClient(
                                            thread,
                                            OperationType.WRITE,
                                    50,
                                            thread == 2 ? 1 :1,
                                            CompressionType.SNAPPY,
                                            true);
            Thread _writeThread = new Thread(_writeClient);
            _writeThread.start();
        });

         //Read
//        Arrays.asList(1).stream().forEach((thread) -> {
//            RedisClient _readClient = new RedisClient(thread,OperationType.READ,1,CompressionType.SNAPPY,true);
//            Thread _readThread = new Thread(_readClient);
//            _readThread.start();
//        });

    }
}
