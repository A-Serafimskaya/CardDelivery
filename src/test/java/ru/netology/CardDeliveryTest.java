package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {


    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    // Задача 1

    @Test
    void shouldRegisterApplication() {

        LocalDate Date = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = Date.format(formatter);

        SelenideElement form = $("form");

        form.$("[data-test-id='city'] input").setValue("Екатеринбург");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").sendKeys(date);

        form.$("[data-test-id='name'] input").setValue("Иванов-Петров Петр");
        form.$("[data-test-id='phone'] input").setValue("+79251234567");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
    }

    // Задача 2

//    @Test
//    void testAutoFil() {
//
//        LocalDate Date = LocalDate.now().plusDays(7);
//        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd");
//        DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM", new Locale("ru"));
//        DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy", new Locale("ru"));
//
//        String day = Date.format(formatterDay);
//        String month = Date.format(formatterMonth);
//        String year = Date.format(formatterYear);
//
//        if (month.endsWith("я")) {
//            month = month.substring(0, month.length() - 1);
//        }
//
//        if (month.endsWith("а")) {
//            month = month.substring(0, month.length() - 1);
//        }
//
//
//        SelenideElement form = $("form");
//        form.$("[data-test-id='city'] input").setValue("Ек");
//        $(Selectors.withText("Екатеринбург")).click();
//        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
//        form.$("[data-test-id='date'] input").click();
//
//        SelenideElement actualPeriod = $("calendar-input__calendar-wrapper");
//        actualPeriod.find(Condition.text(month)).find(Condition.text(year)).find(Condition.text(day)).click();
//
//        actualPeriod.$(day).click();
//
//
//        form.$("[data-test-id='name'] input").setValue("Иванов-Петров Петр");
//        form.$("[data-test-id='phone'] input").setValue("+79251234567");
//        form.$("[data-test-id='agreement']").click();
//        form.$$("button").find(text("Забронировать")).click();
//        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
//    }

}
