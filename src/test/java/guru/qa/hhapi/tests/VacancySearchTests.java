package guru.qa.hhapi.tests;

import guru.qa.hhapi.api.VacancyApi;
import guru.qa.hhapi.models.VacanciesResponse;
import guru.qa.hhapi.models.Vacancy;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("api.hh.ru — Вакансии")
@Feature("/vacancies (поиск)")
@Owner("sadchill82")
public class VacancySearchTests extends BaseApiTest {

    private final VacancyApi vacancies = new VacancyApi();

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Поиск вакансий по тексту «Java» возвращает непустой набор")
    void searchByText() {
        VacanciesResponse response = vacancies.search("Java");

        assertThat(response.getFound()).isPositive();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getPerPage()).isPositive();
    }

    @ParameterizedTest(name = "Поиск «{0}» — found > 0, items не пустой")
    @ValueSource(strings = {"QA Automation", "Java Developer", "Python", "DevOps"})
    @Severity(SeverityLevel.NORMAL)
    void parametrizedSearch(String query) {
        VacanciesResponse response = vacancies.search(query);

        assertThat(response.getFound())
                .as("По запросу «%s» должна быть хотя бы одна вакансия", query)
                .isPositive();
        assertThat(response.getItems()).isNotEmpty();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Фильтр по area=1 (Москва) возвращает вакансии только с area.id=1")
    void filterByMoscow() {
        VacanciesResponse response = vacancies.search(Map.of(
                "text", "Java",
                "area", "1",
                "per_page", 20
        ));

        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems())
                .as("Все вакансии должны быть в area=1")
                .allSatisfy(v -> assertThat(v.getArea().getId()).isEqualTo("1"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Фильтр only_with_salary=true: у всех найденных вакансий есть salary")
    void filterOnlyWithSalary() {
        VacanciesResponse response = vacancies.search(Map.of(
                "text", "Java",
                "only_with_salary", true,
                "per_page", 20
        ));

        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems())
                .as("У всех должен быть salary")
                .allSatisfy(v -> assertThat(v.getSalary()).isNotNull());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("per_page=5 ограничивает страницу до 5 элементов")
    void perPageRespected() {
        VacanciesResponse response = vacancies.search(Map.of(
                "text", "Java",
                "per_page", 5
        ));

        assertThat(response.getPerPage()).isEqualTo(5);
        assertThat(response.getItems().size()).isLessThanOrEqualTo(5);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получить детали вакансии по id из выдачи")
    void getVacancyById() {
        VacanciesResponse list = vacancies.search("Java");
        String firstId = list.getItems().get(0).getId();

        Vacancy detail = vacancies.get(firstId);

        assertThat(detail.getId()).isEqualTo(firstId);
        assertThat(detail.getName()).isNotBlank();
        assertThat(detail.getAlternateUrl()).startsWith("https://hh.ru/vacancy/");
    }
}
