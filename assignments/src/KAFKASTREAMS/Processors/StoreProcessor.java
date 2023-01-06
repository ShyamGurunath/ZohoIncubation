package KAFKASTREAMS.Processors;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import KAFKASTREAMS.models.User;


public class StoreProcessor implements Processor<String,User,String,User> {
    private ProcessorContext context;




    @Override
    public void process(Record<String,User> record) {
        context.forward(record);
    }


    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void close() {
        // Close any resources here
    }
}
