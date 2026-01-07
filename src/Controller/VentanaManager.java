package Controller;

import javax.swing.*;

public class VentanaManager {
    public static void cambiar(JFrame actual, JFrame siguiente) {
        siguiente.setVisible(true);
        actual.dispose();
    }
}
