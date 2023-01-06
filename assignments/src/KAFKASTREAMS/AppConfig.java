package KAFKASTREAMS;

import KAFKASTREAMS.Serdes.UsersSerde.UserSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.rocksdb.RocksDB;

import java.util.Properties;

public class AppConfig {

    public static final String STATESTORE = "Usere";
    public static final Integer STORE_RECORDS_RETENTION_PERIOD_MS = 1000;

    public static final String TOPIC = "userVisit";

    public static Properties getStreamProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "UserPageVisitStreamapp");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG,0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG,1);
        props.put(StreamsConfig.APPLICATION_SERVER_CONFIG,"localhost:8080");
        props.put(StreamsConfig.WINDOW_STORE_CHANGE_LOG_ADDITIONAL_RETENTION_MS_CONFIG,100);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,UserSerde.UsersPos().getClass());

        return props;
    }


}
