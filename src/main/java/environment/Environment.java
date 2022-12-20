package environment;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Environment {

    private static String config = "";

    public static Config setConfig(String conf) {
        config = conf;
        Config config = ConfigFactory.parseFile(new File("src/test/resources/environment.conf"));
        Config all = config.getConfig("env.all");
        return config.getConfig("env." + conf).withFallback(all).resolve();
    }

    public static Config getConfig() {
        if (config.isEmpty()) {
            throw new RuntimeException("please provide config name from src/test/resources/environment.conf file");
        } else {
            return setConfig(config);
        }
    }
}
