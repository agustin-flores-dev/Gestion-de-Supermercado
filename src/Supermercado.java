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
    private HashSet<Producto> productos;
    private HashMap<Fecha, Double> dineroMes;
    private RepositorioGenerico<Empleados> empleados;
    private GestoraEmpleados gestoraEmpleados;
    private HashSet<Socio> socios;

    public Supermercado(String nombre, int id, ArrayList<Double> dineroDelDia, HashMap<Fecha, Double> dineroMes, RepositorioGenerico<Empleados> empleados, HashSet<Socio> socios, HashSet<Producto> productos) {
        this.nombre = nombre;
        this.id = id;
        this.dineroDelDia = dineroDelDia;
        this.dineroMes = dineroMes;
        this.empleados = empleados;
        this.gestoraEmpleados = new GestoraEmpleados(this.empleados.obtenerTodos());
        this.socios = socios;
        this.hoy = LocalDate.now();
        this.productos = productos;
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

    public Double calcularPlataMes() {
        double plata = 0.0;
        for (int i = 0; i < dineroDelDia.size(); i++){
            plata += dineroDelDia.get(i);
        }
        int mes = hoy.getMonthValue();
        int anio = hoy.getYear();
        Fecha fechaAUX = new Fecha(mes, anio);

        if (dineroMes.containsKey(fechaAUX)) throw new IllegalStateException("[!] El mes ya fue cerrado...");
        this.dineroMes.put(fechaAUX, plata);
        dineroDelDia.clear();
        return plata;
    }

    public double calcularPlataDia() {
        double plata = 0.0;
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista) {
            if (emp instanceof Cajero) {
                plata += ((Cajero) emp).getPlataRecaudadaDia();
            }
        }
        this.dineroDelDia.add(plata);
        return plata;
    }

    public boolean funcionAdminAddEmp(int id, Empleados emp) throws BusquedaFallidaException{

        for (Empleados empAUX : empleados.obtenerTodos()){
            if (empAUX.getId_empleado() == id && empAUX instanceof Admin){
                if (((Admin) empAUX).agregarEmpleado(emp)){
                    return ((Admin) empAUX).agregarEmpleado(emp);
                }
            }
        } throw new BusquedaFallidaException("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

    }

    public boolean funcionAdminRemoveEmp (int id, Empleados emp) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados empAUX : lista){
            if (empAUX.getId_empleado() == id && empAUX instanceof Admin){
                if (((Admin) empAUX).despedirEmpleado(emp)){
                    return ((Admin) empAUX).despedirEmpleado(emp);
                }
            }
        } throw new BusquedaFallidaException("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

    }

    public String funcionAdminMostrarDataEmp(int id) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp.getId_empleado() == id && emp instanceof Admin){
                StringBuilder str = new StringBuilder();
                Set<Empleados> listaEmpleadosAdmin = ((Admin) emp).obtenerEmpleados();

                if (listaEmpleadosAdmin != null) {
                    for (Empleados e : listaEmpleadosAdmin){
                        str.append(e.toString()).append("\n");
                    }
                }
                return str.toString();
            }
        }

        throw new BusquedaFallidaException("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public String funcionAdminMostrarEmp(int id, int idEmp) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados empAUX : lista){
            if (empAUX.getId_empleado() == id && empAUX instanceof Admin){

                for (Empleados empleados1 : empleados.obtenerTodos()){
                    if (empleados1.getId_empleado() == idEmp){
                        return empleados1.toString();
                    }
                }
                throw new BusquedaFallidaException("[!] El empleado a buscar no se encuentra registrado...");
            }
        }

        throw new BusquedaFallidaException("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

    }

    public boolean realizarVenta (int id, ArrayList<Producto> prod, ArrayList<Integer> cant) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados empAUX : lista){
            if (empAUX.getId_empleado() == id && empAUX instanceof Cajero)  {
                try{
                    return ((Cajero) empAUX).realizarVenta(prod, cant);
                } catch (ListaVaciaException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        throw new BusquedaFallidaException("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public double chequearPrecios(Producto prod) throws ElementoVacioException, ListaVaciaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Cajero){
                return ((Cajero) emp).chequearPrecio(prod);
            }
        }
        throw new ListaVaciaException("No hay cajero ingresado para realizar la operacion, por favor contacte con el admin");
    }



}
