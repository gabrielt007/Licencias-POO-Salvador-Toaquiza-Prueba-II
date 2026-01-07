package View;

import javax.swing.*;

public class GestionTramites extends JFrame{
    private JTable table1;
    private JPanel GestionTramites;
    private JButton filtrarButton;
    private JButton registrarRequisitosButton;
    private JButton verDetalleButton;
    private JButton registrarExamenButton;
    private JButton generarLicenciaButton;
    public GestionTramites(){
        setContentPane(GestionTramites);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sistema de Licencias");
    }
}
