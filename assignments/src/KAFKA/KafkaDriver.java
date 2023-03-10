package KAFKA;

import KAFKASTREAMS.Serdes.JsonDeserializer;
import KAFKASTREAMS.Serdes.JsonSerializer;
import KAFKASTREAMS.models.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;

import java.util.Properties;

public class KafkaDriver {

    private static String bootstrapServers = "127.0.0.1:9092,127.0.0.1:9093";

    public static Properties consumerProperties(String consumerGroup) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, User.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return consumerProperties;
    }

    public static Properties producerProperties() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.setProperty(ProducerConfig.ACKS_CONFIG,"0");
//        producerProperties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"3");
        producerProperties.setProperty(ProducerConfig.RETRIES_CONFIG,"3");
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"500");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return producerProperties;
    }


}
