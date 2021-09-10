import MPSSoft.Smoke;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


@Smoke
@DisplayName("Проверка отображения пользовательского параметра тревоги в журнале")
@ExtendWith(ScreenshotExt.class)
public class Journal1 {

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeEach
    public void createDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver.set(new ChromeDriver());
    }

    @Test
    @Attachment
    @DisplayName("При активации и квитировании тревоги в журнале")

    public void test1() throws InterruptedException{
        TestJournal1 testJournal = new TestJournal1(driver.get());
        testJournal.ActivateMessage();
        testJournal.EqualsTextInputTextMessage();
        testJournal.AckedMessage();

    }
}
