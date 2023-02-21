package techproed.tests.smoketests;

import org.testng.Assert;
import org.testng.annotations.Test;
import techproed.pages.BlueRentalHomePage;
import techproed.pages.BlueRentalLoginPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;

public class Day22_NegatifLoginTest {
    /*
    •Name:
        •US100208_Negative_Login
        •Description:
        •Kullanimda olmayan kullanıcı adi ve şifre ile giriş yapilamamali
        •Acceptance Criteria
        •
        •Customer email: fake@bluerentalcars.com
        •Customer password: fakepass
        •Error:
        •User with email fake@bluerentalcars.com not found
     */
    BlueRentalHomePage blueRentalHomePage;
    BlueRentalLoginPage blueRentalLoginPage;

    @Test
    public void US100208_Negative_Login() throws InterruptedException {
        blueRentalHomePage = new BlueRentalHomePage();
        blueRentalLoginPage = new BlueRentalLoginPage();

        Driver.getDriver().get(ConfigReader.getProperty("app_url"));
        blueRentalHomePage.loginLink.click();

        blueRentalLoginPage.emailBox.sendKeys(ConfigReader.getProperty("fake_email"));
        blueRentalLoginPage.passwordBox.sendKeys(ConfigReader.getProperty("fake_pass"));
        blueRentalLoginPage.loginButton.click();
        Thread.sleep(3000);

        Assert.assertEquals(blueRentalLoginPage.error_message_1.getText(),"User with email fake@bluerentalcars.com not found");
        Driver.closeDriver();
    }
}