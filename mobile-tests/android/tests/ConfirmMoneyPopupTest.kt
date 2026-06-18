package tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pages.ConfirmMoneyPopupPage

/**
 * Verifies the confirm-money popup approval flow.
 */
class ConfirmMoneyPopupTest : BaseMobileTest() {

    /**
     * Approves the transfer and validates the final confirmation text.
     */
    @Test
    fun verifyMoneyReceivedPopupApprovalMessage() {
        val popupPage = ConfirmMoneyPopupPage(driver)

        popupPage.approveTransfer()

        assertEquals(
            "הכסף הועבר לחשבונך",
            popupPage.getSuccessMessageText()
        )
    }
}
