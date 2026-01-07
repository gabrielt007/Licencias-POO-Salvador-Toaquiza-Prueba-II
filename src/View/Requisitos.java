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

        String resultados=UsuarioDAO.requisitos(cedulaSolicitante);
        String[] partes = resultados.split("/");
        String certMedO= partes[0];
        String pagoO= partes[1];
        String multaO= partes[2];
        String obsO= partes[3];

        if(multaO.equals("1")){
            ChBoxMultas_t.setSelected(true);
        }else if(multaO.equals("0")){
            ChBoxMultas_f.setSelected(true);
        }
        if(pagoO.equals("1")){
            ChBoxPago_t.setSelected(true);
        }else if(pagoO.equals("0")){
            ChBoxPago_f.setSelected(true);
        }
        if(certMedO.equals("1")){
            ChBoxCertificado_t.setSelected(true);
        }else if(certMedO.equals("0")){
            ChBoxCertificado_f.setSelected(true);
        }
        if(obsO.equals("Ninguna")){
            TextFieldObservaciones.setText("Ninguna");
        }else {
            TextFieldObservaciones.setText(obsO);
        }

        aceptarCambiosButton.addActionListener(e -> {
            String certMedNuevo="",pagoNuevo="",multaNuevo="",obsNuevo="";
            if (ChBoxCertificado_f.isSelected()) {
                certMedNuevo="0";
            }else if (ChBoxCertificado_t.isSelected()) {
                certMedNuevo="1";
            }

            if (ChBoxPago_f.isSelected()) {
                pagoNuevo="0";
            }else if (ChBoxPago_t.isSelected()) {
                pagoNuevo="1";
            }

            if (ChBoxMultas_f.isSelected()) {
                multaNuevo="0";
            }else if (ChBoxMultas_t.isSelected()) {
                multaNuevo="1";
            }

            if (TextFieldObservaciones.getText().trim().isEmpty()) {
                obsNuevo="Ninguna";
            }else{
                obsNuevo=TextFieldObservaciones.getText().trim();
            }

            String ejecucion=UsuarioDAO.actualizarRequisitos(certMedNuevo,pagoNuevo,multaNuevo,obsNuevo,cedulaSolicitante);
            if(ejecucion.equals("Exitoso")){
                dispose();
                JOptionPane.showMessageDialog(null,"Actualizacion exitosa");
                new PerfilAdmin(cedula).setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"No se pudo actualizar");
            }
        });
    }
}
