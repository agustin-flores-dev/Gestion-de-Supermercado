import java.util.HashSet;

public class GestoraEmpleados {
    private HashSet<Empleados>listaEmpleados;

    public GestoraEmpleados(){
        this.listaEmpleados = new HashSet<>();
    }

    public boolean agregarEmpleados(Empleados nuevo){
        //faltan excepciones
        if (listaEmpleados.add(nuevo)){
            return true;
        }
        return false;
    }

    public boolean eliminarEmpleados(Empleados eliminar){
        //faltan excepciones
        if (listaEmpleados.remove(eliminar)){
            return true;
        }
        return false;
    }

    public String mostrarEmpleados() throws ListaVaciaException{
        if (listaEmpleados.isEmpty()){
            throw new ListaVaciaException("La lista Empleados esta vacia");
        }
        StringBuilder mensaje = new StringBuilder();
        for (Empleados emp : listaEmpleados){
            mensaje.append(emp.toString()+"\n");
        }
        return mensaje.toString();
    }

    public Empleados buscarEmpleado(int id_buscar)throws BusquedaFallidaException{

        for (Empleados emp : listaEmpleados){
            if (emp.getId_empleado() == id_buscar){

                return emp;
            }
        }

        throw new BusquedaFallidaException("El id que busca no esta en la lista");
    }

}
