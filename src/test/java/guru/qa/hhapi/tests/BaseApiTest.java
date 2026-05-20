package guru.qa.hhapi.tests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import guru.qa.hhapi.support.WireMockServerHolder;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    @BeforeAll
    static void configure() {
        RestAssured.baseURI = WireMockServerHolder.baseUrl();
        RestAssured.config = RestAssured.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                        (type, charset) -> new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                )
        );
    }
}
