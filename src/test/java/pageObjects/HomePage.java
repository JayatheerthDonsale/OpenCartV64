package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    /*  The PageObjects class consists of 3 parts
    1. Constructor
    2. Locators
    3. Action Methods   */

    //Constructor as we have already created BasePage having Constructor, we will inherit the same here in this child class
    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Locators here we are locating elements by using @FindBy annotation
    @FindBy(xpath = "//span[text()='My Account']")
    WebElement linkMyaccount;

    @FindBy(xpath = "//a[text()='Register']")
    WebElement linkRegister;

    @FindBy(xpath = "//a[text()='Login']")
    WebElement linkLogin;

    @FindBy(xpath = "//div[@id='search']/input")
    WebElement textboxInputSearch;

    @FindBy (xpath = "//span[@class='input-group-btn']")
    WebElement searchButton;

    //Actions Methods
    public void clickMyAccount() {
        linkMyaccount.click();
    }
    public void clickregister() {
        linkRegister.click();
    }
    public void clickLogin() {
        linkLogin.click();
    }

    public void enterSearchItem(String searchItem) {
        textboxInputSearch.sendKeys(searchItem);
    }

    public void clickSearchButton() {
        searchButton.click();
    }
}
