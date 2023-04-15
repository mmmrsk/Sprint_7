package ru.yandex.practicum.qascooter.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.qascooter.couriers.BaseClient;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDER_CREATE = "/api/v1/orders";
    private static final String ORDER_LIST = "/api/v1/orders";
    private static final String ORDER_CANCEL = "/api/v1/orders/cancel";
    @Step("Creating a new order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then();
    }
    @Step("List of orders")
    public ValidatableResponse listOfOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_LIST)
                .then();
    }
    @Step("Cancel order")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .when()
                .delete(ORDER_CANCEL + track)
                .then();
    }
}