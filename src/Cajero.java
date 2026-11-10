import java.util.ArrayList;

public final class Cajero extends Empleados implements IGestionarCaja{

    private double plataRecaudadaDia;

    public Cajero(String nombre, String dni, String contrasenia, String direccion, double sueldo, ETurno turno) {
        super(nombre, dni, contrasenia, direccion, sueldo, turno);
        this.plataRecaudadaDia = 0;
    }

    public double getPlataRecaudadaDia() {
        return plataRecaudadaDia;
    }

    @Override
    public double chequearPrecio(Producto pro) throws ElementoVacioException{
        if(pro == null) throw new ElementoVacioException("producto ingresado, no posee informacion cargada");

        return pro.getPrecioUnitario();
    }

    @Override
    public boolean realizarVenta(ArrayList<Producto> producto, ArrayList<Integer> cantidad) throws ListaVaciaException {
        if (producto == null || cantidad == null || producto.isEmpty() || cantidad.isEmpty()) {
            throw new ListaVaciaException("[!] La lista de productos está vacía o es inexistente.");
        }

        if (producto.size() != cantidad.size()) {
            throw new IllegalArgumentException("[!] Las listas no contienen la misma cantidad.");
        }

        double valorVenta = 0;
        for (int i = 0; i < producto.size(); i++) {
            Producto p = producto.get(i);
            int cantVendida = cantidad.get(i);

            if (p.getStock() < cantVendida) {
                throw new IllegalArgumentException("[!] Stock insuficiente para el producto: " +p.getNombre());
            }

            // Actualizamos valorVenta y el stock.
            valorVenta += (p.getPrecioUnitario() * cantVendida);
            p.setStock(p.getStock() - cantVendida);
        }

        if (valorVenta > 0) {
            plataRecaudadaDia += valorVenta;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[ROL]: " +ERol.CAJERO.toString() +"\n"
                +super.toString() +"\n"
                +"[Plata Recaudada en el día]: $" +plataRecaudadaDia;
    }
}
