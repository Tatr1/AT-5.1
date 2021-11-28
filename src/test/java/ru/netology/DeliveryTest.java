package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        String locale = "ru";
        var validUser = DataGenerator.Registration.generateUser();
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        System.out.println(validUser);
        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue(validUser.getCity());
        $$(".menu-item_type_block").filter(visible).find(text(validUser.getCity())).click();
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [class='input__control']").setValue(validUser.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $$("[class='button__content'] [class='button__text']").filter(visible).first().click();
        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(appear, Duration.ofSeconds(20)).shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[class='input__control'][placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__control']").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).shouldBe(appear, Duration.ofSeconds(15)).click();
        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(appear, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
