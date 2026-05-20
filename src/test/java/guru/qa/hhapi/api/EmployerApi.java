package guru.qa.hhapi.api;

import guru.qa.hhapi.models.Employer;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static guru.qa.hhapi.specs.HhSpecs.ok;
import static guru.qa.hhapi.specs.HhSpecs.request;
import static io.restassured.RestAssured.given;

public class EmployerApi {

    @Step("GET /employers?text={text}")
    public Response search(String text) {
        return given(request())
                .queryParam("text", text)
                .when().get("/employers")
                .then().spec(ok())
                .extract().response();
    }

    @Step("GET /employers/{id}")
    public Employer get(String id) {
        return given(request())
                .when().get("/employers/{id}", id)
                .then().spec(ok())
                .extract().as(Employer.class);
    }
}
