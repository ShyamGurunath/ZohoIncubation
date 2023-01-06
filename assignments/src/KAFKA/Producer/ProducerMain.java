package KAFKA.Producer;

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

            Producer producer = new Producer(1,"userVisit",0, ProducerTypes.

                    ASYNC,25,false,41
            );
            Thread producerThread = new Thread(producer);
            producerThread.start();

//        Producer producer1 = new Producer(2,"userVisit",0, ProducerTypes.
//
//                ASYNC,25
//        );
//        Thread producerThread1 = new Thread(producer1);
//        producerThread1.start();

    }
}
