package driver;

import environment.Environment;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

@Log4j2
public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    @SneakyThrows
    public static void setDriverInstance() {
        log.debug("driver is going to start");
        WebDriver driver = null;

        switch (Environment.getConfig().getString("driver")) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "docker":
                WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker()
                        .enableVnc().enableRecording();
                driver = wdm.create();
                break;

            default:
                throw new RuntimeException("Wrong driver type!");
        }

        driver.manage().window().maximize();

        DRIVER_THREAD_LOCAL.set(driver);

        log.debug("driver started");
    }

    public static WebDriver getDriverInstance() {
        if (DRIVER_THREAD_LOCAL.get() == null)
            setDriverInstance();

        return DRIVER_THREAD_LOCAL.get();
    }

    public static void killDriverInstance() {
        if (DRIVER_THREAD_LOCAL.get() != null) {
            DRIVER_THREAD_LOCAL.get().quit();
            DRIVER_THREAD_LOCAL.remove();
            log.debug("driver killed");
        }
    }

    public static void switchToTheNextTab() {
        var currentHandle = getDriverInstance().getWindowHandle();
        var allHandles = getDriverInstance().getWindowHandles();

        for (String handle : allHandles) {
            if (!handle.equals(currentHandle)) {
                getDriverInstance().switchTo().window(handle);
                return;
            }
        }
    }
}
