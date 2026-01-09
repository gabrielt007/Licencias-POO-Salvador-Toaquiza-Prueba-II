package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilUsuario extends JFrame {

    private JPanel PerfilUsuario;
    private JButton cerrarSesionButton;
    private JTable table1;
    private JLabel iconoUsuario;
    private JPanel PanelUsuarioLicencia;
    private JButton mostrarLicenciaButton;
    private JPanel licencia;
    private JLabel tipoLicencia;
    private JLabel emision;
    private JLabel vencimiento;
    private JLabel logo;
    private JLabel cedula;
    private String cedulaUsuario;

    public PerfilUsuario(String cedula) {
        this.cedulaUsuario = cedula;

        setContentPane(PerfilUsuario);
        setTitle("Sistema de Licencias");
        setSize(600,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/usuario.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        iconoUsuario.setIcon(new ImageIcon(img));

        VentanaManager.ajustarColumnas(table1);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setModel(UsuarioDAO.cargarVistaTramitesUsuario(cedulaUsuario));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilUsuario.this, new Login());
            }
        });

        cerrarSesionButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null,"Cierre de sesion exitoso");
            new Login().setVisible(true);
        });

        mostrarLicenciaButton.addActionListener(e -> {
            if (!PanelUsuarioLicencia.isVisible()) {
                PanelUsuarioLicencia.setVisible(true);
            }else{
                PanelUsuarioLicencia.setVisible(false);
            }
        });

    }

//    private void configurarTabla() {
//        DefaultTableModel modelo = new DefaultTableModel();
//        modelo.addColumn("Tipo de Licencia");
//        modelo.addColumn("Fecha de Solicitud");
//        modelo.addColumn("Estado del Trámite");
//        tablaTramiteUsuario.setModel(modelo);
//    }
//
//    private void cargarDatosUsuario() {
//        String sql = "SELECT nombre, cedula FROM usuariosSolicitantes WHERE cedula = ?";
//
//        try (Connection con = Conexion.getConexion();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, cedulaUsuario);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                txtNombreUsuario.setText(rs.getString("nombre"));
//                txtCedulaUsuario.setText(rs.getString("cedula"));
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error cargando usuario");
//        }
//    }
//
//    private void cargarTramiteUsuario() {
//
//        DefaultTableModel modelo = (DefaultTableModel) tablaTramiteUsuario.getModel();
//        modelo.setRowCount(0);
//
//        String sql = "SELECT tipoLicencia,fechaSolicitud, estadoTramite FROM usuariosSolicitantes WHERE cedula = ?";
//
//        try (Connection con = Conexion.getConexion();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, cedulaUsuario);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Object[] fila = new Object[3];
//                fila[0]=rs.getString("tipoLicencia");
//                fila[1] = rs.getTimestamp("fechaSolicitud");
//                fila[2] = rs.getString("estadoTramite");
//                modelo.addRow(fila);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error cargando trámite");
//        }
//    }


}

