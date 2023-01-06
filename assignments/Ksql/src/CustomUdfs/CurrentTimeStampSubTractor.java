package CustomUdfs;

import io.confluent.ksql.function.udf.*;

import java.util.concurrent.TimeUnit;

@UdfDescription(name = "currenttimestampsubtractor", description = "subtracts the currenttimestamp with given interval")
public class CurrentTimeStampSubTractor {

        @Udf(description = "subtracts the currenttimestamp with given interval")
        public long current_timestamp_subtractor(
                @UdfParameter(value = "Interval", description = "Interval to subtract from currenttime") final int Interval) {
            return System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(100);
        }

}
