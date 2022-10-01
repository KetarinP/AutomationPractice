package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OthersPage extends BasePage {

    public OthersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "btn-status")
    public WebElement statusBtn;

    @FindBy(id = "toggle-btn")
    public  WebElement toggleBtn;

}
