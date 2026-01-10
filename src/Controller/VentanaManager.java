package Controller;

import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
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
        BufferedImage imagen = new BufferedImage(
                panel.getWidth(),
                panel.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = imagen.createGraphics();
        panel.paintAll(g2d);   // clave para JPanel
        g2d.dispose();

        return imagen;
    }

    private static void exportarJPanelAPdf(JPanel panel, String rutaPdf) {
        try {
            BufferedImage imagen = capturarJPanel(panel);

            Document document = new Document(
                    new Rectangle(imagen.getWidth(), imagen.getHeight())
            );

            PdfWriter.getInstance(document, new FileOutputStream(rutaPdf));
            document.open();

            Image img = Image.getInstance(imagen, null);
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
