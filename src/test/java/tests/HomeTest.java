package tests;

import base.BaseTest;
import data.DataProviders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentManager;

import java.util.List;


public class HomeTest {
    protected WebDriver driver;
    protected ExtentManager extentManager;
    @Test(testName = "Test title of the home page")
    public void testHomepage()  {

        String homePage = driver.findElement(By.id("title")).getText();
        Assert.assertTrue(homePage.contains("Automation with Selenium"));

    }

    @Test(testName = "Test navigation buttons")
    public void testNavBtns() {

        List<WebElement> navBtn = driver.findElements(By.xpath("//nav[@class='navbar nav1']/a"));
        for (WebElement i : navBtn) {
            Assert.assertTrue(i.isDisplayed());
        }

    }

    @Test(dataProvider = "nav buttons", dataProviderClass = DataProviders.class)
    public void test03(String navBtn){

        Assert.assertTrue(driver.findElement(
                By.xpath("//a[contains(@class,'navbar-brand ml-3') and text()='" + navBtn + "']")).isDisplayed());

    }
}
