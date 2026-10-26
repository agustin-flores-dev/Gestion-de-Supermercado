import java.util.HashSet;

public class Repositor extends Empleados implements IGestionarProductos{

    public Repositor(String nombre, String dni, String direccion, double sueldo, ETurno turno) {
        super(nombre, dni, direccion, sueldo, turno);
    }

    @Override
    public Producto buscarProducto(HashSet<Producto> productos, int id) throws ElementoNoEncontradoException, ListaVaciaException {
        if (productos.isEmpty()){
            throw new ListaVaciaException("no hay productos cargados en el momento");
        }
        for (Producto pro : productos){
            if (pro.getId() == id){
                return pro;
            }
        }
        throw new ElementoNoEncontradoException("producto no encontrado...");
    }

    @Override
    public boolean agregarStock(Producto pro, int stock) throws EValorFueraDeRango {
        if (stock > 0){
            pro.setStock(pro.getStock()+stock);
            return true;
        }
        throw new EValorFueraDeRango("cantidad de stock fuera de rango...");
    }

    @Override
    public boolean aumentarPrecioProducto(Producto pro, double aumento) throws EValorFueraDeRango {
        if(aumento <= 0 || aumento > 100){
            throw new EValorFueraDeRango("aumento fuera de rango");
        }
        pro.setPrecioUnitario(pro.getPrecioUnitario()+(pro.getPrecioUnitario()*aumento));
        return true;
    }

    @Override
    public boolean reducirPrecioProducto(Producto pro, double descuento) throws EValorFueraDeRango {
        if(descuento <= 0 || descuento > 1){
            throw new EValorFueraDeRango("aumento fuera de rango");
        }
        pro.setPrecioUnitario(pro.getPrecioUnitario()-(pro.getPrecioUnitario()*descuento));
        return true;
    }

    @Override
    public boolean sacarVencido(String fechaActual, HashSet<Producto> pro) {
        if (pro.isEmpty()){
            return true;
        } else {
            for (Producto prod : pro) {

                if (fechaActual.equalsIgnoreCase(prod.getFechaVencimiento())){
                    pro.remove(prod);
                }

            }

            return true;
        }
    }

    @Override
    public boolean agregarProducto(HashSet<Producto> pro, Producto pr) {
        return pro.add(pr);
    }
}
