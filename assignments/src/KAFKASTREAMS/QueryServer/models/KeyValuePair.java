package KAFKASTREAMS.QueryServer.models;

import org.apache.kafka.streams.kstream.Windowed;

public class KeyValuePair {
    private Windowed<String> key;
    private Long value;

    public KeyValuePair(Windowed<String> key, Long value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters for key and value
}
