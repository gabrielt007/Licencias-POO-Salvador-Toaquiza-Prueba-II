package Controller;

import javax.swing.*;

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

}
