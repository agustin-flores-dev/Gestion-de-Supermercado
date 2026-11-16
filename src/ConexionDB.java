import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final  String url = "jdbc:mysql://172.16.1.64:3306/SuperVicchioFloresLezcano"; // DB UTN
    //private static final String url = "jdbc:mysql://localhost:3306/midb"; // Prueba Local
    private static final  String userName ="bdd1";
    private static final String password ="bdd1";

    public static Connection conexion;

    public static Connection getConexion()throws ConexionFallidaException
    {
        try {
            if(conexion != null) conexion.close();
            conexion = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e){
            throw new ConexionFallidaException("[!] No se pudo conectar con la base de datos...", e);
        }
        return conexion;
    }

    public static void closeConexion()throws ConexionFallidaException{
        try {
            if(conexion != null) conexion.close();
        }catch(SQLException e){
            throw new ConexionFallidaException("[!] No existe una conexión para cerrar...", e);
        }
    }
}
