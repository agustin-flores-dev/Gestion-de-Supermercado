import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ConexionAdmin {

    public static void crearTablas() throws CreacionDeTablaException {
        crearTablaProducto();
        crearTablaCajero();
        crearTablaRepositor();
        crearTablaAdmin();
        crearTablaGanancias();
        crearTablaSocio();
    }

    public static void crearTablaProducto() throws CreacionDeTablaException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Producto ("+
                    "id INT(10) NOT NULL," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "descripcion VARCHAR(100) NOT NULL," +
                    "dia INT(10) NOT NULL," +
                    "mes INT(10) NOT NULL," +
                    "anio INT(10) NOT NULL," +
                    "stock INT(10) NOT NULL," +
                    "precioUnitario DOUBLE NOT NULL," +
                    "PRIMARY KEY (id))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] No se pudo crear la tabla Producto...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void insertarProducto(Producto producto) throws CreacionDeTablaException{
        try {
            crearTablaProducto();
            Connection conexion = ConexionDB.getConexion();
            String query = "REPLACE INTO Producto values (?, ?, ?, ?, ?, ?, ?, ?)";
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
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] No se pudo crear el producto...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void actualizarProducto(int id, String nuevoNombre, String nuevaDescripcion,
                                          int nuevoDia, int nuevoMes, int nuevoAnio, int nuevoStock, double nuevoPrecioUnitario)
            throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Producto SET nombre = ?, descripcion = ?, " +
                    "dia = ?, mes = ?, anio = ?, stock = ?, precioUnitario = ? " +
                    "WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, nuevoNombre);
            pt.setString(2, nuevaDescripcion);
            pt.setInt(3, nuevoDia);
            pt.setInt(4, nuevoMes);
            pt.setInt(5, nuevoAnio);
            pt.setInt(6, nuevoStock);
            pt.setDouble(7, nuevoPrecioUnitario);
            pt.setInt(8, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] Hubo un error al actualizar Producto...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Producto> listarProductos() throws ListarException {
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
                        rs.getInt("id"),
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
            throw new ListarException("[!] No se pudo lista los Productos...");
        } finally {
            ConexionDB.closeConexion();
        }

        return productos;
    }


    public static void crearTablaCajero() throws CreacionDeTablaException {
        try {

            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Cajero (" +
                    "id_empleado INT (15)NOT NULL," +
                    "dni VARCHAR(15) NOT NULL," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "contrasenia VARCHAR(15) NOT NULL," +
                    "direccion VARCHAR(50) NOT NULL," +
                    "sueldo DOUBLE NOT NULL," +
                    "turno VARCHAR(15) NOT NULL," +
                    "plataRecaudadaDia DOUBLE NOT NULL," +
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] No se pudo crear la tabla cajero...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void insertarCajero(Cajero cajero) throws CreacionDeTablaException {
        try {
            crearTablaCajero();
            Connection conexion = ConexionDB.getConexion();
            String query = "REPLACE INTO Cajero values (?,?,?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, cajero.getId_empleado());
            pt.setString(2, cajero.getDni());
            pt.setString(3, cajero.getNombre());
            pt.setString(4, cajero.getContrasenia());
            pt.setString(5, cajero.getDireccion());
            pt.setDouble(6, cajero.getSueldo());
            pt.setString(7, cajero.getTurno().name());
            pt.setDouble(8, cajero.getPlataRecaudadaDia());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarCajero(int id, String nuevoDni, String nuevoNombre, String nuevaContrasenia, String nuevaDireccion,
                                        double nuevoSueldo, ETurno nuevoTurno, double nuevaPlataRecaudadaDia)
            throws ActualizacionUpdateException {

        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Cajero SET dni = ?, nombre = ?, contrasenia = ?, " +
                    "direccion = ?, sueldo = ?, turno = ?, plataRecaudadaDia = ? " +
                    "WHERE id_empleado = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, nuevoDni);
            pt.setString(2, nuevoNombre);
            pt.setString(3, nuevaContrasenia);
            pt.setString(4, nuevaDireccion);
            pt.setDouble(5, nuevoSueldo);
            pt.setString(6, nuevoTurno.name());
            pt.setDouble(7, nuevaPlataRecaudadaDia);
            pt.setInt(8, id);
            pt.executeUpdate();
            pt.close();

        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo actualizar cajero...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Cajero> listarCajeros() throws ListarException {
        Set<Cajero> cajeros = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Cajero";
            ResultSet rs = stmt.executeQuery(query);

            Cajero c;

            while (rs.next()) {

                c = new Cajero(
                        rs.getInt("id_empleado"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getDouble("sueldo"),
                        ETurno.valueOf(rs.getString("turno")),
                        rs.getDouble("plataRecaudadaDia")
                );

                cajeros.add(c);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Cajeros...");
        } finally {
            ConexionDB.closeConexion();
        }

        return cajeros;
    }

    public static void eliminarCajero(int id) throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "DELETE FROM Cajero WHERE id_empleado = ?";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo eliminar el Cajero de la base de datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void crearTablaGanancias() throws CreacionDeTablaException{
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Ganancias(" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "gananciasDiarias DOUBLE NOT NULL," +
                    "gananciasMensuales DOUBLE," +
                    "dia INT NOT NULL," +
                    "mes INT NOT NULL," +
                    "anio INT NOT NULL," +
                    "PRIMARY KEY (id))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e){
            throw new CreacionDeTablaException("[!] No se pudo crear la tabla Ganancias");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void insertarNuevasGanancias (double ganDia, double ganMes, int dia, int mes, int anio)
            throws ActualizacionUpdateException{
        try {
            crearTablaGanancias();
            Connection con = ConexionDB.getConexion();
            String query = "INSERT INTO Ganancias (gananciasDiarias, gananciasMensuales, dia, mes, anio)" +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pr = con.prepareStatement(query);
            pr.setDouble(1, ganDia);
            pr.setDouble(2, ganMes);
            pr.setInt(3, dia);
            pr.setInt(4, mes);
            pr.setInt(5, anio);
            pr.executeUpdate();
            con.close();
        } catch (SQLException e){
            throw new ActualizacionUpdateException("[!] No se pudo actualizar ganancias...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void actualizarGananciasDiarias(int dia, int mes, int anio, double nuevasGanDia)
            throws ActualizacionUpdateException{
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Ganancias SET gananciasDiarias = ? WHERE dia = ? AND mes = ? AND anio = ?";
            PreparedStatement pr = conexion.prepareStatement(query);
            pr.setDouble(1, nuevasGanDia);
            pr.setInt(2, dia);
            pr.setInt(3, mes);
            pr.setInt(4, anio);
            pr.executeUpdate();
            conexion.close();
        } catch (SQLException e){
            throw new ActualizacionUpdateException("[!] No se pudo actualizar ganancias...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void actualizarGananciasMensuales (int dia, int mes, int anio, double nuevasGanMen)
            throws ActualizacionUpdateException{
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Ganancias SET gananciasMensuales = ? WHERE dia = ? AND mes = ? AND anio = ?";
            PreparedStatement pr = conexion.prepareStatement(query);
            pr.setDouble(1, nuevasGanMen);
            pr.setInt(2, dia);
            pr.setInt(3, mes);
            pr.setInt(4, anio);
            pr.executeUpdate();
            conexion.close();
        } catch (SQLException e){
            throw new ActualizacionUpdateException("[!] No se pudo actualizar ganancias...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Double> listarGananciasDiarias () throws ListarException{

        Set<Double> d = new HashSet<Double>();

        try{
            Connection con = ConexionDB.getConexion();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM Ganancias";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                d.add(rs.getDouble("gananciasDiarias"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e){
            throw new ListarException("[!] No se pudo listar las ganacias diarias...");
        } finally {
            ConexionDB.closeConexion();
        }

        return d;
    }

    public static HashMap<Fecha, Double> listarGananciasMensual () throws ListarException{

        HashMap<Fecha, Double> d = new HashMap<>();
        Fecha f;

        try{

            Connection con = ConexionDB.getConexion();
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM Ganancias";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                f = new Fecha(rs.getInt("dia"), rs.getInt("mes"), rs.getInt("anio"));
                d.put(f, rs.getDouble("gananciasMensuales"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e){
            throw new ListarException("[!] No se pudo listar las ganacias Mensuales...");
        } finally {
            ConexionDB.closeConexion();
        }

        return d;
    }

    public static void crearTablaSocio() throws CreacionDeTablaException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Socio ("+
                    "id INT(15) NOT NULL AUTO_INCREMENT," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "dni VARCHAR(15) NOT NULL," +
                    "contrasenia VARCHAR(15) NOT NULL," +
                    "direccion VARCHAR(50) NOT NULL," +
                    "estadoSocio BOOLEAN NOT NULL," +
                    "PRIMARY KEY (id))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] No se pudo crear la tabla Socio...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void insertarSocio(Socio socio) throws CreacionDeTablaException {
        try {
            crearTablaSocio();
            Connection conexion = ConexionDB.getConexion();
            String query = "INSERT INTO Socio (nombre, dni, contrasenia, direccion, estadoSocio)" +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, socio.getNombre());
            pt.setString(2, socio.getDni());
            pt.setString(3, socio.contrasenia);
            pt.setString(4, socio.getDireccion());
            pt.setBoolean(5, socio.isEstadoSocio());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error al insertar el socio: " + e.getMessage());
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void actualizarSocio(int id, String nuevoNombre, String nuevoDni, String nuevaContrasenia,
                                       String nuevaDireccion, boolean nuevoEstadoSocio)
            throws ActualizacionUpdateException {

        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "UPDATE Socio SET nombre = ?, dni = ?, contrasenia = ?, " +
                    "direccion = ?, estadoSocio = ? WHERE id = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, nuevoNombre);
            pt.setString(2, nuevoDni);
            pt.setString(3, nuevaContrasenia);
            pt.setString(4, nuevaDireccion);
            pt.setBoolean(5, nuevoEstadoSocio);
            pt.setInt(6, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo actualizar Socio...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Socio> listarSocio() throws ListarException {
        Set<Socio> socios = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Socio";
            ResultSet rs = stmt.executeQuery(query);

            Socio s;

            while (rs.next()) {

                s = new Socio(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getBoolean("estadoSocio")
                );
                socios.add(s);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Socios...");
        } finally {
            ConexionDB.closeConexion();
        }

        return socios;
    }

    public static void crearTablaRepositor() throws CreacionDeTablaException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "CREATE TABLE IF NOT EXISTS Repositor (" +
                    "id_empleado INT(15) NOT NULL," +
                    "dni VARCHAR(15) NOT NULL," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "direccion VARCHAR(50) NOT NULL ," +
                    "contrasenia VARCHAR(15) NOT NULL ," +
                    "sueldo DOUBLE NOT NULL," +
                    "turno VARCHAR(15) NOT NULL ," +
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error en la creacion de la Tabla Repositor...");
        } finally {
            ConexionDB.closeConexion();
        }

    }

    public static void insertarRepositor(Repositor repositor) throws CreacionDeTablaException {
        try {
            crearTablaRepositor();
            Connection conexion = ConexionDB.getConexion();
            String query = "REPLACE INTO Repositor Values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, repositor.getId_empleado());
            pt.setString(2, repositor.getDni());
            pt.setString(3, repositor.getNombre());
            pt.setString(4, repositor.getDireccion());
            pt.setString(5, repositor.getContrasenia());
            pt.setDouble(6, repositor.getSueldo());
            pt.setString(7, repositor.getTurno().name());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error a UPDATEAR los datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarRepositor(int id, String nuevoDni, String nuevoNombre,
                                           String nuevaDireccion, String nuevaContrasenia, double nuevoSueldo, ETurno nuevoTurno)
            throws ActualizacionUpdateException {

        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Repositor SET dni = ?, nombre = ?, direccion = ?, " +
                    "contrasenia = ?, sueldo = ?, turno = ? " +
                    "WHERE id_empleado = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, nuevoDni);
            pt.setString(2, nuevoNombre);
            pt.setString(3, nuevaDireccion);
            pt.setString(4, nuevaContrasenia);
            pt.setDouble(5, nuevoSueldo);
            pt.setString(6, nuevoTurno.name());
            pt.setInt(7, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo actualizar Repositor...");
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
                        rs.getInt("id_empleado"),
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
            throw new ListarException("[!] No se pudo listar los Repositores...");
        } finally {
            ConexionDB.closeConexion();
        }

        return repositores;
    }

    public static void eliminarRepositor(int id) throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "DELETE FROM Repositor WHERE id_empleado = ?";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo eliminar el Repositor de la base de datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static void crearTablaAdmin() throws CreacionDeTablaException {
        try {
            Connection conexion = ConexionDB.getConexion();

            String query = "CREATE TABLE IF NOT EXISTS Admin (" +
                    "id_empleado INT(15) NOT NULL," +
                    "dni VARCHAR(15) NOT NULL," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "direccion VARCHAR(50) NOT NULL ," +
                    "contrasenia VARCHAR(15) NOT NULL ," +
                    "sueldo DOUBLE NOT NULL," +
                    "turno VARCHAR(15) NOT NULL ," +
                    "PRIMARY KEY (dni))";
            Statement pt = conexion.createStatement();
            pt.execute(query);
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error al crear la Tabla Admin...");
        } finally {
            ConexionDB.closeConexion();
        }

    }

    public static void insertarAdmin(Admin admin) throws CreacionDeTablaException {
        try {
            crearTablaAdmin();
            Connection conexion = ConexionDB.getConexion();
            String query = "REPLACE INTO Admin Values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, admin.getId_empleado());
            pt.setString(2, admin.getDni());
            pt.setString(3, admin.getNombre());
            pt.setString(4, admin.getDireccion());
            pt.setString(5, admin.getContrasenia());
            pt.setDouble(6, admin.getSueldo());
            pt.setString(7, admin.getTurno().name());
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new CreacionDeTablaException("[!] Hubo un error al UPDATEAR los datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }


    public static void actualizarAdmin(int id, String nuevoDni, String nuevoNombre, String nuevaDireccion,
                                       String nuevaContrasenia, double nuevoSueldo, ETurno nuevoTurno)
            throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "UPDATE Admin SET dni = ?, nombre = ?, direccion = ?, " +
                    "contrasenia = ?, sueldo = ?, turno = ? WHERE id_empleado = ?";

            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setString(1, nuevoDni);
            pt.setString(2, nuevoNombre);
            pt.setString(3, nuevaDireccion);
            pt.setString(4, nuevaContrasenia);
            pt.setDouble(5, nuevoSueldo);
            pt.setString(6, nuevoTurno.name());
            pt.setInt(7, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo actualizar Administrador...");
        } finally {
            ConexionDB.closeConexion();
        }
    }

    public static Set<Admin> listarAdmin(GestoraEmpleados gestor) throws ListarException {
        Set<Admin> admins = new HashSet<>();

        try {
            Connection conexion = ConexionDB.getConexion();
            Statement stmt = conexion.createStatement();
            String query = "SELECT * FROM Admin";
            ResultSet rs = stmt.executeQuery(query);

            Admin a;

            while (rs.next()) {

                a = new Admin(
                        rs.getInt("id_empleado"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getDouble("sueldo"),
                        ETurno.valueOf(rs.getString("turno")),
                        gestor
                );

                admins.add(a);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            throw new ListarException("[!] No se pudo listar los Admin...");
        } finally {
            ConexionDB.closeConexion();
        }

        return admins;
    }

    public static void eliminarAdmin(int id) throws ActualizacionUpdateException {
        try {
            Connection conexion = ConexionDB.getConexion();
            String query = "DELETE FROM Admin WHERE id_empleado = ?";
            PreparedStatement pt = conexion.prepareStatement(query);
            pt.setInt(1, id);
            pt.executeUpdate();
            pt.close();
        } catch (SQLException e) {
            throw new ActualizacionUpdateException("[!] No se pudo eliminar el Administrador de la base de datos...");
        } finally {
            ConexionDB.closeConexion();
        }
    }
}