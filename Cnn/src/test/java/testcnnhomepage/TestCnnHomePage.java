package testcnnhomepage;

import base.CommonAPI;
import cnnhomepage.CnnHomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCnnHomePage extends CommonAPI {
    String hpgurl="https://www.cnn.com/";
    CnnHomePage hmpage;

    @BeforeClass
    public void initialize(){
        driver.get(hpgurl);
        hmpage= PageFactory.initElements(driver,CnnHomePage.class);

    }
    @AfterClass
    public void init(){
        driver.get(hpgurl);
    }

    @Test
    public void menubtn(){
        hmpage.gotoMenu();
        driver.navigate().to(hpgurl);
    }

    @Test
    public void pageTech(){
        hmpage.getBusinessPage();
    }

    @Test
    public void glaceAtMarket(){
        hmpage.seeMarketaGlance();
    }
    @Test
    public void imageTesting1(){
        hmpage.image1();
    }
}
