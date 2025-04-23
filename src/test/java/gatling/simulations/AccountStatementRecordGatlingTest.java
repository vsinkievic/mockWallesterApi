package gatling.simulations;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.headerRegex;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 * Performance test for the AccountStatementRecord entity.
 *
 * @see <a href="https://github.com/jhipster/generator-jhipster/tree/v8.10.0/generators/gatling#logging-tips">Logging tips</a>
 */
public class AccountStatementRecordGatlingTest extends Simulation {

    String baseURL = Optional.ofNullable(System.getProperty("baseURL")).orElse("http://localhost:8080");

    HttpProtocolBuilder httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources(); // Silence all resources like css or css so they don't clutter the results

    Map<String, String> headersHttp = Map.of("Accept", "application/json");

    Map<String, String> headersHttpAuthentication = Map.of("Content-Type", "application/json", "Accept", "application/json");

    Map<String, String> headersHttpAuthenticated = Map.of("Accept", "application/json", "Authorization", "${access_token}");

    ChainBuilder scn = exec(http("First unauthenticated request").get("/api/account").headers(headersHttp).check(status().is(401)))
        .exitHereIfFailed()
        .pause(10)
        .exec(
            http("Authentication")
                .post("/api/authenticate")
                .headers(headersHttpAuthentication)
                .body(StringBody("{\"username\":\"admin\", \"password\":\"admin\"}"))
                .asJson()
                .check(header("Authorization").saveAs("access_token"))
        )
        .exitHereIfFailed()
        .pause(2)
        .exec(http("Authenticated request").get("/api/account").headers(headersHttpAuthenticated).check(status().is(200)))
        .pause(10)
        .repeat(2)
        .on(
            exec(
                http("Get all accountStatementRecords")
                    .get("/api/account-statement-records")
                    .headers(headersHttpAuthenticated)
                    .check(status().is(200))
            )
                .pause(Duration.ofSeconds(10), Duration.ofSeconds(20))
                .exec(
                    http("Create new accountStatementRecord")
                        .post("/api/account-statement-records")
                        .headers(headersHttpAuthenticated)
                        .body(
                            StringBody(
                                "{" +
                                "\"cardId\": null" +
                                ", \"type\": \"AccountAdjustment\"" +
                                ", \"group\": \"AccountClosureFee\"" +
                                ", \"date\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"transactionAmount\": 0" +
                                ", \"transactionCurrencyCode\": \"AED\"" +
                                ", \"accountAmount\": 0" +
                                ", \"accountCurrencyCode\": \"AED\"" +
                                ", \"merchantCategoryCode\": \"MCC0742\"" +
                                ", \"merchantId\": \"SAMPLE_TEXT\"" +
                                ", \"terminalId\": \"SAMPLE_TEXT\"" +
                                ", \"merchantName\": \"SAMPLE_TEXT\"" +
                                ", \"merchantCity\": \"SAMPLE_TEXT\"" +
                                ", \"merchantCountryCode\": \"ABW\"" +
                                ", \"description\": \"SAMPLE_TEXT\"" +
                                ", \"originalAuthorizationId\": \"SAMPLE_TEXT\"" +
                                ", \"isReversal\": null" +
                                ", \"isDeclined\": null" +
                                ", \"isCleared\": null" +
                                ", \"markedForDisputeAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"markedForDisputeBy\": \"SAMPLE_TEXT\"" +
                                ", \"status\": \"Canceled\"" +
                                ", \"response\": \"Approved\"" +
                                ", \"responseCode\": \"SAMPLE_TEXT\"" +
                                ", \"accountExternalId\": \"SAMPLE_TEXT\"" +
                                ", \"maskedCardNumber\": \"SAMPLE_TEXT\"" +
                                ", \"hasPaymentDocumentFiles\": null" +
                                ", \"hasPaymentNotes\": null" +
                                ", \"cardName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingFirstName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingLastName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingCompanyName\": \"SAMPLE_TEXT\"" +
                                ", \"subType\": \"SAMPLE_TEXT\"" +
                                ", \"purchaseDate\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"exchangeRate\": 0" +
                                ", \"enrichedMerchantName\": \"SAMPLE_TEXT\"" +
                                ", \"enrichedMerchantUrl\": \"SAMPLE_TEXT\"" +
                                ", \"enrichedMerchantDomain\": \"SAMPLE_TEXT\"" +
                                ", \"enrichedMerchantTelephone\": \"SAMPLE_TEXT\"" +
                                ", \"enrichedMerchantIconUrl\": \"SAMPLE_TEXT\"" +
                                ", \"totalAmount\": 0" +
                                "}"
                            )
                        )
                        .asJson()
                        .check(status().is(201))
                        .check(headerRegex("Location", "(.*)").saveAs("new_accountStatementRecord_url"))
                )
                .exitHereIfFailed()
                .pause(10)
                .repeat(5)
                .on(
                    exec(
                        http("Get created accountStatementRecord")
                            .get("${new_accountStatementRecord_url}")
                            .headers(headersHttpAuthenticated)
                    ).pause(10)
                )
                .exec(
                    http("Delete created accountStatementRecord")
                        .delete("${new_accountStatementRecord_url}")
                        .headers(headersHttpAuthenticated)
                )
                .pause(10)
        );

    ScenarioBuilder users = scenario("Test the AccountStatementRecord entity").exec(scn);

    {
        setUp(
            users.injectOpen(rampUsers(Integer.getInteger("users", 100)).during(Duration.ofMinutes(Integer.getInteger("ramp", 1))))
        ).protocols(httpConf);
    }
}
