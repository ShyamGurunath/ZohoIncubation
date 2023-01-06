package REDIS;

import java.util.Arrays;

public class RedisMain {

    public static void main(String[] args) {
         //Write
        Arrays.asList(1,2).stream().forEach((thread) -> {
            RedisClient _writeClient = new RedisClient(1,OperationType.WRITE,1000,thread == 2 ? 1000 :500);
            Thread _writeThread = new Thread(_writeClient);
            _writeThread.start();
        });

        // Read
        Arrays.asList(1,2).stream().forEach((thread) -> {
            RedisClient _readClient = new RedisClient(1,OperationType.READ,1000);
            Thread _readThread = new Thread(_readClient);
            _readThread.start();
        });

    }
}
