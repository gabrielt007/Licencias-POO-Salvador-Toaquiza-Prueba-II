package View;

import Controller.VentanaManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GestionUsuarios extends JFrame{
    private JTextField txtCedulaCambios;
    private JTextField txtRolCambios;
    private JButton guardarButton;
    private JButton buscarButton;
    private JButton activarDesactivarButton;
    private JTextField txtNombre_Cambios;
    private JPanel GestionUsuarios;
    private JLabel txtEstadoActual;
    private JLabel txtCedulaActual;
    private JLabel txtPasswordActual;
    private JLabel txtRolActual;
    private JTextField txtPasswordCambios;

    public GestionUsuarios(String  cedula,String rol){
        setContentPane(GestionUsuarios);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setTitle("Sistema de Licencias");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (rol.equals("ADMIN")){
                    VentanaManager.cambiar(GestionUsuarios.this, new PerfilAdmin(cedula));
                }else if (rol.equals("ANALISTA")){
                    VentanaManager.cambiar(GestionUsuarios.this, new PerfilAnalista(cedula));
                }
            }
        });
    }
}
