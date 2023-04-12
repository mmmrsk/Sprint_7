package ru.yandex.practicum.qascooter.couriers;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class CourierClient extends BaseClient {
    private static final String COURIER_CREATE = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = COURIER_CREATE + "/{courierId}";

    @Step("Creating a courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then();
    }
    @Step("Login by courier")
    public ValidatableResponse loginByCourier(CourierCreds courierCreds) {
        return given()
                .spec(getSpec())
                .body(courierCreds)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }
    @Step("Deleting a courier")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_DELETE)
                .then();
    }

}
