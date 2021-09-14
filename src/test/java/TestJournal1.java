import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;


@ExtendWith(ScreenshotExt.class)
public class TestJournal1 extends BasePage{
    private static final By test1Button = By.id("81112");
    private static final String k = "-k";
    private static final By textInput = By.id("79104");
    private static final By dischargeButton = By.id("84002");
    private static final By activateButton = By.id("78174");

    public TestJournal1(WebDriver driver) {

        super(driver);
        //Переход на окно проекта
        driver.get(Base_URL);
        //Ожидание (загрузка страницы, элементов)
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
    }

    JavascriptExecutor jse = (JavascriptExecutor) driver;

    @Step (value = "Активации тревоги")
    @Story("Проверка отображения в журнале пользовательского параметра тревоги")

    public void ActivateMessage() throws InterruptedException {
        //Поиск кнопки с переходом на окно теста 1
        driver.findElement(test1Button).click();
        //Ожидание (загрузка страницы, элементов)
        Thread.sleep(1000);

        //Поиск кнопки "Сброс" и клик по ней
        driver.findElement(dischargeButton).click();
        Thread.sleep(1000);

        //Поиск элемента "Текстовый ввод" и задание ему значения "-k"
        driver.findElement(textInput).sendKeys(k);

        //Поиск кнопки "Активность" и клик по ней (кнопка с фиксацией, положение вкл.)
        driver.findElement(activateButton).click();

        //Ожидание появления сообщения в Архивном журнале
        Thread.sleep(2000);
        //Поиск поля пользовательского параметра в журнале и получения из него значения
        WebElement text1 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(8)\")");
        //Сравнение что в журнале и что в текстовом вводе
        Assertions.assertEquals(text1.getText(), k);
        //Ожидание (чтобы успеть все увидеть глазами)
        Thread.sleep(3000);
    }

    @Step(value = "Квитирование")
    @Story("Проверка поля пользовательского параметра после квитирования")

    public void AckedMessage() throws InterruptedException{

        //Поиск кнопки "квит" у последнего появившегося сообщения в архивном журнале
        WebElement search_button = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelectorAll(\".ack\")[0]");
        //Клик по кнопке
        search_button.click();
        //Ожидание появления окна с предложением задать комментарий при квитировании
        Thread.sleep(2000);
        //Поиск поля ввода для комментария
        WebElement search_input1 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\"#inp\")");
        //Задание комментария
        search_input1.sendKeys("квитировано");

        //Поиск кнопки подтверждения квитирования "ок"
        WebElement search_button1 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\"#btnOk\")");
        //Подтверждением квитирования "ок"
        search_button1.click();

        //Ожидание нового сообщения в журнале
        Thread.sleep(1000);

        //Поиск у последнего сообщения поля Параметр_1
        WebElement text1 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(8)\")");
        //Из поля Параметр_1 в параметр String parameter_1
        String parameter_1 = text1.getText();
        //Поиск У последнего сообщения поля Комментарий
        WebElement text2 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(9)\")");
        //Из поля Комментарий в параметр String par1
        String comment = text2.getText();

        //Вывод параметров в консоль
        System.out.println("Параметр_1 :" + parameter_1);
        System.out.println("Комментарий :" + comment);

        //Сравнение
        Assertions.assertEquals(parameter_1,k);

    }
    @Step(value = "Перезагрузка клиента F5")
    @Story("Проверка поля пользовательского параметра после перезагрузки клиента")

    public void RefreshPage() throws InterruptedException{
        //Перезагрузка страницы
        driver.navigate().refresh();
        //Ожидание загрузки страницы и элементов
        Thread.sleep(4000);

        //Поиск у последнего сообщения поля Параметр_1
        WebElement text1 = (WebElement)
                jse.executeScript
                        ("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(8)\")");
        //Из поля Параметр_1 в параметр String parameter_1
        String parameter_1 = text1.getText();

        //Сравнение
        Assertions.assertEquals(parameter_1,k);
        //Поиск кнопки "Активность" и клик по ней (кнопка с фиксацией, положение выкл.)
        driver.findElement(activateButton).click();

        //Ожидание перед закрытием
        Thread.sleep(2000);
    }
}
