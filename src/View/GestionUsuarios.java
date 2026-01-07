package View;

import javax.swing.*;

public class GestionUsuarios extends JFrame{
    private JTextField txtNombreCambios;
    private JTextField txtCedulaCambios;
    private JTextField txtUsernameCambios;
    private JTextField txtRolCambios;
    private JButton guardarButton;
    private JButton buscarButton;
    private JButton activarDesactivarButton;
    private JTextField txtNombre_Cambios;
    private JPanel GestionUsuarios;
    private JLabel txtEstadoActual;
    private JLabel txtNombreActual;
    private JLabel txtCedulaActual;
    private JLabel txtUsernameActual;
    private JLabel txtPasswordActual;
    private JLabel txtRolActual;
    private JTextField txtPasswordCambios;

    public GestionUsuarios(){
        setContentPane(GestionUsuarios);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
    }
}
