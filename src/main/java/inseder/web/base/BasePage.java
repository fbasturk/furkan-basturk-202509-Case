package inseder.web.base;

import inseder.web.driver.WebDriverFactory;
import inseder.web.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class BasePage {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected final String logFormatSpace = "  ";

    protected void logError(String message) {
        logger.error("{}{}", logFormatSpace, message);
        Assert.fail(message);
    }

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String baseUrl;


    public BasePage() {
        this.driver = WebDriverFactory.getDriver();
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.baseUrl = ConfigReader.getProperty("baseUrl");
    }

    protected void goHomePage() {
        driver.get(baseUrl);
    }

    protected void goPage(String link) {
        driver.get(baseUrl + link);
    }

    protected void verifyHomePageIsOpened() {
        verifyPageIsOpened("");
    }

    protected void verifyPageIsOpened(String pageUrl) {
        try {
            wait.until(ExpectedConditions.urlContains(baseUrl + pageUrl));
            logger.info("{}Sayfanın URL'i doğrulandı. Mevcut URL: {}", logFormatSpace, driver.getCurrentUrl());
        } catch (Exception e) {
            logError("PageBase verifyPageIsOpened methodunda HATA olustu !!");
        }
    }

    protected void verifyContainsPageIsOpened(String containsPageUrl) {
        String currentURL = driver.getCurrentUrl();

        if (currentURL.contains(containsPageUrl)) {
            logger.info("{}Sayfanın URL'i doğrulandı. Mevcut URL: {}", logFormatSpace, driver.getCurrentUrl());
        } else {
            logError("PageBase verifyContainsPageIsOpened methodunda HATA olustu !!");
        }
    }

    protected void changeWindowsHandle() {
        String originalWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();

        for (String handle : allWindowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    protected void waitForPageLoad() {
        try {
            wait.until(driver -> {
                logger.info("{}Current Window State  : {}", logFormatSpace, ((JavascriptExecutor) driver).executeScript("return document.readyState"));
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
            });

        } catch (Exception e) {
            logError("PageBase waitForPageLoad methodunda HATA olustu !!");
        }

    }

    protected WebElement waitPresenceByLocator(By locator) {
        WebElement element = null;

        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(driver, Duration.ofSeconds(30L))).pollingEvery(Duration.ofMillis(500L)).withTimeout(Duration.ofSeconds(60L)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (Throwable var3) {
            logError("Web element gorunur degil!");
        }

        return element;
    }

    protected WebElement waitVisibleByLocator(By locator) {
        WebElement element = null;

        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(driver, Duration.ofSeconds(30L))).pollingEvery(Duration.ofMillis(500L)).withTimeout(Duration.ofSeconds(60L)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        } catch (Throwable var3) {
            logError("Web element gorunur degil!");
        }

        return element;
    }

    public WebElement waitVisibleElement(WebElement element) {
        WebElement waitElement = null;
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(driver, Duration.ofSeconds(30L))).pollingEvery(Duration.ofMillis(500L)).withTimeout(Duration.ofSeconds(30L)).ignoring(NotFoundException.class).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);

            waitElement = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception var5) {
            logError("Web element gorunur degil!");
        }
        return waitElement;
    }

    public boolean isElementPresent(By locator, int timeOutInSeconds) {
        try {
            WebElement element = driver.findElement(locator);
            FluentWait<WebDriver> wait = (new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).pollingEvery(Duration.ofMillis(500L)).withTimeout(Duration.ofSeconds(timeOutInSeconds)).ignoring(NotFoundException.class).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);

            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    protected void hoverToByLocator(By hoverSelector) {
        Actions action = new Actions(driver);
        WebElement hoverElement = waitPresenceByLocator(hoverSelector);
        action.moveToElement(hoverElement).perform();
    }

    protected void clickByLocator(By locator) {
        WebElement element = this.waitPresenceByLocator(locator);
        element.click();
    }

    protected void hoverToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void scrollWebElement(By locator) {
        WebElement element = waitPresenceByLocator(locator);
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            waitVisibleByLocator(locator);
            waitSecond(2);
        } catch (Throwable var3) {
            logError("Acilan sayfada element: " + element + " scroll edilmesine ragmen bulunamadi!");
        }
    }

    public void scrollWebElementToCenter(By locator) {
        WebElement element = waitPresenceByLocator(locator);
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitVisibleByLocator(locator);
            waitSecond(2);
        } catch (Throwable var3) {
            logError("Acilan sayfada element: " + element + " scroll edilmesine ragmen bulunamadi!");
        }
    }

    public void scrollWebElementToCenter(WebElement element) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitSecond(2);
        } catch (Throwable var3) {
            logError("Acilan sayfada element: " + element + " scroll edilmesine ragmen bulunamadi!");
        }
    }

    protected List<WebElement> getElementList(By mainLocator) {
        waitPresenceByLocator(mainLocator);
        return driver.findElements(mainLocator);
    }

    protected List<WebElement> getElementList(By mainLocator, By childLocator) {
        WebElement element = waitPresenceByLocator(mainLocator);
        return element.findElements(childLocator);
    }

    protected int getElementSizeFromElement(By mainLocator, By childLocator) {
        WebElement element = waitPresenceByLocator(mainLocator);
        List<WebElement> elements = element.findElements(childLocator);

        return elements.size();
    }

    protected List<String> getTextFromElement(By mainLocator, By childLocator, By textLocator) {
        WebElement element = waitPresenceByLocator(mainLocator);
        List<WebElement> elements = element.findElements(childLocator);

        List<String> textList = new ArrayList<>();
        for (WebElement tempElement : elements) {
            String text = tempElement.findElement(textLocator).getText();
            textList.add(text);
        }

        return textList;
    }

    protected void waitSecond(int second) {
        try {
            sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectItemByValue(By locator, String value) {
        Select dropdown = new Select(waitVisibleByLocator(locator));
        List<WebElement> options = dropdown.getOptions();

        int index = 0;
        for (int i = 0; i < options.size(); i++) {
            WebElement element = options.get(i);
            if (element.getText().equalsIgnoreCase(value)) {
                index = i;
                break;
            }
        }

        WebElement selectElement = options.get(index);
        String attribute = selectElement.getAttribute("selected");

        if (attribute == null || !attribute.equalsIgnoreCase("selected")) dropdown.selectByIndex(index);
    }


}
