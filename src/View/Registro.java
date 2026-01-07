package View;

import javax.swing.*;

public class Registro extends JFrame {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox ComBxTipoLicencia;
    private JPanel Registro;
    private JButton registrarButton;
    private JTextField txtPassword;

    public Registro() {
        setContentPane(Registro);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setVisible(true);
        setSize(600,300);

    }
}
