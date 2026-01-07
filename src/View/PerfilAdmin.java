package View;

import javax.swing.*;

public class PerfilAdmin extends JFrame {
    private JPanel PerfilAdmin;
    private JButton registrarButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton usuariosButton;
    private JButton reportesButton;
    private JButton cerrarSesionButton;
    private JLabel tipoUsuario;
    private JLabel nombreUsuario;
    private JButton requisitosButton;
    private JButton examenesButton;

    public PerfilAdmin(String cedula){
        setContentPane(PerfilAdmin);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);
        nombreUsuario.setText(cedula);


        cerrarSesionButton.addActionListener(e ->  {
            setVisible(false);
            JOptionPane.showMessageDialog(null,"Cierre de sesion exitoso");
            new Login().setVisible(true);
        });
    }
}
