package techproed.tests.excelautomation;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import techproed.pages.BlueRentalHomePage;
import techproed.pages.BlueRentalLoginPage;
import techproed.utilities.ConfigReader;
import techproed.utilities.Driver;
import techproed.utilities.ExcelUtils;
import techproed.utilities.ReusableMethods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Day23_ExcelLogin {

    BlueRentalHomePage blueRentalHomePage;
    BlueRentalLoginPage blueRentalLoginPage;
    ExcelUtils excelUtils;
    List<Map<String, String>> excelDatalari;

    // Bu metot login sayfasina gitmek icin kullanilacak
    public void login() {
        //Sayfaya Git
        Driver.getDriver().get(ConfigReader.getProperty("app_url"));
        //home page login tikla
        blueRentalHomePage = new BlueRentalHomePage();
        blueRentalLoginPage = new BlueRentalLoginPage();

//     ------------ SADECE ILK GIRIS--------------------------------
        //     loginLink butonu sadece ilk girisde sayfada gorunur
        //     Ikinci ve ucuncu girislerde sayfada gorunmeyeceginden NOSUCHELEMENTEXCEPTION alinir
        //      Biz bu exception u try catch kullanarak yakalariz ve test cases calismaya devan eder

        try {
            blueRentalHomePage.loginLink.click();
            ReusableMethods.waitFor(1);//1 saniye bekle
            //       LOGIN SAYFASINDAYIZ
        } catch (Exception e) {
        }

//     ----------------------SONRKI GIRISLER---------------------------
        try {
//            kullanici ID ye tikla         ---->>> try catch
            blueRentalHomePage.userID.click();
            ReusableMethods.waitFor(1);
//            Logout linkine tikla          ---->>> try catch
            blueRentalHomePage.logOutLink.click();
            ReusableMethods.waitFor(1);
//            OK e tikla                   ---->>> try catch
            blueRentalHomePage.OK.click();
            ReusableMethods.waitFor(1);
//            home page logine tikla        ---->>> try catch
            blueRentalHomePage.loginLink.click();
            ReusableMethods.waitFor(1);

        } catch (Exception e) {
        }


    }

    @Test
    public void customerLogin() throws IOException {
        String path = "./src/test/java/resources/mysmoketestdata.xlsx";
        //     ./ ONCEKI TUM DOSYALARI ICERIR. RELATIVE PATH.
        String sayfa = "customer_info";
//      DATALARI EXCEL UTILS METOTLARINI KULLANARAK BU SINIFA ALACAGIZ
        excelUtils = new ExcelUtils(path, sayfa);
//      EXCEL DATAARINI getDataList metotu ile cekelim
       excelDatalari = excelUtils.getDataList();
       //LOOP KULLANARAK DATALARI TEK TEK TEST CASEDE KULLAN
        for(Map<String,String> data :excelDatalari){
            login();   // Her Loopta Login sayfasina Goturecek
            ReusableMethods.waitFor(1);
            //kullanici adini gir
            blueRentalLoginPage.emailBox.sendKeys(data.get("username"));
            ReusableMethods.waitFor(1);
            // kullanici sifresini gir
            blueRentalLoginPage.passwordBox.sendKeys(data.get("password"));
            ReusableMethods.waitFor(1);
            //login butonuna tikla
            blueRentalLoginPage.loginButton.click();
            ReusableMethods.waitFor(1);
            //giris isleminin basarili oldugunu gostermek icin assertion
            ReusableMethods.verifyElementDisplayed(blueRentalHomePage.userID);
            ReusableMethods.waitFor(1);
            //her  bir giristen sonra ekran goruntusu aldik
            ReusableMethods.getScreenshot("EkranGoruntusu");


        }
    }
    @AfterMethod
    public void tearDown(){
        Driver.closeDriver();
    }
}
/*
sam.walker@bluerentalcars.com   c!fas_art
kate.brown@bluerentalcars.com   tad1$Fas
raj.khan@bluerentalcars.com v7Hg_va^
pam.raymond@bluerentalcars.com  Nga^g6!
------ILK GIRIS---------
HOME PAGE DEYIZ
home page logine tikla  ---->>> try catch
LOGIN PAGE DEYIZ
kullanici adini gir(excelden al)
kullanici sifresini git(excelden al)
login page login buttonuna tikla
------2. GIRIS-----
HOME PAGE DEYIZ
kullanici ID ye tikla         ---->>> try catch
Logout linkine tikla          ---->>> try catch
OK e tikla                   ---->>> try catch
home page logine tikla        ---->>> try catch

LOGIN PAGE DEYIZ
kullanici adini gir(excelden al)
kullanici sifresini git(excelden al)
login page login buttonuna tikla
------3. GIRIS---------
HOME PAGE DEYIZ
kullanici ID ye tikla
Logout linkine tikla
OK e tikla
home page logine tikla
LOGIN PAGE DEYIZ
kullanici adini gir(excelden al)
kullanici sifresini git(excelden al)
login page login buttonuna tikla
---------4. GIRIS------------
HOME PAGE DEYIZ
kullanici ID ye tikla
Logout linkine tikla
OK e tikla
home page logine tikla
LOGIN PAGE DEYIZ
kullanici adini gir(excelden al)
kullanici sifresini git(excelden al)
login page login buttonuna tikla
 */
