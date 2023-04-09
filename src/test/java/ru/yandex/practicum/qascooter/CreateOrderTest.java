package ru.yandex.practicum.qascooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.qascooter.orders.OrderClient;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateNewOrderByJsonTest {
    private final String fileName;
    private OrderClient orderClient = new OrderClient();
    private int track;


    public CreateNewOrderByJsonTest(String fileName) {
        this.fileName = fileName;
    }

    @Parameterized.Parameters //для цвета самоката
    public static String[] getColor() {
        return new String[] {
                "src\\test\\resources\\orderFormBlack.json",
                "src\\test\\resources\\orderFormGrey.json",
                "src\\test\\resources\\orderForm2Colors.json",
                "src\\test\\resources\\orderFormNOColor.json",
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        orderClient = new OrderClient();
    }

    @DisplayName("Create new order.")
    @Description("Параметрический тест создание заказа. Параметр используется в графе выбора цвета самоката. " +
            "Тест проверяет, что в ответе возвращается track-номер нового заказа. Заказ может быть создан без передачи какого-либо параметра." +
            "В конце теста заказ отменяется (проверяется код 400, так как сервис отмены не работает).")
    @Test
    public void NewOrderTest() {
        ValidatableResponse response= orderClient.CreateNewOrderByFile(fileName);
        assertEquals(SC_CREATED, response.extract().statusCode()); //201
        track = response.extract().path("track"); //получение трек-номера
    }

    @After
    public void tearDown() {
        assertEquals(SC_BAD_REQUEST, orderClient.CancelOrder(track).extract().statusCode()); // сервер не работает 400
    }
}