package api;

import java.math.BigDecimal;

/**
 * Represents the JSON payload sent to the request-payment API.
 */
public class PaymentRequest {

    private final BigDecimal amount;
    private final String senderPhone;
    private final String receiverPhone;

    /**
     * Creates an immutable request from a validated builder.
     */
    private PaymentRequest(Builder builder) {
        this.amount = builder.amount;
        this.senderPhone = builder.senderPhone;
        this.receiverPhone = builder.receiverPhone;
    }

    /**
     * Creates a new builder for a payment request payload.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the requested payment amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the sender phone number.
     */
    public String getSenderPhone() {
        return senderPhone;
    }

    /**
     * Returns the receiver phone number.
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * Builds immutable payment request payloads.
     */
    public static class Builder {
        private BigDecimal amount;
        private String senderPhone;
        private String receiverPhone;

        /**
         * Sets the payment amount.
         */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the sender phone number.
         */
        public Builder senderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
            return this;
        }

        /**
         * Sets the receiver phone number.
         */
        public Builder receiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
            return this;
        }

        /**
         * Validates required fields and creates the request object.
         */
        public PaymentRequest build() {
            if (amount == null || senderPhone == null || receiverPhone == null) {
                throw new IllegalStateException("amount, senderPhone and receiverPhone are required");
            }

            return new PaymentRequest(this);
        }
    }
}
