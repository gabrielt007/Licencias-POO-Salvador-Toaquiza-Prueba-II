package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MostarUsuarios extends JFrame {
    private JPanel PanelPrincipal;
    private JTable tablaLicencias;
    private JLabel licenciaIcon;
    private JComboBox comboBox1;
    private JButton FILTRARButton;

    public MostarUsuarios(String usuario,String cedulaI) {
        setContentPane(PanelPrincipal);
        setTitle("Usuarios Plataforma");
        setSize(500,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/licencias.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        licenciaIcon.setIcon(new ImageIcon(img));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(MostarUsuarios.this, new PerfilAdmin(cedulaI));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(MostarUsuarios.this, new PerfilAnalista(cedulaI));
                }
            }
        });
        tablaLicencias.setModel(UsuarioDAO.mostrarUsuarios("NINGUNA"));
        FILTRARButton.addActionListener(e -> {
            if (comboBox1.getSelectedItem().toString().equals("NINGUNA")){
                tablaLicencias.setModel(UsuarioDAO.mostrarUsuarios("NINGUNA"));
            } else if (comboBox1.getSelectedItem().toString().equals("ADMINISTRADOR")) {
                tablaLicencias.setModel(UsuarioDAO.mostrarUsuarios("ADMINISTRADOR"));
            }else if (comboBox1.getSelectedItem().toString().equals("ANALISTA")) {
                tablaLicencias.setModel(UsuarioDAO.mostrarUsuarios("ANALISTA"));
            }
        });
    }
}
