package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestCardDelivery {

    private Faker faker;

    String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldBeDeliveryCardIsBegining() {
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Москва");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(planningDate);
        $("[data-test-id='name'] [class='input__control']").setValue(name);
        $("[data-test-id='phone'] [class='input__control']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] [class='notification__content']").shouldHave(visible, Duration.ofSeconds(20)).shouldBe(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldBeDeliveryCardAnotherDay() {
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Москва");
        String planningDate1 = generateDate(3, "dd.MM.yyyy");
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(planningDate1);
        $("[data-test-id='name'] [class='input__control']").setValue(name);
        $("[data-test-id='phone'] [class='input__control']").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно забронирована на " + planningDate1));
        String planningDate2 = generateDate(4, "dd.MM.yyyy");
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(planningDate2);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).shouldBe(appear, Duration.ofSeconds(15)).click();
        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно забронирована на " + planningDate2));

    }
}
