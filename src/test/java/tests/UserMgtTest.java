package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import data.DataProviders;
import data.pojos.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.UserMgtPage;
import utils.BrowserUtils;

import java.util.List;
import java.util.Set;


public class UserMgtTest extends BaseTest {

    private UserMgtPage page;
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        super.setUp();
        driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();
        page = new UserMgtPage(driver);
    }

    @Test(testName = "US1010: Staging table view", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void test01(String role) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.phoneNumber().cellPhone();
        String email = faker.internet().emailAddress();

        //version 1
//        driver.findElement(By.id("Firstname")).sendKeys(firstName);
//        driver.findElement(By.id("Lastname")).sendKeys(lastName);
//        driver.findElement(By.id("Phonenumber")).sendKeys(phone);
//        driver.findElement(By.id("Email")).sendKeys(email);
//        driver.findElement(By.id("Select-role")).sendKeys(role);
//        driver.findElement(By.id("submit-btn")).click();
//        driver.findElement(By.id("submit-btn")).click();

        //version 2
//        page.firstNameInput.sendKeys(firstName);
//        page.lastNameInput.sendKeys(lastName);
//        page.phoneNumberInput.sendKeys(phone);
//        page.emailInput.sendKeys(email);
//        page.roleInput.sendKeys(role);
//        page.submitBtn.click();

        //version 3
        page.addNewUser(firstName, lastName, phone, email, role);

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

        User user = new User(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress(), role);

        //adding user to the table
        //page.addNewUser(firstName, lastName, phone, email, role);
        page.addNewUser(user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail(), user.getRole());

        //accessing db page
        driver.findElement(By.id("access-db-btn")).click();

        //switch to db window
        BrowserUtils.switchToNewWindow(driver);

        //validating user email doesn't exist
        String xpat = "//td[text='" + user.getEmail() + "']";
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

        page.addNewUser(firstName, lastName, phone, email, role);

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
        page.addNewUser(firstName, lastName, phone, email, role);

        driver.findElement(By.id("submit-table-btn")).click();

        List<WebElement> userInfo = driver.findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(userInfo.size(), 0);
    }

    @Test(testName = "US2004 - New user's password format", dataProvider = "roles", dataProviderClass = DataProviders.class)
    public void testPasswordFormat(String role) {
        //creating a test user
        Faker faker = new Faker();

        User user = new User(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress(), role);

        //adding user to the table
        page.addNewUser(user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail(), user.getRole());

        //click submit user info
        page.submitBtn.click();

        //submit table
        page.submitTableBtn.click();

        //accessing db page
        page.accessDbBtn.click();

        //switch to db window
        BrowserUtils.switchToNewWindow(driver);

        String searchEmails = user.getEmail();

        //loop through emails to search for input emaails
        for(int i = 0; i < page.emails.size(); i++) {

                if(searchEmails.equals(page.emails.get(i).getText())) {

                Assert.assertTrue(true);

                Assert.assertEquals(page.passwords.get(i).getText(), user.getFirstName().toLowerCase()
                                    + "." + user.getLastName().toLowerCase() + "$");
                break;
            }
        }

    }

}
