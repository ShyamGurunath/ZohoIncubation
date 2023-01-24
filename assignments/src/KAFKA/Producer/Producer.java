package KAFKA.Producer;

import KAFKA.KafkaDriver;
import KAFKASTREAMS.models.User;
import KAFKASTREAMS.models.UserPageVisit;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Producer implements Runnable {


    private Properties _properties;

    private Integer _threadNumber;

    private String _topic;

    private Integer _partition;

    private ProducerTypes _type;

    private List<User> _recordsToProduce;

    private boolean isRandomUser;

    private Integer waitUntilNextRecordMs;

    Producer(Integer threadNumber,
                String topic,
             Integer partition,
             ProducerTypes type,
             Integer recordsToProduce,
             boolean isRandomUser,
             Integer waitUntilNextRecordMs
    ) {
        this._threadNumber = threadNumber;
        this._properties  = KafkaDriver.producerProperties();
        this._topic = topic;
        this._partition = partition;
        this._type = type;
        this.isRandomUser = isRandomUser;
        this.waitUntilNextRecordMs = waitUntilNextRecordMs;

        List<User> users = new ArrayList<>();

        for (int i = 1; i < recordsToProduce; i++) {

            User user = new User();
            user.setUserName("");
            List<UserPageVisit> pageVisits = new ArrayList<>();

            for (int y = 1; y <= 5; y++) {
                UserPageVisit pageVisit = new UserPageVisit();
                pageVisit.setPage("Page" + y);
                pageVisit.setServer("Server" + y);
                pageVisit.setPageStartTime(new Date().getTime() - TimeUnit.SECONDS.toMillis(20));
                pageVisit.setPageEndTime(new Date().getTime() - TimeUnit.SECONDS.toMillis(10));
                pageVisits.add(pageVisit);
            }
            user.setPageVisits(pageVisits);
            users.add(user);
        }

        this._recordsToProduce = users.stream().collect(Collectors.toList());
        System.out.println("I am Kafka Producer with Thread - "+ this._threadNumber);
    }



    @Override
    public void run() {
        long startTime = System.nanoTime();
        System.out.println("Started Producing Events - "+this._threadNumber);

            // create the producer
            KafkaProducer<String, User> producer = new KafkaProducer<>(this._properties);

            List<String> keys = new ArrayList<>();
            keys.add("Sathya");
            keys.add("Shanmugam");
            keys.add("Shankar");

            this._recordsToProduce.stream().forEach((recordValue) ->{
                // create a producer record


                String key = isRandomUser ?
                        keys.get(new Random().nextInt(keys.size()))+UUID.randomUUID() :
                        keys.get(new Random().nextInt(keys.size()));
                recordValue.setUserName(key);
                ProducerRecord<String, User> producerRecord =
                        new ProducerRecord<>(this._topic,this._partition,key,recordValue);

                if (this._type == ProducerTypes.ASYNC ) {
                    // send data - asynchronous
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            System.out.println("Event Sent to Topic - " + recordMetadata.topic());
                            System.out.println("Event Sent To Partition - " + recordMetadata.partition());
                            System.out.println("Event Offset - " + recordMetadata.offset());
                            System.out.println("Record Data - Key "+producerRecord.key()+recordValue.getPageVisits());
                            System.out.println("ASYNC SEND - Sent with Producer - "+  Producer.this._threadNumber);
                        }
                    });
                    try {
                        Thread.sleep(waitUntilNextRecordMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("ASYNC SEND - Sent with Producer - "+  Producer.this._threadNumber);

                }
                if (this._type == ProducerTypes.SYNC) {

                    RecordMetadata recordMetadata;
                    try {
                        recordMetadata = producer.send(producerRecord).get();
                        Thread.sleep(waitUntilNextRecordMs);
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