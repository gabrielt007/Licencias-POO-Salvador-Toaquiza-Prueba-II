package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PerfilAdmin extends JFrame {
    private JPanel PerfilAdmin;
    private JButton registrarSolicitantesButton;
    private JButton tramitesButton;
    private JButton generarButton;
    private JButton usuariosButton;
    private JButton reportesButton;
    private JButton cerrarSesionButton;
    private JLabel tipoUsuario;
    private JLabel nombreUsuario;
    private JButton requisitosButton;
    private JButton examenesButton;
    private JButton registrarUsuariosButton;
    private JButton licenciasButton;
    private JLabel admin;
    private JButton VerUsuarios;

    public PerfilAdmin(String cedula){
        setContentPane(PerfilAdmin);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Licencias");
        setSize(500,500);
        setLocationRelativeTo(null);

        ImageIcon adminIcon = new ImageIcon(
                getClass().getResource("/img/admin.png")
        );
        Image imgAdmin = adminIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        admin.setIcon(new ImageIcon(imgAdmin));

        nombreUsuario.setText(cedula);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaManager.cambiar(PerfilAdmin.this, new Login());
            }
        });

        cerrarSesionButton.addActionListener(e ->  {
            dispose();
            JOptionPane.showMessageDialog(null,"Cierre de sesion exitoso");
            new Login().setVisible(true);
        });

        registrarSolicitantesButton.addActionListener(e ->  {
           dispose();
           new Registro(cedula,"ADMIN").setVisible(true);
        });

        registrarUsuariosButton.addActionListener(e ->  {
           dispose();
           new RegistroUsuarios(cedula,"ADMIN").setVisible(true);
        });


        tramitesButton.addActionListener(e ->  {
            dispose();
            new GestionTramites(cedula,"ADMIN").setVisible(true);
        });

        reportesButton.addActionListener(e->{
            dispose();
            new Reportes(cedula,"ADMIN").setVisible(true);
        });

        usuariosButton.addActionListener(e ->  {
            String cedulaSolicitante = JOptionPane.showInputDialog(
                    "Digite la cédula del Usuario a modificar:"
            );

            if (cedulaSolicitante == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada");
                return;
            }

            if (cedulaSolicitante.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía");
                return;
            }
            boolean resultados= UsuarioDAO.verificarCedulaPlataforma(cedulaSolicitante);
            boolean resultadosS=UsuarioDAO.verificarCedula(cedulaSolicitante);
            if (!resultados) {
                if(!resultadosS){
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario no encontrado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }else{
                    dispose();
                    new GestionUsuarios(cedula,"ADMIN",cedulaSolicitante,"usuariosSolicitantes").setVisible(true);
                    return;
                }
            }
           dispose();
           new GestionUsuarios(cedula,"ADMIN",cedulaSolicitante,"usuariosPlataforma").setVisible(true);
        });

        licenciasButton.addActionListener(e ->  {
            dispose();
            new MostarLicencias(cedula,"ADMIN").setVisible(true);
        });

        VerUsuarios.addActionListener(e ->  {
            dispose();
            new MostarUsuarios("ADMIN",cedula).setVisible(true);
        });
    }
}
