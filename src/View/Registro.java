package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;
import Utils.HashUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Registro extends JFrame {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox ComBxTipoLicencia;
    private JPanel Registro;
    private JButton registrarButton;
    private JTextField txtPassword;
    private JLabel iconNuevo;

    public Registro(String nombreUsuario,String usuario) {
        setContentPane(Registro);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setVisible(true);
        setSize(600,350);
        setLocationRelativeTo(null);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/nuevo.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        iconNuevo.setIcon(new ImageIcon(img));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(Registro.this, new PerfilAdmin(nombreUsuario));
            }
        });

        registrarButton.addActionListener(e -> {
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText().toUpperCase();
            String edad =txtEdad.getText();
            String tipoLicencia = Objects.requireNonNull(ComBxTipoLicencia.getSelectedItem()).toString();
            String password = txtPassword.getText();

            if (cedula.trim().isEmpty() || nombre.trim().isEmpty() || tipoLicencia.trim().isEmpty() || password.trim().isEmpty() || cedula.trim().length() != 10||edad.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese datos validos");
            }else {
                int edadInt = Integer.parseInt(edad);
                if(edadInt >= 18){
                    String passwordHash = HashUtil.hash(password);
                    if (UsuarioDAO.registrarUsuarioSolicitante(cedula, nombre, edadInt, tipoLicencia, passwordHash)) {
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                        dispose();
                        new PerfilAdmin(nombreUsuario).setVisible(true);
                    }
                }else  {
                    JOptionPane.showMessageDialog(null, "La edad debe ser mayor o igual a 18");
                }

            }
        });
    }
}
