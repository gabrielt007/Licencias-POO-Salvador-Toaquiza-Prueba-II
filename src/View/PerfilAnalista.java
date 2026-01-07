package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilAnalista extends JFrame {
    private JLabel txtTipoUsuario;
    private JLabel txtNombreUsuario;
    private JButton registrarButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton cerrarSesionButton;
    private JPanel PerfilAnalista;
    private JButton requisitosButton;
    private JButton examenesButton;

    public PerfilAnalista(String nombreUsuario) {
        setContentPane(PerfilAnalista);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilAnalista.this, new Login());
            }
        });

        txtNombreUsuario.setText(nombreUsuario);

        registrarButton.addActionListener(e -> {
            dispose();
            new Registro(nombreUsuario).setVisible(true);
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
            new Requisitos(nombreUsuario, cedulaSolicitante,resultados).setVisible(true);
        });

        cerrarSesionButton.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }
}
