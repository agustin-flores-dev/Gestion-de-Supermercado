import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final  String url = "jdbc:mysql://172.16.1.64:3306/faltaelNombre";
    private static final  String userName ="nombreUsuario";
    private static final String password ="contraseña";
    public static Connection conexion;

    public static Connection getConexion(){
        try {
            conexion = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e){
            throw new ConexionFallidaException();
        }
        return conexion;
    }

    public static void closeConexion(){
        try {
            conexion.close();
        }catch(SQLException e){
            throw new ConexionFallidaException();
        }
    }
}
