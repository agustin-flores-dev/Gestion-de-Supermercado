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

    

}
