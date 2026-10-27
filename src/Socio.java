import java.util.Objects;

public final class Socio extends Persona{

    private static int idAUX = 1;
    private int id;
    private boolean estadoSocio;

    public Socio(String nombre, String dni, String direccion, boolean estadoSocio) {
        super(nombre, dni, direccion);
        this.estadoSocio = estadoSocio;
        this.id = idAUX;
        idAUX++;
    }

    public Socio(String dni) {
        super(dni);
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
        return super.getDni().equalsIgnoreCase(socio.getDni());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getDni());

    }

    public int compareTo(Socio socioAUX){

        return super.getDni().compareTo(socioAUX.getDni());

    }


}
