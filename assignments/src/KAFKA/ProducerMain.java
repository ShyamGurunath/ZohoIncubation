package KAFKA;

import java.util.Arrays;
import java.util.List;

public class ProducerMain {

    public static void main(String[] args) {


        List<Integer> producers = Arrays.asList(1,2,3);

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

        // Topic - Sample2
        producers.stream().forEach((p) -> {
            Producer producer = new Producer(p,"sample2",0);
            producer.run();
        });

    }
}
