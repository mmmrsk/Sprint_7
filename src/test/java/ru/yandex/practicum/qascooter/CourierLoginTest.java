package ru.yandex.practicum.qascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.practicum.qascooter.couriers.Courier;
import ru.yandex.practicum.qascooter.couriers.CourierClient;
import ru.yandex.practicum.qascooter.couriers.CourierCreds;
import ru.yandex.practicum.qascooter.couriers.CourierGen;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        if(id != 0) {
            courierClient.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Check status code and order id return of POST /api/v1/courier/login")
    @Description("Positive test for POST /api/v1/courier/login with valid courier with all required fields filled in")
    public void courierCanLogin(){
        courier = CourierGen.getValidAllFields();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);

        id = loginResponse.extract().path("id");
        assertTrue(id != 0);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login")
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with invalid login")
    public void courierCanNotLoginWithFakeLogin() {
        courier = CourierGen.getValidAllFields();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.returnCredentialsWithInvalidLogin(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);

        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login")
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with invalid password")
    public void courierCanNotLoginWithFakePassword() {
        courier = CourierGen.getValidAllFields();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.returnCredentialsWithInvalidPassword(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);

        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login")
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with null login")
    public void courierCanNotLoginWithLoginNull(){
        courier = CourierGen.getInvalidWithLoginNull();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login") // имя теста
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with empty login")
    public void courierCanNotLoginWithLoginEmpty(){
        courier = CourierGen.getInvalidWithLoginEmpty();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login")
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with null password")
    public void courierCanNotLoginWithPasswordNull(){
        courier = CourierGen.getInvalidWithPasswordNull();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login")
    @Description("Negative test for POST /api/v1/courier/login with invalid courier with empty password")
    public void courierCanNotLoginWithPasswordEmpty(){
        courier = CourierGen.getInvalidWithPasswordEmpty();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier/login") // имя теста
    @Description("Negative test for POST /api/v1/courier/login with non-existing courier")
    public void notCreatedCourierCanNotLogin() {
        courier = CourierGen.getValidAllFields();
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);

        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = loginResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }
}