package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintWriter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;


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
    private JLabel logo;
    private JButton fechaButton;
    private JButton licenciaButton;
    private JButton cedulaButton;

    public Reportes(String cedulaI, String usuario){
        setContentPane(Reportes);
        setTitle("Sistema de Licencias");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(680,550);
        setLocationRelativeTo(null);
        setVisible(true);


        ImageIcon Icon = new ImageIcon(
                getClass().getResource("/img/reporte.png")
        );
        Image img = Icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));

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

            String estado = ComBoxEstado.getSelectedItem().toString();

            DefaultTableModel model = UsuarioDAO.cargarReporte("","",estado,"","");

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);

        });

        fechaButton.addActionListener(e->{
            String desde = txtFechaDesde.getText();
            String hasta = txtFechaHasta.getText();

            DefaultTableModel model = UsuarioDAO.cargarReporte(desde, hasta,"","","");

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
        });
        cedulaButton.addActionListener(e->{

            String cedulaa = txtCedula.getText();

            DefaultTableModel model = UsuarioDAO.cargarReporte("","","","",cedulaa);

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
        });
        licenciaButton.addActionListener(e->{

            String tipo = comBoxTipoLicencia.getSelectedItem().toString();


            DefaultTableModel model = UsuarioDAO.cargarReporte("","","",tipo,"");

            table1.setModel(model);
            VentanaManager.ajustarColumnas(table1);
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

        exportarEnExcelButton.addActionListener(e -> {
           exportarJTableAExcel(table1);
        });
        actualizarTotales();



    }
    private void actualizarTotales() {
        int pendientes=0, aprobados=0, reprobados=0, examenes=0, licencias=0;

        for (int i=0; i<table1.getRowCount(); i++) {
            String estado = table1.getValueAt(i,5).toString();

            switch(estado) {
                case "Pendiente": pendientes++; break;
                case "APROBADO": aprobados++; break;
                case "REPROBADO": reprobados++; break;
                case "en_examenes": examenes++; break;
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

    public static void exportarJTableAExcel(JTable tabla) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Excel");
        chooser.setSelectedFile(new File("reporte.xlsx"));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File archivo = chooser.getSelectedFile();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet hoja = wb.createSheet("Reporte");

            TableModel model = tabla.getModel();

            // Estilo encabezado
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Encabezados
            Row header = hoja.createRow(0);
            for (int c = 0; c < model.getColumnCount(); c++) {
                Cell cell = header.createCell(c);
                cell.setCellValue(model.getColumnName(c));
                cell.setCellStyle(headerStyle);
            }

            // Datos
            for (int r = 0; r < model.getRowCount(); r++) {
                Row fila = hoja.createRow(r + 1);
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object valor = model.getValueAt(r, c);
                    fila.createCell(c).setCellValue(
                            valor == null ? "" : valor.toString()
                    );
                }
            }

            // Autoajustar columnas
            for (int c = 0; c < model.getColumnCount(); c++) {
                hoja.autoSizeColumn(c);
            }

            // Guardar
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                wb.write(fos);
            }

            JOptionPane.showMessageDialog(null,
                    "Excel generado correctamente ✔",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar Excel: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


}
