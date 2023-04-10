package ru.yandex.practicum.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.qascooter.orders.OrderClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @DisplayName("Order list")
    @Description("Displaying the entire list of orders")
    @Test
    public void OrderFullListTest(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = (ValidatableResponse) orderClient.listOfOrders();
        response.assertThat().body("orders", notNullValue());
        response.assertThat().body("orders.id", notNullValue());
        assertEquals(SC_OK, response.extract().statusCode());
    }

}
