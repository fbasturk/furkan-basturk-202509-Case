package inseder.web.pages;

import inseder.web.base.BasePage;
import inseder.web.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class QualityAssurancePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(QualityAssurancePage.class);

    private static final By BTN_ALL_QA_JOBS = By.xpath("//a[text()='See all QA jobs']");
    private static final By SL_LOCATION = By.xpath("//select[@id='filter-by-location']");
    private static final By SL_DEPARTMENT = By.xpath("//select[@id='filter-by-department']");

    private static final By SPAN_TOTAL_JOB = By.xpath("//span[@id='deneme']");

    private static final By DIV_JOB_LIST_ITEMS = By.xpath("//div[contains(@class,'position-list-item-wrapper')]");

    private static final By SPAN_JOB_ITEM_DEPARTMENT = By.xpath(".//span");
    private static final By DIV_JOB_ITEM_LOCATION = By.xpath(".//div");
    private static final By P_JOB_ITEM_TITLE = By.xpath(".//p");

    private static final By BTN_JOB_ITEM_VIEW_ROLE = By.xpath(".//a");

    private static final By BTN_REJECT_COOKIES = By.xpath("//a[@id='wt-cli-reject-btn']");


    public QualityAssurancePage openHomePage() {
        goPage("careers/quality-assurance/");
        verifyPageIsOpened("careers/quality-assurance/");
        waitForPageLoad();
        if (isElementPresent(BTN_REJECT_COOKIES, 3)) clickByLocator(BTN_REJECT_COOKIES);
        return this;
    }

    public QualityAssurancePage scrollToSeeAllQaJobs() {
        scrollWebElementToCenter(BTN_ALL_QA_JOBS);
        return this;
    }

    public QualityAssurancePage clickSeeAllQaJobs() {
        clickByLocator(BTN_ALL_QA_JOBS);
        waitForPageLoad();
        return this;
    }

    public QualityAssurancePage waitLoadJobList() {

        for (int i = 0; i < 6; i++) {
            WebElement element = waitPresenceByLocator(SPAN_TOTAL_JOB);
            String totalCount = element.getText();
            if (totalCount != null && !totalCount.equals(""))
                break;

            waitSecond(2);
        }

        WebElement element = waitPresenceByLocator(SPAN_TOTAL_JOB);
        String totalCount = element.getText();

        if (totalCount == null || totalCount.equals(""))
            Assert.fail("Job List yuklenmedi!");

        return this;
    }

    public QualityAssurancePage changeLoadJobList(String beforeTotalCount) {

        for (int i = 0; i < 6; i++) {
            WebElement tempElement = waitPresenceByLocator(SPAN_TOTAL_JOB);
            String tempCount = tempElement.getText();
            if (!beforeTotalCount.equals(tempCount))
                break;

            waitSecond(2);
        }

        return this;
    }

    public QualityAssurancePage scrollToJobLocation() {
        scrollWebElementToCenter(SL_LOCATION);
        return this;
    }

    public QualityAssurancePage selectJobLocation() {
        String beforeTotalCount = waitPresenceByLocator(SPAN_TOTAL_JOB).getText();

        String jobLocation = ConfigReader.getProperty("job.location");
        selectItemByValue(SL_LOCATION, jobLocation);
        waitForPageLoad();
        waitSecond(2);
        changeLoadJobList(beforeTotalCount);
        return this;
    }

    public QualityAssurancePage scrollToJobDepartment() {
        scrollWebElementToCenter(SL_DEPARTMENT);
        return this;
    }

    public QualityAssurancePage selectJobDepartment() {
        String beforeTotalCount = waitPresenceByLocator(SPAN_TOTAL_JOB).getText();

        String jobDepartment = ConfigReader.getProperty("job.department");
        selectItemByValue(SL_DEPARTMENT, jobDepartment);
        waitForPageLoad();
        changeLoadJobList(beforeTotalCount);
        return this;
    }


    public QualityAssurancePage writeLogJobList() {
        logger.info("{}JOB LIST:", logFormatSpace);
        List<WebElement> locationList = getElementList(DIV_JOB_LIST_ITEMS);

        for (int i = 0; i < locationList.size(); i++) {
            String elementDepartment = locationList.get(i).findElement(SPAN_JOB_ITEM_DEPARTMENT).getText();
            String elementLocation = locationList.get(i).findElement(DIV_JOB_ITEM_LOCATION).getText();
            String elementJobTitle = locationList.get(i).findElement(P_JOB_ITEM_TITLE).getText();

            logger.info("{}{}-{} : {},{}", logFormatSpace, (i + 1), elementJobTitle, elementDepartment, elementLocation);
        }

        return this;
    }

    public QualityAssurancePage checkLocationJobList() {
        String jobLocation = ConfigReader.getProperty("job.location");

        List<WebElement> locationList = getElementList(DIV_JOB_LIST_ITEMS);

        for (int i = 0; i < locationList.size(); i++) {
            String elementLocation = locationList.get(i).findElement(DIV_JOB_ITEM_LOCATION).getText();

            if (jobLocation.equals(elementLocation))
                logger.info("{}{}-Location Dogru", logFormatSpace, (i + 1));
            else
                logger.error("{}{}-Location Hatali! Actual:{} , Expected:{}", logFormatSpace, (i + 1), elementLocation, jobLocation);
        }
        return this;
    }

    public QualityAssurancePage checkDepartmentJobList() {
        String jobDepartment = ConfigReader.getProperty("job.department");

        List<WebElement> locationList = getElementList(DIV_JOB_LIST_ITEMS);

        for (int i = 0; i < locationList.size(); i++) {
            String elementDepartment = locationList.get(i).findElement(SPAN_JOB_ITEM_DEPARTMENT).getText();

            if (jobDepartment.equals(elementDepartment)) logger.info("{}{}-Department Dogru", logFormatSpace, (i + 1));
            else
                logger.error("{}{}-Department Hatali! Actual:{} , Expected:{}", logFormatSpace, (i + 1), elementDepartment, jobDepartment);
        }

        return this;
    }

    public QualityAssurancePage scrollToJobItemWithIndex(int index) {
        List<WebElement> locationList = getElementList(DIV_JOB_LIST_ITEMS);
        WebElement element = locationList.get(index);
        scrollWebElementToCenter(element);
        return this;
    }

    public QualityAssurancePage clickViewRoleWithIndex(int index) {
        List<WebElement> locationList = getElementList(DIV_JOB_LIST_ITEMS);
        WebElement element = locationList.get(index).findElement(BTN_JOB_ITEM_VIEW_ROLE);
        hoverToElement(element);
        element.click();
        return this;
    }

}
