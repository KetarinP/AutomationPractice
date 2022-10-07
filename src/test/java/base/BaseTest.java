package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentManager;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentManager extentManager;

    @BeforeSuite
    public void startReport(){
        extentManager = new ExtentManager();
        extentManager.createdReport();
    }
    @AfterSuite
    public void closeReport(){
        extentManager.closeReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver(Method method) {
        initializeDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigReader.readProperty("url"));
        extentManager.createTestReport(method);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    public void initializeDriver(String browser){
        switch (browser){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ketarin\\Desktop\\Selenium\\libs\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\Ketarin\\Desktop\\Selenium\\libs\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name");
        }
    }
}