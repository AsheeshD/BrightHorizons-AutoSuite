package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(className = "nav-link-search")
    @CacheLookup
    private WebElement searchIcon;

    // Using @FindBy for dynamic elements (without @CacheLookup)
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(id = "search-field")
    private WebElement searchField;

    // Constructor with PageFactory initialization
    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // Handling cookie consent popup
    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
        } catch (Exception e) {
            System.out.println("No cookie consent popup appeared.");
        }
    }

    // Clicking search icon using JavaScript Executor
    public void clickSearchIcon() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", searchIcon);
    }

    // Entering search query with explicit wait
    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchField)).sendKeys(query);
    }
}
