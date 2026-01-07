package View;

import Model.UsuarioDAO;

import javax.swing.*;

public class Requisitos extends JFrame{
    private JCheckBox ChBoxCertificado_f;
    private JCheckBox ChBoxPago_t;
    private JCheckBox ChBoxPago_f;
    private JCheckBox ChBoxMultas_t;
    private JCheckBox ChBoxMultas_f;
    private JCheckBox ChBoxCertificado_t;
    private JFormattedTextField TextFieldObservaciones;
    private JButton aceptarCambiosButton;
    private JButton rechazarButton;
    private JPanel Requisitos;
    public Requisitos(String cedula, String cedulaSolicitante){
        setContentPane(Requisitos);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        aceptarCambiosButton.addActionListener(e -> {
            UsuarioDAO.requisitos(cedulaSolicitante);
        });
    }
}
