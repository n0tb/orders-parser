package ordersparser.service.parser;

import ordersparser.domain.Order;
import ordersparser.domain.OrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class ParserCsvTest {

    private Parser parserCsv;
    private String filename;
    private Order standardOrder;

    @BeforeEach
    void setUp() {
        parserCsv = Parsers.newParserCsv();
        filename = "test.csv";
        standardOrder = new OrderBuilder()
                .id(1).amount(100)
                .currency("USD")
                .comment("оплата заказа")
                .filename(filename)
                .line(1).result("OK")
                .build();
    }

    @Test
    void parseCorrectCsvString() {
        String csvCorrectString = "1,100,USD,оплата заказа";
        Order order = parserCsv.parseToObj(csvCorrectString, filename, 1);
        assertEquals(standardOrder, order);
    }

    @Test
    void parseCsvWithInsufficientColumns() {
        String csvInsufficientNumberColumnsString = "1,100,USD";
        Order order = parserCsv.parseToObj(csvInsufficientNumberColumnsString, filename, 1);
        assertEquals("Invalid number of columns (required 4, received 3)", order.result);
    }

    @Test
    void parseCsvWithIllegalNumberFormat() {
        String csvNumberFormatExceptionString = "g,100,USD,оплата заказа";
        Order order = parserCsv.parseToObj(csvNumberFormatExceptionString, filename, 1);
        assertEquals("Invalid value (For input string: \"g\")", order.result);
    }
}