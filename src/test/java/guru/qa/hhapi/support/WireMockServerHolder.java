package guru.qa.hhapi.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

// Один WireMock на JVM. Стартует по первому обращению, стопится shutdown-хуком.
// Маппинги и тела ответов - в src/test/resources/wiremock/{mappings,__files}/
public final class WireMockServerHolder {

    private static volatile WireMockServer INSTANCE;

    private WireMockServerHolder() {
    }

    public static WireMockServer get() {
        if (INSTANCE == null) {
            synchronized (WireMockServerHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = startNew();
                    Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE::stop));
                }
            }
        }
        return INSTANCE;
    }

    private static WireMockServer startNew() {
        WireMockServer server = new WireMockServer(WireMockConfiguration.options()
                .dynamicPort()
                .usingFilesUnderClasspath("wiremock")
                .notifier(new com.github.tomakehurst.wiremock.common.Slf4jNotifier(false)));
        server.start();
        return server;
    }

    public static String baseUrl() {
        return get().baseUrl();
    }
}
