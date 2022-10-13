package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaPrincipalAdministrador {

    private JPanel contentPane;
    private JButton botonAltaEmpleado;

    private VistaLogin vistaLogin;

    private JFrame frame;

    public VistaPrincipalAdministrador (VistaLogin vistaLogin) {
        this.vistaLogin = vistaLogin;
        this.frame = new JFrame("Administrador");
        this.mostrarVista();

        inicializarListeners();
    }

    private void inicializarListeners(){

        botonAltaEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: abrir form para dar de alta empleado
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


}
