package techproed.utilities;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class ReusableMethods {

    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    //========Switching Window=====//
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    //========Hover Over=====//
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }


    //   HARD WAIT WITH THREAD.SLEEP
//   waitFor(5);  => waits for 5 second => Thread.sleep(5000)
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }
    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1))////Check for the element every 1 second
                .ignoring(NoSuchMethodException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    /**
     * Performs double click action on an element
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * @param element
     * @param check
     */

    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * Selects a random value from a dropdown list and returns the selected Web Element
     * @param select
     * @return
     */
    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + by);
        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementNotDisplayed(By by) {
        try {
            assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided WebElement is NOT displayed on page
     * fails if the element matching the WebElement is not found or not displayed
     * @paramWebElement
     */
    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element is displayed on page
     * fails if the element is not found or not displayed
     *
     * @param element
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }
//==============================DOSYA YUKLEME========================================
    /**
    *param filePath ABSOLUTE DOSYA YOLU
    * *ORNEK : ReusableMethods.uploadFilePath("/Users/techproed/Desktop/logo.jpeg")
     */
    public static void uploadFilePath(String filePath){
        try{
            ReusableMethods.waitFor(3);
            //Dosyayi bulmak icin kullanilir
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
            //ROBOT CLASS MASAUSTU UYGULAMALARI ILE ILETISIME GECMEK ICIN KULLANILIR
            Robot robot = new Robot();
            //pressing ctrl+v
            robot.keyPress(KeyEvent.VK_CONTROL);
            ReusableMethods.waitFor(3);
            robot.keyPress(KeyEvent.VK_V);
            ReusableMethods.waitFor(3);
            //releasing ctrl+v
            robot.keyRelease(KeyEvent.VK_CONTROL);
            ReusableMethods.waitFor(3);
            robot.keyRelease(KeyEvent.VK_V);
            ReusableMethods.waitFor(3);
            System.out.println("PASSED");
            //pressing enter
            ReusableMethods.waitFor(3);
            robot.keyPress(KeyEvent.VK_ENTER);
            ReusableMethods.waitFor(3);
            //releasing enter
            robot.keyRelease(KeyEvent.VK_ENTER);
            ReusableMethods.waitFor(3);
            System.out.println("ENTER");
        }catch (Exception e){
        }
    }


   // ==================================================================================================================
    // ================================   JUNIT REASUBLEMETHODS BASLANGHIC   ========================================
    // ==================================================================================================================
   //===============================================================================================================//
   //    MULTIPLE WINDOW
   //    1 parametre alir : Gecis Yapmak Istedigim sayfanin Title
//    ORNEK:
//    driver.get("https://the-internet.herokuapp.com/windows");
//    switchToWindow("New Window");
//    switchToWindow("The Internet")
//   public static void switchToWindow(String targetTitle) {
//       String origin = Driver.getDriver().getWindowHandle();
//       for (String handle : Driver.getDriver().getWindowHandles()) {
//           Driver.getDriver().switchTo().window(handle);
//           if (Driver.getDriver().getTitle().equals(targetTitle)) {
//               return;
//           }
//       }
//       Driver.getDriver().switchTo().window(origin);
//   }

    //    windowNumber sıfır (0)'dan başlıyor.
//    index numarasini parametre olarak alir
//    ve o indexli pencerece gecis yapar
    public static void switchToWindow(int windowNumber) {
        List<String> list = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(list.get(windowNumber));
    }

    //====================================================================================================================//
    /*   HARD WAIT:
    @param : second
*/
//    public static void waitFor(int seconds) {
//        try {
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    //================================================================================================================//
//    ACTIONS_RIGHT CLICK
    public static void rightClickOnElementActions(WebElement element) {
        Actions actions = new Actions( Driver.getDriver());
        actions.contextClick(element).perform();
    }

    //ACTIONS_DOUBLE CLICK
//    public static void doubleClick(WebElement element) {
//        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
//    }

    //    ACTIONS_HOVER_OVER
    public static void hoverOverOnElementActions(WebElement element) {
//        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).moveToElement(element).perform();
    }

    //    ACTIONS_SCROLL_DOWN
    public static void scrollDownActions() {
//        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).sendKeys(Keys.PAGE_DOWN).perform();
    }

    //    ACTIONS_SCROLL_UP
    public static void scrollUpActions() {
//        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).sendKeys(Keys.PAGE_UP).perform();
    }

    //    ACTIONS_SCROLL_RIGHT
    public static void scrollRightActions() {
        new Actions(Driver.getDriver()).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).perform();
    }

    //    ACTIONS_SCROLL_LEFT
    public static void scrollLeftActions() {
        new Actions(Driver.getDriver()).sendKeys(Keys.ARROW_LEFT).sendKeys(Keys.ARROW_LEFT).perform();
    }

    //    ACTIONS_DRAG_AND_DROP
    public static void dragAndDropActions(WebElement source, WebElement target) {
//        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).dragAndDrop(source, target).perform();
    }

    //    ACTIONS_DRAG_AND_DROP_BY
    public static void dragAndDropActions(WebElement source, int x, int y) {
//        Actions actions = new Actions(driver);
        new Actions(Driver.getDriver()).dragAndDropBy(source, x, y).perform();
    }

    //===============================================================================================================//

    //    DYNAMIC SELENIUM WAITS:
