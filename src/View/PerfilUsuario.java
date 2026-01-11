package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilUsuario extends JFrame {

    private JPanel PerfilUsuario;
    public JButton cerrarSesionButton;
    private JTable table1;
    private JLabel iconoUsuario;
    private JPanel PanelUsuarioLicencia;
    private JButton mostrarLicenciaButton;
    private JLabel tipoLicencia;
    private JLabel emision;
    private JLabel vencimiento;
    private JLabel logo;
    private JLabel cedulaL;
    private JLabel nombre;
    private JButton exportarEnPDFButton;
    private String cedulaUsuario;
    private JFrame ventanaOrigen;

    public PerfilUsuario(String cedula, JFrame origen) {

        this.ventanaOrigen = origen;
        this.cedulaUsuario = cedula;
        exportarEnPDFButton.setVisible(false);
        setContentPane(PerfilUsuario);
        setTitle("Sistema de Licencias");
        setSize(600,335);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
if (ventanaOrigen instanceof MostarLicencias) {
    cerrarSesionButton.setVisible(false);
}
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
                ventanaOrigen.setVisible(true);
                dispose();
            }
        });


        cerrarSesionButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null,"Cierre de sesion exitoso");
            new Login().setVisible(true);
        });

        mostrarLicenciaButton.addActionListener(e -> {
            String datos=UsuarioDAO.mostrarLicencia(cedula);
            if(datos.equals("")){
                JOptionPane.showMessageDialog(null,"La licencia no se puede mostrar, no se encontro el usuario");
                return;
            }
            String[] lista=datos.split("/");
            cedulaL.setText(lista[0]);
            nombre.setText(lista[1]);
            tipoLicencia.setText(lista[2]);
            emision.setText(lista[3]);
            vencimiento.setText(lista[4]);
            if (!PanelUsuarioLicencia.isVisible()) {
                PanelUsuarioLicencia.setVisible(true);
                exportarEnPDFButton.setVisible(true);
            }else{
                PanelUsuarioLicencia.setVisible(false);
            }
        });

        exportarEnPDFButton.addActionListener(e -> {
            VentanaManager.guardarPdfConDialogo(PanelUsuarioLicencia);
        });
    }
}

