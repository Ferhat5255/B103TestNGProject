package techproed.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import techproed.pages.TechproHomePage;
import techproed.pages.TechproLoginPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;

public class Day21_TechproLoginTest {
    @Test(groups="regresion-tests")
    public void techproLoginTest() throws InterruptedException {
        TechproLoginPage techproLoginPage = new TechproLoginPage();
        TechproHomePage techproHomePage = new TechproHomePage();

        Driver.getDriver().get(ConfigReader.getProperty("techpro_test_url"));

        techproLoginPage.username.sendKeys(ConfigReader.getProperty("techpro_test_username"));
        techproLoginPage.password.sendKeys(ConfigReader.getProperty("techpro_test_password"));
        techproLoginPage.submitButton.click();

        //Login yapildi. Driver su an Home Page
        Assert.assertTrue(techproHomePage.homeHeader.isDisplayed());

        //Sayfadan cikis yap
        techproHomePage.homeLogoutButton.click();

        //cikis yaptigina dair assertion yap
        Assert.assertTrue(techproLoginPage.loginPage.isDisplayed());

        //Driver'i kapat
        Driver.closeDriver();



    }

    /*
    https://testcenter.techproeducation.com/index.php?page=form-authentication
Page object Model kullanarak sayfaya giriş yapildigini test edin
Sayfadan cikis yap ve cikis yapildigini test et
techproed
SuperSecretPassword
TechproLoginPage
userName
password
submitButton
TechproHomePage
homeHeader
homeLogoutButton
TEST:
Class: TechproLoginTest
Metot : loginTest()
     */
}
