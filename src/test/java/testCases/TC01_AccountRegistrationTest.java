package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC01_AccountRegistrationTest extends BaseClass {
    

    @Test(groups = {"sanity","master"})
    public void Verify_account_registration() {
    	try {
    	logger.info("******** TestCase TC01 Account Registration Test Started! ********");
    	
        HomePage hp = new HomePage(driver);
        
        hp.clickMyAccount();
        logger.info("Clicked on My Account Link");
        hp.clickregister();
        logger.info("Clicked on Registration Link");

        AccountRegistrationPage ar = new AccountRegistrationPage(driver);
        logger.info("Moving to Registration Page!");
        logger.info("Entering Customer Details");
        ar.setFirstName(randomeAlphabeticString());
        ar.setLastName(randomeAlphabeticString());
        
        String email = randomeAlphabeticString();
        ar.setEmail(email+"@gmail.com");
        logger.debug("Setting up Email: "+email+"@gmail.com");
        
        String telephone = randomeNumericString();
        ar.setTelephone(telephone);
        logger.debug("Setting up Telephone Number: "+telephone);
       
        String password = randomeAlphaNumeric();
        
        ar.setPassword(password);
        ar.setConfirmPassword(password);
        
        ar.setCheckBoxPrivacy();
        logger.info("Clicking on Continue button!");
        ar.clickContinue();
        
        logger.info("Validating the Confirmation Message!");
        String message = ar.getConfirmationMessage();
        Assert.assertEquals(message, "Your Account Has Been Created");
    	}
    	catch (AssertionError e) {
			logger.error("Test Failed!");
			logger.debug("Validation Failed! "+e.getMessage());
			throw e;
		}
    	finally {
    		logger.info("****** TestCase TC01 Account Registration Test Execution Completed! *******");	
		}
    }
    
    
}
