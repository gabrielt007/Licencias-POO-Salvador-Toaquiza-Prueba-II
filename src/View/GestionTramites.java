package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GestionTramites extends JFrame{
    private JTable table1;
    private JPanel GestionTramitesP;
    private JButton filtrarButton;
    private JButton registrarRequisitosButton;
    private JButton verDetalleButton;
    private JButton registrarExamenButton;
    private JButton generarLicenciaButton;
    private JComboBox comboBox1;

    public GestionTramites(String cedula, String usuario){
        setContentPane(GestionTramitesP);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setSize(600,400);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(GestionTramites.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(GestionTramites.this, new PerfilAnalista(cedula));
                }
            }
        });

        registrarRequisitosButton.addActionListener(e -> {
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
                new Requisitos(cedula, cedulaSolicitante,resultadosRequisitos,usuario).setVisible(true);
            }

        });

        registrarExamenButton.addActionListener(e -> {
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
                }else if (!"OK".equals(estado)) {
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
                new Examenes(cedula, cedulaSolicitante,resultadosExamenes,usuario).setVisible(true);
            }
        });

        verDetalleButton.addActionListener(e->{
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
            }
            dispose();
            new DetalleUsuarios(usuario,cedula,cedulaSolicitante).setVisible(true);
        });

        filtrarButton.addActionListener(e->{});

        table1.setModel(UsuarioDAO.cargarVistaTramites());
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        VentanaManager.ajustarColumnas(table1);

        generarLicenciaButton.addActionListener(e -> {
            dispose();
            new Licencias(cedula,usuario).setVisible(true);
        });
    }
}
