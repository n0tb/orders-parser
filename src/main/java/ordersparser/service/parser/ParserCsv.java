package ordersparser.service.parser;

import ordersparser.domain.Order;
import ordersparser.exception.IllegalNumberColumnsException;

public class ParserCsv implements Parser {

    public Order parseToObj(String line, String fileName, int numberLine) {
        String[] elements = line.split(",");
        Order order = new Order(fileName, numberLine);

        try {
            if (elements.length < 4) {
                throw new IllegalNumberColumnsException(elements.length);
            }
            fillOrder(order, elements);
        } catch (IllegalNumberColumnsException e) {
            order.result = e.getMessage();
        }
        return order;
    }

    private void fillOrder(Order order, String[] elements) {
        try {
            order.id = Long.parseLong(elements[0]);
            order.amount = Long.parseLong(elements[1]);
            order.currency = elements[2];
            order.comment = elements[3];
            order.result = "OK";
        } catch (NumberFormatException e) {
            order.result = "Invalid value (" + e.getMessage() + ")";
        }
    }

    @Override
    public String toString() {
        return "ParserCsv{}";
    }
}