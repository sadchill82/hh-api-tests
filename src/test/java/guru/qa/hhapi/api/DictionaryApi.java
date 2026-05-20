package guru.qa.hhapi.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static guru.qa.hhapi.specs.HhSpecs.ok;
import static guru.qa.hhapi.specs.HhSpecs.request;
import static io.restassured.RestAssured.given;

public class DictionaryApi {

    @Step("GET /dictionaries — справочники")
    public Response all() {
        return given(request())
                .when().get("/dictionaries")
                .then().spec(ok())
                .extract().response();
    }
}
