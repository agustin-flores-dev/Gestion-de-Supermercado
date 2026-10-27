import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Supermercado {

    private String nombre;
    private LocalDate hoy;
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
        this.hoy = LocalDate.now();
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
                aux = aux+dineroDelDia.get(i);
            }
            int mes = hoy.getMonthValue();
            int anio = hoy.getYear();
            Fecha fechaAUX = new Fecha(mes, anio);
            this.dineroMes.put(fechaAUX, aux);
            return aux;
        } else {
            throw new DateErrorExcepcion("error, no es el fin del mes");
        }
    }

    public double calcularPlataDia() throws EEstadoYaIngresado{
        double aux = 0;
        Set<Empleados> empAUX = empleados.obtenerTodos();
        if (hoy.getDayOfMonth()-1 < empAUX.size()){
            for (Empleados emp1 : empAUX){
                if (emp1 instanceof Cajero) {
                    aux = aux + ((Cajero) emp1).getPlataRecaudadaDia();
                }
            }
            this.dineroDelDia.add(aux);
            return dineroDelDia.getLast();
        }else {
            throw new EEstadoYaIngresado("dinero del dia ya calculado...");
        }
    }



}
