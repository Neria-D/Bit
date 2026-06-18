package tests;

import api.RequestPaymentClient;
import aws.DynamoDbValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static aws.AwsClientFactory.createDynamoDbClient;

/**
 * Base class for API tests that owns backend test client lifecycle.
 */
public abstract class BaseApiTest {

    protected RequestPaymentClient requestPaymentClient;
    protected DynamoDbValidator dynamoDbValidator;

    private DynamoDbClient dynamoDbClient;

    /**
     * Creates fresh API and AWS clients before each test.
     */
    @BeforeEach
    void setupBackendClients() {
        dynamoDbClient = createDynamoDbClient();
        requestPaymentClient = new RequestPaymentClient();
        dynamoDbValidator = new DynamoDbValidator(dynamoDbClient);
    }

    /**
     * Closes the AWS SDK client after each test.
     */
    @AfterEach
    void closeBackendClients() {
        if (dynamoDbClient != null) {
            dynamoDbClient.close();
        }
    }
}
