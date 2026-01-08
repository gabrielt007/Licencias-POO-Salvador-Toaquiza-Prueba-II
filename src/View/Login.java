package View;

import Model.UsuarioDAO;
import Utils.HashUtil;
import java.awt.*;
import javax.swing.*;
import java.security.Principal;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JButton ingresarButton;
    private JPanel LoginPane;
    private JPasswordField txtPassword;
    private JLabel iconPanel;

    public Login() {
        setContentPane(LoginPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Login");
        setVisible(true);
        setSize(600,300);
        setLocationRelativeTo(null);

        ImageIcon iconoLogin = new ImageIcon(getClass().getResource("/Imagenes/icono.png"));
        iconPanel.setIcon(iconoLogin);


        ingresarButton.addActionListener(e -> {

            String usuario = txtUsuario.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());
            String passwordHash = HashUtil.hash(password);

            // 1. Validar bloqueo primero Plataforma
            String estado = UsuarioDAO.estadoUsuario(usuario);
            if ("BLOQUEADO".equals(estado)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Su usuario está bloqueado, contacte con el administrador",
                        "BLOQUEADO",
                        JOptionPane.WARNING_MESSAGE
                );
                txtUsuario.setText("");
                txtPassword.setText("");
                return;
            }

            //Bloqueo Solicitantes

            String estadoS = UsuarioDAO.estadoUsuarioSolicitante(usuario);
            if ("BLOQUEADO".equals(estadoS)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Su usuario está bloqueado, contacte con el administrador",
                        "BLOQUEADO",
                        JOptionPane.WARNING_MESSAGE
                );
                txtUsuario.setText("");
                txtPassword.setText("");
                return;
            }

            // 2. Validar usuario plataforma
            String tipoUsuario = UsuarioDAO.existeUsuario(usuario, passwordHash);
            if ("ADMINISTRADOR".equals(tipoUsuario)) {
                UsuarioDAO.modificarIntentos(usuario);
                new PerfilAdmin(usuario).setVisible(true);
                dispose();
                return;
            }
            if ("ANALISTA".equals(tipoUsuario)) {
                UsuarioDAO.modificarIntentos(usuario);
                new PerfilAnalista(usuario).setVisible(true);
                dispose();
                return;
            }

            // 3. Validar solicitante
            String tipoUserSol = UsuarioDAO.existeSolicitante(usuario, passwordHash);
            if (!"No encontrado".equals(tipoUserSol)) {
                UsuarioDAO.modificarIntentosSolicitantes(usuario);
                new PerfilUsuario(usuario).setVisible(true);
                dispose();
                return;
            }

            // 4. Fallo de login → manejar intentos
            // 4. Fallo de login → manejar intentos SOLO donde exista el usuario

            if (!UsuarioDAO.estadoUsuario(usuario).isEmpty()) {
                // Existe como usuario plataforma
                String intentos = UsuarioDAO.intentos(usuario);
                mostrarMensajeIntentos(intentos);

            } else if (!UsuarioDAO.estadoUsuarioSolicitante(usuario).isEmpty()) {
                // Existe como solicitante
                String intentosS = UsuarioDAO.intentosSolicitantes(usuario);
                mostrarMensajeIntentos(intentosS);

            } else {
                // No existe en ningún lado
                JOptionPane.showMessageDialog(
                        null,
                        "Usuario no encontrado",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
            }


            txtUsuario.setText("");
            txtPassword.setText("");
        });

    }

    private void mostrarMensajeIntentos(String intentos) {
        if ("BLOQUEADO".equals(intentos)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ha superado el número de intentos. Usuario bloqueado.",
                    "BLOQUEADO",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Credenciales incorrectas. Intento " + intentos + " de 3",
                    "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

}
