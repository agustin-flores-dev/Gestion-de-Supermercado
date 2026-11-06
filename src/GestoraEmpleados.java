import java.util.HashSet;
import java.util.Set;

public final class GestoraEmpleados {
    private HashSet<Empleados>listaEmpleados;

    // agregue el que tome el set de uno preexistente para conectarlo con nuestra clase generica
    public GestoraEmpleados(HashSet<Empleados> emp){
        this.listaEmpleados = emp;
    }

    public boolean agregarEmpleados(Empleados nuevo) throws ElementoVacioException, EEstadoYaIngresado{
        if (nuevo == null){
            throw new ElementoVacioException("[!] Empleado sin informacion cargada...");
        }
        if (nuevo.getNombre().equalsIgnoreCase("")){
            throw new ElementoVacioException("[!] Empleado sin informacion cargada...");
        }
        if (listaEmpleados.add(nuevo)){
            return true;
        }
        throw new EEstadoYaIngresado("[!] Empleado ya ingresado previamente...");
    }

    public boolean eliminarEmpleados(Empleados eliminar) throws ElementoNoEncontradoException, ElementoVacioException{
        if (eliminar == null || eliminar.getNombre().equalsIgnoreCase("")){
            throw new ElementoVacioException("[!] Empleado sin informacion cargada...");
        }
        if(!listaEmpleados.contains(eliminar)){
            throw new ElementoNoEncontradoException("[!] Elemento no encontrado...");
        }
        if (listaEmpleados.remove(eliminar)){
            return true;
        }
        return false;
    }

    public Set<Empleados> obtenerEmpleados() throws ListaVaciaException{
        if (listaEmpleados == null || listaEmpleados.isEmpty()){
            throw new ListaVaciaException("[!] La lista Empleados esta vacia...");
        }

        return listaEmpleados;
    }

    public Empleados buscarEmpleado(int id_buscar)throws BusquedaFallidaException{

        for (Empleados emp : listaEmpleados){
            if (emp.getId_empleado() == id_buscar){
                return emp;
            }
        }

        throw new BusquedaFallidaException("[!] El id que busca no esta en la lista...");
    }

}
