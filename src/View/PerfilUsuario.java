package View;

import Controller.VentanaManager;
import Utils.Conexion;

import javax.swing.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class PerfilUsuario extends JFrame {

    private JPanel PerfilUsuario;
    private JTable tablaTramiteUsuario;
    private JButton cerrarSesionButton;
    private JLabel txtNombreUsuario;
    private JLabel txtCedulaUsuario;
    private String cedulaUsuario;

    public PerfilUsuario(String cedula) {
        this.cedulaUsuario = cedula;

        setContentPane(PerfilUsuario);
        setTitle("Sistema de Licencias");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        configurarTabla();
        cargarDatosUsuario();
        cargarTramiteUsuario();

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

        setVisible(true);
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Tipo de Licencia");
        modelo.addColumn("Fecha de Solicitud");
        modelo.addColumn("Estado del Trámite");
        tablaTramiteUsuario.setModel(modelo);
    }

    private void cargarDatosUsuario() {
        String sql = "SELECT nombre, cedula FROM usuariosSolicitantes WHERE cedula = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedulaUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombreUsuario.setText(rs.getString("nombre"));
                txtCedulaUsuario.setText(rs.getString("cedula"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuario");
        }
    }

    private void cargarTramiteUsuario() {

        DefaultTableModel modelo = (DefaultTableModel) tablaTramiteUsuario.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT tipoLicencia,fechaSolicitud, estadoTramite FROM usuariosSolicitantes WHERE cedula = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedulaUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0]=rs.getString("tipoLicencia");
                fila[1] = rs.getTimestamp("fechaSolicitud");
                fila[2] = rs.getString("estadoTramite");
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando trámite");
        }
    }


}

