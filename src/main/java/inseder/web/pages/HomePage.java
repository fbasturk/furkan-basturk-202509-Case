package inseder.web.pages;

import inseder.web.base.BasePage;
import org.openqa.selenium.By;


public class HomePage extends BasePage {

    private static final By MENU_COMPANY = By.xpath("//a[contains(text(), 'Company')]");
    private static final By BTN_CAREER_COMPANY = By.xpath("//a[contains(text(), 'Careers')]");

    private static final By BTN_REJECT_COOKIES = By.xpath("//a[@id='wt-cli-reject-btn']");


    public HomePage openHomePage() {
        goHomePage();
        verifyHomePageIsOpened();
        waitForPageLoad();
        if (isElementPresent(BTN_REJECT_COOKIES, 3))
            clickByLocator(BTN_REJECT_COOKIES);
        return this;
    }

    public HomePage goToCareersPageFromCompanyMenu() {
        hoverToByLocator(MENU_COMPANY);
        clickByLocator(BTN_CAREER_COMPANY);
        waitForPageLoad();
        return this;
    }

}
