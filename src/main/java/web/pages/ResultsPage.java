package web.pages;

import driver.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ResultsPage extends AbstractBasePage {

    @FindBy(css = ".a-button-dropdown")
    private WebElement sortDropdown;

    @FindBy(css = ".a-dropdown-item")
    private List<WebElement> sortDropdownOptions;

    @FindBy(css = ".s-search-results > [data-component-type='s-search-result']")
    private List<WebElement> products;

    public ResultsPage filterBy(String filter){
        sortDropdown.click();
        waitForSeconds(1);

        var element = sortDropdownOptions.stream().filter(e -> e.getText().equals(filter)).findFirst().get();
        element.click();

        return this;
    }

    public ResultsPage filterByPriceFromHighToLow(){
        filterBy("Price: High to Low");

        return this;
    }

   public ProductPage selectProductByIndex(int index){
       products.get(index).click();

       DriverFactory.switchToTheNextTab();

       return new ProductPage();
   }


}
