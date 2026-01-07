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


    public static String requisitos(String cedula) {
        String sql="select 1 from usuariosSolicitantes where cedula=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, cedula);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                String sqlRequisitosActuales="select * from requisitos where cedula=?";
                try (PreparedStatement psRequisitos=conn.prepareStatement(sqlRequisitosActuales)){
                    psRequisitos.setString(1, cedula);
                    ResultSet rsRequisitos=psRequisitos.executeQuery();
                    if(rsRequisitos.next()){
                        String cm=rsRequisitos.getString("certificadoMedico");
                        String p=rsRequisitos.getString("pago");
                        String m=rsRequisitos.getString("multas");
                        String obs=rsRequisitos.getString("observaciones");
                        return cm+"/"+p+"/"+m+"/"+obs;
                    }
                }
            }else{
                return "No encontrado";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String actualizarRequisitos(String certMedNuevo, String pagoNuevo, String multaNuevo, String obsNuevo, String cedulaSolicitante) {
        String sql="update requisitos set certificadoMedico=?,pago=?,multas=?,observaciones=? where cedula=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(certMedNuevo));
            ps.setInt(2, Integer.parseInt(pagoNuevo));
            ps.setInt(3, Integer.parseInt(multaNuevo));
            ps.setString(4, obsNuevo);
            ps.setString(5, cedulaSolicitante);
            ps.executeUpdate();
            return "Exitoso";
        }catch (NullPointerException| SQLException e){
            throw new RuntimeException(e);
        }
    }
}
