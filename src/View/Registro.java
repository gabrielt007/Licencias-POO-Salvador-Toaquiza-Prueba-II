package View;

import Model.UsuarioDAO;

import javax.swing.*;
import java.security.Principal;
import java.util.Objects;

public class Registro extends JFrame {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox ComBxTipoLicencia;
    private JPanel Registro;
    private JButton registrarButton;
    private JTextField txtPassword;

    public Registro(String nombreUsuario) {
        setContentPane(Registro);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setVisible(true);
        setSize(600,300);

        registrarButton.addActionListener(e -> {
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String edad =txtEdad.getText();
            String tipoLicencia = Objects.requireNonNull(ComBxTipoLicencia.getSelectedItem()).toString();
            String password = txtPassword.getText();

            if (cedula.trim().isEmpty() || nombre.trim().isEmpty() || tipoLicencia.trim().isEmpty() || password.trim().isEmpty() || cedula.trim().length() != 10||edad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese datos validos");
            }else {
                int edadInt = Integer.parseInt(edad);
                if(edadInt >= 18){
                    if (UsuarioDAO.registrarUsuarioSolicitante(cedula, nombre, edadInt, tipoLicencia, password)) {
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                        setVisible(false);
                        new PerfilAdmin(nombreUsuario).setVisible(true);
                    }
                }else  {
                    JOptionPane.showMessageDialog(null, "La edad debe ser mayor o igual a 18");
                }

            }
        });
    }
}
