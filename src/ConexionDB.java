import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final  String url = "jdbc:mysql://172.16.1.64:3306/midb";
    private static final  String userName ="bdd1";
    private static final String password ="bdd1";
    public static Connection conexion;

    public static Connection getConexion(){
        try {
            if(conexion != null) conexion.close();
            conexion = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e){
            throw new ConexionFallidaException("[!] No se pudo conectar con la base de datos...");
        }
        return conexion;
    }

    public static void closeConexion(){
        try {
            if(conexion != null) conexion.close();
        }catch(SQLException e){
            throw new ConexionFallidaException("[!] No existe una conexión para cerrar...");
        }
    }
}
