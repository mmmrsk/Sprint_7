package ru.yandex.practicum.qascooter.clients;

import io.restassured.response.ValidatableResponse ;
import static io.restassured.RestAssured.given;
public class CourierClient extends BaseClient {
    private static final String COURIER_CREATE = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = COURIER_CREATE + "/{courierId}";

    public ValidatableResponse createCourier (Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    public ValidatableResponse loginByCourier (CourierCredentials courierCreds) {
        return given()
                .spec(getSpec())
                .body(courierCreds)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse deleteCourier (int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_DELETE)
                .then();
    }

}
