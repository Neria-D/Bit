package utils;

/**
 * Contains small helpers for validating required JSON values.
 */
public final class JsonUtils {

    /**
     * Prevents creating instances of this utility class.
     */
    private JsonUtils() {
    }

    /**
     * Returns a string only when it is present and non-empty.
     */
    public static String requiredString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required JSON field: " + fieldName);
        }

        return value;
    }
}
