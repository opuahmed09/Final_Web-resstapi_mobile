package testcitybankhomepage;

import base.CommonAPI;
import citybankhomepage.CItybankHomapge;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCitybankHomepage extends CommonAPI {
    CItybankHomapge testcityhompage;
    String url="https://online.citi.com";

    @BeforeClass
    public void initialization(){
        testcityhompage = PageFactory.initElements(driver, CItybankHomapge.class);
        driver.get(url);
    }
    @AfterClass
    public void quitbwsr(){
        driver.get(url);
    }
    @Test
    public void usernamefield(){
        testcityhompage.setUsernamefield();

    }

}
