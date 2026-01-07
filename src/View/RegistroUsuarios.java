package View;

import javax.swing.*;

public class RegistroUsuarios extends JFrame{
    private JPanel RegistroUsuarios;
    private JButton button1;
    private JTextField txtCedula;
    private JTextField txtClave;
    private JComboBox CombBoxRol;
    private JComboBox CombBoxEstado;
    public RegistroUsuarios(){
        setContentPane(RegistroUsuarios);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
    }
}
