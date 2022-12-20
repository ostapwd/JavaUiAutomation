package web.elements;

import driver.DriverFactory;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractElement {
    
    protected AbstractElement() {
        PageFactory.initElements(DriverFactory.getDriverInstance(), this);
    }

}
