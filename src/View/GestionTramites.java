package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JComboBox comBoxTipoLicencia;
    private JComboBox ComBoxEstado;
    private JTextField txtCedula;
    private JButton buscarButton;
    private JButton limpiarBusquedaButton;
    private JLabel tramiteIcon;
    private JButton cedulaButton;
    private JButton fechaButton;
    private JButton licenciaButton;
    private JButton verCedulaButton;

    public GestionTramites(String cedula, String usuario){
        setContentPane(GestionTramitesP);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setSize(750,600);
        setLocationRelativeTo(null);

        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/tramite.png")
        );
        Image img = Icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        tramiteIcon.setIcon(new ImageIcon(img));

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

        buscarButton.addActionListener(e -> {

            String estado = ComBoxEstado.getSelectedItem().toString();

            DefaultTableModel model = UsuarioDAO.cargarReporte(null,null,estado,null,null);

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);

        });

        fechaButton.addActionListener(e->{
            String desde = limpiarr(txtFechaDesde.getText());
            String hasta = limpiarr(txtFechaHasta.getText());

            DefaultTableModel model = UsuarioDAO.cargarReporte(desde, hasta,null,null,null);

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
        });
        cedulaButton.addActionListener(e->{

            String cedulaa = limpiarr(txtCedula.getText());

            DefaultTableModel model = UsuarioDAO.cargarReporte(null,null,null,null,cedulaa);

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
        });
        licenciaButton.addActionListener(e->{

            String tipo = limpiarr(comBoxTipoLicencia.getSelectedItem().toString());


            DefaultTableModel model = UsuarioDAO.cargarReporte(null,null,null,tipo,null);

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
        });

        limpiarBusquedaButton.addActionListener(e -> {
            VentanaManager.ajustarColumnas(table1);
            table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table1.setModel(UsuarioDAO.cargarVistaTramites());
        });

        table1.setModel(UsuarioDAO.cargarVistaTramites());
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        VentanaManager.ajustarColumnas(table1);

        generarLicenciaButton.addActionListener(e -> {
            dispose();
            new Licencias(cedula,usuario).setVisible(true);
        });

    }
    private String limpiarr(String valor) {
        if (valor == null) return null;
        valor = valor.trim();
        if (valor.isEmpty()) return null;
        if (valor.equalsIgnoreCase("Todos")) return null;
        if (valor.equalsIgnoreCase("Seleccione")) return null;
        return valor;
    }


}
