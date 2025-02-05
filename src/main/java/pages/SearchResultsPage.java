package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By firstResult = By.xpath("//h3[contains(text(),'Employee Education in 2018: Strategies to Watch')]");
    private By searchButton = By.xpath("//button[@type='submit']");

    public SearchResultsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickSearchButton() {
        WebElement searchButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        searchButtonElement.click();
    }

    public boolean isFirstResultDisplayed() {
        WebElement firstResultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstResult));
        return firstResultElement.isDisplayed();
    }
}