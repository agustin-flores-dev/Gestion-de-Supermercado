import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Supermercado {

    private String nombre;
    private final LocalDate hoy;
    private final Fecha fechaHoy;
    private final int id;
    private final ArrayList<Double> dineroDelDia;
    private final HashSet<Producto> productos;
    private final HashMap<Fecha, Double> dineroMes;
    private final RepositorioGenerico<Empleados> empleados;
    private final GestoraEmpleados gestoraEmpleados;
    private final HashSet<Socio> socios;

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
        this.fechaHoy = new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
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


    // Calcular mes por defecto.
    public Double calcularPlataMes() {
        return calcularPlataMes(fechaHoy.getMes(), fechaHoy.getAnio());
    }
    // Calcular mes específico.
    public Double calcularPlataMes(int mes, int anio) {
        if (anio > hoy.getYear() || (anio == hoy.getYear() && mes > hoy.getMonthValue())) {
            throw new IllegalArgumentException("[!] No se puede calcular la plata de un mes futuro...");
        }

        Fecha fechaAUX = new Fecha(mes, anio);
        if (dineroMes.containsKey(fechaAUX)) {
            return dineroMes.get(fechaAUX);
        }

        double plata = 0.0;
        if (mes == hoy.getMonthValue() && anio == hoy.getYear()) {
            for (Double d : dineroDelDia) {
                plata += d;
            }
        } else {
            return 0.0;
        }

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
            if (empAUX.getId_empleado() == id && empAUX instanceof Admin admin){
                if (admin.agregarEmpleado(emp)){
                    return admin.agregarEmpleado(emp);
                }
            }
        } throw new BusquedaFallidaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

    }

    public boolean funcionAdminRemoveEmp(int id, Empleados emp) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados empAUX : lista){
            if (empAUX.getId_empleado() == id && empAUX instanceof Admin admin){
                if (admin.despedirEmpleado(emp)){
                    return admin.agregarEmpleado(emp);
                }
            }
        } throw new BusquedaFallidaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

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

        throw new BusquedaFallidaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
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

        throw new BusquedaFallidaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");

    }

    public boolean realizarVenta(int id, ArrayList<Producto> prod, ArrayList<Integer> cant) throws BusquedaFallidaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados empAUX : lista){
            if (empAUX.getId_empleado() == id && empAUX instanceof Cajero)  {
                try{
                    return ((Cajero) empAUX).realizarVenta(prod, cant);
                } catch (ListaVaciaException | IllegalArgumentException | ActualizacionUpdateException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        throw new BusquedaFallidaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public double chequearPrecios(Producto prod) throws ElementoVacioException, ListaVaciaException{

        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Cajero){
                return ((Cajero) emp).chequearPrecio(prod);
            }
        }
        throw new ListaVaciaException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public Producto buscarProductoPorID(int idProdu) throws IllegalArgumentException, ListaVaciaException, ElementoNoEncontradoException{
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).buscarProducto(productos, idProdu);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public Producto buscarProductoPorNombre(String nombre) throws IllegalArgumentException, ListaVaciaException, ElementoNoEncontradoException{
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).buscarProductoNombre(productos, nombre);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean aumentarStockID(int id, int cant) throws ListaVaciaException, ElementoNoEncontradoException, EValorFueraDeRango, ActualizacionUpdateException {
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).agregarStock(((Repositor) emp).buscarProducto(productos, id), cant);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean aumentarStockNombre(String nombre, int cant) throws ListaVaciaException, ElementoNoEncontradoException, EValorFueraDeRango, ActualizacionUpdateException {
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).agregarStock(((Repositor) emp).buscarProductoNombre(productos, nombre), cant);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean sacarVencido() throws IllegalArgumentException, ActualizacionUpdateException{
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).sacarVencido(fechaHoy, productos);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean reducirPrecioProducto(int idProducto, int descuento) throws ListaVaciaException, ElementoNoEncontradoException, EValorFueraDeRango, ActualizacionUpdateException {
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).reducirPrecioProducto(((Repositor) emp).buscarProducto(productos, idProducto), descuento);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean aumentarPrecioProducto(int idProducto, int aumento) throws ListaVaciaException, ElementoNoEncontradoException, EValorFueraDeRango, ActualizacionUpdateException {
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).aumentarPrecioProducto(((Repositor) emp).buscarProducto(productos, idProducto), aumento);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }

    public boolean agregarProducto(Producto pro) throws ElementoVacioException {
        Set<Empleados> lista = empleados.obtenerTodos();
        for (Empleados emp : lista){
            if (emp instanceof Repositor){
                return ((Repositor) emp).agregarProducto(productos, pro);
            }
        }
        throw new IllegalArgumentException
                ("[!] El ID ingresado no pertenece a un empleado autorizado para realiza esta operación...");
    }
}
