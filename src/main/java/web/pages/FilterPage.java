package web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FilterPage extends AbstractBasePage {

    @FindBy(css = ".a-list-item")
    private List<WebElement> filterOptions;

    public ResultsPage selectFilter(String option) {
        explicitWait(5).until(driver -> { return filterOptions.size() > 0; });

        var element = filterOptions.stream().filter(e -> e.getText().equals(option)).findFirst().get();
        scrollIntoView(element);
        element.click();

        return new ResultsPage();
    }

}
