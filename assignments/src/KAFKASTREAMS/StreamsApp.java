package KAFKASTREAMS;


//import KAFKASTREAMS.QueryServer.Query.QueryAppServer;
import KAFKASTREAMS.Serdes.UsersSerde.UserSerde;
import KAFKASTREAMS.models.User;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StreamsApp {

    public static ReadOnlyWindowStore<String, Long> waitUntilStoreIsQueryable(final KafkaStreams streams) throws InterruptedException {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(AppConfig.STATESTORE,QueryableStoreTypes.windowStore()));
            } catch (InvalidStateStoreException ignored) {
                // store not yet ready for querying
                Thread.sleep(1000);
            }
        }
    }

    public static void query(KafkaStreams streams,ReadOnlyWindowStore<String,Long> store) {

            ReadOnlyWindowStore<String, Long> countStore =
                    store;
            // System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3600)
            Instant timeFrom = Instant.ofEpochMilli(0);
            Instant timeTo = Instant.now(); // now (in processing-time)
            KeyValueIterator<Windowed<String>, Long> iterator = countStore.fetchAll(timeFrom, timeTo);

            List<String> list = new ArrayList<>();

            while (iterator.hasNext()) {
                KeyValue<Windowed<String>, Long> next = iterator.next();
                Windowed<String> windowTimestamp = next.key;
                list.add("Key - "+windowTimestamp+" - Value - "+next.value);
                System.out.println("Count of Key - @" + windowTimestamp+": Value - " + next.value );
            }
            System.out.println(list.size());

    }

    public static void main(String[] args) {


        try {

            StreamsBuilder streamsBuilder = new StreamsBuilder();

           //KeyValueBytesStoreSupplier storeSupplier = Stores.lruMap(AppConfig.STATESTORE,30);


            // Consuming From Sample8
            KStream<String, User> ks0 = streamsBuilder.stream(AppConfig.TOPIC,
                    Consumed.with(UserSerde.String(),UserSerde.UsersPos()));


            // KS0 Counts the number of users by key in a window time
            TimeWindows timeWindows = TimeWindows.of(Duration.ofSeconds(1));

            KTable ks1 = ks0
                    .groupByKey()
                            .windowedBy(timeWindows)
                    .aggregate(
                            User::new,
                            (key, value, aggregate) -> {
                                // update the aggregate value (i.e., the current state) with the new value
                                aggregate.setPageVisits(value.getPageVisits());
                                aggregate.setUserName(key);
                                return aggregate;
                            },Materialized.as(AppConfig.STATESTORE)
                    );

            ks1.toStream().peek((k,v) -> System.out.println("Key - "+k +" : Value - "+ v ));
            //System.out.println(ks1);
//
//            // Printing out the Streams
//            ks0.toStream()
//                    .foreach((k,v) -> {
//                        System.out.println("key - "+k +" : " +v);
//                        System.out.println(k.window().startTime());
//                        System.out.println(k.window().endTime());
//                    });


            // Constructing a Stream
            KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), AppConfig.getStreamProperties());

            // QueryServer
            //QueryAppServer queryServerApp = new QueryAppServer(streams);

            // Catching Exceptions That are Uncaught
            streams.setUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());

            // StateListener Streams
            streams.setStateListener((currentState, prevState) -> {
                System.out.println(currentState);
                if (currentState == KafkaStreams.State.RUNNING) {

                    ReadOnlyWindowStore<String, Long> store;
                    try {
                        store = waitUntilStoreIsQueryable(streams);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    query(streams, store);
//                    try {
//                        queryServerApp.start();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }

                }
                //queryServerApp.setIsActive(currentState == KafkaStreams.State.RUNNING);
            });

            //streams.cleanUp();
            // Starting the Stream
            streams.start();


            //Shutdown Hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                streams.close();
            }));
        } catch (NullPointerException e) {

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}