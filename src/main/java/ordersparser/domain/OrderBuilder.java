package ordersparser.domain;

public class OrderBuilder {
    long id;
    long amount;
    String currency;
    String comment;
    String filename;
    int line;
    String result;

    public OrderBuilder id(long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder amount(long amount) {
        this.amount = amount;
        return this;
    }

    public OrderBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public OrderBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }

    public OrderBuilder line(int line) {
        this.line = line;
        return this;
    }

    public OrderBuilder result(String result) {
        this.result = result;
        return this;
    }

    public Order build() {
        return new Order(this);
    }
}
