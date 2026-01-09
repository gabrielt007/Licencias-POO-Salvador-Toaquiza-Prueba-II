package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MostarLicencias extends JFrame {
    private JPanel PanelPrincipal;
    private JTable tablaLicencias;
    private JLabel licenciaIcon;

    public MostarLicencias(String cedulaI,String usuario) {
        setContentPane(PanelPrincipal);
        setTitle("LICENCIAS");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/licencias.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        licenciaIcon.setIcon(new ImageIcon(img));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(MostarLicencias.this, new PerfilAdmin(cedulaI));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(MostarLicencias.this, new PerfilAnalista(cedulaI));
                }
            }
        });

        tablaLicencias.setModel(UsuarioDAO.cargarLicencia());
    }
}
