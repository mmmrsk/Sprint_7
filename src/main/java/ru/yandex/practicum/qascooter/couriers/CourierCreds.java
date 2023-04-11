package ru.yandex.practicum.qascooter.couriers;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCreds {

    private String login;
    private String password;

    static int length = 1;
    static boolean useLetters = true;
    static boolean useNumbers = true;
    static String randomSymbol = RandomStringUtils.random(length, true, true);

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds from(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CourierCreds returnCredentialsWithInvalidLogin (Courier courier) {
        return new CourierCreds(courier.getLogin() + randomSymbol, courier.getPassword());
    }

    public static CourierCreds returnCredentialsWithInvalidPassword (Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword() + randomSymbol);
    }
}