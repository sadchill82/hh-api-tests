package guru.qa.hhapi.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static guru.qa.hhapi.specs.HhSpecs.request;
import static guru.qa.hhapi.specs.HhSpecs.status;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// Проверяют конкретные shape ответов hh.ru на ошибочные запросы.
// На живом API структура ошибок может отличаться, поэтому гоняем только в mock-режиме.
@Epic("api.hh.ru — Негативные кейсы")
@Feature("Обработка ошибок")
@Owner("sadchill82")
@DisabledIfSystemProperty(named = "env", matches = "live", disabledReason = "Mock-only error-shape assertions")
public class NegativeApiTests extends BaseApiTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /vacancies/0 → 404 not_found")
    void vacancyNotFound() {
        given(request())
                .when().get("/vacancies/0")
                .then().spec(status(404))
                .body("errors[0].type", equalTo("not_found"))
                .body("errors[0].value", equalTo("vacancy_not_found"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /vacancies?per_page=999 → 400 bad_argument")
    void invalidPerPage() {
        given(request())
                .queryParam("per_page", 999)
                .when().get("/vacancies")
                .then().spec(status(400))
                .body("errors[0].type", equalTo("bad_argument"))
                .body("errors[0].value", equalTo("per_page"));
    }
}
