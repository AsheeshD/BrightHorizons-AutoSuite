package pages;

import java.util.Map;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CenterLocatorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(linkText = "Find a Center")
    private WebElement findCenterLink;

    @FindBy(id = "addressInput")
    private WebElement searchBox;

    // Keeping By locators for dynamic elements
    private final By suggestionList = By.className("pac-container");
    private final By resultsCountElement = By.className("resultsNumber");
    private final By centerResultsContainer = By.id("center-results-container");
    private final By centerName = By.className("centerResult__name");
    private final By centerAddress = By.className("centerResult__address");
    private final By mapTooltip = By.xpath("//div[@class='mapTooltip']");
    private final By mapTooltipHeadline = By.xpath(".//span[contains(@class,'mapTooltip__headline')]");
    private final By centerPopup = By.className("gm-style-iw-t");
    private final By centerPopupAddress = By.className("mapTooltip__address");

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
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionList));

        // Use Actions class to simulate pressing down arrow and Enter
        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
    }

    public int getResultsCount() {
        WebElement resultsCountElementObj = wait.until(ExpectedConditions.visibilityOfElementLocated(resultsCountElement));
        return Integer.parseInt(resultsCountElementObj.getText());
    }

    public Map<String, Object> getFirstCenterDetailsAndVerify() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(centerResultsContainer));
        List<WebElement> centersList = driver.findElements(By.cssSelector("#center-results-container .centerResult"));
        int displayedCentersCount = centersList.size();

        WebElement firstCenter = centersList.get(0);
        String firstCenterName = firstCenter.findElement(centerName).getText();
        String firstCenterAddress = firstCenter.findElement(centerAddress).getText();
        firstCenter.click();

        WebElement centerPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(centerPopup));
        String popupCenterAddress = centerPopupElement.findElement(centerPopupAddress).getText().trim().replaceAll("\\s+", " ");

        WebElement mapTooltipElement = wait.until(ExpectedConditions.visibilityOfElementLocated(mapTooltip));
        String popupCenterName = mapTooltipElement.findElement(mapTooltipHeadline).getText();

        // Storing data in maps
        Map<String, String> listCenterDetails = new HashMap<>();
        listCenterDetails.put("name", firstCenterName);
        listCenterDetails.put("address", firstCenterAddress);

        Map<String, String> popupCenterDetails = new HashMap<>();
        popupCenterDetails.put("name", popupCenterName);
        popupCenterDetails.put("address", popupCenterAddress);

        // Combine the center details and count in the result map
        Map<String, Object> result = new HashMap<>();
        result.put("displayedCentersCount", displayedCentersCount);
        result.put("listCenter", listCenterDetails);
        result.put("popupCenter", popupCenterDetails);

        return result;
    }
}
