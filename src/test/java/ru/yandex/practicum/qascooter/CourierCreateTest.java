package ru.yandex.practicum.qascooter;

import ru.yandex.practicum.qascooter.couriers.CourierClient;
import ru.yandex.practicum.qascooter.couriers.Courier;
import ru.yandex.practicum.qascooter.couriers.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreateTest {
    @Parameterized.Parameters // создания аккаунта курьера
    public static Object[] getCourierInfo() {
        return new Object[][]{
                {"", "", "", SC_BAD_REQUEST},
                {"Courier123", "", "", SC_BAD_REQUEST},
                {"Courier123", "Test1234", "", SC_BAD_REQUEST},
                {"Courier123", "", "John", SC_BAD_REQUEST},
                {"", "Test1234", "", SC_BAD_REQUEST},
                {"", "Test1234", "John", SC_BAD_REQUEST},
                {"", "", "John", SC_BAD_REQUEST},
                {"Courier123", "Test1234", "", SC_BAD_REQUEST},
                {"Courier123", "Test1234", "John", SC_CREATED}
        };
    }

    private CourierClient courierClient;
    private Courier courier;
    private int expectedStatusCode;
    private int actualStatusCode;

    public CourierCreateTest(String login, String password, String firstName, int code) {
        this.courier = new Courier(login, password, firstName);
        this.courierClient = new CourierClient();
        this.expectedStatusCode = code;
    }

    @DisplayName("Creation of courier.")
    @Description("Attempts to create a courier")
    @Test
    public void checkingCreationForCourier() {
        ValidatableResponse response = (ValidatableResponse) courierClient.createCourier(courier);
        actualStatusCode = response.extract().statusCode();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @After
    public void tearDown() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.loginByCourier(courierLogin);
        if (actualStatusCode == SC_CREATED) {
            ValidatableResponse response = courierClient.deleteCourier(loginResponse.extract().path("id"));
        } else
            assertThat(loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
    }
}
