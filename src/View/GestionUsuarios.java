package View;

import javax.swing.*;

public class GestionUsuarios extends JFrame{
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtUsername;
    private JPasswordField txtxPassword;
    private JTextField txtRol;
    private JTextField txtxEstado;
    private JButton guardarButton;
    private JButton buscarButton;
    private JButton activarDesactivarButton;
    private JTextField txtNombre_Cambios;
    private JPanel GestionUsuarios;
    public GestionUsuarios(){
        setContentPane(GestionUsuarios);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
