package ordersparser.parser;

import ordersparser.domain.Order;

public interface Parser {
    Order parseToObj(String line, String fileName, int numberLine);
}
