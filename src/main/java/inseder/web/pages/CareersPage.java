package inseder.web.pages;

import inseder.web.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CareersPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CareersPage.class);

    private static final By DIV_TEAM_BLOCK = By.xpath("//div[contains(@class, 'career-load-more')]");
    private static final By DIV_TEAM_TITLE = By.xpath(".//div[contains(@class, 'job-title')]");
    private static final By H3_TEAM_TITLE_NAME = By.xpath(".//h3");

    private static final By BTN_ALL_TEAMS = By.xpath("//a[text()='See all teams']");

    private static final By DIV_LOCATION_BLOCK = By.xpath("//div[@id='location-slider']");
    private static final By LI_LOCATION_CHILD = By.xpath(".//li");
    private static final By P_LOCATION_CHILD_NAME = By.xpath(".//p");

    private static final By BTN_LOCATION_NEXT = By.xpath("//i[contains(@class,'location-slider-next')]");

    private static final By H2_LIFE_AT_INSIDER_TITLE = By.xpath("//h2[contains(text(),'Life at Insider')]");


    public CareersPage scrollToTeamBlock() {
        scrollWebElement(DIV_TEAM_BLOCK);
        return this;
    }

    public int getVisibleTeamSize() {
        return getElementSizeFromElement(DIV_TEAM_BLOCK, DIV_TEAM_TITLE);
    }

    public CareersPage scrollToSeeAllTeams() {
        scrollWebElementToCenter(BTN_ALL_TEAMS);
        return this;
    }

    public CareersPage clickSeeAllTeams() {
        clickByLocator(BTN_ALL_TEAMS);
        return this;
    }

    public CareersPage writeLogTeamNameList() {
        logger.info("{}TEAM NAME:", logFormatSpace);
        List<String> teamNameList = getTextFromElement(DIV_TEAM_BLOCK, DIV_TEAM_TITLE, H3_TEAM_TITLE_NAME);
        for (int i = 0; i < teamNameList.size(); i++) {
            logger.info("{}{}-{}", logFormatSpace, (i + 1), teamNameList.get(i));
        }
        return this;
    }

    public CareersPage scrollToLocationBlock() {
        scrollWebElementToCenter(DIV_LOCATION_BLOCK);
        return this;
    }

    public int getLocationSize() {
        return getElementSizeFromElement(DIV_LOCATION_BLOCK, LI_LOCATION_CHILD);
    }

    public CareersPage writeLogLocationNameList() {
        logger.info("{}LOCATION NAME:", logFormatSpace);
        List<WebElement> locationList = getElementList(DIV_LOCATION_BLOCK, LI_LOCATION_CHILD);

        for (int i = 0; i < locationList.size(); i++) {
            WebElement tempElement = locationList.get(i).findElement(P_LOCATION_CHILD_NAME);
            logger.info("{}{}-{}", logFormatSpace, (i + 1), tempElement.getText());
            clickByLocator(BTN_LOCATION_NEXT);
            waitSecond(2);
        }
        return this;
    }

    public CareersPage checkLifeAtInsider() {
        Assert.assertTrue(isElementPresent(H2_LIFE_AT_INSIDER_TITLE, 3));
        return this;
    }

}
