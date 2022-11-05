package vista;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaSolicitudesPrestaciones {
    private JPanel contentPane;
    private JTable tablaSolicitudes;
    private JButton btnAprobar;
    private JButton btnRechazar;

    private VistaPrincipalEmpleado vistaPrincipalEmpleado;

    private JFrame frame;

    public VistaSolicitudesPrestaciones(VistaPrincipalEmpleado vistaEmpleado){
        this.frame = new JFrame("Solicitudes de Prestaciones");
        this.vistaPrincipalEmpleado = vistaEmpleado;
        this.mostrarVista();
        this.inicializarListeners();
        tablaSolicitudes = new JTable();
        tablaSolicitudes.setEnabled(false);
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

    private void inicializarListeners(){

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                vistaPrincipalEmpleado.habilitarInteraccion();
            }
        });
    }

}

