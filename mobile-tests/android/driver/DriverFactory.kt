package driver

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

/**
 * Creates Appium driver instances for Android tests.
 */
object DriverFactory {

    /**
     * Creates an Appium driver with conceptual Android capabilities.
     */
    fun createAndroidDriver(): AppiumDriver {
        val capabilities = DesiredCapabilities().apply {
            setCapability("platformName", "Android")
            setCapability("automationName", "UiAutomator2")
            setCapability("appPackage", "il.co.bit.mock")
            setCapability("appActivity", ".MainActivity")
        }

        return AppiumDriver(URL("http://localhost:4723/wd/hub"), capabilities)
    }
}
