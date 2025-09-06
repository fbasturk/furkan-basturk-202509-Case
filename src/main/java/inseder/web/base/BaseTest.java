package inseder.web.base;


import java.lang.reflect.Method;
import java.time.Duration;

import inseder.web.driver.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import inseder.web.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    private String testSetName;

    public BaseTest() {
    }

    @BeforeClass(alwaysRun = true)
    @Parameters("browserName")
    public void setUpClass(String browserName, ITestContext context) {
        this.testSetName = this.getClass().getName();

        WebDriver driver = BrowserType.createDriver(browserName);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2L));
        WebDriverFactory.setDriver(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        logger.info("Test senaryolarina kosum basliyor: {} - Senaryo Adi: {}", this.testSetName, method.getName());
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.info("Test senaryosu kosumu tamamlandi: {} - Senaryo Adi: {}", this.testSetName, result.getMethod().getMethodName());

        if (result.isSuccess()) {
            logger.info("TEST BASARILI");
        } else {
            logger.error("TEST BAŞARISIZ - Hata: {}", result.getThrowable().getMessage());
        }
    }

    @AfterClass
    public void quitDriver() {
        WebDriver driver = WebDriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
            logger.info("Tarayici kapatildi.");
        }
    }

}
