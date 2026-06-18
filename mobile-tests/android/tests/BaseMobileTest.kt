package tests

import driver.DriverFactory
import io.appium.java_client.AppiumDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

/**
 * Base class responsible for Appium driver lifecycle.
 */
abstract class BaseMobileTest {

    protected lateinit var driver: AppiumDriver

    /**
     * Creates an Appium driver before each test.
     */
    @BeforeEach
    fun setup() {
        driver = DriverFactory.createAndroidDriver()
    }

    /**
     * Closes the Appium session after each test.
     */
    @AfterEach
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }
}
