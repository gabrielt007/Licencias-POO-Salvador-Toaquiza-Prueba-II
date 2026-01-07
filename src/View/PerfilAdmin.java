package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilAdmin extends JFrame {
    private JPanel PerfilAdmin;
    private JButton registrarSolicitantesButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton usuariosButton;
    private JButton reportesButton;
    private JButton cerrarSesionButton;
    private JLabel tipoUsuario;
    private JLabel nombreUsuario;
    private JButton requisitosButton;
    private JButton examenesButton;
    private JButton registrarUsuariosButton;

    public PerfilAdmin(String cedula){
        setContentPane(PerfilAdmin);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);
        nombreUsuario.setText(cedula);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilAdmin.this, new Login());
            }
        });

        cerrarSesionButton.addActionListener(e ->  {
            dispose();
            JOptionPane.showMessageDialog(null,"Cierre de sesion exitoso");
            new Login().setVisible(true);
        });

        registrarSolicitantesButton.addActionListener(e ->  {
           dispose();
           new Registro(cedula).setVisible(true);
        });

        requisitosButton.addActionListener(e -> {

            String cedulaSolicitante = JOptionPane.showInputDialog(
                    "Digite la cédula del solicitante:"
            );

            if (cedulaSolicitante == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada");
                return;
            }

            if (cedulaSolicitante.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía");
                return;
            }
            String resultados= UsuarioDAO.requisitos(cedulaSolicitante);
            if (resultados.equals("NO_EXISTE")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Usuario no encontrado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            dispose();
            new Requisitos(cedula, cedulaSolicitante,resultados).setVisible(true);
        });

    }
}
