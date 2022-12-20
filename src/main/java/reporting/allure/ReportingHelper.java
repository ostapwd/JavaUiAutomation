package reporting.allure;

import com.typesafe.config.ConfigObject;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.util.Properties;

@Log4j2
public class ReportingHelper {

    public static void attachScreenshot(String name, WebDriver driver) {
        byte[] screenshotByteArray = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        log.debug("attaching screenshot");
        Allure.addAttachment(name, new ByteArrayInputStream(screenshotByteArray));
        log.debug("screenshot attached");
    }

    public static void attachContent(String name, String content) {
        log.debug("attaching content");
        Allure.addAttachment(name, content);
        log.debug("content attached");
    }

    @SneakyThrows
    public static void attachEnvironmentInfo(ConfigObject object) {
        OutputStream output = new FileOutputStream("build/allure-results/environment.properties");
        Properties properties = new Properties();
        properties.setProperty("Driver", object.get("driver").toString());
        properties.setProperty("Url", object.get("url").toString());
        properties.store(output, "Environment info");
    }

}
