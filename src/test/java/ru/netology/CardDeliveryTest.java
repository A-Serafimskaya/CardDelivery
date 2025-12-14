package ru.netology;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.Keys.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;


public class CardDeliveryTest {

    public static String shouldGenerateDate(int daysToAdd, String pattern) {
        LocalDate date = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }


    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    // Задача 1

    @Test
    void shouldRegisterApplication() {

        SelenideElement form = $("form");

        String date = shouldGenerateDate(3, "dd.MM.yyyy");

        form.$("[data-test-id='city'] input").setValue("Екатеринбург");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        form.$("[data-test-id='date'] input").sendKeys(date);

        form.$("[data-test-id='name'] input").setValue("Иванов-Петров Петр");
        form.$("[data-test-id='phone'] input").setValue("+79251234567");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(text("Забронировать")).click();

        SelenideElement successMessage = $("[data-test-id='notification'] .notification__title");
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
        successMessage.shouldHave(Condition.exactText("Успешно!"));

        SelenideElement meetingDate = $("[data-test-id='notification'] .notification__content");
        meetingDate.shouldBe(Condition.visible, Duration.ofSeconds(15));
        meetingDate.shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
    }


//    Задача 2

    @Test
    void testAutoFil() {

        SelenideElement form = $("form");
        String date = shouldGenerateDate(4, "dd.MM.yyyy");

        form.$("[data-test-id='city'] input").setValue("Ек");

        $$(".menu-item_type_block").find(Condition.text("Екатеринбург")).click();

        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").click();

        if (!shouldGenerateDate(3, "MM").equals(shouldGenerateDate(7, "MM"))) {

            $(".calendar__arrow").click();


            $$(".calendar__layout").find(Condition.text(shouldGenerateDate(7, "d"))).click();
            form.$("[data-test-id='name'] input").setValue("Иванов-Петров Петр");
            form.$("[data-test-id='phone'] input").setValue("+79251234567");
            form.$("[data-test-id='agreement']").click();
            form.$$("button").find(text("Забронировать")).click();

            SelenideElement successMessage = $("[data-test-id='notification'] .notification__title");
            successMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
            successMessage.shouldHave(Condition.exactText("Успешно!"));

            SelenideElement meetingDate = $("[data-test-id='notification'] .notification__content");
            meetingDate.shouldBe(Condition.visible, Duration.ofSeconds(15));
            meetingDate.shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
        }
    }
}



