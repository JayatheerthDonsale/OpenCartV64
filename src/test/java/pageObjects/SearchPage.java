package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Locale;

public class SearchPage extends BasePage {
    //Constructor
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    //Locators
    @FindBy(xpath = "//h4/a")
    WebElement searchItemConfirmText;

    @FindBy(xpath = "//img[@title='iPhone']")
    WebElement searchItem;

    @FindBy(xpath = "//button/span[text()='Add to Cart']")
    WebElement buttonAddToCart;

    @FindBy(xpath = "//div[contains(@class,'alert-success') and contains(text(),'Success')]")
    WebElement cartConfirmationText;

    //Action Methods
    public String isProductExists() {
        try {
           return searchItemConfirmText.getText().trim().toLowerCase();
        }
        catch (Exception e) {
            return "";
        }
    }
    public void clickOnItem() {
        searchItem.click();
    }
    public void clickOnButtonAddToCart() {
        buttonAddToCart.click();
    }
    public String getCartConfirmationText() {
        return cartConfirmationText.getText();
    }
}
