package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC05_AddToCartTest extends BaseClass {
    @Test
    public void addToCartTest() {
        String expectedProduct = "MacBook";
        logger.info("*****Starting TC05_AddToCartTest*****");
        try {

            HomePage hp = new HomePage(driver);
            hp.enterSearchItem(expectedProduct);
            hp.clickSearchButton();

            logger.info("Searching for MacBook");

            SearchPage sp =new SearchPage(driver);
            String actualProductName = sp.isProductExists();

            Assert.assertEquals(actualProductName,expectedProduct.trim().toLowerCase(),"Product Not Found! ");
            logger.info("Searched Product Exists! ");

            sp.clickOnButtonAddToCart();
            String successmessage = sp.getCartConfirmationText();

            Assert.assertTrue(successmessage.contains((expectedProduct)), "Product was Not Added to Cart! ");
            logger.info("Product was Successfully Added to Cart! ");

        } catch (Exception e) {
            logger.error("Error in adding to cart! {}", e.getMessage());
            Assert.fail();
        }
        finally {
            logger.info("*****Ending TC05_AddToCartTest*****");
        }
    }
}
