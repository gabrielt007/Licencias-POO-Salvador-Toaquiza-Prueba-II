package View;

import javax.swing.*;

public class GestionUsuarios extends JFrame{
    private JTextField txtCedulaCambios;
    private JTextField txtRolCambios;
    private JButton guardarButton;
    private JButton buscarButton;
    private JButton activarDesactivarButton;
    private JTextField txtNombre_Cambios;
    private JPanel GestionUsuarios;
    private JLabel txtEstadoActual;
    private JLabel txtCedulaActual;
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
