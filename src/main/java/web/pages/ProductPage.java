package web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends AbstractBasePage {

    @FindBy(css = "#feature-bullets > h1")
    private WebElement productDescriptionHeader;

    @FindBy(css = "#feature-bullets > ul")
    private WebElement productDescriptionContent;

    public boolean isProductDescriptionHeaderPresent(){
        return isElementShown(productDescriptionHeader);
    }

    public String getProductDescription(){
        return productDescriptionContent.getText();
    }
}
