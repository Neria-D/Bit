package config;

/**
 * Provides environment-based configuration with safe conceptual defaults.
 */
public final class ConfigManager {

    private static final String DEFAULT_REQUEST_PAYMENT_ENDPOINT = "https://api.bit.co.il/mock/request-payment";
    private static final String DEFAULT_TRANSACTIONS_TABLE = "BitTransactions";
    private static final String DEFAULT_AWS_REGION = "eu-west-1";

    /**
     * Prevents creating instances of this utility class.
     */
    private ConfigManager() {
    }

    /**
     * Returns the configured request-payment API endpoint.
     */
    public static String requestPaymentEndpoint() {
        return valueOrDefault("REQUEST_PAYMENT_ENDPOINT", DEFAULT_REQUEST_PAYMENT_ENDPOINT);
    }

    /**
     * Returns the configured DynamoDB transactions table name.
     */
    public static String transactionsTableName() {
        return valueOrDefault("TRANSACTIONS_TABLE_NAME", DEFAULT_TRANSACTIONS_TABLE);
    }

    /**
     * Returns the configured AWS region.
     */
    public static String awsRegion() {
        return valueOrDefault("AWS_REGION", DEFAULT_AWS_REGION);
    }

    /**
     * Reads an environment variable or returns a default value.
     */
    private static String valueOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
