package api;

/**
 * Represents the relevant fields returned by the request-payment API.
 */
public class PaymentResponse {

    private String transactionId;

    /**
     * Returns the unique transaction id from the API response.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the unique transaction id during JSON deserialization.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
