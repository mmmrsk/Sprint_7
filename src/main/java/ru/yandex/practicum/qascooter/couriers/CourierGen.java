package ru.yandex.practicum.qascooter.couriers;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGen {

    static int length = 8;
    static boolean useLetters = true;
    static boolean useNumbers = true;

    // Метод создания валидного курьера со всеми полями
    public static Courier getValidAllFields() {

        String randomLogin = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomFirstName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(randomLogin, randomPassword, randomFirstName);
    }

    // Метод создания валидного курьера без имени: firstName = null
    public static Courier getValidWithNameNull() {

        String randomLogin = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(randomLogin, randomPassword, null);
    }

    // Метод создания валидного курьера без имени: firstName = ""
    public static Courier getValidWithNameEmpty() {

        String randomLogin = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(randomLogin, randomPassword, "");
    }

    // Метод создания невалидного курьера без логина: login = null
    public static Courier getInvalidWithLoginNull() {

        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomFirstName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(null, randomPassword, randomFirstName);
    }

    // Метод создания невалидного курьера без логина: login = ""
    public static Courier getInvalidWithLoginEmpty() {

        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomFirstName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier("", randomPassword, randomFirstName);
    }

    // Метод создания невалидного курьера без пароля: password = null
    public static Courier getInvalidWithPasswordNull() {

        String randomLogin = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomFirstName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(randomLogin, null, randomFirstName);
    }

    // Метод создания невалидного курьера без пароля: password = ""
    public static Courier getInvalidWithPasswordEmpty() {

        String randomLogin = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomFirstName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new Courier(randomLogin, "", randomFirstName);
    }
}