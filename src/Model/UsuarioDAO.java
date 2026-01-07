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

    public static String existeSolicitante(String cedula, String password) {

        String sql = "select * from usuariosSolicitantes where cedula = ? and clave = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("estadoTramite");
            }

            return "No encontrado";

        } catch (NullPointerException | SQLException e) {
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

    public static boolean verificarCedula(String cedula){
        String sqlUsuario = "SELECT 1 FROM usuariosSolicitantes WHERE cedula = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {

            psUsuario.setString(1, cedula);
            ResultSet rsUsuario = psUsuario.executeQuery();

            if (rsUsuario.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String requisitos(String cedula) {

        //String sqlUsuario = "SELECT 1 FROM usuariosSolicitantes WHERE cedula = ?";
        String sqlRequisitos = "SELECT certificadoMedico, pago, multas, observaciones FROM requisitos WHERE cedula = ? ";

//        try (Connection conn = Conexion.getConexion();
//             PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
//
//            psUsuario.setString(1, cedula);
//            ResultSet rsUsuario = psUsuario.executeQuery();
//
//            if (!rsUsuario.next()) {
//                return "NO_EXISTE";
//            }

            try (Connection conn=Conexion.getConexion();
                    PreparedStatement psReq = conn.prepareStatement(sqlRequisitos)) {
                psReq.setString(1, cedula);
                ResultSet rsReq = psReq.executeQuery();

                if (rsReq.next()) {
                    return rsReq.getString("certificadoMedico") + "/" +
                            rsReq.getString("pago") + "/" +
                            rsReq.getString("multas") + "/" +
                            rsReq.getString("observaciones");
                } else {
                    return "SIN_REQUISITOS";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
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

    public static boolean registrarUsuarioPlataforma(String cedulaI, String passHash, String rol, String estado) {
        String resultado=existeUsuario(cedulaI,passHash);
        if(resultado.equals("No encontrado")){
            String sql="insert into usuariosPlataforma(cedula,clave,rol,estadoUsuario) values(?,?,?,?)";
            try(Connection conn=Conexion.getConexion();
            PreparedStatement ps=conn.prepareStatement(sql)){
                ps.setString(1, cedulaI);
                ps.setString(2, passHash);
                ps.setString(3, rol.toUpperCase());
                ps.setString(4, estado.toUpperCase());
                ps.executeUpdate();
                return true;
            }catch (NullPointerException|SQLException e){
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static String examenes(String cedulaSolicitante) {
        String sql="select practica,teorica,promedio from examen where cedula=?";
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)) {
            ps.setString(1,cedulaSolicitante);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                String mensaje = "";
                mensaje+=rs.getString("practica")+"/";
                mensaje+=rs.getString("teorica")+"/";
                mensaje+=rs.getString("promedio");
                return mensaje;
            }
            return "No hay datos";
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
