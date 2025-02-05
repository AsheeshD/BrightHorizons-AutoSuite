package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By acceptCookiesButton = By.id("onetrust-accept-btn-handler");
    private By searchIcon = By.className("nav-link-search");
    private By searchField = By.id("search-field");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

    }

    public void acceptCookies() {
        try {
            WebElement acceptCookiesButtonElement = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            acceptCookiesButtonElement.click();
        } catch (Exception e) {
            System.out.println("No cookie consent popup appeared.");
        }
    }

    public void clickSearchIcon() {
        WebElement searchIconElement = driver.findElement(searchIcon);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", searchIconElement);
    }

    public void enterSearchQuery(String query) {
        WebElement searchFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        searchFieldElement.sendKeys(query);
    }
}