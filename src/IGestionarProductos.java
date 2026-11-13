import java.util.HashSet;

public interface IGestionarProductos {

    public Producto buscarProducto (HashSet<Producto> lista, int id) throws ElementoNoEncontradoException, ListaVaciaException;
    public Producto buscarProductoNombre (HashSet<Producto> lista, String nombre) throws ElementoNoEncontradoException, ListaVaciaException;
    public boolean agregarStock (Producto pro, int stock) throws EValorFueraDeRango, ActualizacionUpdateException;
    public boolean aumentarPrecioProducto(Producto pro, double aumento) throws EValorFueraDeRango, ActualizacionUpdateException;
    public boolean reducirPrecioProducto(Producto pro, double descuento) throws EValorFueraDeRango, ActualizacionUpdateException;
    public boolean sacarVencido(Fecha fechaActual, HashSet<Producto> lista) throws ActualizacionUpdateException;
    public boolean agregarProducto(HashSet<Producto> lista, Producto pro) throws ElementoVacioException;

}
