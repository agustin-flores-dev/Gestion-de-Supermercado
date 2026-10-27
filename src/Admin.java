public class Admin extends Empleados{

    private GestoraEmpleados gestor;

    public Admin(String nombre, String dni, String direccion, double sueldo, ETurno turno, GestoraEmpleados gestora) {
        super(nombre, dni, direccion, sueldo, turno);
        this.gestor = gestora;
    }

    public boolean agregarEmpleado (Empleados nuevo){
        try {
            gestor.agregarEmpleados(nuevo);
            return true;
        } catch (ElementoVacioException | EEstadoYaIngresado e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean despedirEmpleado(Empleados eliminar){
        try {
            gestor.eliminarEmpleados(eliminar);
            return true;
        } catch (ElementoNoEncontradoException | ElementoVacioException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String mostrarEmpleados(){
        String aux;
        try {
            aux = gestor.mostrarEmpleados();
            return aux;
        } catch (ListaVaciaException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Empleados buscarEmpleado(int id){
        try{
            return gestor.buscarEmpleado(id);
        } catch (BusquedaFallidaException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
