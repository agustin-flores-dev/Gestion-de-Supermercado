public class Producto {
    private static int contador = 0;
    private int id;
    private String nombre;
    private String descripcion;
    private String fechaVencimiento;
    private int stock;
    private double precioUnitario;

    public Producto(String nombre, String descripcion, String fechaVencimiento, int stock, double precioUnitario) {
        this.id = contador++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.stock = stock;
        this.precioUnitario = precioUnitario;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "[ID]: " +id +"\n"
                +"[Nombre]: " +nombre +"\n"
                +"[Descripción]: " +descripcion +"\n"
                +"[Fecha de Vencimiento]: " +fechaVencimiento +"\n"
                +"[Stock]: " +stock +"\n"
                +"[Precio Unitario]: $" +precioUnitario;
    }
}
