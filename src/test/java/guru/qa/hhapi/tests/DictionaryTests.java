package guru.qa.hhapi.tests;

import guru.qa.hhapi.api.DictionaryApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;

@Epic("api.hh.ru — Справочники")
@Feature("/dictionaries")
@Owner("sadchill82")
public class DictionaryTests extends BaseApiTest {

    private final DictionaryApi dict = new DictionaryApi();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Справочник опыта содержит noExperience, between1And3, between3And6, moreThan6")
    void experienceValues() {
        Response res = dict.all();

        List<String> ids = res.jsonPath().getList("experience.id");
        assertThat(ids).contains("noExperience", "between1And3", "between3And6", "moreThan6");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Справочник содержит формы занятости full, part, project")
    void employmentValues() {
        dict.all().then().body("employment.id", hasItems("full", "part", "project"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Справочник содержит валюты RUR, USD, EUR")
    void currencyValues() {
        Response res = dict.all();
        List<String> codes = res.jsonPath().getList("currency.code");
        assertThat(codes).contains("RUR", "USD", "EUR");
    }
}
