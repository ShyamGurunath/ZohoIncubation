package REDIS;

import REDIS.Compression.CompressionType;
import REDIS.Compression.Gzip;
import REDIS.Compression.Snappy;
import REDIS.Configs.AppConfig;
import REDIS.Configs.OperationType;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedisClient implements Runnable {

    private OperationType _operationType;

    private Integer _threadNumber;

    private JedisPooled _jedisPool;

    private Integer _expirationTimeSeconds;

    private Integer _writeQuantity;

    private Integer _readQuantity;

    private CompressionType compressionType;

    private Boolean isCompression;

    private AppConfig _appConfig;

    public RedisClient(Integer threadNumber,
                       OperationType operationType,
                       Integer readQuantity,
                       CompressionType compressionType,
                       Boolean isCompression) {
        this._operationType = operationType;
        this._threadNumber = threadNumber;
        this._readQuantity = readQuantity;
        this.compressionType = compressionType;
        this.isCompression = isCompression;
        this._appConfig = new AppConfig();
        this._jedisPool = new JedisPooled("localhost",6379);
        System.out.println("Redis Client Intiated with Thread - "+this._threadNumber+" Operation - "+this._operationType);
    }

    public RedisClient(Integer _threadNumber,
                       OperationType operationType,
                       Integer expirationTimeSeconds,
                       Integer writeQuantity,
                       CompressionType compressionType,
                       Boolean isCompression){
        this._operationType = operationType;
        this._threadNumber = _threadNumber;
        this._writeQuantity = writeQuantity;
        this._expirationTimeSeconds = expirationTimeSeconds;
        this.compressionType = compressionType;
        this.isCompression = isCompression;
        this._jedisPool = new JedisPooled("localhost",6379);
        this._appConfig = new AppConfig();
        System.out.println("Redis Client Intiated with Thread - "+this._threadNumber+
                            " Operation - "+this._operationType+
                            " expirationSeconds - "+this._expirationTimeSeconds+
                            " writeQuantity "+this._writeQuantity);
    }

    @Override
    public void run() {

        // READ
        if (this._operationType == OperationType.READ) {

            try {
                if (compressionType == CompressionType.SNAPPY) {
                    byte[] uncompressed = _jedisPool.lpop("users").getBytes();
                    String uncompressedString = REDIS.Compression.Snappy.uncompress(uncompressed);
                    System.out.println(Arrays.asList(uncompressedString.split(",")));
                }
                if (compressionType == CompressionType.GZIP) {
                    byte[] uncompressed = _jedisPool.lpop("users").getBytes();
                    String uncompressedString = Gzip.decompress(uncompressed);
                    System.out.println(uncompressedString);
                    System.out.println(Arrays.asList(uncompressedString.split(",")));
                }
                else {
                    String data = _jedisPool.lpop("users-unc");
                    System.out.println(Arrays.asList(data.split(",")));
                }
            }
            catch (NullPointerException | IOException r) {
                System.out.println("Null");
            }
        }


        // WRITE
        if (this._operationType == OperationType.WRITE) {
            SetParams params = new SetParams();
            params.ex(this._expirationTimeSeconds);

            List<String> listStrings = new ArrayList<>();

            listStrings.add("url://5030");
            listStrings.add("url://5050");


            for (int i = 0; i < 50; i++) {

                // with Compression
                try {
                    if (compressionType == CompressionType.SNAPPY) {
                        _jedisPool.lpush("users".getBytes(), Snappy.compress(String.join(",", listStrings)));
                    }
                    if (compressionType == CompressionType.GZIP) {
                        _jedisPool.lpush("users".getBytes(), Gzip.compress(String.join(",", listStrings)));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //without Compression
                _jedisPool.lpush("users-unc", String.join(",",listStrings));
            }
            //System.out.println("Writing Data to Redis"+" count - " +i);
            }
            _jedisPool.close();
    }
}
