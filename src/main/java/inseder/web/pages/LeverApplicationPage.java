package inseder.web.pages;

import inseder.web.base.BasePage;

public class LeverApplicationPage extends BasePage {


    public LeverApplicationPage checkOpenPage() {
        changeWindowsHandle();
        waitForPageLoad();
        verifyContainsPageIsOpened("jobs.lever.co/useinsider");
        return this;
    }
}
