package guru.qa.hhapi.config;

import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public final class Project {

    public static final ApiConfig CONFIG = readConfig();

    private Project() {
    }

    private static ApiConfig readConfig() {
        Map<String, String> ctx = new HashMap<>();
        ctx.put("env", System.getProperty("env", "mock"));
        return ConfigFactory.create(ApiConfig.class, ctx);
    }

    public static boolean isLive() {
        return "live".equalsIgnoreCase(System.getProperty("env", "mock"));
    }

    public static boolean hasHhToken() {
        String t = CONFIG.hhToken();
        return t != null && !t.isBlank() && !t.equals("${HH_TOKEN}");
    }
}
