package ordersparser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ordersparser.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class Converter {

    private ObjectMapper mapper;
    private BlockingQueue<Order> queue;
    private long amountLines;

    @Autowired
    public Converter(BlockingQueue<Order> queue) {
        this.queue = queue;
        mapper = new ObjectMapper();
    }

    public List<String> convert(long amountLines, ExecutorService executorService){
        this.amountLines = amountLines;
        List<String> jsonStrings = null;

        Future<List<String>> future = executorService.submit(new ConversionTask());
        try {
            jsonStrings = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jsonStrings;
    }

    private class ConversionTask implements Callable<List<String>> {
        List<String> jsonStrings = new LinkedList<>();
        @Override
        public List<String> call(){
            while (amountLines > 0) {
                amountLines--;
                try {
                    Order order = queue.take();
                    jsonStrings.add(objToJson(order));
                } catch (InterruptedException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                }
            }
            return jsonStrings;
        }
    }

    private String objToJson(Order order) {
        String resultJsonString = null;
        try {
            if (!order.result.equals("OK"))
                resultJsonString = "{\"filename\":\"" + order.filename +
                        "\", line\":" + order.line +
                        ", \"result\":\"" + order.result + "\"}";
            else
                resultJsonString = mapper.writeValueAsString(order);

        } catch (JsonProcessingException e) {
            System.out.println("Error converting a string");
        }
        return resultJsonString;
    }
}
