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

        table1.setModel(UsuarioDAO.cargarVistaTramites());
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        VentanaManager.ajustarColumnas(table1);
    }
}
