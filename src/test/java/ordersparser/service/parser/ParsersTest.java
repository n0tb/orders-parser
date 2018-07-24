package ordersparser.service.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParsersTest {

    @Test
    void shouldReturnCsvParserByRelativePath() {
        String[] file = new String[]{"test.csv"};

        List<Object[]> parser = Parsers.getParser(file);

        assertEquals("test.csv", parser.get(0)[0]);
        assertEquals(ParserCsv.class, parser.get(0)[1].getClass());
    }

    @Test
    void shouldReturnJsonParserByAbsolutePath() {
        String[] file = new String[]{"C:\\folder\\test.json"};

        List<Object[]> parser = Parsers.getParser(file);

        assertEquals("C:\\folder\\test.json", parser.get(0)[0]);
        assertEquals(ParserJson.class, parser.get(0)[1].getClass());
    }

    @Test
    void shouldReturnCsvParserByParentPath() {
        String[] file = new String[]{"..\\..\\test.csv"};

        List<Object[]> parser = Parsers.getParser(file);

        assertEquals("..\\..\\test.csv", parser.get(0)[0]);
        assertEquals(ParserCsv.class, parser.get(0)[1].getClass());
    }
}