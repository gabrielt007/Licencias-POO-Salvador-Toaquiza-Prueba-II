package View;

import javax.swing.*;

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
    }
}
