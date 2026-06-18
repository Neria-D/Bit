package pages

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

/**
 * Page object for the confirm-money popup.
 */
class ConfirmMoneyPopupPage(
    private val driver: AppiumDriver,
    private val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(10))
) {

    private val confirmTransferButton: By = By.id("CONFIRM_TRANSFER_BUTTON_ID")
    private val successMessage: By = By.id("SUCCESS_MESSAGE_ID")

    /**
     * Taps the approve transfer button after it becomes clickable.
     */
    fun approveTransfer() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmTransferButton)).click()
    }

    /**
     * Returns the final success message shown after approval.
     */
    fun getSuccessMessageText(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).text
    }
}
