package guru.qa.hhapi.specs;

import guru.qa.hhapi.config.Project;
import guru.qa.hhapi.helpers.CustomAllureRestAssured;
import guru.qa.hhapi.support.WireMockServerHolder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public final class HhSpecs {

    private HhSpecs() {
    }

    public static RequestSpecification request() {
        RequestSpecBuilder b = new RequestSpecBuilder()
                .setBaseUri(baseUri())
                .setAccept(ContentType.JSON)
                .addHeader("HH-User-Agent", Project.CONFIG.userAgent())
                .addFilter(CustomAllureRestAssured.withTemplates())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.PARAMS);

        if (Project.isLive() && Project.hasHhToken()) {
            b.addHeader("Authorization", "Bearer " + Project.CONFIG.hhToken());
        }
        return b.build();
    }

    private static String baseUri() {
        return Project.isLive() ? Project.CONFIG.baseUri() : WireMockServerHolder.baseUrl();
    }

    public static ResponseSpecification ok() {
        return status(200);
    }

    public static ResponseSpecification status(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
