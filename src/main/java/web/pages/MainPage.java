package web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends AbstractBasePage {

    @FindBy(css = "#nav-hamburger-menu")
    private WebElement hamburgerMenu;

    @FindBy(css = "#hmenu-content a.hmenu-item")
    private List<WebElement> menuOptions;

    public MainPage() {
        waitForElementToBeVisible(hamburgerMenu);
    }

    public MainPage expandHamburgerMenu(){
        hamburgerMenu.click();

        explicitWait(5).until(driver -> { return menuOptions.size() > 0; });

        return this;
    }

    public MainPage selectMenuOption(String option) {
        var element = menuOptions.stream().filter(e -> e.getText().equals(option)).findFirst().get();
        scrollIntoView(element);
        element.click();

        return this;
    }

    public FilterPage goToFilterPage(){
        return new FilterPage();
    }

}
