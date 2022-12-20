package listeners.testng;

import environment.Environment;
import lombok.Getter;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int counter = 0;
    @Getter
    private static int retryLimit = 0;

    public static void setRetryLimit() {
        if (Environment.getConfig().getString("retry.on.fail") != null) {
            retryLimit = Integer.parseInt(Environment.getConfig().getString("retry.on.fail"));
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (counter < retryLimit) {
                counter++;
                return true;
            }
        }
        return false;
    }
}
