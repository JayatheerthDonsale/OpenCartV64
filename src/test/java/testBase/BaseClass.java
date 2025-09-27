package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
    public Properties property;
    public static WebDriver driver;
    public Logger logger;

    @BeforeClass(groups = {"sanity", "regression", "master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {
        //loading Properties File
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//config.properties");

        property = new Properties();
        property.load(file);

        //loading Logger
        logger = LogManager.getLogger(this.getClass());

        if (property.getProperty("execution_env").equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            }
            else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            }
            else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }
            else {
                System.out.println("No Matching Operating System Found! ");
            }

            //Browser Selection
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("Invalid Browser! ");
                    return;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }
        if (property.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                //WebDriverManager.edgedriver().setup();
                //System.setProperty("webdriver.edge.driver", "D:\\Selenium2.0\\msedgedriver\\msedgedriver.exe");
                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                default:
                    System.out.println("Invalid Browser! ");
                    return;
            }
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(property.getProperty("appurl"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"sanity", "regression", "master"})
    public void tearDown() {
        driver.quit();
    }

    public String randomeAlphabeticString() {
        @SuppressWarnings("deprecation")
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;

    }

    public String randomeNumericString() {
        @SuppressWarnings("deprecation")
        String generatedString = RandomStringUtils.randomNumeric(10);
        return generatedString;
    }

    public String randomeAlphaNumeric() {
        @SuppressWarnings("deprecation")
        String generatedAlpha = RandomStringUtils.randomAlphabetic(3);
        @SuppressWarnings("deprecation")
        String generatedNumeric = RandomStringUtils.randomNumeric(3);
        return (generatedAlpha + "@" + generatedNumeric);
    }

    // Method to capture screenshot and return its saved path
    public String captureScreen(String tname) throws IOException {
        // Generate a unique timestamp for the screenshot file
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // Convert WebDriver object to TakesScreenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        // Capture screenshot as a temporary file
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        // Define target file path where screenshot will be saved
        String targetFilePath = System.getProperty("user.dir") +
                "/screenshots/" +
                tname + "-" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        // Use FileUtils to copy screenshot to target location
        org.apache.commons.io.FileUtils.copyFile(sourceFile, targetFile);
        // Return the screenshot path so it can be used in reports
        return targetFilePath;
    }
}




