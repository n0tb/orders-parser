package ordersparser.exception;

public class IllegalNumberColumnsException extends Exception {
    private int elements;

    public IllegalNumberColumnsException(int elements) {
        super("Invalid number of columns (required 4, received " + elements + ")");
        this.elements = elements;
    }
}
