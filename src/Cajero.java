import java.util.ArrayList;

public class Cajero extends Empleados implements IGestionarCaja{

    private double plataRecaudadaDia;

    public Cajero(String nombre, String dni, String direccion, double sueldo, ETurno turno) {
        super(nombre, dni, direccion, sueldo, turno);
        this.plataRecaudadaDia = 0;
    }

    public Cajero(String dni, int id_empleado) {
        super(dni, id_empleado);
        this.plataRecaudadaDia = 0;
    }

    public double getPlataRecaudadaDia() {
        return plataRecaudadaDia;
    }

    @Override
    public double chequearPrecio(Producto pro) {
        return pro.getPrecioUnitario();
    }

    @Override
    public boolean realizarVenta(ArrayList<Producto> producto, ArrayList<Integer> cantidad) throws ListaVaciaException {
        double valorVenta = 0;
        if (producto.isEmpty() || cantidad.isEmpty()){
            throw new ListaVaciaException("la lista de productos o su cantidad es no existente");
        }
        for(int i = 0; i < producto.size(); i++){
            valorVenta = valorVenta+(producto.get(i).getPrecioUnitario()*cantidad.get(i));
        }
        if (valorVenta != 0){
            plataRecaudadaDia = plataRecaudadaDia+valorVenta;
            return true;
        }

        return false;

    }


}
