package listeners.testng;

import driver.DriverFactory;
import lombok.extern.log4j.Log4j2;
import org.testng.*;
import org.testng.reporters.XMLReporter;
import org.testng.xml.XmlSuite;
import reporting.allure.ReportingHelper;

import java.util.List;
import java.util.UUID;

@Log4j2
public class TestListener extends XMLReporter implements IReporter, ITestListener, ISuiteListener,
        IInvokedMethodListener {

    @Override
    public void onStart(ISuite suite) {
        super.getConfig().setGenerateTestResultAttributes(true);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        method.getTestMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
        method.getTestMethod().setInvocationCount(RetryAnalyzer.getRetryLimit());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.getTestMethod().hasMoreInvocation()) {
            method.getTestMethod().setRetryAnalyzerClass(null);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " STARTED");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportingHelper.attachScreenshot("screenshot-" + UUID.randomUUID(), DriverFactory.getDriverInstance());
        log.info(result.getMethod().getMethodName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ReportingHelper.attachScreenshot("screenshot-" + UUID.randomUUID(), DriverFactory.getDriverInstance());
        log.info(result.getMethod().getMethodName() + " FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportingHelper.attachScreenshot("screenshot-" + UUID.randomUUID(), DriverFactory.getDriverInstance());
        log.info(result.getMethod().getMethodName() + " SKIPPED");
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        super.generateReport(xmlSuites, suites, outputDirectory);
    }

}
