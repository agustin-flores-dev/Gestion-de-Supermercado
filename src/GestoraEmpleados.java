import java.util.HashSet;
import java.util.Set;

public final class GestoraEmpleados {
    private HashSet<Empleados>listaEmpleados;

    // agregue el que tome el set de uno preexistente para conectarlo con nuestra clase generica
    public GestoraEmpleados(HashSet<Empleados> emp){
        this.listaEmpleados = emp;
    }

    // Metodo auxiliar
    private Empleados buscarPorDni(String dni) {
        for (Empleados e : listaEmpleados) {
            if (e.getDni().equalsIgnoreCase(dni)) {
                return e;
            }
        }

        return null;
    }

    public Empleados buscarPorID(int id_buscar)throws BusquedaFallidaException{

        if(id_buscar > 0) {
            for (Empleados emp : listaEmpleados){
                if (emp.getId_empleado() == id_buscar){
                    return emp;
                }
            }
        }

        throw new BusquedaFallidaException("[!] El ID que busca no se encuentra en la lista...");
    }

    public boolean agregarEmpleados(Empleados nuevo) throws ElementoVacioException, EEstadoYaIngresado{
        if (nuevo == null || nuevo.getNombre().equalsIgnoreCase("")){
            throw new ElementoVacioException("[!] Empleado sin informacion cargada...");
        }

        if(buscarPorDni(nuevo.getDni()) == null) {
            listaEmpleados.add(nuevo);
            return true;
        }

        throw new EEstadoYaIngresado("[!] Empleado ya ingresado previamente...");
    }

    public boolean eliminarEmpleados(int id_buscar) throws ElementoNoEncontradoException, BusquedaFallidaException {
        Empleados emp = buscarPorID(id_buscar);
        return listaEmpleados.remove(emp);
    }

    public Set<Empleados> obtenerEmpleados() throws ListaVaciaException{
        if (listaEmpleados == null || listaEmpleados.isEmpty()){
            throw new ListaVaciaException("[!] La lista Empleados esta vacia...");
        }

        return listaEmpleados;
    }

}
