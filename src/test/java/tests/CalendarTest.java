package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CalendarPage;


public class CalendarTest extends BaseTest {

    private CalendarPage calendarPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        super.setUp();
        driver.findElement(By.xpath("//nav/a[text()='Calendar']")).click();
        calendarPage = new CalendarPage(driver);
    }

    @Test(testName = "US1015: Choosing date from calendar")
    public void test01() {
        //driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[2]")).click();
        calendarPage.endDateInput.click();

        //driver.findElement(By.xpath("//div[contains(@class, 'keyboard-selected')]/following-sibling::div")).click();
        calendarPage.endDateCalendar.click();

        //driver.findElement(By.xpath("//button[text()='Submit']")).click();
        calendarPage.submitButton.click();

        //String startDate = driver.findElement(By.xpath("//div[@class='react-datepicker__input-container']/input")).getAttribute("value");
        String startDate = calendarPage.startDateText.getAttribute("value");

        //String endDate = driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[2]/input")).getAttribute("value");
        String endDate = calendarPage.endDateText.getAttribute("value");
        if (startDate.charAt(0) == '0')
            startDate = startDate.substring(1);

        if (endDate.charAt(0) == '0')
            endDate = endDate.substring(1);


        String expectedDays = "There are 1 days between " + startDate + " and " + endDate + "";

        //String actualDays = driver.findElement(By.id("num-days")).getText();
        String actualDays = calendarPage.actualDaysText.getText();

        Assert.assertEquals(actualDays, expectedDays);
    }
}
