package MPSSoft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


/**Тест 2. Архивный журнал. Квитирование.
 * Необходимо проверить, что после квитирования сообщения от тревоги с пользовательским параметром, поле пользовательского параметра в журнале не будет пустым
 * Bug 21358: При квитировании появляется сообщение с пустым полем пользовательского параметра тревоги
 */


public class Acked {
    WebDriver driver;

    @BeforeEach
    public void start(){
        //Открываем браузер
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
    }
    @AfterEach
    public void finish(){
        //Закрываем браузер
        driver.quit();
    }
    @Test
    public void test() throws InterruptedException{

        driver = new ChromeDriver();
        //Переходим на тестируемую страницу
        driver.get("http://10.30.5.57:8043/?77866");
        //Ожидаем
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Ждем загрузки страницы (элементов на станице)
        Thread.sleep(3000);

        //Находим элемент "Текстовый ввод" для задания пользовательского параметра тревоги (Параметр_1)
        WebElement search_input = driver.findElement(By.id("79104"));
        //Передаем в пользовательский параметр (Параметр_1) значение
        String k = "к-";
        search_input.sendKeys(k);
        //Находим элемент "Кнопка с фиксацией", которая активирует тревогу
        WebElement button1 = driver.findElement(By.id("78174"));
        Actions actions = new Actions(driver);
        //Нажимаем на кнопку, но пока не отпускаем
        actions.moveToElement(button1).click(button1).perform();
        //Ждем появления сообщения в Архивном журнале
        Thread.sleep(1000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        //находим кнопку "квит" у последнего появившегося сообщения в архивном журнале
        WebElement search_button = (WebElement) jse.executeScript("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelectorAll(\".ack\")[0]");
        //Кликаем по кнопке
        search_button.click();
        //Ожидаем появления окна с предложением задать комментарий при квитировании
        Thread.sleep(2000);
        //Находим поле ввода для комментария
        WebElement search_input1 = (WebElement) jse.executeScript("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\"#inp\")");
        //Задаем комментарий
        search_input1.sendKeys("квитировано");

        //Находим кнопку подтверждения квитирования "ок"
        WebElement search_button1 = (WebElement) jse.executeScript("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\"#btnOk\")");
        //Подтверждаем квитирование "ок"
        search_button1.click();

        //Ждем новое сообщения в журнале
        Thread.sleep(1000);

        //У последнего сообщения находим поле Параметр_1 и Комментарий
        WebElement text1 = (WebElement) jse.executeScript("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(8)\")");
        //Помещаем текст поля Параметр_1 в параметр String par
        String parameter_1 = text1.getText();
        //У последнего сообщения находим поле Комментарий
        WebElement text2 = (WebElement) jse.executeScript("return document.querySelector(\"#\\\\37 7958\").shadowRoot.querySelector(\" td:nth-child(9)\")");
        //Помещаем текст поля Комментарий в параметр String par1
        String comment = text2.getText();

        //Выводим параметры в консоль
        System.out.println("Параметр_1 :" + parameter_1);
        System.out.println("Комментарий :" + comment);

        Assertions.assertEquals(parameter_1,"к-");
        //Снова ожидание
        Thread.sleep(5000);
        //Отпускаем кнопку Активности сообщения
        actions.moveToElement(button1).click(button1).perform();
        //Снова ожидание
        Thread.sleep(1000);
        //Закрываем браузер
        //driver.quit();
    }
}
