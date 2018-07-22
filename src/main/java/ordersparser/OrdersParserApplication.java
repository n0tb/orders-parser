package ordersparser;

import ordersparser.domain.Order;
import ordersparser.exception.IllegalFileFormatException;
import ordersparser.parser.Parser;
import ordersparser.parser.ParserCsv;
import ordersparser.parser.ParserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
public class OrdersParserApplication implements CommandLineRunner {

    @Autowired private Converter converter;

    public static void main(String[] args) {
        SpringApplication.run(OrdersParserApplication.class, args);
    }

    @Bean
    BlockingQueue<Order> getQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    @Scope(value = "prototype")
    public Reader getReader() {
        return new Reader(getQueue());
    }


    @Override
    public void run(String... args) throws Exception {
        String fileCsv = "test.csv";
        String fileJson = "test.json";
        String badtest = "badtest.json";
        String[] args1 = {fileCsv, fileJson, badtest};
        List<Object[]> parsers = getParser(args1);
        for (Object[] parser : parsers) {
            Reader reader = getReader();
            reader.setFileName((String) parser[0]);
            reader.setParser((Parser) parser[1]);
            long numberLines = reader.numberLines();
            new Thread(reader).start();
            converter.convert(numberLines);
        }
    }

    private List<Object[]> getParser(String[] files)  {
        List<Object[]> parsers = new ArrayList<>(files.length);
        for (String file : files) {
            try {
                String[] elements = file.split("\\.");
                String extension = elements[elements.length - 1];
                switch (extension) {
                    case "csv":
                        parsers.add(new Object[]{file, new ParserCsv()});
                        break;
                    case "json":
                        parsers.add(new Object[]{file, new ParserJson()});
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