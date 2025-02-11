package pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CenterLocatorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(linkText = "Find a Center")
    @CacheLookup
    private WebElement findCenterLink;

    @FindBy(id = "addressInput")
    @CacheLookup
    private WebElement searchBox;

    // Using @FindBy for dynamic elements
    @FindBy(className = "pac-container")
    private WebElement suggestionList;

    @FindBy(className = "resultsNumber")
    private WebElement resultsCountElement;

    @FindBy(id = "center-results-container")
    private WebElement centerResultsContainer;

    @FindBy(css = "#center-results-container .centerResult")
    private List<WebElement> centersList;

    @FindBy(className = "centerResult__name")
    private WebElement firstCenterNameElement;

    @FindBy(className = "centerResult__address")
    private WebElement firstCenterAddressElement;

    @FindBy(xpath = "//div[@class='mapTooltip']")
    private WebElement mapTooltip;

    @FindBy(xpath = ".//span[contains(@class,'mapTooltip__headline')]")
    private WebElement mapTooltipHeadline;

    @FindBy(className = "gm-style-iw-t")
    private WebElement centerPopup;

    @FindBy(className = "mapTooltip__address")
    private WebElement centerPopupAddress;

    // Constructor with PageFactory initialization
    public CenterLocatorPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // Click on "Find a Center" option (top header)
    public String clickFindCenterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(findCenterLink)).click();
        wait.until(ExpectedConditions.urlContains("/child-care-locator"));
        return driver.getCurrentUrl();
    }

    public void enterLocation(String location) {
        wait.until(ExpectedConditions.visibilityOf(searchBox)).clear();
        searchBox.sendKeys(location);

        // Wait for the suggestion list to appear
        wait.until(ExpectedConditions.visibilityOf(suggestionList));

        // Use Actions class to simulate pressing down arrow and Enter
        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    public int getResultsCount() {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(resultsCountElement)).getText());
    }

    public Map<String, Object> getFirstCenterDetailsAndVerify() {
        wait.until(ExpectedConditions.visibilityOf(centerResultsContainer));

        // Get the count of displayed centers
        int displayedCentersCount = centersList.size();

        // Fetch the first center's details
        WebElement firstCenter = centersList.get(0);
        String firstCenterName = firstCenterNameElement.getText();
        String firstCenterAddress = firstCenterAddressElement.getText();
        firstCenter.click();

        // Fetch popup details
        String popupCenterAddress = wait.until(ExpectedConditions.visibilityOf(centerPopupAddress)).getText().trim().replaceAll("\\s+", " ");
        String popupCenterName = wait.until(ExpectedConditions.visibilityOf(mapTooltipHeadline)).getText();

        // Store data in maps
        Map<String, String> listCenterDetails = new HashMap<>();
        listCenterDetails.put("name", firstCenterName);
        listCenterDetails.put("address", firstCenterAddress);

        Map<String, String> popupCenterDetails = new HashMap<>();
        popupCenterDetails.put("name", popupCenterName);
        popupCenterDetails.put("address", popupCenterAddress);

        // Combine details and return as a result map
        Map<String, Object> result = new HashMap<>();
        result.put("displayedCentersCount", displayedCentersCount);
        result.put("listCenter", listCenterDetails);
        result.put("popupCenter", popupCenterDetails);

        return result;
    }
}
