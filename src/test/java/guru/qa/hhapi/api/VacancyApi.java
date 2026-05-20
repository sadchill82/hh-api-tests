package guru.qa.hhapi.api;

import guru.qa.hhapi.models.VacanciesResponse;
import guru.qa.hhapi.models.Vacancy;
import io.qameta.allure.Step;

import java.util.Map;

import static guru.qa.hhapi.specs.HhSpecs.ok;
import static guru.qa.hhapi.specs.HhSpecs.request;
import static io.restassured.RestAssured.given;

public class VacancyApi {

    @Step("GET /vacancies?text={text}")
    public VacanciesResponse search(String text) {
        return given(request())
                .queryParam("text", text)
                .when().get("/vacancies")
                .then().spec(ok())
                .extract().as(VacanciesResponse.class);
    }

    @Step("GET /vacancies (custom params: {params})")
    public VacanciesResponse search(Map<String, ?> params) {
        return given(request())
                .queryParams(params)
                .when().get("/vacancies")
                .then().spec(ok())
                .extract().as(VacanciesResponse.class);
    }

    @Step("GET /vacancies/{id}")
    public Vacancy get(String id) {
        return given(request())
                .when().get("/vacancies/{id}", id)
                .then().spec(ok())
                .extract().as(Vacancy.class);
    }
}
