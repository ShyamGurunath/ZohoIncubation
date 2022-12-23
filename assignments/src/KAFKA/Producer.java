package KAFKA;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;

public class Producer implements Runnable {


    private Properties _properties;

    private Integer _threadNumber;

    private String _topic;

    private Integer _partition;

    Producer(Integer threadNumber,String topic,Integer partition) {
        this._threadNumber = threadNumber;
        this._properties  = KafkaDriver.producerProperties();
        this._topic = topic;
        this._partition = partition;
        System.out.println("I am Kafka Producer with Thread - "+ this._threadNumber);
    }



    @Override
    public void run() {
        System.out.println("Started Producing Events - "+this._threadNumber);

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(this._properties);


            // create a producer record
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(this._topic,this._partition,"","Hello World !!!");

            // send data - asynchronous
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("Event Sent to Topic - " + recordMetadata.topic());
                    System.out.println("Event Sent To Partition - " + recordMetadata.partition());
                    System.out.println("Event Offset - " + recordMetadata.offset());
//                System.out.println("Sent Event - "+  Producer.this._threadNumber);
                }
            });

            // flush data - synchronous
            producer.flush();
            // flush and close producer
            producer.close();
    }
}