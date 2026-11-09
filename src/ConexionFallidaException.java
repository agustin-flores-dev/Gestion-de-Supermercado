public class ConexionFallidaException extends RuntimeException {
    public ConexionFallidaException() {
        super("No se pudo conectar ");
    }
}
