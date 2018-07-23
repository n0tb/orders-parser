package ordersparser.service.parser;

import ordersparser.exception.IllegalFileFormatException;

import java.util.ArrayList;
import java.util.List;

public class Parsers {

    private static Parser newParserCsv() {
        return new ParserCsv();
    }

    private static Parser newParserJson() {
        return new ParserJson();
    }

    public static List<Object[]> getParser(String[] files)  {
        List<Object[]> parsers = new ArrayList<>(files.length);
        for (String file : files) {
            try {
                String[] elements = file.split("\\.");
                String extension = elements[elements.length - 1];
                switch (extension) {
                    case "csv":
                        parsers.add(new Object[]{file, Parsers.newParserCsv()});
                        break;
                    case "json":
                        parsers.add(new Object[]{file, Parsers.newParserJson()});
                        break;
                    default:
                        throw new IllegalFileFormatException();
                }
            } catch (IllegalFileFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return parsers;
    }
}
