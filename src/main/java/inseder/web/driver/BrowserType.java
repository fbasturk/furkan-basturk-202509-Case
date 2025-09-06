package inseder.web.driver;

import inseder.web.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserType {

    enum BrowserName {
        CHROME, FIREFOX
    }

    public static WebDriver createDriver(String browserName) {
        BrowserName browserType;
        try {
            browserType = BrowserName.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Desteklenmeyen tarayici: " + browserName);
        }

        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = getChromeOptions();
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = getFirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
            default:
                throw new UnsupportedOperationException("Belirtilen tarayici desteklenmiyor: " + browserName);
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        String[] chromeArgs = ConfigReader.getProperty("chrome.options").split(",");
        for (String arg : chromeArgs) {
            options.addArguments(arg.trim());
        }

        String isVisible = ConfigReader.getProperty("chrome.options.visible");
        if (isVisible.equalsIgnoreCase("false")) {
            String[] chromeInvisibleArgs = ConfigReader.getProperty("chrome.options.visible.options").split(",");
            for (String arg : chromeInvisibleArgs) {
                options.addArguments(arg.trim());
            }
        }

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        String[] firefoxArgs = ConfigReader.getProperty("firefox.options").split(",");
        for (String arg : firefoxArgs) {
            options.addArguments(arg.trim());
        }
        return options;
    }

}