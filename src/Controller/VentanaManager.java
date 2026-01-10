package Controller;

import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.File;

public class VentanaManager {
    public static void cambiar(JFrame actual, JFrame siguiente) {
        siguiente.setVisible(true);
        actual.dispose();
    }

    public static void ajustarColumnas(JTable tabla) {
        for (int col = 0; col < tabla.getColumnCount(); col++) {
            int anchoMax = 50; // mínimo decente

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                Object valor = tabla.getValueAt(fila, col);
                if (valor != null) {
                    int ancho = tabla.getFontMetrics(tabla.getFont())
                            .stringWidth(valor.toString());
                    anchoMax = Math.max(ancho + 20, anchoMax);
                }
            }

            tabla.getColumnModel()
                    .getColumn(col)
                    .setPreferredWidth(anchoMax);
        }
    }

    private static BufferedImage capturarJPanel(JPanel panel) {

        Dimension size = panel.getPreferredSize();
        panel.setSize(size);        // fuerza tamaño real
        panel.doLayout();           // fuerza layout

        BufferedImage imagen = new BufferedImage(
                size.width,
                size.height,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = imagen.createGraphics();
        panel.printAll(g2d); // 👈 mejor que paintAll
        g2d.dispose();

        return imagen;
    }

    private static void exportarJPanelAPdf(JPanel panel, String rutaPdf) {
        try {
            BufferedImage imagen = capturarJPanel(panel);

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(rutaPdf));
            document.open();

            Image img = Image.getInstance(imagen, null);

// Escalar para que entre completa
            img.scaleToFit(
                    document.getPageSize().getWidth() - 40,
                    document.getPageSize().getHeight() - 40
            );

            img.setAlignment(Image.ALIGN_CENTER);
            document.add(img);

            document.close();

            JOptionPane.showMessageDialog(
                    panel,
                    "PDF generado correctamente 📄✨"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void guardarPdfConDialogo(JPanel panel) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar PDF");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Filtro para que solo muestre PDF
        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Archivos PDF (*.pdf)", "pdf"
                )
        );

        int opcion = chooser.showSaveDialog(panel);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            // Asegurar extensión .pdf
            String ruta = archivo.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta += ".pdf";
            }

            exportarJPanelAPdf(panel, ruta);
        }
    }
}
