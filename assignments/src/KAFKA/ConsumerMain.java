package KAFKA;

import java.util.Arrays;
import java.util.List;

public class ConsumerMain {

    public static void main(String[] args) {

        // Constants
        String consumerGroup1 = "GROUP-5-Consumers";

        //String consumerGroup2 = "GROUP-7-Consumers";


        // Given Partition

        // CASE 1 - 1 topic - 2 partition - 1 - consumer Group with 2 consumers
        List<Integer> consumers = Arrays.asList(1);

        consumers.stream().forEach((c) -> {
            Consumer consumer = new Consumer(c,"sample6",Arrays.asList(0,1),consumerGroup1);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
        });

        // CASE 2 - 1 topic - 1 partition - 1 - consumer group with 2 consumers

//        List<Integer> consumers = Arrays.asList(1,2);
//
//        consumers.stream().forEach((c) -> {
//            Consumer consumer = new Consumer(c,"sample2",Arrays.asList(0),consumerGroup1);
//            Thread consumerThread = new Thread(consumer);
//            consumerThread.start();
//        });

        // CASE 3 - 1 topic - 1 partition - 2 Consumer Group with 1 consumer each

//        Consumer consumer_group1_1 = new Consumer(1,"sample",0,consumerGroup1);
//        Thread consumerThreadGroup1 = new Thread(consumer_group1_1);
//        consumerThreadGroup1.start();
//
//        Consumer consumer_group2_1 = new Consumer(2,"sample",0,consumerGroup2);
//        Thread consumerThreadGroup2 = new Thread(consumer_group2_1);
//        consumerThreadGroup2.start();


    }
}
