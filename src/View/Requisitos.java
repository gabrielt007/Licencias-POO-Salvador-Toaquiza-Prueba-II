package View;

import javax.swing.*;

public class Requisitos extends JFrame{
    private JCheckBox ChBoxCertificado_f;
    private JCheckBox ChBoxPago_t;
    private JCheckBox ChBoxPago_f;
    private JCheckBox ChBoxMultas_t;
    private JCheckBox ChBoxMultas_f;
    private JCheckBox ChBoxCertificado_t;
    private JFormattedTextField TextFieldObservaciones;
    private JButton aprobarButton;
    private JButton rechazarButton;
    private JPanel Requisitos;
    public Requisitos(){
        setContentPane(Requisitos);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
