public abstract class Persona {
    private final String dni;
    private String nombre;
    private String direccion;

    public Persona(String dni, String nombre, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Persona(String dni){
        this.dni = dni;
        this.nombre = "";
        this.direccion = "";
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDni() {
        return dni;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "[Nombre]: " +nombre +"\n"
                +"[DNI]: " +dni +"\n"
                +"[Dirección]: " +direccion +"\n";
    }
}
