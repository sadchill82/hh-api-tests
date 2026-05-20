package guru.qa.hhapi.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config/${env}.properties",
        "classpath:config/prod.properties"
})
public interface ApiConfig extends Config {

    @Key("baseUri")
    @DefaultValue("https://api.hh.ru")
    String baseUri();

    @Key("userAgent")
    @DefaultValue("hh-api-tests/1.0 (qa-guru diploma)")
    String userAgent();

    @Key("timeoutMs")
    @DefaultValue("15000")
    int timeoutMs();

    @Key("hhToken")
    @DefaultValue("${HH_TOKEN}")
    String hhToken();
}
