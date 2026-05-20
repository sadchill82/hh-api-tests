package guru.qa.hhapi.tests;

import guru.qa.hhapi.api.EmployerApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("api.hh.ru — Работодатели")
@Feature("/employers")
@Owner("sadchill82")
public class EmployerTests extends BaseApiTest {

    private final EmployerApi employers = new EmployerApi();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Поиск «Яндекс» возвращает >= 1 работодателя")
    void searchYandex() {
        Response res = employers.search("Яндекс");
        Integer found = res.path("found");

        assertThat(found).isPositive();
    }
}
