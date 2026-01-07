package Model;

import Utils.Conexion;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public static String existeUsuario(String usuario, String password){
        String sql="select * from usuariosPlataforma where cedula=? and clave=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps= conn.prepareStatement(sql)){
            ps.setString(1,usuario);
            ps.setString(2,password);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getString("rol");
            }
            return "No encontrado";
        }catch (NullPointerException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean registrarUsuarioSolicitante(String cedula, String nombre, int edad, String tipoLicencia, String password) {

        String sqlBuscar = "SELECT 1 FROM usuariosPlataforma WHERE cedula = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlBuscar)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false; // ya existe
            }

            String sqlFuncion = "SELECT insertarUsuario(?,?,?,?,?) AS Resultado";

            try (PreparedStatement psInsertar = conn.prepareStatement(sqlFuncion)) {

                psInsertar.setString(1, cedula);
                psInsertar.setString(2, nombre);
                psInsertar.setInt(3, edad);
                psInsertar.setString(4, tipoLicencia);
                psInsertar.setString(5, password);

                ResultSet rsInsertar = psInsertar.executeQuery();

                if (rsInsertar.next()) {
                    return "Exitoso".equals(rsInsertar.getString("Resultado"));
                }

                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
