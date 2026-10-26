public abstract class Persona {
    private String nombre;
    private String dni;
    private String direccion;

    public Persona(String nombre, String dni, String direccion) {
        this.nombre = nombre;
        this.dni = dni;
        this.direccion = direccion;
    }

    public Persona(String dni){

        this.nombre = "";
        this.dni = dni;
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
