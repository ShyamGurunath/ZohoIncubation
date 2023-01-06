package PullPushQueries;

import io.confluent.ksql.api.client.*;

import java.util.concurrent.ExecutionException;

public class PushQuery {

    public static String KSQLDB_SERVER_HOST = "localhost";
    public static int KSQLDB_SERVER_HOST_PORT = 8088;

    public static void main(String[] args) throws InterruptedException {

        // Server Config
        ClientOptions options = ClientOptions.create()
                .setHost(KSQLDB_SERVER_HOST)
                .setPort(KSQLDB_SERVER_HOST_PORT);
        Client client = Client.create(options);

        StreamedQueryResult streamedQueryResult = null;

        try {
            // EMITING CHANGES FROM THE TABLE
            streamedQueryResult =
                    client.streamQuery
                            ("SELECT COUNT(*),userName FROM pageVisitTable Group By userName EMIT CHANGES;").get();
        } catch (ExecutionException e) {
            Runtime.getRuntime().addShutdownHook(new Thread(client::close));
            throw new RuntimeException(e);
        }


        while (true) {
            // Block until a new row is available
            Row row = streamedQueryResult.poll();
            if (row != null) {
                System.out.println("Received a row!");
                System.out.println("Row: " + row.values());
                Thread.sleep(100);
            }
        }
    }
}
