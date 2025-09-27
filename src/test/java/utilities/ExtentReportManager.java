package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

    // Reporter object that defines where the report will be stored
    public ExtentSparkReporter sparkReporter;

    // Main ExtentReports object (manages the entire report)
    public ExtentReports extent;

    // ExtentTest object (represents a single test case in the report)
    public ExtentTest test;

    // To hold the name of the report
    String repName;

    // ======= Triggered when the test execution starts =======

    public void onStart(ITestContext testContext) {
        // Create a timestamp so each report is unique
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Initialize the Spark reporter with the file path
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);

        // Configure report details
        sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title shown in browser
        sparkReporter.config().setReportName("opencart Functional Testing");   // Name shown on top of report
        sparkReporter.config().setTheme(Theme.DARK);                           // Dark theme

        // Attach reporter to ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add some common information about the environment/project
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Capture OS and Browser details from testng.xml parameters
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // Capture test groups (like sanity, regression, etc.)
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    // ======= Triggered when a test case passes =======

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName()); // Create entry in report
        test.assignCategory(result.getMethod().getGroups());       // Assign group/category
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    // ======= Triggered when a test case fails =======

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        // Capture screenshot and attach it to the report
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ======= Triggered when a test case is skipped =======

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    // ======= Triggered when all test execution finishes =======

    public void onFinish(ITestContext testContext) {
        // Save the report
        extent.flush();

        // Automatically open the report in browser after execution
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ======= Optional: Email the report =======
        /*
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

            // Create email with embedded HTML report
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
            email.setSSLOnConnect(true);

            email.setFrom("pavanoltraining@gmail.com"); // sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report....");

            email.addTo("pavankumar.busyqa@gmail.com"); // receiver
            email.attach(url, "extent_report", "please check report...");
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
