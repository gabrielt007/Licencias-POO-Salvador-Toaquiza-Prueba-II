package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
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
    private JLabel analista;

    public PerfilAnalista(String nombreUsuario) {
        setContentPane(PerfilAnalista);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        ImageIcon analistaIcon = new ImageIcon(
                getClass().getResource("/img/analista.png")
        );
        Image imgAnalista = analistaIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        analista.setIcon(new ImageIcon(imgAnalista));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilAnalista.this, new Login());
            }
        });

        txtNombreUsuario.setText(nombreUsuario);

        registrarButton.addActionListener(e -> {
            dispose();
            new Registro(nombreUsuario,"ANALISTA").setVisible(true);
        });

//        requisitosButton.addActionListener(e -> {
//            String cedulaSolicitante = JOptionPane.showInputDialog(
//                    "Digite la cédula del solicitante:"
//            );
//
//            if (cedulaSolicitante == null) {
//                JOptionPane.showMessageDialog(null, "Operación cancelada");
//                return;
//            }
//
//            if (cedulaSolicitante.trim().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía");
//                return;
//            }
//            boolean resultados= UsuarioDAO.verificarCedula(cedulaSolicitante);
//            if (!resultados) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "Usuario no encontrado",
//                        "Error",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }else{
//                if (!UsuarioDAO.actualizarEstado(cedulaSolicitante).equals("Pendiente")) {
//                    JOptionPane.showMessageDialog(null,"El usuario ya tiene los requisitos aprobados");
//                    return;
//                }
//                String resultadosRequisitos=UsuarioDAO.requisitos(cedulaSolicitante);
//                dispose();
//                new Requisitos(nombreUsuario, cedulaSolicitante,resultadosRequisitos,"ANALISTA").setVisible(true);
//            }
//
//        });

        cerrarSesionButton.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        examenesButton.addActionListener(e -> {
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
            boolean resultados= UsuarioDAO.verificarCedula(cedulaSolicitante);
            if (!resultados) {
                JOptionPane.showMessageDialog(
                        null,
                        "Usuario no encontrado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }else{
                String estado = UsuarioDAO.actualizarEstado(cedulaSolicitante);
// Prioridad máxima: APROBADO
                if ("PREPARADO".equals(estado)) {
                    JOptionPane.showMessageDialog(null, "El usuario ya está aprobado");
                    return;
                }else if (!"REPROBADO".equals(estado)) {
                    // continúa el proceso
                    if (!"en_examenes".equals(estado)){
                        JOptionPane.showMessageDialog(null, "El usuario no cumple los requisitos");
                        return;
                    }
                }
                String resultadosExamenes=UsuarioDAO.examenes(cedulaSolicitante);
                if (resultadosExamenes.equals("No hay datos")){
                    JOptionPane.showMessageDialog(null,"El usuario no tiene registro de examenes","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
                new Examenes(nombreUsuario, cedulaSolicitante,resultadosExamenes,"ANALISTA").setVisible(true);
            }
        });

        tramitesButton.addActionListener(e -> {
            dispose();
            new GestionTramites(nombreUsuario,"ANALISTA").setVisible(true);
        });

//        generarButton.addActionListener(e->{
//            dispose();
//            new Licencias(nombreUsuario,"ANALISTA").setVisible(true);
//        });
    }
}
