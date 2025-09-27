package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import static org.openqa.selenium.support.How.ID;

public class AccountRegistrationPage extends BasePage{

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    //There is another way of using the @FindBy Annotations using How Keyword
    @FindBy(how = ID, using="input-firstname")
    WebElement textBoxFirstName;

    @FindBy(how = How.ID, using = "input-lastname")
    WebElement textBoxLastName;

    @FindBy(id = "input-email")
    WebElement textBoxEmail;

    @FindBy(id = "input-telephone")
    WebElement textBoxTelephone;

    @FindBy(id = "input-password")
    WebElement textBoxPassword;

    @FindBy(id = "input-confirm")
    WebElement textBoxConfirmPassword;

    @FindBy(xpath = "//input[@value='0']")
    WebElement radioButtonSubscribe;

    @FindBy(xpath = "//input[@type='checkbox']")
    WebElement checkBoxPrivacy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement buttonContinue;

    @FindBy(xpath = "//h1[normalize-space()= 'Your Account Has Been Created!']")
    WebElement messageConfirmation;


    public void setFirstName(String firstName) {
        textBoxFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastname) {
        textBoxLastName.sendKeys(lastname);
    }

    public void setEmail(String email) {
        textBoxEmail.sendKeys(email);
    }

    public void setTelephone(String telephone) {
        textBoxTelephone.sendKeys(telephone);
    }

    public void setPassword(String password){
        textBoxPassword.sendKeys(password);
    }

    public void setConfirmPassword(String password) {
        textBoxConfirmPassword.sendKeys(password);
    }

    public void setCheckBoxPrivacy(){
        checkBoxPrivacy.click();
    }

    public void clickContinue(){
        buttonContinue.click();

        /*If the above method doesn't work, then we can use
        buttonContinue.submit();

        Actions act = new Actions(driver);
        act.moveToElement(buttonContinue).click();

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",buttonContinue);

        buttonContinue.sendKeys(Keys.RETURN);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonContinue)).click();  */
    }
    public String getConfirmationMessage() {
        try {
            return (messageConfirmation.getText());
        }
        catch (Exception e) {
            return (e.getMessage());
        }
    }
}
