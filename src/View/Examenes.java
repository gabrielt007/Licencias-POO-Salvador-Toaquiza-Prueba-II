package View;

import Controller.VentanaManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Examenes extends JFrame{
    private JPanel Examenes;
    private JTextField txtNotaP;
    private JTextField txtNotaT;
    private JButton ingresarNotasButton;
    private JLabel cedula;

    public Examenes(String cedula, String cedulaSolicitante, String resultadosExamenes,String usuario){
        setContentPane(Examenes);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(Examenes.this, new PerfilAdmin(cedula));
            }
        });
    }
}
