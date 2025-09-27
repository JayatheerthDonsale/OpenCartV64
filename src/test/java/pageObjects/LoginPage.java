package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-email")
    WebElement textBoxEmail;

    @FindBy(id = "input-password")
    WebElement textBoxPassword;

    @FindBy(xpath = "//input[@type='submit']")
    WebElement buttonSubmit;

    public void setEmailAddress(String email) {
        textBoxEmail.sendKeys(email);
    }
    public void setPassword(String password) {
        textBoxPassword.sendKeys(password);
    }
    public void clickSubmitButton() throws InterruptedException {
       Thread.sleep(2000);
        buttonSubmit.click();
    }
}
