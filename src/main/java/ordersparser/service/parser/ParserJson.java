package ordersparser.service.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import ordersparser.domain.Order;
import ordersparser.domain.RawOrder;
import ordersparser.exception.IllegalNumberColumnsException;

import java.io.IOException;

public class ParserJson implements Parser {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Order parseToObj(String line, String fileName, int numberLine) {
        String[] elements = line.split(",");
        Order order = new Order(fileName, numberLine);

        try {
            if (elements.length < 4) {
                throw new IllegalNumberColumnsException(elements.length);
            } else if (elements.length == 6) {
                order = mapper.readValue(line, Order.class);
                order.line = numberLine;
                order.filename = fileName;
            } else {
                RawOrder rawOrder = mapper.readValue(line, RawOrder.class);
                fillOrder(order, rawOrder);
            }

        } catch (IllegalNumberColumnsException e) {
            order.result = e.getMessage();
        } catch (IOException e) {
            if (e instanceof UnrecognizedPropertyException) {
                order.result = "Unrecognized field: " +
                        ((UnrecognizedPropertyException) e).getPropertyName();
            } else if (e instanceof JsonParseException) {
                order.result = "Unrecognized token";
            } else {
                order.result = e.getMessage();
            }
        }
        return order;
    }

    private void fillOrder(Order order, RawOrder rawOrder) {
        order.id = rawOrder.orderId;
        order.amount = rawOrder.amount;
        order.comment = rawOrder.comment;
        order.currency = rawOrder.currency;
        order.result = "OK";
    }

    @Override
    public String toString() {
        return "ParserJson{}";
    }
}
