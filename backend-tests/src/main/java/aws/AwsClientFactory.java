package aws;

import config.ConfigManager;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Creates AWS SDK clients used by the automation framework.
 */
public final class AwsClientFactory {

    /**
     * Prevents creating instances of this utility class.
     */
    private AwsClientFactory() {
    }

    /**
     * Creates a DynamoDB client using the AWS SDK default provider chain.
     */
    public static DynamoDbClient createDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(ConfigManager.awsRegion()))
                .build();
    }
}
