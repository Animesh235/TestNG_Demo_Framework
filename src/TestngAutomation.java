import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestngAutomation {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser not supported");
        }

        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initialize Extent Reports
        extent = ExtentManager.createInstance();
    }

    @Test
    public void TestCase1() {
        test = ExtentManager.createTest("TestCase1", "Click on Men category");

        try {
            driver.findElement(By.xpath("//span[text() = 'Men']")).click();
            test.log(Status.PASS, "Successfully clicked on Men category");
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on Men category: " + e.getMessage());
        }
    }

    @Test
    public void TestCase2() {
        test = ExtentManager.createTest("TestCase2", "Click on Jackets subcategory");

        try {
            driver.findElement(By.xpath("//a[text() = 'Jackets']")).click();
            test.log(Status.PASS, "Successfully clicked on Jackets subcategory");
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on Jackets subcategory: " + e.getMessage());
        }
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }

        // Flush Extent Reports
        if (extent != null) {
            extent.flush();
        }
    }
}
