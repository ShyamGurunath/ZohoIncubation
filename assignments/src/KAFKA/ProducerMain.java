package KAFKA;

import java.util.Arrays;
import java.util.List;

public class ProducerMain {

    public static void main(String[] args) {


        // Topic - Sample
//        producers.stream().forEach((p) -> {
//            Producer producer = new Producer(p,"sample",0);
//            producer.run();
//        });

        // Topic - Sample1
//        producers.stream().forEach((p) -> {
//            Producer producer = new Producer(p,"sample1",0);
//            producer.run();
//        });

        // SYNC SEND Topic - Sample4

            Producer producer = new Producer(1,"sample6",0, ProducerTypes.

                    ASYNC,10
            );
            Thread producerThread = new Thread(producer);
            producerThread.start();




    }
}
