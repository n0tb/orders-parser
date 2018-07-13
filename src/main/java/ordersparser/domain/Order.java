package ordersparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Order {
    public Long id;
    public Long amount;
    @JsonIgnore
    public String currency;
    public String comment;
    public String filename;
    public int line;
    public String result;

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
