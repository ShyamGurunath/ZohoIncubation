package KAFKA;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Consumer implements Runnable {

    private Properties _consumerProperties;

    private Integer _threadNumber;

    private String topic;

    private List<Integer> partitions;

    private String _consmerGroup;

    Consumer(Integer threadNumber,String topic,List<Integer> partitions,String consmerGroup) {
        this._threadNumber = threadNumber;
        this._consmerGroup = consmerGroup;
        this.topic = topic;
        this.partitions = partitions;
        this._consumerProperties = new KafkaDriver().consumerProperties(consmerGroup);
        System.out.println("I am a Kafka Consumer with Thread - "+this._threadNumber);
    }

    @Override
    public void run() {
        System.out.println("Consumer - "+this._threadNumber+" - Started Polling Data ");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(this._consumerProperties);

        // List of Topic Partitions
        List<TopicPartition> topicPartitions;

        try {

            if (this.partitions.isEmpty()){
               consumer.subscribe(Arrays.asList(topic));
            }
            else {

                topicPartitions = this.partitions
                        .stream()
                        .map((partition) -> new TopicPartition(topic, partition))
                        .collect(Collectors.toList());

                System.out.println("Polling From - " + topicPartitions);

                // subscribe consumer to our topic(s)
                consumer.assign(topicPartitions);
            }

            // poll for new data
            while (true) {
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Consumer - "+ this._threadNumber+" Key: " + record.key() + ", Value: " + record.value());
                    System.out.println("Consumer - "+ this._threadNumber+" Partition: " + record.partition() + ", Offset:" + record.offset());
                }
                consumer.commitAsync();
            }

        } catch (WakeupException e) {
            System.out.println("Wakeup exception" + e.getMessage());
            // we ignore this as this is an expected exception when closing a consumer
        } catch (Exception e) {
            System.out.println("Unexpected Exception"+ e.getMessage());
        } finally {
            consumer.close(); // this will also commit the offsets if need be.
            System.out.println("The consumer is now gracefully closed.");
        }
    }
}
