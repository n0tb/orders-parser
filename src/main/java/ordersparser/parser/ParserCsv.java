package ordersparser.parser;

import ordersparser.domain.Order;
import ordersparser.exception.IllegalNumberColumnsException;

public class ParserCsv implements Parser {

    public Order parseToObj(String line, String fileName, int numberLine) {
        String[] elements = line.split(",");
        String exception;
        Order order = new Order();
        order.line = numberLine;
        order.filename = fileName;

        try {
            if (elements.length < 4) {
                throw new IllegalNumberColumnsException(elements.length);
            }
            order.id = Long.parseLong(elements[0]);
            order.amount = Long.parseLong(elements[1]);
            order.currency = elements[2];
            order.comment = elements[3];
            order.result = "OK";

        } catch (IllegalNumberColumnsException e) {
            exception = e.getMessage();
            order.result = exception;
        } catch (NumberFormatException e) {
            order.result = "Invalid value (" + e.getMessage() + ")";
        }
        return order;
    }

    @Override
    public String toString() {
        return "ParserCsv{}";
    }
}