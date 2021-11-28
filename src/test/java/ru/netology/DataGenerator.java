package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {
    private DataGenerator() {
    }

    static private Faker faker = new Faker(new Locale("ru"));

     public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
      City randomCity = new City();
        String city = randomCity.randomChoiceCity();
        return city;
    }

    public static String generateName() {
         String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone() {
       String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser() {
          UserInfo user = new UserInfo();
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city = generateCity();
        String name = generateName();
        String phone = generatePhone();

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }


    }

}

