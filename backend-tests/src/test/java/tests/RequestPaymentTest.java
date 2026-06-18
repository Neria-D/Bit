package tests;

import api.PaymentRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static assertions.RequestPaymentAssertions.assertTransactionWasPersisted;
import static assertions.RequestPaymentAssertions.extractRequiredTransactionId;
import static assertions.RequestPaymentAssertions.validateRequestStatusCode;
import static testdata.PaymentRequestFactory.preparePaymentRequestPayload;

/**
 * Verifies the request-payment flow from API creation to backend persistence.
 */
public class RequestPaymentTest extends BaseApiTest {

    /**
     * Creates a payment request, validates the API response, and verifies DynamoDB persistence.
     */
    @Tag("backend")
    @Test
    void verifyRequestPaymentFlow() {
        PaymentRequest request = preparePaymentRequestPayload();

        Response response = requestPaymentClient.createPaymentRequest(request);
        validateRequestStatusCode(response, 200);

        String transactionId = extractRequiredTransactionId(response);
        assertTransactionWasPersisted(dynamoDbValidator, transactionId, 5, Duration.ofSeconds(2));
    }
}
