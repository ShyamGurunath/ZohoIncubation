package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class HdfsSinkTask extends SinkTask {

    public KafkaProducer<String, String> kafkaProducer;

    private ObjectMapper _mapper;

    public static Properties producerProperties() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerProperties.setProperty(ProducerConfig.ACKS_CONFIG,"0");
        producerProperties.setProperty(ProducerConfig.RETRIES_CONFIG,"3");
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"500");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return producerProperties;
    }
    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        System.out.println("Starting HDFS CONNECTOR");
        kafkaProducer = new KafkaProducer(producerProperties());
        _mapper = new ObjectMapper();

    }

    @Override
    public void put(Collection<SinkRecord> collection) {

        collection.stream().forEach((i) -> {
            try {
                ProducerRecord<String,String> record = new ProducerRecord<>("users",1,i.key().toString(),i.value().toString());
                kafkaProducer.send(record).get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void stop() {

    }
}
