package ru.yandex.practicum.qascooter;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.qascooter.couriers.CourierClient;
import ru.yandex.practicum.qascooter.couriers.Courier;
import ru.yandex.practicum.qascooter.couriers.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginTest {
    private CourierClient courierClient;
    private CourierLogin courierLogin;
    private Courier courier;
    private int expectedCode;
    public CourierLoginTest(String login, String password, int code) {
        this.courierLogin = new CourierLogin(login, password);
        this.expectedCode = code;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courierClient = new CourierClient();
        courier = new Courier("courier_1", "Test1234", "John");
        courierClient.createCourier(courier);
    }

    @Parameterized.Parameters
    public static Object[] getLoginInfo() {
        return new Object[][]{
                {"", "", SC_BAD_REQUEST},
                {"", "Test1234", SC_BAD_REQUEST},
                {"courier_1", "", SC_BAD_REQUEST},
                {"courier_1", "Test", SC_NOT_FOUND}, // неверный пароль
                {"courier", "Test1234", SC_NOT_FOUND}, // неверный логин
                {"courier_1", "Test1234", SC_OK} //верный набор данных
        };
    }

    @After
    public void tearDown() {
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        courierClient.deleteCourier(courierClient.loginByCourier(courierLogin).extract().path("id"));
    }

    @DisplayName("Login.")
    @Description("Failed authorization attempts with incorrect data")
    @Test
    public void TryToLogin()  {
        ValidatableResponse response = courierClient.loginByCourier(courierLogin);
        assertEquals(expectedCode, response.extract().statusCode());
    }

}
