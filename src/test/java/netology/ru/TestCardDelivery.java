package netology.ru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestCardDelivery {


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
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        // Проверяем, что текущая дата не равна ожидаемой
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=notification] .notification_theme_alfa-on-white").shouldNotBe(visible);
        $("[data-test-id=notification] .notification__content").shouldNotBe(Condition.exactText("Встреча успешно забронирована на " + expectedDate));
        //Selenide.sleep(10000);
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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);

        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);

        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);

        assertTrue(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата не наступает раньше чем через три дня от текущей даты");

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
    void positiveTesInvalidDate() throws InterruptedException {
        Selenide.open("http://localhost:9999");

        $("[data-test-id=city] .input__control").setValue("Казань");
        // Устанавливаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Ожидаемая дата для тестирования
        LocalDate expectedDate = LocalDate.of(2024, 5, 2);
        assertFalse(expectedDate.isAfter(currentDate.plusDays(2)), "Ожидаемая дата наступает раньше чем через три дня от текущей даты");

        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79094397835");
        $("[data-test-id=agreement]").click();
        SelenideElement continueButton =
                $(".button__text").shouldBe(visible);
        continueButton.click();

        $("[data-test-id=notification] .notification_theme_alfa-on-white").shouldNotBe(visible);
        $("[data-test-id=notification] .notification__content").shouldNotBe(Condition.exactText("Встреча успешно забронирована на " + expectedDate));

    }

    @Test
    void testCalendarCity() throws InterruptedException {
        Selenide.open("http://localhost:9999");

        // выбор города из выпадающего списка по первым двум буквам
        $("[data-test-id=city] .input__control").val("Ка");
        $(byText("Казань")).click();
        $("[data-test-id=city] .input__control").shouldHave(Condition.value("Казань"));

        // выбор даты по календарю
        $(".icon-button").click();

        LocalDate currentDate = LocalDate.now();
        // Получение даты на неделю вперед
        LocalDate futureDate = currentDate.plusWeeks(1);

        // Форматирование дат в строку с учетом формата календаря
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        String futureDateStr = futureDate.format(formatter);
        $(byText("9")).click();
    }


}
