package automation.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class DriverManager {
    private static DriverManager instance= null;
    private ThreadLocal<WebDriver> webDriver = new InheritableThreadLocal<>();

    private DriverManager(){
    }

    public static DriverManager getDriverManagerInstance(){
        if(instance == null){
            instance = new DriverManager();
        }
        return instance;
    }

    public void initWebDriver() throws IOException {
        String browserType =  System.getProperty("browser.type", Helper.getProperty("project.properties", "browser.type"));
        switch (browserType.toLowerCase()){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver.set(new FirefoxDriver());
                break;
            default:
                ChromeOptions options = new ChromeOptions();
                Helper.setProperties("chrome.properties");
                System.getProperties().keySet().forEach(
                        sys -> {
                            if(sys.toString().contains("chrome.options")){
                                options.addArguments(System.getProperty(sys.toString()));
                            }
                        }
                );
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver(options));
                break;
        }
    }

    public void terminateWebDriver(){
        webDriver.get().quit();
        webDriver.remove();
    }

    public WebDriver getDriver(){
        return webDriver.get();
    }
}
