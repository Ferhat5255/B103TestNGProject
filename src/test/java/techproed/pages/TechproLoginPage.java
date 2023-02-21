package techproed.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import techproed.utilities.Driver;

public class TechproLoginPage {

    public TechproLoginPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath="//input[@id='exampleInputEmail1']")
    public WebElement username;

    @FindBy(xpath="//input[@id='exampleInputPassword1']")
    public WebElement password;

    @FindBy(xpath="//button[text()='Submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//h2[text()='Login Page']")
    public WebElement loginPage;

}
