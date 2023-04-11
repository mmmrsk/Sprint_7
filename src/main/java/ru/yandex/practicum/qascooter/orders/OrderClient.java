package ru.yandex.practicum.qascooter.orders;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.qascooter.couriers.BaseClient;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDER_CREATE = "/api/v1/orders";
    private static final String ORDER_LIST = "/api/v1/orders";
    private static final String ORDER_CANCEL = "/api/v1/orders/cancel";

    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then();
    }

    public ValidatableResponse listOfOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_LIST)
                .then();
    }

    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .when()
                .delete(ORDER_CANCEL + track)
                .then();
    }
}