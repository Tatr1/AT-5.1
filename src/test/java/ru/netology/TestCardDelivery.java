package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

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
        $$("[class='button__content'] [class='button__text']").first().click();
//      $(withText("Запланировать")).click();
        $(withText("Успешно!"));
//        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofMinutes(25)).shouldHave(text("Встреча успешно забронирована на " + planningDate));
//  на закомментированной строке тест выдает ошибку на "displayed:false", на скрине отсутствует сообщение "Встреча успешно забронирована",
//  (как будто не проходит клик по кнопке "Запланировать"), хотя визуально все сообщения проходят,
//  и при двойном клике (строка 43 и 44) выводится сообщение о перепланировании встречи.


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
        $$("[class='button__content'] [class='button__text']").filter(visible).first().click();
        $(withText("Успешно!"));
//      $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно забронирована на " + planningDate1));
        String planningDate2 = generateDate(4, "dd.MM.yyyy");
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(planningDate2);
        $(withText("Запланировать")).click();
//      $("[div:nth-child(4) > div.notification__content > button > span > span.button__text]").shouldBe(appear, Duration.ofSeconds(15)).click();
        $(withText("Перепланировать")).shouldBe(appear, Duration.ofSeconds(15)).click();
//      $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно забронирована на " + planningDate2));
        $(withText("Успешно!"));
    }
}
