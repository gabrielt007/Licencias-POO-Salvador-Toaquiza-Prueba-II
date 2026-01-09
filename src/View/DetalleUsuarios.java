package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetalleUsuarios extends JFrame {
    private JTable datosPersonalesTable;
    private JTable requisitosTable;
    private JTable notasTable;
    private JScrollPane datosPersonales;
    private JScrollPane requisitos;
    private JScrollPane notasP;
    private JPanel panelPrincipal;
    private JLabel detallesIcon;

    public DetalleUsuarios(String usuario,String cedula,String cedulaSolicitante) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panelPrincipal);
        setTitle("Detalle Usuarios");
        setSize(480, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/detallesUsuario.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        detallesIcon.setIcon(new ImageIcon(img));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(DetalleUsuarios.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(DetalleUsuarios.this, new PerfilAnalista(cedula));
                }
            }
        });

        datosPersonalesTable.setModel(UsuarioDAO.cargarVistaTramitesUsuario(cedulaSolicitante));
        notasTable.setModel(UsuarioDAO.cargarDatosUsuarios_notas(cedulaSolicitante));
        requisitosTable.setModel(UsuarioDAO.cargarDatosUsuarios_requisitos(cedulaSolicitante));

        datosPersonalesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        requisitosTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        notasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        VentanaManager.ajustarColumnas(datosPersonalesTable);
        VentanaManager.ajustarColumnas(requisitosTable);
        VentanaManager.ajustarColumnas(notasTable);
    }

}
