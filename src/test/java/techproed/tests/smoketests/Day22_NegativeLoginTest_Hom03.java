package techproed.tests.smoketests;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import techproed.pages.BlueRentalHomePage;
import techproed.pages.BlueRentalLoginPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;

public class Day22_NegativeLoginTest_Hom03 {
    /*
    Name:
US101201_Negative_Login
Description:
Geçerli giriş yapmadan rezervasyon yapamamalı
Acceptance Criteria:
Kullanici arac bilgilerini girip CONTINUE RESERVATION butonuna tikladiginda
Ve giris yapilmadiginda
Hata mesaji almali : Please first login
Giris yapildiginda hata mesaji alınmamalı

     */
    BlueRentalLoginPage blueRentalLoginPage;
    BlueRentalHomePage blueRentalHomePage;

    @Test
    public void US101201_Negative_Login() throws InterruptedException {
        blueRentalLoginPage = new BlueRentalLoginPage();
        blueRentalHomePage = new BlueRentalHomePage();

        Driver.getDriver().get(ConfigReader.getProperty("app_url"));

        Select select = new Select(blueRentalHomePage.carDdm);
        select.selectByIndex(1);

       blueRentalHomePage.pickUpLocation.sendKeys(ConfigReader.getProperty("pickUpLocation"));
//        Select select1 = new Select(blueRentalHomePage.pickUpLocation);
//        select1.selectByIndex(3);

       blueRentalHomePage.dropOfLocation.sendKeys(ConfigReader.getProperty("dropOfLocation"));
//        Select select2 = new Select(blueRentalHomePage.dropOfLocation);
//        select2.selectByIndex(2);

        Thread.sleep(5000);

        blueRentalHomePage.pickUpDate.sendKeys(ConfigReader.getProperty("pickUpDate"));
        blueRentalHomePage.pickUpTime.sendKeys(ConfigReader.getProperty("pickUpTime"));
        blueRentalHomePage.dropOffDate.sendKeys(ConfigReader.getProperty("dropOffDate"));
        blueRentalHomePage.dropOffTime.sendKeys(ConfigReader.getProperty("dropOffTime"));
        blueRentalHomePage.buttonReservation.click();
Thread.sleep(5000);
        Assert.assertEquals(blueRentalHomePage.pleaseLogin.getText(),"Please first login");
        Thread.sleep(5000);
    }

    @Test
    public void US101201_Negative_Login2() throws InterruptedException {
        blueRentalHomePage = new BlueRentalHomePage();
        blueRentalLoginPage = new BlueRentalLoginPage();

        Driver.getDriver().get(ConfigReader.getProperty("app_url"));
        blueRentalHomePage.loginLink.click();

        blueRentalLoginPage.emailBox.sendKeys(ConfigReader.getProperty("admin_email"));
        blueRentalLoginPage.passwordBox.sendKeys(ConfigReader.getProperty("admin_sifre"));
        blueRentalLoginPage.loginButton.click();


        Select select = new Select(blueRentalHomePage.carDdm);
        select.selectByIndex(1);

        blueRentalHomePage.pickUpLocation.sendKeys(ConfigReader.getProperty("pickUpLocation"));
        blueRentalHomePage.dropOfLocation.sendKeys(ConfigReader.getProperty("dropOfLocation"));
        blueRentalHomePage.pickUpDate.sendKeys(ConfigReader.getProperty("pickUpDate"));
        blueRentalHomePage.pickUpTime.sendKeys(ConfigReader.getProperty("pickUpTime"));
        blueRentalHomePage.dropOffDate.sendKeys(ConfigReader.getProperty("dropOffDate"));
        blueRentalHomePage.dropOffTime.sendKeys(ConfigReader.getProperty("dropOffTime"));
        blueRentalHomePage.buttonReservation.click();
Thread.sleep(5000);
      Assert.assertTrue(blueRentalHomePage.pleaseSelect.isDisplayed());

    }
}
