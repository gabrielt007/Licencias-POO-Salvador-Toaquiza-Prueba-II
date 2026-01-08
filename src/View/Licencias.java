package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Licencias extends JFrame{
    private JPanel Licencia;
    private JButton generarButton;
    private JTextField txtCedula;
    public Licencias(String cedula, String usuario){
        setContentPane(Licencia);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        pack();
        setTitle("Sistema Licencias");
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(Licencias.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(Licencias.this, new PerfilAnalista(cedula));
                }
            }
        });


        generarButton.addActionListener(e->{
            String cedulaa = txtCedula.getText();

            if (!UsuarioDAO.esAptoParaLicencia(cedulaa)) {
                JOptionPane.showMessageDialog(null, "El usuario NO cumple requisitos o no aprobó");
                return;
            }

            int r = JOptionPane.showConfirmDialog(null,
                    "¿Desea generar la licencia para esta cédula?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (r == JOptionPane.YES_OPTION) {
                if (UsuarioDAO.generarLicencia(cedulaa)) {
                    JOptionPane.showMessageDialog(null, "Licencia generada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al generar licencia");
                }
            }

        });

    }

}
