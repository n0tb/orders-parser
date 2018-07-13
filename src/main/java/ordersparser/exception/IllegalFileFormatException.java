package ordersparser.exception;

public class IllegalFileFormatException extends Exception {

    public IllegalFileFormatException() {
        super("[ERROR] Unsupported file format");
    }
}
