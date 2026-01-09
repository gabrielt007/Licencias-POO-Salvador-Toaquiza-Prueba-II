package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Licencias extends JFrame{
    private JPanel Licencia;
    private JButton generarButton;
    private JTextField txtCedula;
    private JLabel iconoLicenciaGenerador;

    public Licencias(String cedula, String usuario){
        setContentPane(Licencia);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        pack();
        setTitle("Sistema Licencias");
        setLocationRelativeTo(null);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/generar.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        iconoLicenciaGenerador.setIcon(new ImageIcon(img));

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

            boolean validacion=UsuarioDAO.verificarCedula(cedulaa);
            if(!validacion){
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                txtCedula.setText("");
                return;
            }
            if (!UsuarioDAO.esAptoParaLicencia(cedulaa)) {
                JOptionPane.showMessageDialog(null, "El usuario NO cumple requisitos o no aprobó");
                dispose();
                if (usuario.equals("ADMIN")){
                    new PerfilAdmin(cedula).setVisible(true);
                }else if (usuario.equals("ANALISTA")){
                    new PerfilAnalista(cedula).setVisible(true);
                }
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
