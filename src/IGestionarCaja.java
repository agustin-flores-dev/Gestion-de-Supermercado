import java.util.ArrayList;
import java.util.HashSet;

public interface IGestionarCaja {

    public double chequearPrecio(Producto pro) throws ElementoVacioException;
    public boolean realizarVenta(ArrayList<Producto> producto, ArrayList<Integer> cantidad) throws ListaVaciaException;


}
