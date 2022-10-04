package vista;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipalEmpleado extends JFrame {

    private JPanel contentPane;
    private VistaLogin vistaLogin;
    private JFrame frame;

    public VistaPrincipalEmpleado(VistaLogin vistaLogin){
        this.frame = new JFrame("Empleado");
        this.vistaLogin = vistaLogin;
        this.mostrarVista();
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

}
