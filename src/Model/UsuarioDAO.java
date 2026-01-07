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
}
