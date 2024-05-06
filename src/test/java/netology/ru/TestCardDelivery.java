package netology.ru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestCardDelivery {

    public String generateDate(long expectedDate, String pattern) {
        return LocalDate.now().plusDays(expectedDate).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        System.setProperty("selenide.browser", "chrome");
    }

    @Test
    void positiveTestCardsSending() throws InterruptedException {
        // закгрузить страницу
        // поиск элементов
        // взаимодействие с элементами
        open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + expectedDate), Duration.ofSeconds(15));
    }

    @Test
    void negativeTestCardsSendingEmptyField() throws InterruptedException {
        // закгрузить страницу
        // поиск элементов
        // взаимодействие с элементами
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue(" ");

        $("[data-test-id=name] .input__control").setValue(" ");
        $("[data-test-id=phone] .input__control").setValue(" ");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=city] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
        $("[data-test-id=name].input__control").shouldNotBe(visible);
    }

    @Test
    void negativeTestCityEngLayout() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Kazan");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=city] .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void negativeTestName() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue(" ");
        $("[data-test-id=phone] .input__control").setValue(" ");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=name] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
        $("[data-test-id=phone].input__control").shouldNotBe(visible);
    }

    @Test
    void negativeTestNameEngLayout() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Ivan Ivanov");
        $("[data-test-id=phone] .input__control").setValue(" ");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=name] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $("[data-test-id=phone].input__control").shouldNotBe(visible);
    }

    @Test
    void negativeTestNameEnteringNumbers() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("123456");
        $("[data-test-id=phone] .input__control").setValue(" ");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=name] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $("[data-test-id=phone].input__control").shouldNotBe(visible);
    }

    @Test
    void negativeTestPhone() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иван Иванов");
        $("[data-test-id=phone] .input__control").setValue(" ");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=phone] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void negativeTestPhoneWithoutPlus() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иван Иванов");
        $("[data-test-id=phone] .input__control").setValue("90998765768");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=phone] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeTestPhoneLetters() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иван Иванов");
        $("[data-test-id=phone] .input__control").setValue("fffff");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=phone] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeTestMoreNumberPhone() {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иван Иванов");
        $("[data-test-id=phone] .input__control").setValue("+8994333999993999");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=phone] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeTestCheckbox() throws InterruptedException {

        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");

        SelenideElement checkbox =
                $("[data-test-id=agreement] .checkbox__text").shouldBe(visible).shouldBe(enabled);

        assertFalse(checkbox.isSelected());

        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void negativeTesInvalidDate() throws InterruptedException {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        String expectedDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));


    }

    @Test
    void testCalendarCity() throws InterruptedException {
        Selenide.open("http://localhost:9999");

        // выбор города из выпадающего списка по первым двум буквам
        $("[data-test-id=city] .input__control").val("Ка");
        $$(".menu-item").find(Condition.text("Казань")).click();
        $("[data-test-id=city] .input__control").shouldHave(Condition.value("Казань"));

        $(".icon-button").click();


        String expectedDate = generateDate(30, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(expectedDate));
        $("[data-test-id='date'] input").click();

        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {
            $(".calendar__next-arrow").click();
        }

        $("[data-test-id=name] .input__control").val("Иванов Иван");
        $("[data-test-id=phone] .input__control").val("+79094397835");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + expectedDate));
    }
}