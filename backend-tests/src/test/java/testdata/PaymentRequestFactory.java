package testdata;

import api.PaymentRequest;

import java.math.BigDecimal;

/**
 * Provides reusable payment request test data.
 */
public final class PaymentRequestFactory {

    /**
     * Prevents creating instances of this utility class.
     */
    private PaymentRequestFactory() {
    }

    /**
     * Prepares the payment request payload for the standard request-payment scenario.
     */
    public static PaymentRequest preparePaymentRequestPayload() {
        return PaymentRequest.builder()
                .amount(BigDecimal.valueOf(150.00))
                .senderPhone("0501111111")
                .receiverPhone("0502222222")
                .build();
    }
}
