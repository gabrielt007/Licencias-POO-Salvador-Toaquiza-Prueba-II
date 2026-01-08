package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;
import Utils.HashUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class GestionUsuarios extends JFrame{
    private JTextField txtCedulaCambios;
    private JButton guardarButton;
    private JButton cambiarEstado;
    private JPanel GestionUsuarios;
    private JLabel txtEstadoActual;
    private JLabel txtCedulaActual;
    private JLabel txtPasswordActual;
    private JLabel txtRolActual;
    private JTextField txtPasswordCambios;
    private JComboBox CombBoxRol;

    public GestionUsuarios(String  cedula,String rol,String cedulaSolicitante){
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

        String datosActuales= UsuarioDAO.datosActuales(cedulaSolicitante);
        String[] lista=datosActuales.split("/");
        String cedulaActual= lista[0];
        String rolActual= lista[1];
        String estadoActual= lista[2];

        txtCedulaActual.setText(cedulaActual);
        txtRolActual.setText(rolActual);
        txtEstadoActual.setText(estadoActual);

        AtomicReference<String> estadoNuevo= new AtomicReference<>("");
        cambiarEstado.addActionListener(e -> {
            if(estadoActual.equals("ACTIVO")){
                estadoNuevo.set("BLOQUEADO");
            }else if (estadoActual.equals("BLOQUEADO")){
                estadoNuevo.set("ACTIVO");
            }
        });

        guardarButton.addActionListener(e -> {
            String cedulaNueva=txtCedulaCambios.getText();
            if (cedulaNueva.trim().isEmpty()){
                cedulaNueva=cedulaActual;
            }else if (!cedulaNueva.trim().isEmpty()&&cedulaNueva.length()!=10) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un cedula valida","Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String rolNueva=CombBoxRol.getSelectedItem().toString();
            String passwordNueva=txtPasswordCambios.getText();
            if (passwordNueva.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese una password nueva");
                return;
            }
            passwordNueva=HashUtil.hash(passwordNueva);
            UsuarioDAO.actalizarDatos(cedulaActual,cedulaNueva,rolNueva,passwordNueva,estadoNuevo.get());
            JOptionPane.showMessageDialog(null, "Se ha modificado el usuario con exito");
            dispose();
            new PerfilAdmin(cedula);
        });
    }
}
