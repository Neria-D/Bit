package aws;

import config.ConfigManager;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.time.Duration;
import java.util.Map;

/**
 * Validates that payment transactions were persisted in DynamoDB.
 */
public class DynamoDbValidator {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    /**
     * Creates a validator using the configured transactions table name.
     */
    public DynamoDbValidator(DynamoDbClient dynamoDbClient) {
        this(dynamoDbClient, ConfigManager.transactionsTableName());
    }

    /**
     * Creates a validator for a specific DynamoDB table.
     */
    public DynamoDbValidator(DynamoDbClient dynamoDbClient, String tableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = tableName;
    }

    /**
     * Checks whether a transaction record exists for the given id.
     */
    public boolean transactionExists(String transactionId) {
        GetItemRequest request = buildGetTransactionRequest(transactionId);
        return dynamoDbClient.getItem(request).hasItem();
    }

    /**
     * Retries transaction lookup to handle eventual consistency.
     */
    public boolean transactionExistsEventually(String transactionId, int maxAttempts, Duration waitBetweenAttempts) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            if (transactionExists(transactionId)) {
                return true;
            }

            sleep(waitBetweenAttempts);
        }

        return false;
    }

    /**
     * Builds a DynamoDB GetItem request for a transaction id.
     */
    private GetItemRequest buildGetTransactionRequest(String transactionId) {
        return GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of(
                        "transactionId",
                        AttributeValue.builder().s(transactionId).build()))
                .build();
    }

    /**
     * Waits between retry attempts.
     */
    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for DynamoDB consistency", e);
        }
    }
}
