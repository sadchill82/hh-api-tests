package guru.qa.hhapi.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.hhapi.models.Area;
import io.qameta.allure.Step;

import java.util.List;

import static guru.qa.hhapi.specs.HhSpecs.ok;
import static guru.qa.hhapi.specs.HhSpecs.request;
import static io.restassured.RestAssured.given;

public class AreaApi {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Step("GET /areas — полное дерево регионов")
    public List<Area> all() {
        try {
            String body = given(request())
                    .when().get("/areas")
                    .then().spec(ok())
                    .extract().asString();
            return MAPPER.readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse /areas response", e);
        }
    }

    @Step("GET /areas/{id}")
    public Area byId(String id) {
        return given(request())
                .when().get("/areas/{id}", id)
                .then().spec(ok())
                .extract().as(Area.class);
    }
}
