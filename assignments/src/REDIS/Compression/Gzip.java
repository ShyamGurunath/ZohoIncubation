package REDIS.Compression;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Gzip {

    public static byte[] compress(String input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(input.getBytes());
        gzipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static String decompress(byte[] compressed) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressed));
        InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
        StringBuilder stringBuilder = new StringBuilder();
        int data = inputStreamReader.read();
        while (data != -1) {
            stringBuilder.append((char) data);
            data = inputStreamReader.read();
        }
        return stringBuilder.toString();
    }
}
