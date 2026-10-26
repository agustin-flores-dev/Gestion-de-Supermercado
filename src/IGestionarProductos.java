import java.util.HashSet;

public interface IGestionarProductos {

    public Producto buscarProducto (HashSet<Producto> productos, int id) throws ElementoNoEncontradoException, ListaVaciaException;
    public boolean agregarStock (Producto pro, int stock) throws EValorFueraDeRango;
    public boolean aumentarPrecioProducto(Producto pro, double aumento) throws EValorFueraDeRango;
    public boolean reducirPrecioProducto(Producto pro, double descuento) throws EValorFueraDeRango;
    public boolean sacarVencido(String fechaActual, HashSet<Producto> pro);
    public boolean agregarProducto(HashSet<Producto> pro, Producto pr);

}
