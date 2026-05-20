package guru.qa.hhapi.tests;

import guru.qa.hhapi.api.AreaApi;
import guru.qa.hhapi.models.Area;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("api.hh.ru — Справочники")
@Feature("/areas (дерево регионов)")
@Owner("sadchill82")
public class AreaTests extends BaseApiTest {

    private final AreaApi areas = new AreaApi();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Дерево /areas содержит Россию (id=113) и непустой список регионов")
    void areasTreeContainsRussia() {
        List<Area> roots = areas.all();

        assertThat(roots).isNotEmpty();

        Area russia = roots.stream()
                .filter(a -> "113".equals(a.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("В дереве /areas нет России (id=113)"));

        assertThat(russia.getName()).isEqualTo("Россия");
        assertThat(russia.getAreas()).as("У России должны быть дочерние регионы").isNotEmpty();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /areas/1 → Москва, parent_id = 113 (Россия)")
    void moscowDetails() {
        Area moscow = areas.byId("1");

        assertThat(moscow.getId()).isEqualTo("1");
        assertThat(moscow.getName()).isEqualTo("Москва");
        assertThat(moscow.getParentId()).isEqualTo("113");
    }
}
