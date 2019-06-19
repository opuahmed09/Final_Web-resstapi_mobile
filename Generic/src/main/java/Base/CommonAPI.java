package Base;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonAPI {

    //declaring perameter variable for cloud driver

    public static final String BROWSERSTACK_USERNAME = "";
    public static final String BROWSERSTACK_AUTOMATE_KEY = "";
    public static final String SAUCE_USERNAME = "";
    public static final String SAUCE_AUTOMATE_KEY = "";
    public static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_AUTOMATE_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    //initialize the webdriver
    public static WebDriver driver = null;

    //extent report setup
    public static ExtentReports extent;

    //setup cloud driver for running test on cloud browsers

    public static WebDriver getCloudDriver(String browser, String browserVersion, String platform, String envName) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("name", "Cloud Execution");
        capabilities.setCapability("browser", browser);

        capabilities.setCapability("browser_version", browserVersion);
        capabilities.setCapability("os", platform);
        capabilities.setCapability("os_version", "Mojave");

        if (envName.equalsIgnoreCase("Saucelabs")) {
            //resolution for Saucelabs
            driver = new RemoteWebDriver(new URL(SAUCE_URL), capabilities);
        } else if (envName.equalsIgnoreCase("Browserstack")) {
            capabilities.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), capabilities);
        }
        return driver;
    }

    // setup local driver for running the test case on local browsers

    public static WebDriver getLocalDriver(String browser, String platform) {
        if (platform.equalsIgnoreCase("mac") && browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\ahmed\\Desktop\\WebAutomationnn\\Generic\\src\\main\\resources\\drivers\\chromedriver");
        } else if (platform.equalsIgnoreCase("windows") && browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\ahmed\\Desktop\\WebAutomationnn\\Generic\\src\\main\\resources\\drivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (platform.equalsIgnoreCase("windows") && browser.equalsIgnoreCase("gecko")) {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\ahmed\\Desktop\\WebAutomationnn\\Generic\\src\\main\\resources\\drivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    //initializing paremeter
    @Parameters({"platform", "url", "browser", "cloud", "browserVersion", "envName"})

    //setting up the setupdriver for running the test case on either local or cloud driver as you want it.
    @BeforeClass
    public static WebDriver setupDriver(String platform, String url, @Optional("chrome-options") String browser, @Optional("false") boolean cloud, String browserVersion, String envName) throws MalformedURLException {
        if (cloud == true) {
            driver = getCloudDriver(browser, browserVersion, platform, envName);
        } else if (cloud == false) {

            if (browser.equalsIgnoreCase("chrome")) {
                driver = getLocalDriver(browser, platform);

            } else if (browser.equalsIgnoreCase("chrome-options")) {
                getChrpmeOptions();
            }
        }
        driver.get(url);
        return driver;
    }

    //for taking screenshot
    public static void captureScreenshot(WebDriver driver, String screenshotName) {

        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "/screenshots/" + screenshotName + " " + df.format(date) + ".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());

        }

    }
    //setting the chrome driver and explicitly and implicitly wait time and maximizing webpage frame size

    public static void getChrpmeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ahmed\\Desktop\\WebAutomationnn\\Generic\\src\\main\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    //screen shot method for taking screen shot and the driver is a instance of webdriver

    public static void getScreenshot(WebDriver driver) {
        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        String name = df.format(date);
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("src/screenshots/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertToString(String st) {
        String splitString = "";
        //splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }

    //for getting the text from the test case using this method

    public static List<String> getTextFromWebElements(String locator) {
        List<WebElement> element = new ArrayList<WebElement>();
        List<String> text = new ArrayList<String>();
        element = driver.findElements(By.cssSelector(locator));
        for (WebElement web : element) {
            String st = web.getText();
            text.add(st);
        }

        return text;
    }

    // using cssselector  to get the list of webelement by css

    public static List<WebElement> getListOfWebElementsByCss(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.cssSelector(locator));
        return list;
    }

    public static List<WebElement> getListOfWebElementsByCss(String locator, WebDriver driver1) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver1.findElements(By.cssSelector(locator));
        return list;
    }

    //Handling New Tabs or doing any test on new tab

    public static WebDriver handleNewTab(WebDriver driver1) {
        String oldTab = driver1.getWindowHandle();
        List<String> newTabs = new ArrayList<String>(driver1.getWindowHandles());
        newTabs.remove(oldTab);
        driver1.switchTo().window(newTabs.get(0));
        return driver1;
    }

    //handling any pop up on the current page you working on

    public static boolean isPopUpWindowDisplayed(WebDriver driver1, String locator) {
        boolean value = driver1.findElement(By.cssSelector(locator)).isDisplayed();
        return value;
    }

    //method for doing test case on web element using any kind of locators locators

    public static void typeOnElementNEnter(String locator, String value) {
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
        } catch (Exception ex1) {
            try {
                System.out.println("First Attempt was not successful");
                driver.findElement(By.name(locator)).sendKeys(value, Keys.ENTER);
            } catch (Exception ex2) {
                try {
                    System.out.println("Second Attempt was not successful");
                    driver.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
                } catch (Exception ex3) {
                    System.out.println("Third Attempt was not successful");
                    driver.findElement(By.id(locator)).sendKeys(value, Keys.ENTER);
                }
            }
        }
    }

    public static void typeOnElementNEnter(String locator, String value, WebDriver driver1) {
        try {
            driver1.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
        } catch (Exception ex1) {
            try {
                System.out.println("First Attempt was not successful");
                driver1.findElement(By.id(locator)).sendKeys(value, Keys.ENTER);
            } catch (Exception ex2) {
                try {
                    System.out.println("Second Attempt was not successful");
                    driver1.findElement(By.name(locator)).sendKeys(value, Keys.ENTER);
                } catch (Exception ex3) {
                    System.out.println("Third Attempt was not successful");
                    driver1.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
                }
            }
        }
    }

    // for getting report of each test

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }

        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(driver, result.getName());
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    //for closing the browser after each test

    @AfterClass
    public void quitDriver() {
        driver.close();
        driver.quit();
    }

    //This method will wait for any condition
    //@param Seconds The seconds that the thread must wait

    public void sleepFor(int Seconds) {
        try {
            Thread.sleep(Seconds * 2000);
        } catch (Exception e) {
        }
    }

    // this method will click on element using css locator

    public void clickOnCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    //this method will click on element by css xpath or id lopcators.

    public void clickOnElement(String locator) {
        try {
            driver.findElement(By.cssSelector(locator)).click();
        } catch (Exception ex1) {
            try {
                driver.findElement(By.xpath(locator)).click();
            } catch (Exception ex2) {
                driver.findElement(By.id(locator)).click();
            }
        }
    }

    // this method will type on input field by cssselector and id locator.

    public void typeOnInputField(String locator, String value) {
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(value);
        } catch (Exception ex) {
            driver.findElement(By.id(locator)).sendKeys(value);
        }

    }

    //this method for click on element by xpath
    public void clickByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    //this method for type on element using css selector
    public void typeByCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }

    //this method will enter the key after its being type on input by css selector

    public void typeByCssNEnter(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }

    //this method for type on element using xpath locator
    public void typeByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

    //this method will enter the key after its being type on input by xpath locator
    public void takeEnterKeys(String locator) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }

    //this method will clear the input field
    public void clearInputField(String locator) {
        driver.findElement(By.cssSelector(locator)).clear();
    }

    //this method will get the current page url
    public String getCurrentPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    //for navigating the driver to forward
    public void navigateForward() {
        driver.navigate().forward();
    }

    //this method will get the text from element  using css selector

    public String getTextByCss(String locator) {
        String st = driver.findElement(By.cssSelector(locator)).getText();
        return st;
    }

    //this method will get the text from element  using xpath locator
    public String getTextByXpath(String locator) {
        String st = driver.findElement(By.xpath(locator)).getText();
        return st;
    }

    //this method will get the text from elemnet using id
    public String getTextById(String locator) {
        return driver.findElement(By.id(locator)).getText();
    }

    //this method will get the text from element  using name

    public String getTextByName(String locator) {
        String st = driver.findElement(By.name(locator)).getText();
        return st;
    }
    public List<WebElement> getListOfWebElementsById(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.id(locator));
        return list;
    }

    public List<WebElement> getListOfWebElementsByXpath(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.xpath(locator));
        return list;
    }

    public List<String> getListOfString(List<WebElement> list) {
        List<String> items = new ArrayList<String>();
        for (WebElement element : list) {
            items.add(element.getText());
        }
        return items;
    }

    public void selectOptionByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public void mouseHoverByCSS(String locator) {
        try {
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        } catch (Exception ex) {
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }

    public void mouseHoverByXpath(String locator) {
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        } catch (Exception ex) {
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }

    //handling Alert
    public void okAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void cancelAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    //iFrame Handle
    public void iframeHandle(WebElement element) {
        driver.switchTo().frame(element);
    }

    public void goBackToHomeWindow() {
        driver.switchTo().defaultContent();
    }

    //get Links
    public void getLinks(String locator) {
        driver.findElement(By.linkText(locator)).findElement(By.tagName("a")).getText();
    }

    //Taking Screen shots
    public void takeScreenShot() throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(file, new File("screenShots.png"));
    }

    //Synchronization
    public void waitUntilClickAble(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitUntilVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilSelectable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }

    public void upLoadFile(String locator, String path) {
        driver.findElement(By.cssSelector(locator)).sendKeys(path);
        /* path example to upload a file/image
           path= "C:\\Users\\rrt\\Pictures\\ds1.png";
         */
    }

    public void clearInput(String locator) {
        driver.findElement(By.cssSelector(locator)).clear();
    }

    public void keysInput(String locator) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }

    //type
    public void typeOnCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }

    public void typeOnID(String locator, String value) {
        driver.findElement(By.id(locator)).sendKeys(value);
    }

    public void typeOnElement(String locator, String value) {
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(value);
        } catch (Exception ex1) {
            try {
                System.out.println("First Attempt was not successful");
                driver.findElement(By.name(locator)).sendKeys(value);
            } catch (Exception ex2) {
                try {
                    System.out.println("Second Attempt was not successful");
                    driver.findElement(By.xpath(locator)).sendKeys(value);
                } catch (Exception ex3) {
                    System.out.println("Third Attempt was not successful");
                    driver.findElement(By.id(locator)).sendKeys(value);
                }
            }
        }
    }

    public void clearField(String locator) {
        driver.findElement(By.id(locator)).clear();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void typeOnInputBox(String locator, String value) {
        try {
            driver.findElement(By.id(locator)).sendKeys(value, Keys.ENTER);
        } catch (Exception ex1) {
            System.out.println("ID locator didn't work");
        }
        try {
            driver.findElement(By.name(locator)).sendKeys(value, Keys.ENTER);
        } catch (Exception ex2) {
            System.out.println("Name locator didn't work");
        }
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
        } catch (Exception ex3) {
            System.out.println("CSS locator didn't work");
        }
    }
}
