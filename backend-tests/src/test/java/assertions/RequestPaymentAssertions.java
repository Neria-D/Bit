package assertions;

import api.PaymentResponse;
import aws.DynamoDbValidator;
import io.restassured.response.Response;
import utils.JsonUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains validations for the request-payment test flow.
 */
public final class RequestPaymentAssertions {

    /**
     * Prevents creating instances of this utility class.
     */
    private RequestPaymentAssertions() {
    }

    /**
     * Validates the response status code against the expected status code.
     */
    public static void validateRequestStatusCode(Response response, int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                response.getStatusCode(),
                "The Request Status Code Is Incorrect Expected "
                        + expectedStatusCode
                        + " But Actual "
                        + response.getStatusCode());
    }

    /**
     * Extracts and validates the required transaction id from the API response body.
     */
    public static String extractRequiredTransactionId(Response response) {
        PaymentResponse paymentResponse = response.as(PaymentResponse.class);
        return JsonUtils.requiredString(paymentResponse.getTransactionId(), "transactionId");
    }

    /**
     * Verifies that a transaction id was eventually saved in DynamoDB.
     */
    public static void assertTransactionWasPersisted(
            DynamoDbValidator validator,
            String transactionId,
            int maxAttempts,
            Duration waitBetweenAttempts) {

        assertTrue(
                validator.transactionExistsEventually(transactionId, maxAttempts, waitBetweenAttempts),
                "Transaction was not found in DynamoDB: " + transactionId);
    }
}
