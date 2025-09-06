package inseder.web;

import inseder.web.base.BaseTest;
import inseder.web.pages.CareersPage;
import inseder.web.pages.HomePage;
import inseder.web.pages.LeverApplicationPage;
import inseder.web.pages.QualityAssurancePage;
import inseder.web.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class WebTestSet extends BaseTest {
    protected static final Logger logger = LogManager.getLogger(WebTestSet.class);

    @Test(enabled = true, priority = 1, description = "check Insider home page")
    public void checkHomePage() {
        HomePage homePage = new HomePage();

        homePage.openHomePage();

    }

    @Test(enabled = true, priority = 2, description = "check Career page")
    public void checkCareerPage() {
        HomePage homePage = new HomePage();
        homePage
                .openHomePage()
                .goToCareersPageFromCompanyMenu();

        CareersPage careersPage = new CareersPage();

        int teamSize = ConfigReader.getPropertyInteger("teamSize");

        int currentTeamSize = 0;
        for (int i = 1; i <= 2; i++) {
            currentTeamSize = careersPage
                    .scrollToTeamBlock()
                    .getVisibleTeamSize();
            logger.info("  For Index {}: Team Size = {}", i, currentTeamSize);

            if (currentTeamSize == teamSize)
                break;

            careersPage
                    .scrollToSeeAllTeams()
                    .clickSeeAllTeams();
        }

        Assert.assertEquals(currentTeamSize, teamSize);

        careersPage
                .scrollToTeamBlock()
                .writeLogTeamNameList();

        int locationSize = ConfigReader.getPropertyInteger("locationSize");

        int currentLocationSize = careersPage
                .scrollToLocationBlock()
                .getLocationSize();

        Assert.assertEquals(currentLocationSize, locationSize);

        careersPage
                .scrollToLocationBlock()
                .writeLogLocationNameList();

        careersPage
                .checkLifeAtInsider();
    }

    @Test(enabled = true, priority = 3, description = "check Job List")
    public void checkJobList() {
        QualityAssurancePage qualityAssurancePage = new QualityAssurancePage();

        qualityAssurancePage
                .openHomePage()
                .scrollToSeeAllQaJobs()
                .clickSeeAllQaJobs()
                .waitLoadJobList()
                .scrollToJobLocation()
                .selectJobLocation()
                .scrollToJobDepartment()
                .selectJobDepartment()
                .writeLogJobList();

    }

    @Test(enabled = true, priority = 4, description = "check Job Department - Location")
    public void checkJobDepartmentLocation() {
        QualityAssurancePage qualityAssurancePage = new QualityAssurancePage();

        qualityAssurancePage
                .checkLocationJobList()
                .checkDepartmentJobList()
                .writeLogJobList();

    }

    @Test(enabled = true, priority = 5, description = "check Lever Application")
    public void checkLeverApplication() {
        QualityAssurancePage qualityAssurancePage = new QualityAssurancePage();

        qualityAssurancePage
                .scrollToJobItemWithIndex(0)
                .clickViewRoleWithIndex(0);

        LeverApplicationPage leverApplicationPage = new LeverApplicationPage();

        leverApplicationPage
                .checkOpenPage();

    }

}
