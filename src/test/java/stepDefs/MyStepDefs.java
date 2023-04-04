package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepDefs {
    WebDriver driver;
    WebDriverWait wait;

    Actions actions;

    @Given("I open the browser")
    public void iOpenTheBrowser() {

        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();
    }

    @Given("I have accepted cookies")
    public void iHaveAcceptedCookies() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler"))).click();
    }

    @Given("I input email {string}")
    public void iInputEmail(String email) {
        WebElement emailElement = driver.findElement(By.id("email"));
        emailElement.sendKeys(email);
    }

    @Given("I input username {string}")
    public void iInputUsername(String username) throws InterruptedException {
        WebElement usernameElement = driver.findElement(By.id("new_username"));
        usernameElement.click();
        usernameElement.clear();
        usernameElement.sendKeys(username);
    }

    @Given("I input password {string}")
    public void iInputPassword(String password) {
        WebElement passwordElement = driver.findElement(By.id("new_password"));
        passwordElement.sendKeys(password);
    }

    @When("I press sign up")
    public void iPressSignUp() {
        WebElement signUpElement = driver.findElement(By.xpath("//*[@id='create-account-enabled']"));
        actions = new Actions(driver);
        actions.moveToElement(signUpElement).perform();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(signUpElement)).click();

    }

    @Then("I made an account {string}")
    public void iMadeAnAccount(String result) {
        if (result.equals("success")) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.titleIs("Success | Mailchimp"));
            String actual = driver.getTitle();
            String expected = "Success | Mailchimp";
            assertEquals(actual, expected);

        } else if (result.equals("fail")) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class='invalid-error']")));
            String actual = element.getText();
            String expected = checkForErrors((actual));
            assertEquals(actual, expected);
        }
    }

    private String checkForErrors(String error) {
        String result;
        switch (error) {
            case "Great minds think alike - someone already has this username. If it's you, log in.":
                result = error;
            case "An email address must contain a single @.":
                result = error;
            case "Enter a value less than 100 characters long":
                result = error;
            default:
                result = "Error not found";
        }
        return result;
    }

    @After
    public void destroy() {
        driver.close();
        driver.quit();
    }


}

