package ru.yandex.practicum.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

import ru.yandex.practicum.qascooter.couriers.Courier;
import ru.yandex.practicum.qascooter.couriers.CourierClient;
import ru.yandex.practicum.qascooter.couriers.CourierLogin;

public class Create2SameCouriersTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier("courier_2", "Test1234", "Jane");
        courierClient = new CourierClient();
    }

    @DisplayName("Create 2 identical couriers")
    @Description("Attempt to create a courier with an existing login")
    @Test
    public void CreateTwoSameCourierTest()  {
        ValidatableResponse response = (ValidatableResponse) courierClient.createCourier(courier);
        assertEquals(SC_CREATED, response.extract().statusCode());
        assertEquals(response.extract().path("ok"), true);
        ValidatableResponse FailResponse = (ValidatableResponse) courierClient.createCourier(new Courier("courier_2", "Test1234", "Jane"));
        assertEquals(SC_CONFLICT, FailResponse.extract().statusCode());
    }
    @After
    public void tearDown() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse response = courierClient.deleteCourier(courierClient.loginByCourier(courierLogin).extract().path("id"));
    }
}

