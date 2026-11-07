import java.util.HashSet;

public interface IGestionarProductos {

    public Producto buscarProducto (HashSet<Producto> lista, int id) throws ElementoNoEncontradoException, ListaVaciaException;
    public boolean agregarStock (Producto pro, int stock) throws EValorFueraDeRango;
    public boolean aumentarPrecioProducto(Producto pro, double aumento) throws EValorFueraDeRango;
    public boolean reducirPrecioProducto(Producto pro, double descuento) throws EValorFueraDeRango;
    public boolean sacarVencido(Fecha fechaActual, HashSet<Producto> lista);
    public boolean agregarProducto(HashSet<Producto> lista, Producto pro) throws ElementoVacioException;

}
