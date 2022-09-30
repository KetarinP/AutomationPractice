package tests;

import com.github.javafaker.Faker;
import data.DataProviders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ParallelTest {

        @Test(testName = "US1010: Staging table view", dataProvider = "roles", dataProviderClass = DataProviders.class)
        public void test01(String role) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ketarin\\Desktop\\Selenium\\libs\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://automation.techleadacademy.io/#/home");

            driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String phone = faker.phoneNumber().cellPhone();
            String email = faker.internet().emailAddress();

            driver.findElement(By.id("Firstname")).sendKeys(firstName);
            driver.findElement(By.id("Lastname")).sendKeys(lastName);
            driver.findElement(By.id("Phonenumber")).sendKeys(phone);
            driver.findElement(By.id("Email")).sendKeys(email);

            Select select = new Select(driver.findElement(By.id("Select-role")));
            select.selectByVisibleText(role);
            driver.findElement(By.id("submit-btn")).click();

            Assert.assertEquals(driver.findElement(By.xpath("//td[1]")).getText(), firstName);
            Assert.assertEquals(driver.findElement(By.xpath("//td[2]")).getText(), lastName);
            Assert.assertEquals(driver.findElement(By.xpath("//td[3]")).getText(), phone);
            Assert.assertEquals(driver.findElement(By.xpath("//td[4]")).getText(), email);
            Assert.assertEquals(driver.findElement(By.xpath("//td[5]")).getText(), role);

            driver.quit();

        }
        @Test(testName = "US1010: Staging table view - DB check", dataProvider = "roles", dataProviderClass = DataProviders.class)
        public void test02(String role) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ketarin\\Desktop\\Selenium\\libs\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://automation.techleadacademy.io/#/home");

            driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();

            //creating a test user
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String phone = faker.phoneNumber().cellPhone();
            String email = faker.internet().emailAddress();

            //adding user to the table
            driver.findElement(By.id("Firstname")).sendKeys(firstName);
            driver.findElement(By.id("Lastname")).sendKeys(lastName);
            driver.findElement(By.id("Phonenumber")).sendKeys(phone);
            driver.findElement(By.id("Email")).sendKeys(email);

            Select select = new Select(driver.findElement(By.id("Select-role")));
            select.selectByVisibleText(role);
            driver.findElement(By.id("submit-btn")).click();

            //accessing db page
            driver.findElement(By.id("access-db-btn")).click();

            //switch to db window
            Set<String> allWindowHandles = driver.getWindowHandles();
            for(String each: allWindowHandles){
                if (!each.equals(driver.getWindowHandles()))
                    driver.switchTo().window(each);
            }
            //validating user email doesn't exist
            String xpat = "//td[text='" + email + "']";
            List<WebElement> elementList = driver.findElements(By.xpath(xpat));
            Assert.assertEquals(elementList.size(), 0);

            driver.quit();

        }
        @Test(testName = "US1011: Clear Staging table", dataProvider = "roles", dataProviderClass = DataProviders.class)
        public void testClearTable(String role) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ketarin\\Desktop\\Selenium\\libs\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://automation.techleadacademy.io/#/home");

            driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();

            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String phone = faker.phoneNumber().cellPhone();
            String email = faker.internet().emailAddress();

            driver.findElement(By.id("Firstname")).sendKeys(firstName);
            driver.findElement(By.id("Lastname")).sendKeys(lastName);
            driver.findElement(By.id("Phonenumber")).sendKeys(phone);
            driver.findElement(By.id("Email")).sendKeys(email);

            Select select = new Select(driver.findElement(By.id("Select-role")));
            select.selectByVisibleText(role);
            driver.findElement(By.id("submit-btn")).click();

            Assert.assertEquals(driver.findElement(By.xpath("//td[1]")).getText(), firstName);
            Assert.assertEquals(driver.findElement(By.xpath("//td[2]")).getText(), lastName);
            Assert.assertEquals(driver.findElement(By.xpath("//td[3]")).getText(), phone);
            Assert.assertEquals(driver.findElement(By.xpath("//td[4]")).getText(), email);
            Assert.assertEquals(driver.findElement(By.xpath("//td[5]")).getText(), role);
            driver.findElement(By.id("submit-btn")).click();

            driver.findElement(By.id("clear-btn")).click();

            String xpat = "//td[text='" + email + "']";
            List<WebElement> elementList = driver.findElements(By.xpath(xpat));
            Assert.assertEquals(elementList.size(), 0);

            driver.quit();

    }
}
