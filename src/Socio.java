import java.util.Objects;

public final class Socio extends Persona implements Comparable<Socio>{

    private static int contadorId = 1;
    private int id;
    protected String contrasenia;
    private boolean estadoSocio;

    public Socio(String dni, String nombre, String direccion, String contrasenia, boolean estadoSocio) {
        super(dni, nombre, direccion);
        this.id = contadorId ++;
        this.contrasenia = contrasenia;
        this.estadoSocio = estadoSocio;
    }

    public Socio(String dni) {
        super(dni);
    }

    public int getId() {
        return id;
    }

    public boolean isEstadoSocio() {
        return estadoSocio;
    }

    public boolean activarEstado() throws EEstadoYaIngresado{
        if (this.estadoSocio){
            throw new EEstadoYaIngresado("el socio ya esta activo");
        }
        return this.estadoSocio = true;
    }

    public boolean desactivarEstado() throws EEstadoYaIngresado{
        if (this.estadoSocio){
            return this.estadoSocio = false;
        }
        throw new EEstadoYaIngresado("el socio ya esta se encuentra desactivado");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Socio socio = (Socio) o;
        return super.getDni().equalsIgnoreCase(socio.getDni());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getDni().toLowerCase());
    }

    @Override
    public int compareTo(Socio socioAUX){

        return super.getDni().compareTo(socioAUX.getDni());
    }

    @Override
    public String toString() {
        return "Socio{" +
                " id= " + id +
                ", nombre= " + super.getNombre() +
                ", DNI= " + super.getDni() +
                ", Dirreccion= " + super.getDireccion() +
                ", estadoSocio= " + estadoSocio +
                '}';
    }
}
