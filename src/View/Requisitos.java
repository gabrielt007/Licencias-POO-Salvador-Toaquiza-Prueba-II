package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Requisitos extends JFrame{
    private JCheckBox ChBoxCertificado_f;
    private JCheckBox ChBoxPago_t;
    private JCheckBox ChBoxPago_f;
    private JCheckBox ChBoxMultas_t;
    private JCheckBox ChBoxMultas_f;
    private JCheckBox ChBoxCertificado_t;
    private JButton aceptarCambiosButton;
    private JButton rechazarButton;
    private JPanel Requisitos;
    private JTextArea textAreaObservaciones;
    private JLabel requisitoIcon;
    //private JTextField TextFieldObservaciones;

    public Requisitos(String cedula, String cedulaSolicitante,String resultados,String usuario){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(Requisitos.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(Requisitos.this, new PerfilAnalista(cedula));
                }
            }
        });

        setContentPane(Requisitos);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        setSize(400,400);
        setLocationRelativeTo(null);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/tramite.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        requisitoIcon.setIcon(new ImageIcon(img));

        String[] partes = resultados.split("/");
        String certMedO = partes[0];
        String pagoO = partes[1];
        String multaO = partes[2];
        String obsO = partes[3];

        if (multaO.equals("1")) {
            ChBoxMultas_t.setSelected(true);
        } else if (multaO.equals("0")) {
            ChBoxMultas_f.setSelected(true);
        }
        if (pagoO.equals("1")) {
            ChBoxPago_t.setSelected(true);
        } else if (pagoO.equals("0")) {
            ChBoxPago_f.setSelected(true);
        }
        if (certMedO.equals("1")) {
            ChBoxCertificado_t.setSelected(true);
        } else if (certMedO.equals("0")) {
            ChBoxCertificado_f.setSelected(true);
        }
        if (obsO.equals("Ninguna")) {
            textAreaObservaciones.setText("Ninguna");
        } else {
            textAreaObservaciones.setText(obsO);
        }

        aceptarCambiosButton.addActionListener(e -> {
            String certMedNuevo = "", pagoNuevo = "", multaNuevo = "", obsNuevo = "";
            if (ChBoxCertificado_f.isSelected()) {
                certMedNuevo = "0";
            } else if (ChBoxCertificado_t.isSelected()) {
                certMedNuevo = "1";
            }

            if (ChBoxPago_f.isSelected()) {
                pagoNuevo = "0";
            } else if (ChBoxPago_t.isSelected()) {
                pagoNuevo = "1";
            }

            if (ChBoxMultas_f.isSelected()) {
                multaNuevo = "0";
            } else if (ChBoxMultas_t.isSelected()) {
                multaNuevo = "1";
            }

            if (textAreaObservaciones.getText().trim().isEmpty()) {
                obsNuevo = "Ninguna";
            } else {
                obsNuevo = textAreaObservaciones.getText().trim();
            }

            String ejecucion = UsuarioDAO.actualizarRequisitos(certMedNuevo, pagoNuevo, multaNuevo, obsNuevo, cedulaSolicitante);
            if (ejecucion.equals("Exitoso")) {
                dispose();
                UsuarioDAO.actualizarEstado(cedulaSolicitante);
                JOptionPane.showMessageDialog(null, "Actualizacion exitosa");
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(Requisitos.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(Requisitos.this, new PerfilAnalista(cedula));
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar");
            }
        });

        rechazarButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "Deshaciendo los cambios");
            if (usuario.equals("ADMIN")){
                VentanaManager.cambiar(Requisitos.this, new PerfilAdmin(cedula));
            }else if (usuario.equals("ANALISTA")){
                VentanaManager.cambiar(Requisitos.this, new PerfilAnalista(cedula));
            }
        });
    }
}
