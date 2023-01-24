package REDIS.Compression;

import redis.clients.jedis.JedisPooled;
import java.io.IOException;

public class Snappy {

    public static byte[] compress(String input) throws IOException {
        return org.xerial.snappy.Snappy.compress(input);
    }

    public static String uncompress(byte[] input) throws IOException {
        return org.xerial.snappy.Snappy.uncompressString(input);
    }
}
