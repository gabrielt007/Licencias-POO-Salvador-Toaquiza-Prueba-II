package View;

import javax.swing.*;

public class PerfilAnalista extends JFrame {
    private JLabel txtTipoUsuario;
    private JLabel txtNombreUsuario;
    private JButton registrarButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton cerrarSesionButton;
    private JPanel PerfilAnalista;
    private JButton button1;
    private JButton button2;

    public PerfilAnalista(){
        setContentPane(PerfilAnalista);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
    }
}
