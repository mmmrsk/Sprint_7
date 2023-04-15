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

public class CreateCourierTest {

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
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Positive test for POST /api/v1/courier with valid courier with all required fields filled in")
    public void courierCanBeCreated(){
        courier = CourierGen.getValidAllFields();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);

        boolean message = createResponse.extract().path("ok");
        assertTrue(message);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Positive test for POST /api/v1/courier with valid courier with null name")
    public void courierCanBeCreatedWithoutNameNull(){
        courier = CourierGen.getValidWithNameNull();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);

        boolean message = createResponse.extract().path("ok");
        assertTrue(message);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Positive test for POST /api/v1/courier with valid courier with empty name")
    public void courierCanBeCreatedWithoutNameEmpty(){
        courier = CourierGen.getValidWithNameEmpty();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginByCourier(CourierCreds.from(courier));

       id = loginResponse.extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);

        boolean message = createResponse.extract().path("ok");
        assertTrue(message);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Negative test for POST /api/v1/courier with invalid courier with existing login")
    public void twoSameCouriersCanNotBeCreated() {
        courier = CourierGen.getValidAllFields();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);

        String expectedMessage = "Этот логин уже используется. Попробуйте другой.";
        String actualMessage = createResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Negative test for POST /api/v1/courier with invalid courier with null login")
    public void courierCanNotBeCreatedWithLoginNull(){
        courier = CourierGen.getInvalidWithLoginNull();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Negative test for POST /api/v1/courier with invalid courier with empty login")
    public void courierCanNotBeCreatedWithLoginEmpty(){
        courier = CourierGen.getInvalidWithLoginEmpty();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Negative test for POST /api/v1/courier with invalid courier with null password")
    public void courierCanNotBeCreatedWithPasswordNull(){
        courier = CourierGen.getInvalidWithPasswordNull();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/v1/courier")
    @Description("Negative test for POST /api/v1/courier with invalid courier with empty password")
    public void courierCanNotBeCreatedWithPasswordEmpty(){
        courier = CourierGen.getInvalidWithPasswordEmpty();
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        String actualMessage = createResponse.extract().path("message");
        assertEquals(expectedMessage, actualMessage);
    }
}