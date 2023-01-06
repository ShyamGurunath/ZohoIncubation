package KAFKASTREAMS.Serdes.UsersSerde;

import KAFKASTREAMS.Serdes.JsonDeserializer;
import KAFKASTREAMS.Serdes.JsonSerializer;
import KAFKASTREAMS.models.User;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import java.util.HashMap;
import java.util.Map;

public class UserSerde extends Serdes {


    static public final class UsersSerde extends WrapperSerde<User> {
        public UsersSerde() {
            super(new JsonSerializer<>(),new JsonDeserializer<>());
        }
    }

    static public Serde UsersPos() {
        UsersSerde usersSerde = new UsersSerde();

        Map<String,Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, User.class);
        usersSerde.configure(serdeConfigs,false);

        return usersSerde;
    }
}
