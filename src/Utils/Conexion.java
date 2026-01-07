package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL =//url base de datos
            "jdbc:mysql://usp52rnutsbqis9e:IFbhjKkEemRmLdscbZBO@byxpzfxyzgeksaonkut4-mysql.services.clever-cloud.com:3306/byxpzfxyzgeksaonkut4";
    private static final String USER = "usp52rnutsbqis9e";
    private static final String PASS = "IFbhjKkEemRmLdscbZBO";

    public static Connection getConexion() {
        try {//intentar la conexion
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
            return null;
        }
    }
}
