package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC03_LoginTestDDT extends BaseClass {

    @Test(dataProvider="logindata",dataProviderClass =  DataProviders.class,groups = {"master"})
    public void Verify_LoginDDT(String email,String password,String expectedvalue){
    logger.info("***** Starting TC03_LoginTestDDT *****");
    try {
        HomePage hp = new HomePage(driver);
        hp.clickMyAccount();
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.setEmailAddress(email);
        lp.setPassword(password);
        lp.clickSubmitButton();

        MyAccountPage mc = new MyAccountPage(driver);
        boolean value = mc.MyAccountPageExists();

        if (expectedvalue.equalsIgnoreCase("Valid")) {
            if (value == true) {
                mc.clickLogoutButton();
                Assert.assertTrue(true);

            } else {
                mc.clickLogoutButton();
                Assert.assertTrue(false);
            }
        }
        if (expectedvalue.equalsIgnoreCase("Invalid")) {
            if (value == true) {
                mc.clickLogoutButton();
                Assert.assertTrue(false);
            } else {
                Assert.assertTrue(true);
            }
        }
    }
    catch (Exception e) {
        Assert.fail();
    }
    finally {
        logger.info("***** End TC03_LoginTestDDT *****");
    }
    }

}
