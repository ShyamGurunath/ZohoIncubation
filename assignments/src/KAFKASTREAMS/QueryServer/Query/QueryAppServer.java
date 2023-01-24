//package KAFKASTREAMS.QueryServer.Query;
//
//import KAFKASTREAMS.AppConfig;
//import KAFKASTREAMS.QueryServer.models.KeyValuePair;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.util.JacksonFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.StoreQueryParameters;
//import org.apache.kafka.streams.kstream.Windowed;
//import org.apache.kafka.streams.state.KeyValueIterator;
//import org.apache.kafka.streams.state.QueryableStoreTypes;
//import org.apache.kafka.streams.state.ReadOnlyWindowStore;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.ServerConnector;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.servlet.ServletContainer;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Path("/count")
//public class QueryAppServer {
//
//
//    private KafkaStreams streams;
//
//    private Server jettyServer;
//    private Boolean isActive = false;
//
//    public QueryAppServer(KafkaStreams kafkaStreams) {
//        this.streams = kafkaStreams;
//    }
//
//    public void setIsActive(Boolean isActive) {
//        this.isActive = isActive;
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Object allUsersCount() throws JsonProcessingException {
//        ReadOnlyWindowStore<String, Long> countStore =
//                this.streams
//                        .store(StoreQueryParameters
//                                .fromNameAndType(AppConfig.STATESTORE,
//                                        QueryableStoreTypes.windowStore()));
//        Instant timeFrom = Instant.ofEpochMilli(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3600));
//        Instant timeTo = Instant.now(); // now (in processing-time)
//        KeyValueIterator<Windowed<String>, Long> iterator = countStore.fetchAll(timeFrom, timeTo);
//        List<KeyValuePair> keyValuePairs = new ArrayList<>();
//        while (iterator.hasNext()) {
//            KeyValue<Windowed<String>, Long> next = iterator.next();
//            Windowed<String> windowTimestamp = next.key;
//            keyValuePairs.add(new KeyValuePair(windowTimestamp, next.value));
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(keyValuePairs);
//        return json;
//    }
//
//    public void start() throws Exception {
//        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//
//        jettyServer = new Server();
//        jettyServer.setHandler(context);
//
//        final ResourceConfig rc = new ResourceConfig();
//        rc.register(this);
//        rc.register(JacksonFeature.class);
//
//        final ServletContainer sc = new ServletContainer(rc);
//        final ServletHolder holder = new ServletHolder(sc);
//        context.addServlet(holder, "/*");
//
//        final ServerConnector connector = new ServerConnector(jettyServer);
//        connector.setHost("localhost");
//        connector.setPort(8080);
//        jettyServer.addConnector(connector);
//
//        context.start();
//
//        try {
//            jettyServer.start();
//        } catch (final java.net.SocketException exception) {
//            System.out.println(exception.toString());
//            throw new Exception(exception.toString());
//        }
//    }
//
//
//        public void stop() throws Exception {
//            if (jettyServer != null) {
//                jettyServer.stop();
//            }
//        }
//
//
//}
