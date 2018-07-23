package ordersparser.service.parser;

public class Parsers {

    public static Parser newParserCsv() {
        return new ParserCsv();
    }

    public static Parser newParserJson() {
        return new ParserJson();
    }
}
