package REDIS.Configs;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {

    public String users = "users";

    public String users_uncompressed = "user-uncompressed";


    public static List<String> getListOfStrings() {
        List<String> listStrings = new ArrayList<>();
        listStrings.add("url://5030");
        listStrings.add("url://5050");
        return listStrings;
    }

}
