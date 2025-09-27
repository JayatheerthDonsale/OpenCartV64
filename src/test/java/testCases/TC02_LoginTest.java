package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC02_LoginTest extends BaseClass {
    @Test(groups = {"regression","master"})
    public void LoginTest(){
        logger.info("***** Starting TC02_LoginTest *****");
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);
            lp.setEmailAddress(property.getProperty("email"));
            lp.setPassword(property.getProperty("password"));
            lp.clickSubmitButton();

            MyAccountPage ma = new MyAccountPage(driver);
            Boolean value = ma.MyAccountPageExists();

            Assert.assertEquals(value, true);
        }
        catch (Exception e) {
            Assert.fail();
        }
        finally {
            logger.info("***** Ending TC02_LoginTest *****");
        }
    }

}
