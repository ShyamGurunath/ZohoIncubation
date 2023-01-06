package PullPushQueries;

import io.confluent.ksql.api.client.BatchedQueryResult;
import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;
import io.confluent.ksql.api.client.Row;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PullQuery {

    public static String KSQLDB_SERVER_HOST = "localhost";
    public static int KSQLDB_SERVER_HOST_PORT = 8088;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Server Config
        ClientOptions options = ClientOptions.create()
                .setHost(KSQLDB_SERVER_HOST)
                .setPort(KSQLDB_SERVER_HOST_PORT);
        Client client = Client.create(options);


        // Pull Query with "where"
        BatchedQueryResult batchedQueryResult = client.
                                                executeQuery(
                                                        "SELECT * FROM pageVisitTable " +
                                                                "WHERE userName LIKE 'Sat%';");

        // get the whole batch result from query
        List<Row> resultRows = batchedQueryResult.get();


        System.out.println("Received results. Num rows: " + resultRows.size());
        for (Row row : resultRows) {
            System.out.println("Row: " + row.values());
        }

        client.close();
    }
}
