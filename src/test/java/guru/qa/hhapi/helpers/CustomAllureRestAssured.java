package guru.qa.hhapi.helpers;

import io.qameta.allure.restassured.AllureRestAssured;

// Allure-фильтр с подключёнными кастомными ftl-шаблонами из src/test/resources/tpl/
public final class CustomAllureRestAssured {

    private CustomAllureRestAssured() {
    }

    public static AllureRestAssured withTemplates() {
        return new AllureRestAssured()
                .setRequestTemplate("http-request.ftl")
                .setResponseTemplate("http-response.ftl");
    }
}
