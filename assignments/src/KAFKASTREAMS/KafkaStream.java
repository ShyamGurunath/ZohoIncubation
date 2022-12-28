package KAFKASTREAMS;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class KafkaStream {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"word-count-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"10.252.1.7:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder
                .<String,String>stream("sample6")
                .flatMapValues((readOnlyKey,value) -> Arrays.asList(value.toLowerCase().split(" ")))
                .foreach((k,j) -> {
                    System.out.println("Hello");
                    System.out.println(k);
                    System.out.println(j);
                });

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),props);

        System.out.println("Starting Stream");

        kafkaStreams.start();

        System.out.println("Streaming");


        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

}
