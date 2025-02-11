package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(xpath = "//button[@type='submit']")
    @CacheLookup
    private WebElement searchButton;

    // Using @FindBy for dynamic elements
    @FindBy(xpath = "//h3[contains(text(),'Employee Education in 2018: Strategies to Watch')]")
    private WebElement firstResult;

    // Constructor with PageFactory initialization
    public SearchResultsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // Click on the search button
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    // Check if the first result is displayed
    public boolean isFirstResultDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(firstResult)).isDisplayed();
    }
}