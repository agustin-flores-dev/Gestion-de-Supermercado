import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Supermercado {

    private String nombre;
    private int id;
    private ArrayList<Double> dineroDelDia;
    private HashMap<Fecha, Double> dineroMes;
    private RepositorioGenerico<Empleados> empleados;
    private HashSet<Socio> socios;

    public Supermercado(String nombre, int id, ArrayList<Double> dineroDelDia, HashMap<Fecha, Double> dineroMes, RepositorioGenerico<Empleados> empleados, HashSet<Socio> socios) {
        this.nombre = nombre;
        this.id = id;
        this.dineroDelDia = dineroDelDia;
        this.dineroMes = dineroMes;
        this.empleados = empleados;
        this.socios = socios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public Double calcularPlataMes() throws DateErrorExcepcion{
        double aux = 0;
        LocalDate fecha = LocalDate.now();
        boolean esUltimoDia = fecha.getDayOfMonth() == fecha.lengthOfMonth();
        if (esUltimoDia){
            for (int i = 0; i < dineroDelDia.size(); i++){

            }
        }
    }
}
