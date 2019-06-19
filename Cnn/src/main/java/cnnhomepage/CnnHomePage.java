package cnnhomepage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import reporting.TestLogger;

import static base.CommonAPI.convertToString;
import static base.CommonAPI.driver;

public class CnnHomePage  extend CommonAPI{
    @FindBy(css = "#menu")
    WebElement selectingMenu;

    @FindBy(xpath = "//div[@id='nav']/div/div[2]/a[2]")
    WebElement clickOnTech;

    @FindBy(xpath = "//a[@class='nav-menu-links__link' and text()='Markets']")
    WebElement marketInBuseness;

    @FindBy(css = "button:nth-child(2)>svg>path")
    WebElement tredeScrolling;

    @FindBy(id = "downshift-0-input")
    WebElement inputSearchInBusiness;

    @FindBy(xpath = "//img[@class='media__image']")
    WebElement testImage;

    @FindBy(xpath = "//section[@id='business-zone-1']/div[2]/div/div/ul/li/article/div/div/a/img")
    WebElement topTitleImage;


    public void gotoMenu() {
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Verify Menu Button in Business Page");
        selectingMenu.click();
    }

    public void getBusinessPage() {
        clickOnTech.click();
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Verify Tech Button in Business Page");
        driver.navigate().to("https://www.cnn.com/business");
    }

    public void seeMarketaGlance() {
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Verify Go To Market in Business Page");
        marketInBuseness.click();
        driver.navigate().to("https://www.cnn.com/business");
    }

    public void image1() {
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Verify IMAGE in Business Page");
        boolean img = testImage.isDisplayed();
        Assert.assertTrue(img);
        driver.navigate().to("https://www.cnn.com/business");
    }

    public void seeNikeTrade(String locator) throws Exception {
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Search for" + locator);
        inputSearchInBusiness.sendKeys(locator, Keys.ENTER);
        driver.navigate().to("https://www.cnn.com/business");
        Thread.sleep(2000);


    }
}