//===============Explicit Wait==============//
//    public static WebElement waitForVisibility(WebElement element, int timeout) {
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
//        return wait.until(ExpectedConditions.visibilityOf(element));
//    }
//
//    public static WebElement waitForVisibility(By locator, int timeout) {
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }

    //------------------------------YUKARDAKILER YETERLI----------------------------------------------------//
//    public static WebElement waitForClickablility(WebElement element, int timeout) {
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
//        return wait.until(ExpectedConditions.elementToBeClickable(element));
//    }
//
//    public static WebElement waitForClickablility(By locator, int timeout) {
//        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
//        return wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//
//    //COK KULLANILMAZ
//    public static void clickWithTimeOut(WebElement element, int timeout) {
//        for (int i = 0; i < timeout; i++) {
//            try {
//                element.click();
//                return;
//            } catch (WebDriverException e) {
//                waitFor(1);
//            }
//        }
//    }

    //    This can be used when a new page opens. Yeni sayfaya gecislerde kullanilabilir
//    public static void waitForPageToLoad(long timeout) {
//        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver driver) {
//                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//            }
//        };
//        try {
//            System.out.println("Waiting for page to load...");
//            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
//            wait.until(expectation);
//        } catch (Throwable error) {
//            System.out.println(
//                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
//        }
//    }

    //======Fluent Wait====
    // params : xpath of teh element , max timeout in seconds, polling in second
    public static WebElement fluentWait(String xpath, int withTimeout, int pollingEvery) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(withTimeout))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(pollingEvery))//Check for the element every 1 second
                .withMessage("Ignoring No Such Element Exception")
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return element;
    }

    //===================================================================================================================//
//   SCREENSHOTS                   // takeScreenShotOfPage Sayfanin secreenshot ini alir
    public void takeScreenShotOfPage() throws IOException {
//        1. Take screenshot
        File image = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);

//       2. Save screenshot
//        getting the current time as string to use in teh screenshot name, previous screenshots will be kept
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

//        Path of screenshot save folder               folder / folder    /file name
        String path = System.getProperty("user.dir") + "/test-output/Screenshots/" + currentTime + "image.png";
        FileUtils.copyFile(image, new File(path));
    }

    //=================================================================================================================//

    //    SCREENSHOT              //  takeScreenshotOfElement Sayfadaki istenilen elementin secreenshot ini alir
//    @params: WebElement

    public void takeScreenshotOfElement(WebElement element) throws IOException {
//        1. take screenshot
        File image = element.getScreenshotAs(OutputType.FILE);
//        2. save screenshot
//        path
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String path = System.getProperty("user.dir") + "/test-output/Screenshots/" + currentTime + "image.png";
        FileUtils.copyFile(image, new File(path));
    }
//================================================================================================================//

    //      SCROLLINTOVIEWJS
    public void scrollIntoViewJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    //    SAYFANIN EN ALTINA INEBILIRIZ
    public void scrollEndJS() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    //      SAYFANIN EN USTUNE CIKABILIRIZ
    public void scrollTopJS() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,-document.body.scrollHeight);");
    }

    //    Bu metot ile belirli bir elemente JS executor ile tiklanabilir
    public void clickByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    //   gitmis oldugum metni elemente yazdirir
//    bu method sendKeys metotuna bir alternatifdir.
//    sendKeys oncelikli tercihimizdir
    public void typeWithJS(WebElement element, String metin){
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('value','"+metin+"')",element);

    }

    //    input elementindeki degerleri(value) al
//   Belirli bir WebElement'in id değerini String olarak alır ve value attribute değerini String olarak döndürür
//    return
//    document HTML'E GIT
//    .getElementById('" + idOfElement + "') ID'si VERILEN ELEMENTI BUL
//    .value")
//    .toString();
    public  void getValueByJS(String idOfElement) {
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        String metin = js.executeScript("return document.getElementById('"+idOfElement+"').value").toString();
        System.out.println("Kutudaki deger : " + metin);
    }
    // ==================================================================================================================
    // ================================    JUNIT REASUBLEMETHODS BITIS        ========================================
    // ==================================================================================================================
}