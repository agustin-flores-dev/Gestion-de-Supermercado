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
                "dia INT(10) NOT NULL,"+
                "mes INT(10) NOT NULL,"+
                "anio INT(10) NOT NULL,"+
                "stock INT(10) NOT NULL,"+
                "precioUnitario DOUBLE(20) NOT NULL"+
                "PRIMARY KEY (id))";
        Statement pt = conexion.createStatement();
        pt.execute(query);
    }catch (SQLException e){
        throw new CreacionDeTablaException("[!] no se pudo crear la tabla Producto");
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
            pt.setInt(4, producto.getFechaVencimiento().getDia());
            pt.setInt(5, producto.getFechaVencimiento().getMes());
            pt.setInt(6, producto.getFechaVencimiento().getAnio());
            pt.setInt(7, producto.getStock());
            pt.setDouble(8, producto.getPrecioUnitario());
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
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
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
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
        }finally{
            ConexionDB.closeConexion();
        }
    }

    public static void crearTablaSocio() throws CreacionDeTablaException{
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Socio ("+
                    "id INT(15) NOT NULL,"+
                    "nombre VARCHAR(15) NOT NULL"+
                    "dni VARCHAR(15) NOT NULL"+
                    "direccion VARCHAR(15) NOT NULL"+
                    "estadoSocio BOOLEAN NOT NULL"+
                    "PRIMARY KEY (id))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
        }catch(SQLException e){
            throw new CreacionDeTablaException("[!] no se pudo crear la tabla Socio");
        }finally {
            ConexionDB.closeConexion();
        }
    }

    public static void insertarSocio(Socio socio)throws CreacionDeTablaException{
        try {
            crearTablaSocio();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Socio VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, socio.getId());
            pt.setString(2, socio.getNombre());
            pt.setString(3, socio.getDni());
            pt.setString(4, socio.getDireccion());
            pt.setBoolean(5, socio.isEstadoSocio());
            pt.executeUpdate();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
        }finally{
            ConexionDB.closeConexion();
        }

    }

    public static void crearTablaRepositor() throws CreacionDeTablaException{
        try {
           Connection conexion = ConexionDB.getConexion();
           String query ="CREATE TABLE IF NOT EXISTS Repositor ("+
                   "dni VARCHAR(15) NOT NULL,"+
                   "nombre VARCHAR(15) NOT NULL,"+
                   "direccion VARCHAR(15) NOT NULL ,"+
                   "contrasenia VARCHAR(15) NOT NULL ,"+
                   "sueldo DOUBLE(15) NOT NULL,"+
                   "turno VARCHAR(15) NOT NULL ,"+
                   "PRIMARY KEY (dni))";
           Statement pt = conexion.createStatement();
           pt.execute(query);
        }catch(SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error en la creacion de la Tabla Repositor");
        }finally{
            ConexionDB.closeConexion();
        }

    }

    public static void insertarRepositor(Repositor repositor) throws CreacionDeTablaException{
        try {
            crearTablaRepositor();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Repositor Values(?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, repositor.getDni());
            pt.setString(2, repositor.getNombre());
            pt.setString(3, repositor.getDireccion());
            pt.setString(4, repositor.getContrasenia());
            pt.setDouble(5, repositor.getSueldo());
            pt.setString(6, repositor.getTurno().getTurno_nombre());
            pt.executeUpdate();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEar los datos");
        }finally {
            ConexionDB.closeConexion();
        }
    }

    public static void crearTablaAdmin() throws CreacionDeTablaException{
        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "CREATE TABLE IF NOT EXISTS Admin ("+
                    "dni VARCHAR(15) NOT NULL," +
                    "nombre VARCHAR(15) NOT NULL," +
                    "direccion VARCHAR(15) NOT NULL ," +
                    "contrasenia VARCHAR(15) NOT NULL ," +
                    "sueldo DOUBLE(15) NOT NULL," +
                    "turno VARCHAR(15) NOT NULL ," +
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error al crear la Tabla Admin");
        }finally {
            ConexionDB.closeConexion();
        }

    }

    public static void insertarAdmin(Admin admin) throws CreacionDeTablaException{
        try {
            crearTablaAdmin();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Repositor Values(?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, admin.getDni());
            pt.setString(2, admin.getNombre());
            pt.setString(3, admin.getDireccion());
            pt.setString(4, admin.getContrasenia());
            pt.setDouble(5, admin.getSueldo());
            pt.setString(6, admin.getTurno().getTurno_nombre());
            pt.executeUpdate();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error al UPDATEAR los datos");
        }finally {
            ConexionDB.closeConexion();
        }
    }



}
