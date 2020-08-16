package automation.hooks;

import automation.util.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


import java.io.IOException;

import static automation.util.GlobalConstant.BASE_URL;


public class CucumberHook {
    static ExtentTest test;
    static ExtentReports report;
    static ExtentHtmlReporter htmlReporter;

    public static ExtentTest getExtentTest(){
        return test;
    }

    @Before
    public static void setUp (Scenario scenario) throws IOException {
        String filePath = System.getProperty("user.dir") + "/" + "Results/" + "htmlFileName.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        report = new ExtentReports();
        report.attachReporter(htmlReporter);

        test = report.createTest(scenario.getName());

        DriverManager.getDriverManagerInstance().initWebDriver();
        DriverManager.getDriverManagerInstance().getDriver().get(BASE_URL);

    }

    @After
    public void tearDown(){
        report.flush();
        DriverManager.getDriverManagerInstance().terminateWebDriver();
    }
}
