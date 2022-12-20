package web.pages;

import driver.DriverFactory;
import environment.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractBasePage {

    protected AbstractBasePage() {
        PageFactory.initElements(DriverFactory.getDriverInstance(), this);
    }

    public static WebDriverWait explicitWait(long seconds) {
        return new WebDriverWait(DriverFactory.getDriverInstance(), Duration.ofSeconds(seconds));
    }

    public void waitForElementToBeVisible(WebElement element){
        waitForElementToBeVisible(element, Environment.getConfig().getLong("timeout.explicit"));
        waitForSeconds(1);
    }

    public void waitForElementToBeVisible(WebElement element, long timeout){
        explicitWait(timeout).until((driver) -> {
            try {
                return element.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });
    }

    protected void waitForSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static MainPage openTheApp() {
        DriverFactory.getDriverInstance().navigate().to(Environment.getConfig().getString("url"));

        return new MainPage();
    }

    protected void clickOn(WebElement element) {
        element.click();
    }

    protected void sendKeysTo(WebElement element, String keysToSend) {
        element.sendKeys(keysToSend);
    }

    protected boolean isElementShown(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException e) {
            return isElementShown(element);
        }
    }

    protected WebElement scrollIntoView(WebElement e) {
        getJavascriptExecutor().executeScript("arguments[0].scrollIntoView(true)", e);
        return e;
    }

    protected JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) DriverFactory.getDriverInstance();
    }

}
