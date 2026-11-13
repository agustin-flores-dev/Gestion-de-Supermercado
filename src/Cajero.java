import java.util.ArrayList;

public final class Cajero extends Empleados implements IGestionarCaja{

    private double plataRecaudadaDia;

    public Cajero(String dni, String nombre, String direccion, String contrasenia, double sueldo, ETurno turno) {
        super(dni, nombre, direccion, contrasenia, sueldo, turno);
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
    public boolean realizarVenta(ArrayList<Producto> producto, ArrayList<Integer> cantidad) throws ListaVaciaException, ActualizacionUpdateException {
        if (producto == null || cantidad == null || producto.isEmpty() || cantidad.isEmpty()) {
            throw new ListaVaciaException("[!] La lista de productos está vacía o es inexistente.");
        }

        boolean alerta = false;
        String nombreP = "";

        if (producto.size() != cantidad.size()) {
            throw new IllegalArgumentException("[!] Las listas no contienen la misma cantidad.");
        }

        double valorVenta = 0;
        for (int i = 0; i < producto.size(); i++) {
            Producto p = producto.get(i);
            int cantVendida = cantidad.get(i);

            if (p.getStock() < cantVendida) {
                alerta = true;
                nombreP = p.getNombre();
            }

            // Actualizamos valorVenta y el stock.
            valorVenta += (p.getPrecioUnitario() * cantVendida);
            p.setStock(p.getStock() - cantVendida);
        }

        for (int i = 0; i < producto.size(); i++){
            ConexionAdmin.actualizarProducto(producto.get(i).getId(), producto.get(i).getNombre(), producto.get(i).getDescripcion(), producto.get(i).getFechaVencimiento().getDia(), producto.get(i).getFechaVencimiento().getMes(), producto.get(i).getFechaVencimiento().getAnio(), producto.get(i).getStock(), producto.get(i).getPrecioUnitario());
        }

        if (valorVenta > 0) {
            plataRecaudadaDia += valorVenta;
            if (alerta){
                throw new IllegalArgumentException("[!] Stock desactualisado para el producto: " + nombreP +
                        " llame al repositor para actualisar el stock") ;
            }
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
