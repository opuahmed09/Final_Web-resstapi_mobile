package citybankhomepage;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import reporting.TestLogger;

public class CItybankHomapge extends CommonAPI {
    @FindBy(xpath ="//*[@id='usernameMasked']")
    WebElement usernamefield;
    @FindBy (xpath ="//*@id='password']")
    WebElement passwordfield;
    @FindBy(xpath = "//*[@id='signInBtn']")
    WebElement signonbtn;

    public void setUsernamefield(){
        TestLogger.log(getClass().getSimpleName() + "  " + convertToString(new Object() {
        }.getClass().getEnclosingMethod().getName()) + "-> " + "Click on UserName Field");
        usernamefield.click();
        usernamefield.sendKeys("myusernameisthis");

    }


}
