import org.openqa.selenium.WebDriver;

public class BasePage {
    public static final String Base_URL = "http://127.0.0.1:8043";
    public WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }
}
