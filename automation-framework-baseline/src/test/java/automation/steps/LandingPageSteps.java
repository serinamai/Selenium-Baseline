package automation.steps;

import automation.hooks.CucumberHook;
import automation.pages.BasePage;
import automation.pages.InteractionsPage;
import automation.pages.LandingPage;
import automation.util.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static automation.pages.InteractionsPage.DATE_PICKER_PAGE_URL;
import static automation.pages.InteractionsPage.INTERACTIONS_PAGE_URL;
import static automation.pages.LandingPage.LANDING_PAGE_URL;

public class LandingPageSteps{

    public static ExtentTest test = CucumberHook.getExtentTest();
    @Given("I navigate to {string}")
   // @When("I navigate to {string}")
    public void iAmOnLandingPage(String arg0) {
        switch (arg0){
            case "Interactions Page":
                InteractionsPage.navigateTo(INTERACTIONS_PAGE_URL);
                break;
            case "Date Picker Page":
                test.log(Status.INFO, "I navigate to Date Picker page");
                InteractionsPage.navigateTo(DATE_PICKER_PAGE_URL);
                break;
            default:
                test.log(Status.INFO, "I navigate to landing page");
                LandingPage.navigateTo(LANDING_PAGE_URL);
                break;
        }
    }

//    @When("I navigate to {string}")
//    public void reUseThen(String url){
//        iAmOnLandingPage(url);
//    }

    @Then("Page title is correct")
    public void pageTitleIsCorrect() {
    }

    @Then("Page title is {string}")
    public void pageTitleIs(String title) {
        String actualTitle = DriverManager.getDriverManagerInstance().getDriver().getTitle();
        test.log(Status.INFO, "I verify the page title. Actual result: "+ actualTitle +"- Expected result: "+title);
        Assert.assertEquals("Verify page title is correct", DriverManager.getDriverManagerInstance().getDriver().getTitle(), title);
    }

    @When("I set date of current month to {int}")
    public void iSetDateOfCurrentMonthTo(int date) throws InterruptedException {
        new InteractionsPage().pickDateOfCurrentMonth(date);
    }

    @Then("I verify date value is set to {int}")
    public void iVerifyDateValueIsSetTo(int date) {
        test.log(Status.INFO, "I verify date value is set to " + date);
        String actual = new InteractionsPage().datePicker.getAttribute("value");
        Assert.assertTrue(actual.contains(String.valueOf(date)));
    }
}
