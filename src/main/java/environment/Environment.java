package environment;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Environment {

    public static Config getConfig() {
        Config config = ConfigFactory.parseFile(new File("src/test/resources/environment.conf"));
        return config.getConfig("env");
    }
}
