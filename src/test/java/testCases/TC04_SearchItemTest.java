package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;


public class TC04_SearchItemTest extends BaseClass {
    @Test
    public void SearchItemTest() {
        logger.info("*****TC04_SearchItemTest Started*****");
        try{
            HomePage homepage = new HomePage(driver);
            homepage.enterSearchItem("iphone");
            homepage.clickSearchButton();
            logger.info("Searching for Iphone");
            Thread.sleep(3000);

            SearchPage sp = new SearchPage(driver);
            String expectedProductName = "iphone";

            String actualProduct = sp.isProductExists();
            Assert.assertEquals(actualProduct.trim().toLowerCase(), expectedProductName.trim().toLowerCase(), "Product Not Found! ");
        }
        catch (Exception e) {
            Assert.fail();
        }
        finally {
            logger.info("*****TC04_SearchItemTest Ends!*****");
        }
    }
}
