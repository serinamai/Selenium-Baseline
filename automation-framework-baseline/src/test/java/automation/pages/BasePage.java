package automation.pages;

import automation.util.DriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    public BasePage() {
        PageFactory.initElements(DriverManager.getDriverManagerInstance().getDriver(), this);
    }
    @FindBy(partialLinkText = "header")
    @CacheLookup
    public WebElement header;

    /**
     * getTitle - method to return the title of the current page
     *
     * @throws Exception
     */
    public String getTitle() {
        return header.getTagName();
    }

    public static void navigateTo(String pageURL) {
        DriverManager.getDriverManagerInstance().getDriver().navigate().to(pageURL);
    }

    public static String getCurrentURL() {
        return DriverManager.getDriverManagerInstance().getDriver().getCurrentUrl();
    }
}
