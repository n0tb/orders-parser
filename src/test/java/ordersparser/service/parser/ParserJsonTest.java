package ordersparser.service.parser;

import ordersparser.domain.Order;
import ordersparser.domain.OrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserJsonTest {

    private Parser parserJson;
    private String[] filename;
    private Order standardOrder;

    @BeforeEach
    void setUp() {
        filename = new String[] {"test.json"};
        parserJson = (Parser) Parsers.getParser(filename).get(0)[1];
        standardOrder = new OrderBuilder()
                .id(1).amount(100)
                .currency("USD")
                .comment("оплата заказа")
                .filename(filename[0])
                .line(1).result("OK")
                .build();
    }

    @Test
    void parseCorrectJsonString() {
        String jsonCorrectString = "{\"orderId\":1, \"amount\":100, " +
                "\"currency\":\"USD\", \"comment\":\"оплата заказа\"}";

        Order order = parserJson.parseToObj(jsonCorrectString, filename[0], 1);
        assertEquals(standardOrder, order);
    }

    @Test
    void parseFormattedJsonString() {
        String formattedJson = "{\"id\":1,\"amount\":100,\"comment\":\"оплата заказа\"," +
                "\"filename\":\"test.json\",\"line\":1,\"result\":\"OK\"}";

        Order order = parserJson.parseToObj(formattedJson, filename[0], 1);
        order.currency = "USD";

        assertEquals(standardOrder, order);
    }

    @Test
    void parseJsonWithInsufficientColumns() {
        String jsonInsufficientColumnsString = "{\"orderId\":1, \"amount\":100," +
                " \"currency\":\"USD\"}";

        Order order = parserJson.parseToObj(jsonInsufficientColumnsString, filename[0], 1);
        assertEquals("Invalid number of columns (required 4, received 3)", order.result);
    }

    @Test
    void parseJsonWithUnrecognizedField() {
        String jsonUnrecognizedField = "{\"id\":5, \"amount\":100, " +
                "\"currency\":\"USD\", \"comment\":\"оплата заказа\"}";

        Order order = parserJson.parseToObj(jsonUnrecognizedField, filename[0], 1);
        assertEquals("Unrecognized field: id", order.result);
    }

    @Test
    void parseJsonWithUnrecognizedToken() {
        String jsonUnrecognizedToken = "{\"orderId\":xxx, \"amount\":\"100\", " +
                "\"currency\":\"USD\", \"comment\":\"оплата заказа\"}";

        Order order = parserJson.parseToObj(jsonUnrecognizedToken, filename[0], 1);
        assertEquals("Unrecognized token", order.result);
    }
}