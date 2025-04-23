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
 * Performance test for the Card entity.
 *
 * @see <a href="https://github.com/jhipster/generator-jhipster/tree/v8.10.0/generators/gatling#logging-tips">Logging tips</a>
 */
public class CardGatlingTest extends Simulation {

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
            exec(http("Get all cards").get("/api/cards").headers(headersHttpAuthenticated).check(status().is(200)))
                .pause(Duration.ofSeconds(10), Duration.ofSeconds(20))
                .exec(
                    http("Create new card")
                        .post("/api/cards")
                        .headers(headersHttpAuthenticated)
                        .body(
                            StringBody(
                                "{" +
                                "\"predecessorCardId\": null" +
                                ", \"accountId\": null" +
                                ", \"personId\": \"SAMPLE_TEXT\"" +
                                ", \"externalId\": \"SAMPLE_TEXT\"" +
                                ", \"type\": \"ChipAndPin\"" +
                                ", \"name\": \"SAMPLE_TEXT\"" +
                                ", \"maskedCardNumber\": \"SAMPLE_TEXT\"" +
                                ", \"referenceNumber\": \"SAMPLE_TEXT\"" +
                                ", \"expiryDate\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"blockType\": \"BlockedByCardUser\"" +
                                ", \"blockedAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"blockedBy\": \"SAMPLE_TEXT\"" +
                                ", \"status\": \"Active\"" +
                                ", \"embossingName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingFirstName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingLastName\": \"SAMPLE_TEXT\"" +
                                ", \"embossingCompanyName\": \"SAMPLE_TEXT\"" +
                                ", \"limitDailyPurchase\": 0" +
                                ", \"limitDailyWithdrawal\": 0" +
                                ", \"limitMonthlyPurchase\": 0" +
                                ", \"limitMonthlyWithdrawal\": 0" +
                                ", \"limitTransactionPurchase\": 0" +
                                ", \"secure3DType\": \"SMSOTPAndStaticPassword\"" +
                                ", \"secure3DMobile\": \"SAMPLE_TEXT\"" +
                                ", \"secure3DEmail\": \"SAMPLE_TEXT\"" +
                                ", \"secure3DLanguageCode\": \"AAR\"" +
                                ", \"secure3DOutOfBandEnabled\": null" +
                                ", \"secure3DOutOfBandId\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryFirstName\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryLastName\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryCompanyName\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryAddress1\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryAddress2\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryPostalCode\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryCity\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryCountryCode\": \"ABW\"" +
                                ", \"deliveryDispatchMethod\": \"StandardMail\"" +
                                ", \"deliveryPhone\": \"SAMPLE_TEXT\"" +
                                ", \"deliveryTrackingNumber\": \"SAMPLE_TEXT\"" +
                                ", \"isEnrolledFor3DSecure\": null" +
                                ", \"isCard3DSecureActivated\": null" +
                                ", \"renewAutomatically\": null" +
                                ", \"isDisposable\": null" +
                                ", \"securityContactlessEnabled\": null" +
                                ", \"securityWithdrawalEnabled\": null" +
                                ", \"securityInternetPurchaseEnabled\": null" +
                                ", \"securityOverallLimitsEnabled\": null" +
                                ", \"securityAllTimeLimitsEnabled\": null" +
                                ", \"personalizationProductCode\": \"SAMPLE_TEXT\"" +
                                ", \"carrierType\": \"Standard\"" +
                                ", \"cardMetadataProfileId\": \"SAMPLE_TEXT\"" +
                                ", \"activatedAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"createdAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"updatedAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"closedAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"closedBy\": \"SAMPLE_TEXT\"" +
                                ", \"closeReason\": \"ClosedByCardholder\"" +
                                ", \"companyId\": null" +
                                ", \"dispatchedAt\": \"2020-01-01T00:00:00.000Z\"" +
                                ", \"notificationReceiptsReminderEnabled\": null" +
                                ", \"notificationInstantSpendUpdateEnabled\": null" +
                                ", \"disposableType\": \"SingleUse\"" +
                                "}"
                            )
                        )
                        .asJson()
                        .check(status().is(201))
                        .check(headerRegex("Location", "(.*)").saveAs("new_card_url"))
                )
                .exitHereIfFailed()
                .pause(10)
                .repeat(5)
                .on(exec(http("Get created card").get("${new_card_url}").headers(headersHttpAuthenticated)).pause(10))
                .exec(http("Delete created card").delete("${new_card_url}").headers(headersHttpAuthenticated))
                .pause(10)
        );

    ScenarioBuilder users = scenario("Test the Card entity").exec(scn);

    {
        setUp(
            users.injectOpen(rampUsers(Integer.getInteger("users", 100)).during(Duration.ofMinutes(Integer.getInteger("ramp", 1))))
        ).protocols(httpConf);
    }
}
