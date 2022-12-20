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

    @Parameters("config")
    @BeforeSuite(alwaysRun = true)
    public void setUpEnvironment(@Optional(value = "docker") String config) {
        Environment.setConfig(config);
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
