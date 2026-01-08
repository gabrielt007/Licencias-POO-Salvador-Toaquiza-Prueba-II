package View;

import Controller.VentanaManager;
import Model.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Examenes extends JFrame{
    private JPanel Examenes;
    private JTextField txtNotaP;
    private JTextField txtNotaT;
    private JButton ingresarNotasButton;
    private JLabel Labelcedula;
    private JLabel promedio;

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
                if (usuario.equals("ADMIN")){
                    VentanaManager.cambiar(Examenes.this, new PerfilAdmin(cedula));
                }else if (usuario.equals("ANALISTA")){
                    VentanaManager.cambiar(Examenes.this, new PerfilAnalista(cedula));
                }
            }
        });

        Labelcedula.setText(cedulaSolicitante);
        String[] partes = resultadosExamenes.split("/");
        String eP = partes[0];
        String eT = partes[1];
        String P = partes[2];

        txtNotaP.setText(eP);
        txtNotaT.setText(eT);
        promedio.setText(P);

        ingresarNotasButton.addActionListener(e -> {
            double nuevaP= Double.parseDouble(txtNotaP.getText());
            double nuevaT= Double.parseDouble(txtNotaT.getText());
            double promedioN= (nuevaP+nuevaT)/2;
            promedio.setText(promedioN+"");
            UsuarioDAO.modificarNotas(cedulaSolicitante,nuevaP,nuevaT);
            UsuarioDAO.actualizarEstado(cedulaSolicitante);
            dispose();
            new PerfilAdmin(cedula);
        });
    }
}
