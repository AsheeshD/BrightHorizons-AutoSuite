package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Using @FindBy for static elements
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    // Keeping By locator for dynamic elements
    private By firstResult = By.xpath("//h3[contains(text(),'Employee Education in 2018: Strategies to Watch')]");

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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstResult)).isDisplayed();
    }
}
