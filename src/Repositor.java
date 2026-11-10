import java.util.HashSet;

public final class Repositor extends Empleados implements IGestionarProductos{

    public Repositor(String nombre, String dni, String direccion, double sueldo, ETurno turno) {
        super(nombre, dni, direccion, sueldo, turno);
    }

    @Override
    public Producto buscarProducto(HashSet<Producto> lista, int id) throws ElementoNoEncontradoException, ListaVaciaException {
        if (lista == null || lista.isEmpty()){
            throw new ListaVaciaException("[!] No hay productos cargados hasta el momento...");
        }
        for (Producto pro : lista){
            if (pro.getId() == id){
                return pro;
            }
        }
        throw new ElementoNoEncontradoException("[!] Producto no encontrado...");
    }

    @Override
    public boolean agregarStock(Producto pro, int stock) throws EValorFueraDeRango {
        if (stock > 0){
            pro.setStock(pro.getStock()+stock);
            return true;
        }
        throw new EValorFueraDeRango("[!] Cantidad de stock fuera de rango...");
    }

    @Override
    public boolean aumentarPrecioProducto(Producto pro, double aumento) throws EValorFueraDeRango {
        if(pro == null ||aumento <= 0 || aumento > 100){
            throw new EValorFueraDeRango("[!] Aumento fuera de rango...");
        }

        double precio = pro.getPrecioUnitario();
        pro.setPrecioUnitario(precio + precio*aumento /100);
        return true;
    }

    @Override
    public boolean reducirPrecioProducto(Producto pro, double descuento) throws EValorFueraDeRango {
        if(pro == null || descuento <= 0 || descuento > 100){
            throw new EValorFueraDeRango("[!] Descuento fuera de rango...");
        }
        double precio = pro.getPrecioUnitario();
        pro.setPrecioUnitario(precio - precio*descuento /100);
        return true;
    }

    @Override
    public boolean sacarVencido(Fecha fechaActual, HashSet<Producto> lista) {
        if (fechaActual == null || lista == null || lista.isEmpty()) {
            return false;
        }

        return lista.removeIf(prod -> prod.getFechaVencimiento() != null &&
                prod.getFechaVencimiento().vencido(fechaActual));
    }

    @Override
    public boolean agregarProducto(HashSet<Producto> lista, Producto pro) throws ElementoVacioException {
        if (pro != null){
            return lista.add(pro);
        } else {
        throw new ElementoVacioException("[!] Producto vacío o sin información...");
        }
    }

    @Override
    public Producto buscarProductoNombre(HashSet<Producto> lista, String nombre) throws ElementoNoEncontradoException, ListaVaciaException {
        if (lista == null || lista.isEmpty()){
            throw new ListaVaciaException("[!] No hay productos cargados hasta el momento...");
        }
        for (Producto pro : lista){
            if (pro.getNombre().equalsIgnoreCase(nombre)){
                return pro;
            }
        }
        throw new ElementoNoEncontradoException("[!] Producto no encontrado...");
    }

    @Override
    public String toString() {
        return "[ROL]: " +ERol.REPOSITOR.toString() +"\n"
                +super.toString();
    }
}
