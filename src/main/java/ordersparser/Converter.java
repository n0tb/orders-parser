package ordersparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ordersparser.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Converter {

    private ObjectMapper mapper = new ObjectMapper();
    private BlockingQueue<Order> queue;

    @Autowired
    public Converter(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    void convert(long amountLines){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        while (amountLines > 0) {
            amountLines--;
            executor.execute(new ConvertTask());
        }
        executor.shutdown();
    }

    private class ConvertTask implements Runnable {
        @Override
        public void run(){
            try {
                Order order = queue.take();
                objToJson(order);
            } catch (InterruptedException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private void objToJson(Order order) {
        try {
            if (!order.result.equals("OK"))
                System.out.println("{\"filename\":\"" + order.filename +
                        "\", line\":" + order.line +
                        ", \"result\":\""  + order.result + "\"}");
            else
                System.out.println(mapper.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            System.out.println("Error converting a string.");
        }
    }
}
