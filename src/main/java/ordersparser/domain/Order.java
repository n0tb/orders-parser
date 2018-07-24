package ordersparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Order {
    public long id;
    public long amount;
    @JsonIgnore
    public String currency;
    public String comment;
    public String filename;
    public int line;
    public String result;

    public Order() {
    }

    public Order(String filename, int line) {
        this.filename = filename;
        this.line = line;
    }

    Order(OrderBuilder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.comment = builder.comment;
        this.filename = builder.filename;
        this.line = builder.line;
        this.result = builder.result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return line == order.line &&
                Objects.equals(id, order.id) &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(currency, order.currency) &&
                Objects.equals(comment, order.comment) &&
                Objects.equals(filename, order.filename) &&
                Objects.equals(result, order.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, comment, filename, line, result);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", comment='" + comment + '\'' +
                ", filename='" + filename + '\'' +
                ", line=" + line +
                ", result='" + result + '\'' +
                '}';
    }
}
