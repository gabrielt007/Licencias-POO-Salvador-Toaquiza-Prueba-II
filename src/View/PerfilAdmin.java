package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
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
    private JButton licenciasButton;
    private JLabel admin;

    public PerfilAdmin(String cedula){
        setContentPane(PerfilAdmin);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        ImageIcon adminIcon = new ImageIcon(
                getClass().getResource("/img/admin.png")
        );
        Image imgAdmin = adminIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        admin.setIcon(new ImageIcon(imgAdmin));

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
           new Registro(cedula,"ADMIN").setVisible(true);
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
                if (!UsuarioDAO.actualizarEstado(cedulaSolicitante).equals("Pendiente")) {
                    JOptionPane.showMessageDialog(null,"El usuario ya tiene los requisitos aprobados");
                    return;
                }
                String resultadosRequisitos=UsuarioDAO.requisitos(cedulaSolicitante);
                dispose();
                new Requisitos(cedula, cedulaSolicitante,resultadosRequisitos,"ADMIN").setVisible(true);
            }

        });

        registrarUsuariosButton.addActionListener(e ->  {
           dispose();
           new RegistroUsuarios(cedula,"ADMIN").setVisible(true);
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
//  Prioridad máxima: APROBADO
                if ("PREPARADO".equals(estado)) {
                    JOptionPane.showMessageDialog(null, "El usuario ya está aprobado");
                    return;
                }else if (!"REPROBADO".equals(estado)) {
                    // continúa el proceso
                    JOptionPane.showMessageDialog(null, "El usuario no cumple los requisitos");
                    return;
                }

                String resultadosExamenes=UsuarioDAO.examenes(cedulaSolicitante);
                if (resultadosExamenes.equals("No hay datos")){
                    JOptionPane.showMessageDialog(null,"El usuario no tiene registro de examenes","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
                new Examenes(cedula, cedulaSolicitante,resultadosExamenes,"ADMIN").setVisible(true);
            }
        });

        tramitesButton.addActionListener(e ->  {
            dispose();
            new GestionTramites(cedula,"ADMIN").setVisible(true);
        });

        generarButton.addActionListener(e->{
            dispose();
            new Licencias(cedula,"ADMIN").setVisible(true);
        });

        reportesButton.addActionListener(e->{
            dispose();
            new Reportes(cedula,"ADMIN").setVisible(true);
        });

        usuariosButton.addActionListener(e ->  {
            String cedulaSolicitante = JOptionPane.showInputDialog(
                    "Digite la cédula del Usuario a modificar:"
            );

            if (cedulaSolicitante == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada");
                return;
            }

            if (cedulaSolicitante.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía");
                return;
            }
            boolean resultados= UsuarioDAO.verificarCedulaPlataforma(cedulaSolicitante);
            boolean resultadosS=UsuarioDAO.verificarCedula(cedulaSolicitante);
            if (!resultados) {
                if(!resultadosS){
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario no encontrado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }else{
                    dispose();
                    new GestionUsuarios(cedula,"ADMIN",cedulaSolicitante,"usuariosSolicitantes").setVisible(true);
                    return;
                }
            }
           dispose();
           new GestionUsuarios(cedula,"ADMIN",cedulaSolicitante,"usuariosPlataforma").setVisible(true);
        });

        licenciasButton.addActionListener(e ->  {
            dispose();
            new MostarLicencias(cedula,"ADMIN").setVisible(true);
        });
    }
}
