package tests;

import java.util.Map;
import java.time.Duration;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.CenterLocatorPage;
import pages.SearchResultsPage;
import utilities.TestDataReader;
import utilities.ExtentReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrightHorizonsSearchTest {

    private static final Logger logger = LoggerFactory.getLogger(BrightHorizonsSearchTest.class); // Logger setup

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private CenterLocatorPage centerLocatorPage;
    private String baseUrl;

    @BeforeClass
    public void setup() throws IOException {
        WebDriverManager.chromedriver().setup();

        // Set Chrome options for headless execution in CI/CD
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");

        // Initialize WebDriver with Chrome options
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize base URL and wait time
        baseUrl = TestDataReader.getBaseUrl();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TestDataReader.getTimeoutDuration()));

        // Initialize Page Objects
        homePage = new HomePage(driver, wait);
        searchResultsPage = new SearchResultsPage(driver, wait);
        centerLocatorPage = new CenterLocatorPage(driver, wait);

        // Initialize Extent Report
        ExtentReportUtil.initialize("./reports/extent-report.html");
        logger.info("Test setup completed successfully.");
    }

    @Test(priority = 1)
    public void testSearchFunctionality() {
        ExtentReportUtil.startTest("Test Search Functionality");
        logger.info("Starting 'testSearchFunctionality' test.");

        try {
            driver.get(baseUrl);
            logger.info("Navigated to: " + baseUrl);
            ExtentReportUtil.logInfo("Navigated to: " + baseUrl);

            homePage.acceptCookies();
            logger.info("Cookies accepted.");
            ExtentReportUtil.logInfo("Cookies accepted.");

            homePage.clickSearchIcon();
            logger.info("Clicked on the search icon.");
            ExtentReportUtil.logInfo("Clicked on the search icon.");

            homePage.enterSearchQuery(TestDataReader.getSearchQuery());
            logger.info("Entered search query: " + TestDataReader.getSearchQuery());
            ExtentReportUtil.logInfo("Entered search query: " + TestDataReader.getSearchQuery());

            searchResultsPage.clickSearchButton();
            logger.info("Clicked on the search button.");
            ExtentReportUtil.logInfo("Clicked on the search button.");

            Assert.assertTrue(searchResultsPage.isFirstResultDisplayed(), "Search result does not match the search term.");
            logger.info("Search result matches the search term.");
            ExtentReportUtil.logPass("Search result matches the search term.");

        } catch (Exception e) {
            logger.error("Test failed: " + e.getMessage());
            ExtentReportUtil.logFail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void findCenterFunctionality() {
        ExtentReportUtil.startTest("Find Center Functionality");
        logger.info("Starting 'findCenterFunctionality' test.");

        try {
            // Navigate to the home page again
            driver.get(baseUrl);
            logger.info("Navigated to: " + baseUrl);
            ExtentReportUtil.logInfo("Navigated to: " + baseUrl);

            // Accept cookies if the pop-up exists
            homePage.acceptCookies();
            logger.info("Cookies accepted.");
            ExtentReportUtil.logInfo("Cookies accepted.");

            // Click the "Find a Center" link and verify URL
            String url = centerLocatorPage.clickFindCenterLink();
            logger.info("Clicked 'Find a Center' link. URL: " + url);
            ExtentReportUtil.logPass("Clicked 'Find a Center' link. URL: " + url);

            // Enter location (get value from config)
            String location = TestDataReader.getLocation();
            centerLocatorPage.enterLocation(location);
            logger.info("Entered location: " + location);
            ExtentReportUtil.logInfo("Entered location: " + location);

            // Get the results count and verify
            int resultsCount = centerLocatorPage.getResultsCount();
            logger.info("Results count: " + resultsCount);
            ExtentReportUtil.logInfo("Results count: " + resultsCount);
            Assert.assertTrue(resultsCount > 0, "No centers found for location: " + location);
            ExtentReportUtil.logPass("Centers found: " + resultsCount);

            // Get center details and verify
            Map<String, Object> centerDetails = centerLocatorPage.getFirstCenterDetailsAndVerify();
            int centersListCount = (int) centerDetails.get("displayedCentersCount");
            Assert.assertEquals(resultsCount, centersListCount,
                    "Mismatch in the number of centers found and displayed.");
            logger.info("Number of centers found and displayed match.");
            ExtentReportUtil.logPass("Number of centers found and displayed match.");

            // Get center details for the list and popup, and compare
            Map<String, String> listCenterDetails = (Map<String, String>) centerDetails.get("listCenter");
            Map<String, String> popupCenterDetails = (Map<String, String>) centerDetails.get("popupCenter");

            Assert.assertEquals(listCenterDetails.get("name"), popupCenterDetails.get("name"),
                    "Center name does not match.");
            Assert.assertEquals(listCenterDetails.get("address"), popupCenterDetails.get("address"),
                    "Center address does not match.");
            logger.info("Center name and address match.");
            ExtentReportUtil.logPass("Center name and address match.");

        } catch (Exception e) {
            logger.error("Test failed: " + e.getMessage());
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
            logger.info("Browser closed.");
            ExtentReportUtil.logInfo("Browser closed.");
        }

        // Ensure Extent Report is flushed
        ExtentReportUtil.flush();
        logger.info("Extent report flushed.");
    }
}
