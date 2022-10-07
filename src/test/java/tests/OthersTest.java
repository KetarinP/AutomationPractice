package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.OthersPage;
import pages.SynchronizationPage;

public class OthersTest  extends BaseTest {

    private OthersPage page;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver.findElement(By.xpath("//nav/a[text()='Others']")).click();
        page = new OthersPage(driver);
    }

    @Test(testName = "US2001 - Disabled button is working as expected")
    public void testDisabledButton() {

        Assert.assertFalse(page.statusBtn.isEnabled());
    }

    @Test(testName = "US2002 - Enable option to toggle disabled button")
    public void testEnableButton() {

        page.toggleBtn.click();
        Assert.assertTrue(page.statusBtn.isEnabled());
    }
}
