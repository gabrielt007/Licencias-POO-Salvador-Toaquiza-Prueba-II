package View;

import javax.swing.*;

public class Login extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton ingresarButton;
    private JPanel LoginPane;

    public Login() {
        setContentPane(LoginPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Login");
        setVisible(true);
        setSize(600,300);
    }
}
