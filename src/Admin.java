import java.util.Collections;
import java.util.Set;

public final class Admin extends Empleados{

    private GestoraEmpleados gestor;

    public Admin(String dni, String nombre, String direccion, String contrasenia, double sueldo, ETurno turno, GestoraEmpleados gestora) {
        super(dni, nombre, direccion, contrasenia, sueldo, turno);
        this.gestor = gestora;
    }

    public boolean agregarEmpleado(Empleados nuevo){
        try {
            gestor.agregarEmpleados(nuevo);
            return true;
        } catch (ElementoVacioException | EEstadoYaIngresado e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean despedirEmpleado(Empleados emp){
        try {
            if (emp == null) {
                throw new ElementoVacioException("[!] Empleado no válido...");
            }

            // Un admin no puede despedirse a sí mismo.
            if(emp.equals(this)) {
                return false;
            }

            gestor.eliminarEmpleados(emp.getId_empleado());
            return true;
        } catch (ElementoNoEncontradoException | ElementoVacioException | BusquedaFallidaException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Set<Empleados> obtenerEmpleados() {
        try {
            return gestor.obtenerEmpleados();
        } catch (ListaVaciaException e) {
            System.out.println(e.getMessage());
        }
        return Collections.emptySet();
    }

    public Empleados buscarEmpleadoPorID(int id){
        try{
            return gestor.buscarPorID(id);
        } catch (BusquedaFallidaException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "[ROL]: " +ERol.ADMIN.toString() +"\n"
                +super.toString();
    }
}
