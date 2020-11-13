package automation.hooks;

import automation.util.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.*;
import org.gradle.internal.impldep.org.testng.ITestResult;
import org.gradle.internal.impldep.org.testng.annotations.AfterMethod;
import org.junit.runner.Result;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import static automation.util.GlobalConstant.BASE_URL;


public class CucumberHook {
    static ExtentTest test;
    static ExtentReports report;
    static ExtentHtmlReporter htmlReporter;

    public static ExtentTest getExtentTest() {
        return test;
    }

    @Before
    public static void setUp(Scenario scenario) throws IOException {
//        ZonedDateTime dateTime = ZonedDateTime.now();
        SimpleDateFormat sfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZZZZ");
        String dateTime = sfm.format(new Date());
        String reportName = "Report_"+dateTime+".html";
        String filePath = System.getProperty("user.dir") + "/" + "Results/" + reportName;
        htmlReporter = new ExtentHtmlReporter(filePath);
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        report.setSystemInfo("Report name", "Dummy test report");
        htmlReporter.config().setTheme(Theme.DARK);

        test = report.createTest(scenario.getName());

        DriverManager.getDriverManagerInstance().initWebDriver();
        DriverManager.getDriverManagerInstance().getDriver().get(BASE_URL);

    }

    @After
    public void tearDown(Scenario scenario) {
        Status result = scenario.getStatus();
        if(result == Status.FAILED){
            test.fail(MarkupHelper.createLabel(scenario.getName() + " Test case is FAILED", ExtentColor.RED));
        }else if ( result == Status.PASSED ) {
            test.pass(MarkupHelper.createLabel(scenario.getName() + " Test case is PASSED", ExtentColor.GREEN));
        } else {
            test.skip(MarkupHelper.createLabel(scenario.getName()+ " Test case is SKIPPED", ExtentColor.YELLOW));
        }
        report.flush();
        DriverManager.getDriverManagerInstance().terminateWebDriver();
    }
}
