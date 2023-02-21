package techproed.tests.smoketests;

import org.testng.Assert;
import org.testng.annotations.Test;
import techproed.pages.BlueRentalHomePage;
import techproed.pages.BlueRentalLoginPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;

public class Day22_NegatifLoginTest_Hom02 {
    /*
    Name:
US101122_Negative_Login
Description:
Geçerli email uzantısı olmadan kullanıcı girişi yapılamamalı
Acceptance Criteria:
Kullanici geçersiz email uzantısı yazdiginda, hata mesajini almalı
Error Message: email must be a valid email

Dogru email uzantisi girildiğinde hata mesajı alınmamalı
https://email-verify.my-addr.com/list-of-most-popular-email-domains.php
email-verify.my-addr.comemail-verify.my-addr.com
List of most popular email domains (by number of live emails)
Top 100 - List of most popular email domains according to number of live email addreses.
     */

    BlueRentalHomePage blueRentalHomePage;
    BlueRentalLoginPage blueRentalLoginPage;

    @Test
    public void US101122_Negative_Login() {
        blueRentalHomePage = new BlueRentalHomePage();
        blueRentalLoginPage = new BlueRentalLoginPage();

        Driver.getDriver().get(ConfigReader.getProperty("app_url"));

        blueRentalHomePage.loginLink.click();

        blueRentalLoginPage.emailBox.sendKeys(ConfigReader.getProperty("gecersiz_email"));
        Assert.assertEquals(blueRentalLoginPage.email_error.getText(), "email must be a valid email");
        Driver.closeDriver();

    }
        @Test
        public void US101122_Negative_Login2 () {

            blueRentalHomePage = new BlueRentalHomePage();
            blueRentalLoginPage = new BlueRentalLoginPage();


            Driver.getDriver().get(ConfigReader.getProperty("app_url"));
            blueRentalHomePage.loginLink.click();
            blueRentalLoginPage.emailBox.sendKeys(ConfigReader.getProperty("admin_email"));
            Assert.assertFalse(blueRentalLoginPage.email_error.isDisplayed());

    }
}

