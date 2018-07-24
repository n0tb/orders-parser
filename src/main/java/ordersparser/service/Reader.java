package ordersparser.service;

import ordersparser.service.parser.Parser;
import ordersparser.domain.Order;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Reader implements Runnable {

    private Parser parser;
    private int lineNumber;
    private String fileName;
    private BlockingQueue<Order> queue;

    public Reader(BlockingQueue<Order> queue) {
        this.queue = queue;
        this.lineNumber = 0;
    }

    public long numberLines() {
        try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.forName("cp1251"))) {
            return lines.count();
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                System.out.println("[ERROR] Failed to open the file: " + fileName +
                        "  Check the file name");
            } else if (e instanceof AccessDeniedException) {
                System.out.println("[ERROR] Insufficient permission to access file: " + fileName);
            } else {
                System.out.println("Error reading file: " + fileName);
            }
        }
        return 0;
    }
    @Override
    public void run() {
        try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.forName("cp1251"))){
            lines.forEach(line -> {
                String[] strings = fileName.split("\\\\");
                String fileName = strings[strings.length - 1];
                lineNumber++;

                try {
                    Order order = parser.parseToObj(line, fileName, lineNumber);
                    queue.put(order);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                System.out.println("[ERROR] Failed to open the file: " + fileName +
                        "  Check the file name");
            } else if (e instanceof AccessDeniedException) {
                System.out.println("[ERROR] Insufficient permission to access file: " + fileName);
            } else {
                System.out.println("Error reading file" + fileName);
            }
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
