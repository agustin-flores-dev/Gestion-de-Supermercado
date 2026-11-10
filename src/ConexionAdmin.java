import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionAdmin {
    public static void crearTablaProducto() throws CreacionDeTablaException{
    try {
        Connection conexion = ConexionDB.getConexion();
        String query = "CREATE TABLE IF NOT EXISTS Producto ("+
                "id INT(10) NOT NULL,"+
                "nombre VARCHAR(15) NOT NULL,"+
                "descripcion VARCHAR(100) NOT NULL,"+
                "fechaVencimiento DATETIME(10) NOT NULL,"+
                "stock INT(10) NOT NULL,"+
                "precioUnitario DOUBLE(20) NOT NULL"+
                "PRIMARY KEY (id))";
        Statement pt = conexion.createStatement();
        pt.execute(query);
    }catch (SQLException e){
        throw new CreacionDeTablaException("[!] no se pudo crear la tabla producto");
    }finally{
            ConexionDB.closeConexion();
        }
    }

    public static void insertarProducto(Producto producto){
        try {
            crearTablaProducto();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Producto values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, producto.getId());
            pt.setString(2, producto.getNombre());
            pt.setString(3, producto.getDescripcion());
            //pt.setDate(4, producto.getFechaVencimiento()); falta buscar como hacerlo
            pt.setInt(5, producto.getStock());
            pt.setDouble(6, producto.getPrecioUnitario());
            pt.executeUpdate();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] no se pudo crear la tabla producto");
        }finally{
            ConexionDB.closeConexion();
        }
    }

    public static void crearTablaCajero()throws CreacionDeTablaException{
        try {

            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Cajero ("+
                    "dni VARCHAR(15) NOT NULL,"+
                    "nombre VARCHAR(15) NOT NULL,"+
                    "direccion VARCHAR(15) NOT NULL,"+
                    "sueldo DOUBLE(15) NOT NULL ,"+
                    "turno VARCHAR(15) NOT NULL ,"+
                    "plataRecaudadaDia DOUBLE(15) NOT NULL ,"+
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] no se pudo crear la tabla producto");
        }finally {
            ConexionDB.closeConexion();
        }

    }

    public static void insertarCajero(Cajero cajero) throws CreacionDeTablaException{
        try {
            crearTablaCajero();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Cajero values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, cajero.getDni());
            pt.setString(2, cajero.getNombre());
            pt.setString(3, cajero.getDireccion());
            pt.setDouble(4, cajero.getSueldo());
            pt.setString(5, cajero.getTurno().getTurno_nombre());
            pt.setDouble(6, cajero.getPlataRecaudadaDia());
            pt.executeUpdate();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] no se pudo crear la tabla producto");
        }finally{
            ConexionDB.closeConexion();
        }
    }

    /*repositor, socio, admin*/


}
