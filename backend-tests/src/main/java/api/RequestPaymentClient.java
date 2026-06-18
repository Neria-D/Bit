package api;

import config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Sends request-payment API calls and returns raw HTTP responses for validation.
 */
public class RequestPaymentClient {

    private final String requestPaymentEndpoint;

    /**
     * Creates a client using the configured request-payment endpoint.
     */
    public RequestPaymentClient() {
        this(ConfigManager.requestPaymentEndpoint());
    }

    /**
     * Creates a client for the provided request-payment endpoint.
     */
    public RequestPaymentClient(String requestPaymentEndpoint) {
        this.requestPaymentEndpoint = requestPaymentEndpoint;
    }

    /**
     * Posts a payment request payload to the request-payment endpoint.
     */
    public Response createPaymentRequest(PaymentRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post(requestPaymentEndpoint)
                .then()
                .extract()
                .response();
    }
}
