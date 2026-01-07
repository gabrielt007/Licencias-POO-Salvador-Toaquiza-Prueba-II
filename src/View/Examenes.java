package View;

import javax.swing.*;

public class Examenes extends JFrame{
    private JPanel Examenes;
    private JTextField txtCedulaSolicitante;
    private JTextField txtNotaP;
    private JTextField txtNotaT;
    private JButton ingresarButton;
    public Examenes(){
        setContentPane(Examenes);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
    }
}
