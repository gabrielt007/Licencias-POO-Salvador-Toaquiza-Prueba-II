package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintWriter;

public class Reportes extends JFrame{
    private JPanel Reportes;
    private JTable table1;
    private JButton buscarButton;
    private JButton exportarButton;
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JComboBox ComBoxEstado;
    private JComboBox comBoxTipoLicencia;
    private JTextField txtCedula;
    private JButton salirButton;
    private JLabel lblPendiente;
    private JLabel lblReprobados;
    private JLabel lblTotalLicenciasGeneradas;
    private JLabel lblLicenciasEmitidas;
    private JLabel lblEnExamenes;
    private JLabel lblAprobados;
    private JButton limpiarFiltrosButton;
    private JButton exportarEnExcelButton;

    public Reportes(String cedulaI, String usuario){
        setContentPane(Reportes);
        setTitle("Sistema de Licencias");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        //pack();
        setSize(680,800);

        VentanaManager.ajustarColumnas(table1);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setModel(UsuarioDAO.cargarVistaTramites());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(Reportes.this, new PerfilAdmin(cedulaI));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(Reportes.this, new PerfilAnalista(cedulaI));
                }
            }
        });

        buscarButton.addActionListener(e -> {
            String desde = txtFechaDesde.getText();
            String hasta = txtFechaHasta.getText();
            String estado = ComBoxEstado.getSelectedItem().toString();
            String tipo = comBoxTipoLicencia.getSelectedItem().toString();
            String cedula = txtCedula.getText();

            DefaultTableModel model = UsuarioDAO.cargarReporte(
                    desde, hasta, estado, tipo, cedula
            );

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);

            actualizarTotales();
        });

        exportarButton.addActionListener(e -> {
            try {
                // 1. Crear carpeta en el Escritorio
                File carpeta = new File(System.getProperty("user.home") + "/Desktop/ReportesLicencias");
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                // 2. Nombre del archivo
                String nombre = "reporte_" + System.currentTimeMillis() + ".csv";
                File archivo = new File(carpeta, nombre);

                // 3. Crear CSV
                try (PrintWriter pw = new PrintWriter(archivo)) {

                    // Encabezados
                    for (int c = 0; c < table1.getColumnCount(); c++) {
                        pw.print(table1.getColumnName(c));
                        if (c < table1.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();

                    // Filas
                    for (int i = 0; i < table1.getRowCount(); i++) {
                        for (int j = 0; j < table1.getColumnCount(); j++) {
                            Object valor = table1.getValueAt(i, j);
                            pw.print(valor == null ? "" : valor.toString());
                            if (j < table1.getColumnCount() - 1) pw.print(",");
                        }
                        pw.println();
                    }
                }

                JOptionPane.showMessageDialog(null,
                        "Reporte exportado en:\n" + archivo.getAbsolutePath());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al exportar reporte");
                ex.printStackTrace();
            }
        });

        limpiarFiltrosButton.addActionListener(e -> {
            VentanaManager.ajustarColumnas(table1);
            table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table1.setModel(UsuarioDAO.cargarVistaTramites());
        });
        actualizarTotales();



    }
    private void actualizarTotales() {
        int pendientes=0, aprobados=0, reprobados=0, examenes=0, licencias=0;

        for (int i=0; i<table1.getRowCount(); i++) {
            String estado = table1.getValueAt(i,5).toString();

            switch(estado) {
                case "Pendiente": pendientes++; break;
                case "Aprobado": aprobados++; break;
                case "Reprobado": reprobados++; break;
                case "En_Examenes": examenes++; break;
                case "LicenciaEmitida": licencias++; break;
            }
        }

        lblPendiente.setText(""+pendientes);
        lblAprobados.setText(""+aprobados);
        lblReprobados.setText(""+reprobados);
        lblEnExamenes.setText(""+examenes);
        lblLicenciasEmitidas.setText(""+licencias);
        lblTotalLicenciasGeneradas.setText(""+licencias);
    }



}
