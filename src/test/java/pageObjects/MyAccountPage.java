package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "(//div[@id='content']/h2)[1]")
    WebElement myAccountText;

    @FindBy(xpath = "(//a[text()='Logout'])[2]")
    WebElement buttonLogout;

    public boolean MyAccountPageExists() {
       try{
           return myAccountText.isDisplayed();
       } catch (Exception e) {
           return false;
       }
    }
    public void clickLogoutButton() {
        buttonLogout.click();
    }
}
