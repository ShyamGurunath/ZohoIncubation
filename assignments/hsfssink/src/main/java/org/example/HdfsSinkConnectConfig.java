package org.example;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class HdfsSinkConnectConfig extends AbstractConfig {


    public static String HDFS_CLUSTER_ID="SAMPLECLUSTER";

    public HdfsSinkConnectConfig(ConfigDef definition, Map<String, String> originals) {
        super(definition, originals);
    }

    public HdfsSinkConnectConfig(Map<String,String> parsedConfig) {
        this(conf(),parsedConfig);
    }


    public static ConfigDef conf() {
        return new ConfigDef()
                .define(HDFS_CLUSTER_ID, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH,"DOcs");

    }
}


