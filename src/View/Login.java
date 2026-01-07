package View;

import Model.UsuarioDAO;
import Utils.HashUtil;

import javax.swing.*;
import java.security.Principal;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JButton ingresarButton;
    private JPanel LoginPane;
    private JPasswordField txtPassword;

    public Login() {
        setContentPane(LoginPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Login");
        setVisible(true);
        setSize(600,300);
        setLocationRelativeTo(null);

        ingresarButton.addActionListener(e ->  {
            String usuario = txtUsuario.getText();
            String password = String.valueOf(txtPassword.getPassword());
            String passwordHash = HashUtil.hash(password);
            String tipousuario=UsuarioDAO.existeUsuario(usuario, passwordHash);
            String tipoUserSol=UsuarioDAO.existeSolicitante(usuario,passwordHash);
            if(tipousuario.equals("ADMINISTRADOR")) {
                new PerfilAdmin(usuario).setVisible(true);
                dispose();
            } else if (tipousuario.equals("ANALISTA")) {
                new PerfilAnalista(usuario).setVisible(true);
                dispose();
            } else if (!tipoUserSol.equals("No encontrado")){
                new PerfilUsuario(usuario).setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null,"El usuario no fue encontrado. Verifique sus credenciales","No Encontrado",JOptionPane.ERROR_MESSAGE);
                txtUsuario.setText("");
                txtPassword.setText("");
            }
        });
    }
}
