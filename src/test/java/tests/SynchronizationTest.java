package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SynchronizationPage;

public class SynchronizationTest extends BaseTest {

    private SynchronizationPage page;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver.findElement(By.xpath("//nav/a[text()='Synchronization']")).click();
        page = new SynchronizationPage(driver);
    }

    @Test(testName = "US1013 - Display alert")
    public void testDisplayAlert(){

        //driver.findElement(By.xpath("//button[text()='Display alert']")).click();
        page.displayAlertBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.alertIsPresent());

        Assert.assertTrue(true);


    }
    @Test(testName = "US1014 - Text display")
    public void testTextDisplay(){

        //add texts
        //driver.findElement(By.id("input-text")).sendKeys("Automation Test");
        page.textInput.sendKeys("Automation Test");

        //click display
        //driver.findElement(By.xpath("//button[text()='Display']")).click();
        page.displayTextButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("text-display")), "Automation Test"));

        //compare texts
        Assert.assertTrue(true);
    }

}
