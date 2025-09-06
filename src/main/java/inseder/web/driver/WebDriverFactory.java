package inseder.web.driver;

import org.openqa.selenium.WebDriver;


public class WebDriverFactory {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static synchronized void setDriver(WebDriver driver) {
        driverThread.set(driver);
    }

    public static synchronized WebDriver getDriver() {
        return driverThread.get();
    }
}
