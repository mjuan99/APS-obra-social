package vista;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaDarDeAltaEmpleado {

    private VistaPrincipalAdministrador vistaAdministrador;
    private JPanel contentPane;

    private JFrame frame;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField documentoTextField;
    private JTextField telefonoTextField;
    private JTextField emailTextField;
    private JTextField cargoTextField;
    private JTextField usuarioTextField;
    private JPasswordField contraseniaField;

    public VistaDarDeAltaEmpleado(VistaPrincipalAdministrador vistaAdministrador){
        this.vistaAdministrador = vistaAdministrador;
        this.mostrarVista();


    }
    public void mostrarVista() {
        this.frame = new JFrame("Dar de alta empleado");
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                vistaAdministrador.activarBotonDeAltaDeEmpleado();
            }
        });
    }

}
