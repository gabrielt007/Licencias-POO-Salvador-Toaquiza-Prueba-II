package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;
import Utils.HashUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistroUsuarios extends JFrame{
    private JPanel RegistroUsuarios;
    private JButton CREARButton;
    private JTextField txtCedula;
    private JTextField txtClave;
    private JComboBox CombBoxRol;
    private JComboBox CombBoxEstado;
    public RegistroUsuarios(String cedula){
        setContentPane(RegistroUsuarios);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setTitle("Sistema de Licencias");
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(RegistroUsuarios.this, new PerfilAdmin(cedula));
            }
        });

        CREARButton.addActionListener(e -> {
            String cedulaI=txtCedula.getText();
            String clave=txtClave.getText();
            String rol=CombBoxRol.getSelectedItem().toString();
            String estado=CombBoxEstado.getSelectedItem().toString();
            String passHash=HashUtil.hash(clave);

            if(UsuarioDAO.registrarUsuarioPlataforma(cedulaI,passHash,rol,estado)){
                JOptionPane.showMessageDialog(null,"Usuario registrado correctamente");
            }else{
                JOptionPane.showMessageDialog(null,"El usuario ya existe");
            }
        });
    }
}
