package ru.yandex.practicum.qascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.practicum.qascooter.orders.Order;
import ru.yandex.practicum.qascooter.orders.OrderClient;
import ru.yandex.practicum.qascooter.orders.OrdersData;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderClient orderClient;
    private int track;
    final Order order;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        if(track != 0) {
            orderClient.cancelOrder(track);
        }
    }

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] data() throws Exception {
        return new Object[][] {
                {OrdersData.getValid(0)}, // color is not selected
                {OrdersData.getValid(1)}, // chosen black or grey color
                {OrdersData.getValid(2)} // both color selected
        };
    }

    @Test
    @DisplayName("Creating order") // имя теста
    @Description("Successful order creation")
    public void orderCanBeCreated(){
        ValidatableResponse createResponse = orderClient.createOrder(order);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);

        track = createResponse.extract().path("track");
        assertTrue("Track number is missing:", track != 0);
    }

}