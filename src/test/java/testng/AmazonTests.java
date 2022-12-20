package testng;

import dev.failsafe.internal.util.Assert;
import org.testng.annotations.Test;
import reporting.allure.ReportingHelper;

import static web.pages.AbstractBasePage.openTheApp;

public class AmazonTests extends BaseTest {

    @Test
    public void searchTest() {
        var productPage = openTheApp()
                .expandHamburgerMenu()
                .selectMenuOption("TV, Appliances, Electronics")
                .selectMenuOption("Televisions")
                .goToFilterPage()
                .selectFilter("Samsung")
                .filterByPriceFromHighToLow()
                .selectProductByIndex(1);

        Assert.isTrue(productPage.isProductDescriptionHeaderPresent(),
                "The product doesn't have any description.");

        ReportingHelper.attachContent("Product description", productPage.getProductDescription());
    }
}
