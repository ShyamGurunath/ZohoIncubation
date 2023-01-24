package Connect;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.util.Collection;
import java.util.Map;

public class HdfsSinkTask extends SinkTask {
    @Override
    public String version() {
        return "1";
    }

    @Override
    public void start(Map<String, String> map) {

    }

    @Override
    public void put(Collection<SinkRecord> collection) {

        collection.stream().forEach((i) -> {
            System.out.println(i);
        });
    }

    @Override
    public void stop() {

    }
}
