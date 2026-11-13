import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ConexionAdmin {
    public static void crearTablaProducto() throws CreacionDeTablaException{
    try {
        Connection conexion = ConexionDB.getConexion();
        String query = "CREATE TABLE IF NOT EXISTS Producto ("+
                "id INT(10) NOT NULL,"+
                "nombre VARCHAR(50) NOT NULL,"+
                "descripcion VARCHAR(100) NOT NULL,"+
                "dia INT(10) NOT NULL,"+
                "mes INT(10) NOT NULL,"+
                "anio INT(10) NOT NULL,"+
                "stock INT(10) NOT NULL,"+
                "precioUnitario DOUBLE(20) NOT NULL"+
                "PRIMARY KEY (id))";
        Statement pt = conexion.createStatement();
        pt.execute(query);
        pt.close();
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
            String query = "INSERT INTO Producto values (?, ?, ?, ?, ?, ?, ?, ?)";
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
            pt.close();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] no se pudo crear el producto");
        }finally{
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarProducto(int id, String nuevoNombre, String nuevaDescripcion,
            int nuevoDia, int nuevoMes, int nuevoAnio, int nuevoStock, double nuevoPrecioUnitario) throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Producto SET nombre = ?, descripcion = ?, " +
                    "dia = ?, mes = ?, anio = ?, stock = ?, precioUnitario = ? " +
                    "WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.setString(2, nuevoNombre);
            pt.setString(3, nuevaDescripcion);
            pt.setInt(4, nuevoDia);
            pt.setInt(5, nuevoMes);
            pt.setInt(6, nuevoAnio);
            pt.setInt(7, nuevoStock);
            pt.setDouble(8, nuevoPrecioUnitario);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] Hubo un error al actualizar Producto");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Producto> listarProductos() throws ListarException{
        Set<Producto> productos = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Producto";
            ResultSet rs = stmt.executeQuery(query);

            Producto p;

            while (rs.next()) {

                Fecha fecha = new Fecha(
                        rs.getInt("dia"),
                        rs.getInt("mes"),
                        rs.getInt("anio")
                );

                p = new Producto(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        fecha,
                        rs.getInt("stock"),
                        rs.getDouble("precioUnitario")
                );


                productos.add(p);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
                throw new ListarException("[!] No se pudo lista los Productos ");
        } finally {
            ConexionDB.closeConexion();
        }

        return productos;
    }


    public static void crearTablaCajero()throws CreacionDeTablaException{
        try {

            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Cajero ("+
                    "id_empleado INT (15)NOT NULL)"+
                    "dni VARCHAR(15) NOT NULL,"+
                    "nombre VARCHAR(50) NOT NULL,"+
                    "contrasenia VARCHAR(15) NOT NULL,"+
                    "direccion VARCHAR(50) NOT NULL,"+
                    "sueldo DOUBLE(15) NOT NULL ,"+
                    "turno VARCHAR(15) NOT NULL ,"+
                    "plataRecaudadaDia DOUBLE(15) NOT NULL ,"+
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] no se pudo crear la tabla cajero");
        }finally {
            ConexionDB.closeConexion();
        }

    }

    public static void insertarCajero(Cajero cajero) throws CreacionDeTablaException{
        try {
            crearTablaCajero();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Cajero values (?,?,?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, cajero.getId_empleado());
            pt.setString(2, cajero.getDni());
            pt.setString(3, cajero.getNombre());
            pt.setString(4, cajero.getContrasenia());
            pt.setString(5, cajero.getDireccion());
            pt.setDouble(6, cajero.getSueldo());
            pt.setString(7, cajero.getTurno().getTurno_nombre());
            pt.setDouble(8, cajero.getPlataRecaudadaDia());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
        }finally{
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarCajero(int id,String nuevoDni, String nuevoNombre,String nuevaContrasenia, String nuevaDireccion,
            double nuevoSueldo, ETurno nuevoTurno, double nuevaPlataRecaudadaDia) throws ActualizacionUpdateException {

        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Cajero SET nombre = ?, direccion = ?, " +
                    "sueldo = ?, turno = ?, plataRecaudadaDia = ? " +
                    "dni = ?, WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.setString(2, nuevoDni);
            pt.setString(3, nuevoNombre);
            pt.setString(4, nuevaContrasenia);
            pt.setString(5, nuevaDireccion);
            pt.setDouble(6, nuevoSueldo);
            pt.setString(7, nuevoTurno.getTurno_nombre());
            pt.setDouble(8, nuevaPlataRecaudadaDia);
            pt.executeUpdate();
            pt.close();

        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] no se pudo actualizar cajero");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Cajero> listarCajeros() throws ListarException{
        Set<Cajero> cajeros = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Cajero";
            ResultSet rs = stmt.executeQuery(query);

            Cajero c;

            while (rs.next()) {

                c = new Cajero(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("contrasenia"),
                        rs.getString("direccion"),
                        rs.getDouble("sueldo"),
                        ETurno.valueOf(rs.getString("turno"))
                );

                cajeros.add(c);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
                throw new ListarException("[!] No se pudo listar los Cajeros");
        } finally {
            ConexionDB.closeConexion();
        }

        return cajeros;
    }


    public static void crearTablaSocio() throws CreacionDeTablaException{
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Socio ("+
                    "id INT(15) NOT NULL,"+
                    "nombre VARCHAR(50) NOT NULL"+
                    "dni VARCHAR(15) NOT NULL"+
                    "contrasenia VARCHAR(15) NOT NULL"+
                    "direccion VARCHAR(50) NOT NULL"+
                    "estadoSocio BOOLEAN NOT NULL"+
                    "PRIMARY KEY (id))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
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
            String query = "INSERT INTO Socio VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, socio.getId());
            pt.setString(2, socio.getNombre());
            pt.setString(3, socio.getDni());
            pt.setString(4, socio.contrasenia);
            pt.setString(5, socio.getDireccion());
            pt.setBoolean(6, socio.isEstadoSocio());
            pt.executeUpdate();
            pt.close();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
        }finally{
            ConexionDB.closeConexion();
        }

    }


    public static void actualizarSocio(int id, String nuevoNombre, String nuevoDni, String nuevaContrasenia,
                                       String nuevaDireccion, boolean nuevoEstadoSocio) throws ActualizacionUpdateException{

        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Socio SET nombre = ?, dni = ?, contrasenia = ?," +
                    "direccion = ?, estadoSocio = ? WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.setString(2, nuevoNombre);
            pt.setString(3, nuevoDni);
            pt.setString(4, nuevaContrasenia);
            pt.setString(5, nuevaDireccion);
            pt.setBoolean(6, nuevoEstadoSocio);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] no se pudo actualizar Socio");
        } finally {
            ConexionDB.closeConexion();
        }
    }


    public static Set<Socio> listarSocio() throws ListarException{
        Set<Socio> socios = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Socio";
            ResultSet rs = stmt.executeQuery(query);

            Socio s;

            while (rs.next()) {

                s = new Socio(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("contrasenia"),
                        rs.getString("direccion"),
                        rs.getBoolean("estadoSocio")
                );

                socios.add(s);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Socios");
        } finally {
            ConexionDB.closeConexion();
        }

        return socios;
    }

    public static void crearTablaRepositor() throws CreacionDeTablaException{
        try {
           Connection conexion = ConexionDB.getConexion();
           String query ="CREATE TABLE IF NOT EXISTS Repositor ("+
                   "id_empleado INT(15) NOT NULL"+
                   "dni VARCHAR(15) NOT NULL,"+
                   "nombre VARCHAR(50) NOT NULL,"+
                   "direccion VARCHAR(50) NOT NULL ,"+
                   "contrasenia VARCHAR(15) NOT NULL ,"+
                   "sueldo DOUBLE(15) NOT NULL,"+
                   "turno VARCHAR(15) NOT NULL ,"+
                   "PRIMARY KEY (dni))";
           Statement pt = conexion.createStatement();
           pt.execute(query);
           pt.close();
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
            String query = "INSERT INTO Repositor Values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, repositor.getId_empleado());
            pt.setString(2, repositor.getDni());
            pt.setString(3, repositor.getNombre());
            pt.setString(4, repositor.getDireccion());
            pt.setString(5, repositor.getContrasenia());
            pt.setDouble(6, repositor.getSueldo());
            pt.setString(7, repositor.getTurno().getTurno_nombre());
            pt.executeUpdate();
            pt.close();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos");
        }finally {
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarRepositor(int id,String nuevoDni, String nuevoNombre, String nuevaDireccion, String nuevaContrasenia, double nuevoSueldo,
            ETurno nuevoTurno) throws ActualizacionUpdateException {

        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Repositor SET nombre = ?, direccion = ?, " +
                    "contrasenia = ?, sueldo = ?, turno = ? , dni = ?,WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.setString(2, nuevoDni);
            pt.setString(3, nuevoNombre);
            pt.setString(4, nuevaDireccion);
            pt.setString(5, nuevaContrasenia);
            pt.setDouble(6, nuevoSueldo);
            pt.setString(7, nuevoTurno.getTurno_nombre());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] no se pudo actualizar Repositor");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Repositor> listarRepositor() throws ListarException {
        Set<Repositor> repositores = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Repositor";
            ResultSet rs = stmt.executeQuery(query);

            Repositor r;

            while (rs.next()) {

                r = new Repositor(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getDouble("sueldo"),
                        ETurno.valueOf(rs.getString("turno"))
                );

                repositores.add(r);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Repositores");
        } finally {
            ConexionDB.closeConexion();
        }

        return repositores;
    }

    public static void crearTablaAdmin() throws CreacionDeTablaException{
        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "CREATE TABLE IF NOT EXISTS Admin ("+
                    "id_empleado VARCHAR(15) NOT NULL,"+
                    "dni VARCHAR(15) NOT NULL," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "direccion VARCHAR(50) NOT NULL ," +
                    "contrasenia VARCHAR(15) NOT NULL ," +
                    "sueldo DOUBLE(15) NOT NULL," +
                    "turno VARCHAR(15) NOT NULL ," +
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
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
            String query = "INSERT INTO Repositor Values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, admin.getId_empleado());
            pt.setString(2, admin.getDni());
            pt.setString(3, admin.getNombre());
            pt.setString(4, admin.getDireccion());
            pt.setString(5, admin.getContrasenia());
            pt.setDouble(6, admin.getSueldo());
            pt.setString(7, admin.getTurno().getTurno_nombre());
            pt.executeUpdate();
            pt.close();
        }catch (SQLException e){
            throw new CreacionDeTablaException("[!] Hubo un error al UPDATEAR los datos");
        }finally {
            ConexionDB.closeConexion();
        }
    }



    public static void actualizarAdmin(int id,String nuevoDni, String nuevoNombre, String nuevaDireccion,
            String nuevaContrasenia, double nuevoSueldo, ETurno nuevoTurno)throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Admin SET nombre = ?, direccion = ?, " +
                    "contrasenia = ?, sueldo = ?, turno = ?,  dni = ?, WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.setString(2, nuevoDni);
            pt.setString(3, nuevoNombre);
            pt.setString(4, nuevaDireccion);
            pt.setString(5, nuevaContrasenia);
            pt.setDouble(6, nuevoSueldo);
            pt.setString(7, nuevoTurno.getTurno_nombre());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] no se pudo actualizar Administrador");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Admin> listarAdmin(GestoraEmpleados gestora) throws ListarException {
        Set<Admin> admins = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Admin";
            ResultSet rs = stmt.executeQuery(query);

            Admin a;

            while (rs.next()) {

                a = new Admin(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getDouble("sueldo"),
                        ETurno.valueOf(rs.getString("turno")),
                        gestora
                );

                admins.add(a);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Admin");
        } finally {
            ConexionDB.closeConexion();
        }

        return admins;
    }


}
