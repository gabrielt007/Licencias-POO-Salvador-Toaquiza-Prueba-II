package View;

import javax.swing.*;

public class Registro extends JFrame {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox ComBxTipoLicencia;
    private JPasswordField txtPassword;
    private JPanel Registro;
    private JButton registrarButton;
    public Registro() {
        setContentPane(Registro);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Login");
        setVisible(true);
        setSize(600,300);
    }
}
