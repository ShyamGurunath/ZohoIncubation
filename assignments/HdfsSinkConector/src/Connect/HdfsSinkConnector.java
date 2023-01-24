package Connect;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HdfsSinkConnector extends SinkConnector {

    private HdfsSinkConnectConfig config;


    @Override
    public void start(Map<String, String> map) {
        config = new HdfsSinkConnectConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return HdfsSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return HdfsSinkConnectConfig.conf();
    }

    @Override
    public String version() {
        return "1";
    }
}
