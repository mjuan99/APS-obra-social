package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaPrincipalEmpleado {

    private JPanel contentPane;
    private JFrame frame;
    private JButton botonPlanes;
    private JButton botonReintegro;
    private JButton botonPrestaciones;

    private VistaLogin vistaLogin;

    public VistaPrincipalEmpleado(VistaLogin vistaLogin){
        this.frame = new JFrame("Empleado");
        this.vistaLogin = vistaLogin;
        this.mostrarVista();
        inicializarListeners();
    }

    private void inicializarListeners(){
        botonPrestaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaSolicitudesPrestaciones(VistaPrincipalEmpleado.this);
                deshabilitarInteraccion();
            }
        });

        botonReintegro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new VistaSolicitudesReintegroEmpleado( VistaPrincipalEmpleado.this);
               deshabilitarInteraccion();
            }
        });

        botonPlanes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaSolicitudesPlanesEmpleado(VistaPrincipalEmpleado.this);
                deshabilitarInteraccion();
            }
        });

    }

    private void mostrarVista() {
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void deshabilitarInteraccion(){
        frame.setEnabled(false);
    }
    public void habilitarInteraccion(){
        frame.setEnabled(true);
    }


}
