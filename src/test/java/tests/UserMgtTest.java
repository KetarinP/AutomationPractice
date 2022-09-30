package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import data.DataProviders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Set;


public class UserMgtTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        super.setUp();
        driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();
    }

    @Test(testName = "US1010: Staging table view", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void test01(String role) {
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
    }
    @Test(testName = "US1010: Staging table view - DB check", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void test02(String role) {
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
    }
    @Test(testName = "US1011: Clear Staging table", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void testClearTable(String role) {
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

        driver.findElement(By.id("clear-btn")).click();

        String xpat = "//td[text='" + email + "']";
        List<WebElement> elementList = driver.findElements(By.xpath(xpat));
        Assert.assertEquals(elementList.size(), 0);
    }
    @Test(testName = "US1012: Adding users to database", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void testSubmitTable(String role) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.phoneNumber().cellPhone();
        String email = faker.internet().emailAddress();

        //input user info
        driver.findElement(By.id("Firstname")).sendKeys(firstName);
        driver.findElement(By.id("Lastname")).sendKeys(lastName);
        driver.findElement(By.id("Phonenumber")).sendKeys(phone);
        driver.findElement(By.id("Email")).sendKeys(email);

        Select select = new Select(driver.findElement(By.id("Select-role")));
        select.selectByVisibleText(role);

        //submit to db access
        driver.findElement(By.id("submit-btn")).click();

        driver.findElement(By.id("submit-table-btn")).click();

        List<WebElement> userInfo = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(userInfo.size(), 0);
    }
}
