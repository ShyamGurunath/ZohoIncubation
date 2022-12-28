package KAFKA;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


public class Producer implements Runnable {


    private Properties _properties;

    private Integer _threadNumber;

    private String _topic;

    private Integer _partition;

    private ProducerTypes _type;

    private List<String> _recordsToProduce;

    Producer(Integer threadNumber, String topic, Integer partition, ProducerTypes type, Integer recordsToProduce) {
        this._threadNumber = threadNumber;
        this._properties  = KafkaDriver.producerProperties();
        this._topic = topic;
        this._partition = partition;
        this._type = type;
        this._recordsToProduce = new ArrayList<>(Collections.nCopies(recordsToProduce,0)).stream().map((i) -> "Sample Data").collect(Collectors.toList());
        System.out.println("I am Kafka Producer with Thread - "+ this._threadNumber);
    }



    @Override
    public void run() {
        long startTime = System.nanoTime();
        System.out.println("Started Producing Events - "+this._threadNumber);

            // create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(this._properties);

            this._recordsToProduce.stream().forEach((recordValue) ->{
                // create a producer record
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(this._topic,this._partition,"word-count-app",recordValue);

                if (this._type == ProducerTypes.ASYNC ) {
                    // send data - asynchronous
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            System.out.println("Event Sent to Topic - " + recordMetadata.topic());
                            System.out.println("Event Sent To Partition - " + recordMetadata.partition());
                            System.out.println("Event Offset - " + recordMetadata.offset());
                            System.out.println("ASYNC SEND - Sent with Producer - "+  Producer.this._threadNumber);
                        }
                    });

                    System.out.println("ASYNC SEND - Sent with Producer - "+  Producer.this._threadNumber);

                }
                if (this._type == ProducerTypes.SYNC) {

                    RecordMetadata recordMetadata;
                    try {
                        recordMetadata = producer.send(producerRecord).get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("SYNC SEND - Sent with Producer - "+ Producer.this._threadNumber);
                    System.out.println("Event Sent to Topic - " + recordMetadata.topic());
                    System.out.println("Event Sent To Partition - " + recordMetadata.partition());
                    System.out.println("Event Offset - " + recordMetadata.offset());
                }
            });

                long endTime = System.nanoTime();
                // flush data - synchronous
                producer.flush();
                // flush and close producer
                producer.close();

                long time = (endTime - startTime) / 1000000;

                System.out.println(this._type + " - Producer - " +Producer.this._threadNumber + "- topic " + this._topic +" - partition -"+ this._partition + " - took - " + time+" ms");

    }
}