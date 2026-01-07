package View;

import Controller.VentanaManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilAnalista extends JFrame {
    private JLabel txtTipoUsuario;
    private JLabel txtNombreUsuario;
    private JButton registrarButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton cerrarSesionButton;
    private JPanel PerfilAnalista;
    private JButton requisitosButton;
    private JButton examenesButton;

    public PerfilAnalista(String nombreUsuario) {
        setContentPane(PerfilAnalista);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilAnalista.this, new Login());
            }
        });

        txtNombreUsuario.setText(nombreUsuario);


    }
}
