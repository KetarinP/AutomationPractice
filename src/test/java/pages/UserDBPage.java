package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UserDBPage{

    public UserDBPage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

    @FindBy(xpath = "//td[1]")
    public List<WebElement> firstNames;

    @FindBy(xpath = "//td[2]")
    public List<WebElement> lastNames;

    @FindBy(xpath = "//td[6]")
    public List<WebElement> passwords;

    @FindBy(xpath = "//td[5]")
    public List<WebElement> emails;
}
