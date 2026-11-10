public class ConexionFallidaException extends RuntimeException {

    public ConexionFallidaException(String message) {
        super(message);
    }

    public ConexionFallidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
