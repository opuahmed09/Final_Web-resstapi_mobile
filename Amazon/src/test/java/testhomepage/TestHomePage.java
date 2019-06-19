package testhomepage;

import base.CommonAPI;
import homepage.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestHomePage extends CommonAPI {

    HomePage testhomepage;


    String url ="https://www.amazon.com/";

    @BeforeClass
    public void initialization(){
        testhomepage = PageFactory.initElements(driver, HomePage.class);
        driver.get(url);
    }

//    @Test
    public void clicksignIn(){
        testhomepage.clicksignIn();
        testhomepage.signin();

    }
//    @Test
    public void clickforgetpass(){
        testhomepage.clicksignIn();
        testhomepage.forgetpass();

    }
    @Test
    public void searchbooks(){
        testhomepage.purchase();
    }


}
