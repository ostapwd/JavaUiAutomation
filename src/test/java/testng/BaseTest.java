package testng;

import driver.DriverFactory;
import environment.Environment;
import listeners.testng.CustomTestListenerAdapter;
import listeners.testng.RetryAnalyzer;
import listeners.testng.TestListener;
import org.testng.annotations.*;
import reporting.allure.ReportingHelper;

@Listeners({TestListener.class, CustomTestListenerAdapter.class})
public abstract class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setUpEnvironment() {
        Environment.getConfig();
        RetryAnalyzer.setRetryLimit();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.killDriverInstance();
    }

    @AfterSuite(alwaysRun = true)
    public void attachEnvironmentInfo() {
        ReportingHelper.attachEnvironmentInfo(Environment
                .getConfig().root());
    }

}
