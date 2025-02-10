package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(className = "nav-link-search")
    private WebElement searchIcon;

    // Using By locators for dynamic elements
    private By acceptCookiesButton = By.id("onetrust-accept-btn-handler");
    private By searchField = By.id("search-field");

    // Constructor with PageFactory initialization
    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // Handling dynamic element using explicit wait
    public void acceptCookies() {
        try {
            WebElement acceptCookiesButtonElement = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            acceptCookiesButtonElement.click();
        } catch (Exception e) {
            System.out.println("No cookie consent popup appeared.");
        }
    }

    // Clicking search icon using JavaScript Executor (static element)
    public void clickSearchIcon() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", searchIcon);
    }

    // Handling search field (dynamic element) with explicit wait
    public void enterSearchQuery(String query) {
        WebElement searchFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        searchFieldElement.sendKeys(query);
    }
}
