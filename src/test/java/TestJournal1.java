import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;


@ExtendWith(ScreenshotExt.class)
public class TestJournal1 extends BasePage{
    private static final By test1Button = By.id("81112");
    private static final String k = "-k";
    private static final By textInput = By.id("79104");
    private static final By dischargeButton = By.id("84002");

    public TestJournal1(WebDriver driver) {

        super(driver);
        //Переход на окно проекта
        driver.get(Base_URL);
        //Ожидание (загрузка страницы, элементов)
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
    }

    Actions actions = new Actions(driver);
    JavascriptExecutor jse = (JavascriptExecutor) driver;

    @Step (value = "Активации тревоги")
    @Story("Проверка отображения в журнале пользовательского параметра тревоги")
    @Attachment
    public void ActivateMessage() throws InterruptedException {
        //Поиск кнопки с переходом на окно теста 1
        driver.findElement(test1Button).click();
        //Ожидание (загрузка страницы, элементов)
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        //Поиск кнопки "Сброс" и клик по ней
        driver.findElement(dischargeButton).click();
        //Поиск элемента "Текстовый ввод" и задание ему значения "-k"
        Thread.sleep(2000);
        driver.findElement(textInput).sendKeys(k);

        //Поиск элемента "Кнопка с фиксацией", активирует тревогу
        WebElement button1 = driver.findElement(By.id("78174"));
        //Нажатие на кнопку (без отжатия)
        actions.moveToElement(button1).click(button1).perform();
        //Ожидание появления сообщения в Архивном журнале
        Thread.sleep(3000);
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

        //Поиск у последнего сообщения полей Параметр_1 и Комментарий
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

        Assertions.assertEquals(parameter_1,k);
        //Ожидание
        Thread.sleep(3000);

        //Поиск элемента "Кнопка с фиксацией", активирует тревогу
        WebElement button1 = driver.findElement(By.id("78174"));
        //Отжатие кнопки Активности тревоги
        actions.moveToElement(button1).click(button1).perform();

        Thread.sleep(2000);
        //Ожидание перед закрытием
        Thread.sleep(1000);

    }
}
