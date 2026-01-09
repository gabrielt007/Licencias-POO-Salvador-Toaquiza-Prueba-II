package Model;

import Utils.Conexion;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSetMetaData;

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

    public static boolean verificarCedulaPlataforma(String cedula){
        String sqlUsuario = "SELECT 1 FROM usuariosPlataforma WHERE cedula = ?";
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

    private static String manejarIntentos(String usuario, String tabla) {
        String sql = "SELECT intentos FROM " + tabla + " WHERE cedula = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int intentosActuales = rs.getInt("intentos");

                // Bloquear si llega a 3
                if (intentosActuales >= 2) {
                    String sqlEstado = "UPDATE " + tabla +
                            " SET estadoUsuario = 'BLOQUEADO', intentos = 0 WHERE cedula = ?";

                    try (PreparedStatement psEstado = conn.prepareStatement(sqlEstado)) {
                        psEstado.setString(1, usuario);
                        psEstado.executeUpdate();
                    }
                    return "BLOQUEADO";
                }

                // Aumentar intentos
                int nuevosIntentos = intentosActuales + 1;
                String aumentarIntentos = "UPDATE " + tabla + " SET intentos = ? WHERE cedula = ?";

                try (PreparedStatement psIntentos = conn.prepareStatement(aumentarIntentos)) {
                    psIntentos.setInt(1, nuevosIntentos);
                    psIntentos.setString(2, usuario);
                    psIntentos.executeUpdate();
                }

                return String.valueOf(nuevosIntentos);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String intentos(String usuario) {
        return manejarIntentos(usuario, "usuariosPlataforma");
    }

    public static String intentosSolicitantes(String usuario) {
        return manejarIntentos(usuario, "usuariosSolicitantes");
    }


    private static String obtenerEstado(String usuario, String tabla) {
        String sql = "SELECT estadoUsuario FROM " + tabla + " WHERE cedula = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("estadoUsuario");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }


    public static String estadoUsuario(String usuario) {
        return obtenerEstado(usuario, "usuariosPlataforma");
    }

    public static String estadoUsuarioSolicitante(String usuario) {
        return obtenerEstado(usuario, "usuariosSolicitantes");
    }


    private static void resetearIntentos(String usuario, String tabla) {
        String sql = "UPDATE " + tabla + " SET intentos = 0 WHERE cedula = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void modificarIntentos(String usuario) {
        resetearIntentos(usuario, "usuariosPlataforma");
    }

    public static void modificarIntentosSolicitantes(String usuario) {
        resetearIntentos(usuario, "usuariosSolicitantes");
    }


    public static void modificarNotas(String cedulaSolicitante, double nuevaP, double nuevaT) {
        String sql="update examen set practica=?,teorica=? where cedula=?";
        try(Connection conn =Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setDouble(1, nuevaP);
            ps.setDouble(2, nuevaT);
            ps.setString(3, cedulaSolicitante);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static String actualizarEstado(String cedula) {
        String sqlEstadoActual = "SELECT estadoTramite FROM usuariosSolicitantes WHERE cedula=?";
        String sqlEstadoExamen = "SELECT estado FROM examen WHERE cedula=?";
        String sqlEstadoRequisitos = "SELECT estadoRequisitos FROM requisitos WHERE cedula=?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps1 = conn.prepareStatement(sqlEstadoActual);
             PreparedStatement ps2 = conn.prepareStatement(sqlEstadoExamen);
             PreparedStatement ps3 = conn.prepareStatement(sqlEstadoRequisitos)) {

            ps1.setString(1, cedula);
            ps2.setString(1, cedula);
            ps3.setString(1, cedula);

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();

            if (rs1.next() && rs2.next() && rs3.next()) {

                String estadoActual = rs1.getString("estadoTramite");
                String estadoExamen = rs2.getString("estado"); // ✔ corregido
                String estadoRequisitos = rs3.getString("estadoRequisitos");

                String estadoNuevo = estadoActual;

                boolean examenAprobado = "APROBADO".equals(estadoExamen);
                boolean examenReprobado = "REPROBADO".equals(estadoExamen);
                boolean requisitosOK = "OK".equals(estadoRequisitos);

//                if ("Pendiente".equals(estadoActual)) {

                    if (examenReprobado) {
                        estadoNuevo = "REPROBADO";
                    } else if (requisitosOK && examenAprobado) {
                        estadoNuevo = "PREPARADO";
                    } else if (requisitosOK) {
                        estadoNuevo = "en_examenes";
                    } else if (examenAprobado) {
                        estadoNuevo = "APROBADO";
                    } else {
                        if (estadoActual.equals("LicenciaEmitida")) {
                            estadoNuevo = "LicenciaEmitida";
                        }else{
                        estadoNuevo = "Pendiente";}
                    }
  //              }

                String sqlActualizacion =
                        "UPDATE usuariosSolicitantes SET estadoTramite=? WHERE cedula=?";

                try (PreparedStatement psActualizacion =
                             conn.prepareStatement(sqlActualizacion)) {
                    psActualizacion.setString(1, estadoNuevo);
                    psActualizacion.setString(2, cedula);
                    psActualizacion.executeUpdate();
                }
                System.out.println(estadoNuevo);
                return estadoNuevo;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Sin estado";
    }

    public static DefaultTableModel cargarVistaTramites() {

        String sql = "SELECT * FROM tramites";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            // Nombres de columnas
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modelo;
    }

    public static DefaultTableModel cargarVistaTramitesUsuario(String cedula) {

        String sql = "SELECT * FROM tramites where cedula=?";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            // Nombres de columnas
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modelo;
    }
    //Prueba para generar Licencias
    public static boolean esAptoParaLicencia(String cedula) {

        String sql = """
        select estadoTramite
        from usuariosSolicitantes
        where cedula = ?
        and estadoTramite = 'PREPARADO'
    """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }
    public static boolean generarLicencia(String cedula) {

        String sql = "insert into licencia (cedula, numeroLicencia) values (?, floor(rand()*900000)+100000)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.executeUpdate();

            // Cambiar estado del trámite
            PreparedStatement ps2 = con.prepareStatement(
                    "update usuariosSolicitantes set estadoTramite='Aprobado' where cedula=?"
            );
            ps2.setString(1, cedula);
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static DefaultTableModel cargarDatosUsuarios_notas(String cedula) {
        String sql = "SELECT * FROM examen where cedula=?";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            // Nombres de columnas
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelo;
    }

    public static DefaultTableModel cargarDatosUsuarios_requisitos(String cedula) {
        String sql = "SELECT * FROM requisitos where cedula=?";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            // Nombres de columnas
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            // Filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelo;
    }

    public static String datosActuales(String cedulaSolicitante, String table) {
        String sql="select * from "+table+" where cedula=?";
        try(Connection conn =Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, cedulaSolicitante);
            ResultSet rs=ps.executeQuery();
            String mensaje="";
            //System.out.println(rs.next());
            if (table.equals("usuariosPlataforma")) {
                if (rs.next()) {
                    System.out.println(rs.getString("cedula"));
                    System.out.println(rs.getString("rol"));
                    System.out.println(rs.getString("estadoUsuario"));

                    mensaje+=rs.getString("cedula")+"/";
                    mensaje+=rs.getString("rol")+"/";
                    mensaje+=rs.getString("estadoUsuario");
                    System.out.println(mensaje);

                    return mensaje;
                }
            }else if(table.equals("usuariosSolicitantes")) {
                if (rs.next()) {
                    System.out.println(rs.getString("cedula"));
                    System.out.println(rs.getString("estadoUsuario"));

                    mensaje+=rs.getString("cedula")+"/";
                    mensaje+=rs.getString("estadoUsuario");
                    System.out.println(mensaje);

                    return mensaje;
                }
            }

            return "";
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void actalizarDatos(String cedulaActual, String cedulaNueva, String rolNueva,String pass, String estadoNueva,String table) {
        String sql="";
        if (table.equals("usuariosPlataforma")) {
            sql="update "+table+" set cedula=?,clave=?,rol=?,estadoUsuario=? where cedula=?";
            try(Connection conn=Conexion.getConexion();
                PreparedStatement ps=conn.prepareStatement(sql)){
                ps.setString(1, cedulaNueva);
                ps.setString(2, pass);
                ps.setString(3, rolNueva);
                ps.setString(4, estadoNueva);
                ps.setString(5, cedulaActual);
                ps.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }else if (table.equals("usuariosSolicitantes")) {
            sql="update "+table+" set cedula=?,clave=?,estadoUsuario=? where cedula=?";
            try(Connection conn=Conexion.getConexion();
                PreparedStatement ps=conn.prepareStatement(sql)){
                ps.setString(1, cedulaNueva);
                ps.setString(2, pass);
                ps.setString(3, estadoNueva);
                ps.setString(4, cedulaActual);
                ps.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
//prueba para reportes
    public static DefaultTableModel cargarReporte(
            String desde, String hasta,
            String estado, String tipo, String cedula) {

        String sql = "SELECT * FROM tramites WHERE 1=1 ";

        if (!desde.isEmpty()) sql += " AND date(fechaSolicitud) >= ?";
        if (!hasta.isEmpty()) sql += " AND date(fechaSolicitud) <= ?";
        if (!estado.equals("Todos")) sql += " AND estadoTramite = ?";
        if (!tipo.equals("Todos")) sql += " AND tipoLicencia = ?";
        if (!cedula.isEmpty()) sql += " AND cedula = ?";

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            if (!desde.isEmpty()) ps.setString(i++, desde);
            if (!hasta.isEmpty()) ps.setString(i++, hasta);
            if (!estado.equals("Todos")) ps.setString(i++, estado);
            if (!tipo.equals("Todos")) ps.setString(i++, tipo);
            if (!cedula.isEmpty()) ps.setString(i++, cedula);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            for (int c = 1; c <= columnas; c++) {
                modelo.addColumn(meta.getColumnName(c));
            }

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int c = 0; c < columnas; c++) {
                    fila[c] = rs.getObject(c + 1);
                }
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modelo;
    }


    public static TableModel cargarLicencia() {
        String sql="select * from licencia";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try(Connection conn=Conexion.getConexion();
        PreparedStatement ps=conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            for (int c = 1; c <= columnas; c++) {
                modelo.addColumn(meta.getColumnName(c));
            }

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int c = 0; c < columnas; c++) {
                    fila[c] = rs.getObject(c + 1);
                }
                modelo.addRow(fila);
            }
            return  modelo;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
