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
    public void setUp(){
        driver.findElement(By.xpath("//nav/a[text()='Calendar']")).click();
        calendarPage = new CalendarPage(driver);
    }

    @Test(testName = "US1015: Choosing date from calendar")
    public void test01(){
        //clicking input field to see calendar pop-up
        //driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[2]")).click();
        //calendarPage.endDateInput.click();
        calendarPage.click(calendarPage.endDateInput);

        //selecting next day from the calendar
        //driver.findElement(By.xpath("//div[contains(@class, 'keyboard-selected')]/following-sibling::div")).click();
        //calendarPage.endDateCalendar.click();
        calendarPage.click(calendarPage.endDateCalendar);

        //click submit button
        //driver.findElement(By.xpath("//button[text()='Submit']")).click();
        //calendarPage.submitBtn.click();
        calendarPage.click(calendarPage.submitBtn);

        //String startDate = driver.findElement(By.xpath("//div[@class='react-datepicker__input-container']/input")).getAttribute("value");
        String startDate = calendarPage.startDateTxt.getAttribute("value");

        //String endDate = driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[2]/input")).getAttribute("value");
        String endDate = calendarPage.endDateTxt.getAttribute("value");

        if(startDate.charAt(0) == '0')
            startDate = startDate.substring(1);

        if(endDate.charAt(0) == '0')
            endDate = endDate.substring(1);

        String expectedTxt = "There are 1 days between " + startDate + " and " + endDate + "";
        //String actualTxt = driver.findElement(By.id("num-days")).getText();
        //String actualTxt = calendarPage.numberOfDaysTxt.getText();
        String actualTxt = calendarPage.getText(calendarPage.numberOfDaysTxt);

        Assert.assertEquals(actualTxt, expectedTxt);

    }


}