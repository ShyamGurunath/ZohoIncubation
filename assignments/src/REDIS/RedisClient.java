package REDIS;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

public class RedisClient implements Runnable {

    private OperationType _operationType;

    private Integer _threadNumber;

    private JedisPooled _jedisPool;

    private Integer _expirationTimeSeconds;

    private Integer _writeQuantity;

    private Integer _readQuantity;

    public RedisClient(Integer threadNumber,OperationType operationType,
                       Integer readQuantity) {
        this._operationType = operationType;
        this._threadNumber = threadNumber;
        this._readQuantity = readQuantity;
        this._jedisPool = new JedisPooled("localhost",6379);
        System.out.println("Redis Client Intiated with Thread - "+this._threadNumber+" Operation - "+this._operationType);
    }

    public RedisClient(Integer _threadNumber,OperationType operationType,
                       Integer expirationTimeSeconds,Integer writeQuantity){
        this._operationType = operationType;
        this._threadNumber = _threadNumber;
        this._writeQuantity = writeQuantity;
        this._expirationTimeSeconds = expirationTimeSeconds;
        this._jedisPool = new JedisPooled("localhost",6379);
        System.out.println("Redis Client Intiated with Thread - "+this._threadNumber+
                            " Operation - "+this._operationType+
                            " expirationSeconds - "+this._expirationTimeSeconds+
                            " writeQuantity "+this._writeQuantity);
    }

    @Override
    public void run() {

        if (this._operationType == OperationType.READ) {
            for (int i = 0; i < this._readQuantity; i++) {
                System.out.println(this._jedisPool.hgetAll("Sample"+i));
            }
        }
        if (this._operationType == OperationType.WRITE) {
            SetParams params = new SetParams();
            params.ex(this._expirationTimeSeconds);
            for (int i = 0; i < this._writeQuantity; i++) {
                this._jedisPool.hset("Sample" + i,"data","Sample Data"+i);
                        //set("Sample"+i,"SAMPLE WRITE -"+i,params);
                System.out.println("Writing Data to Redis"+" count - " +i);
            }
        }


    }
}
