package tests;

import java.util.Map;
import java.time.Duration;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.CenterLocatorPage;
import pages.SearchResultsPage;
import utilities.TestDataReader;
import utilities.ExtentReportUtil;

public class BrightHorizonsSearchTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private CenterLocatorPage centerLocatorPage;
    private String baseUrl;

    @BeforeClass
    public void setup() throws IOException {
        System.setProperty("web driver.chrome.driver", "path/to/chromedriver");
        baseUrl = TestDataReader.getBaseUrl();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TestDataReader.getTimeoutDuration()));
        homePage = new HomePage(driver, wait);
        searchResultsPage = new SearchResultsPage(driver, wait);
        centerLocatorPage = new CenterLocatorPage(driver, wait);
        driver.manage().window().maximize();

        // Initialize Extent Report
        ExtentReportUtil.initialize("./reports/extent-report.html");
    }

    @Test(priority = 1)
    public void testSearchFunctionality() {
        ExtentReportUtil.startTest("Test Search Functionality");

        try {
            driver.get(baseUrl);
            ExtentReportUtil.logInfo("Navigated to: " + baseUrl);

            homePage.acceptCookies();
            ExtentReportUtil.logInfo("Cookies accepted.");

            homePage.clickSearchIcon();
            ExtentReportUtil.logInfo("Clicked on the search icon.");

            homePage.enterSearchQuery(TestDataReader.getSearchQuery());
            ExtentReportUtil.logInfo("Entered search query: " + TestDataReader.getSearchQuery());

            searchResultsPage.clickSearchButton();
            ExtentReportUtil.logInfo("Clicked on the search button.");

            Assert.assertTrue(searchResultsPage.isFirstResultDisplayed(), "Search result does not match the search term.");
            ExtentReportUtil.logPass("Search result matches the search term.");

        } catch (Exception e) {
            ExtentReportUtil.logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void findCenterFunctionality() {
        // Start the test in ExtentReports
        ExtentReportUtil.startTest("Find Center Functionality");

        try {
            // Navigate to the home page again
            driver.get(baseUrl);
            ExtentReportUtil.logInfo("Navigated to: " + baseUrl);

            // Accept cookies if the pop-up exists
            homePage.acceptCookies();
            ExtentReportUtil.logInfo("Cookies accepted.");

            // Click the "Find a Center" link and verify URL
            String url = centerLocatorPage.clickFindCenterLink();
            Assert.assertTrue(url.contains("/child-care-locator"),
                    "URL does not contain '/child-care-locator'");
            ExtentReportUtil.logPass("Clicked 'Find a Center' link. URL: " + url);

            // Enter location (get value from config)
            String location = TestDataReader.getLocation();
            centerLocatorPage.enterLocation(location);
            ExtentReportUtil.logInfo("Entered location: " + location);

            // Get the results count and verify
            int resultsCount = centerLocatorPage.getResultsCount();
            ExtentReportUtil.logInfo("Results count: " + resultsCount);
            Assert.assertTrue(resultsCount > 0, "No centers found for location: " + location);
            ExtentReportUtil.logPass("Centers found: " + resultsCount);

            // Get center details and verify
            Map<String, Object> centerDetails = centerLocatorPage.getFirstCenterDetailsAndVerify();
            int centersListCount = (int) centerDetails.get("displayedCentersCount");
            Assert.assertEquals(resultsCount, centersListCount,
                    "Mismatch in the number of centers found and displayed.");
            ExtentReportUtil.logPass("Number of centers found and displayed match.");

            // Get center details for the list and popup, and compare
            Map<String, String> listCenterDetails = (Map<String, String>) centerDetails.get("listCenter");
            Map<String, String> popupCenterDetails = (Map<String, String>) centerDetails.get("popupCenter");

            Assert.assertEquals(listCenterDetails.get("name"), popupCenterDetails.get("name"),
                    "Center name does not match.");
            Assert.assertEquals(listCenterDetails.get("address"), popupCenterDetails.get("address"),
                    "Center address does not match.");
            ExtentReportUtil.logPass("Center name and address match.");

        } catch (Exception e) {

            ExtentReportUtil.logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        } finally {
            // End test in ExtentReports
            ExtentReportUtil.endTest();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            ExtentReportUtil.logInfo("Browser closed.");
        }

        //Ensure Extent Report is flushed
        ExtentReportUtil.flush();
    }
}
