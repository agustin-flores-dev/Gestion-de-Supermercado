import java.util.Objects;

public class Socio {

    private static int idAUX = 1;
    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private boolean estadoSocio;

    public Socio (int dni, String nombre, String apellido){

        this.id = idAUX;
        this.idAUX ++;
        this.apellido = apellido;
        this.dni = dni;
        this.nombre = nombre;
        this.estadoSocio = true;

    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
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
        return dni == socio.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    public int compareTo(Socio socioAUX){
        return Integer.compare(this.dni, socioAUX.getDni());
    }

    @Override
    public String toString() {
        return "Socio{" +
                "id=" + id +
                ", dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", estadoSocio=" + estadoSocio +
                '}';
    }
}
