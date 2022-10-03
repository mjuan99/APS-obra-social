package vista;

import database.DataBase;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame{

    private JPanel contentPane;
    private JTextField nombreUsuariotextField;
    private JPasswordField passwordField;
    private JButton botonIniciarSesion;
    private JButton botonRegistrar;
    private DataBase dataBase;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;


    public VistaLogin(VistaPrincipal vistaPrincipal, DataBase database) {
        this.frame = new JFrame("Login");
        this.vistaPrincipal = vistaPrincipal;
        this.dataBase = database;
        this.mostrarVista();
        this.inicializarListeners();
    }

    private void inicializarListeners() {
        this.botonRegistrar.addActionListener(actionEvent -> {
            VistaRegistrarUsuario vistaRegistrarUsuario = new VistaRegistrarUsuario(this, this.dataBase);
            this.botonRegistrar.setEnabled(false);
        });
        this.botonIniciarSesion.addActionListener(actionEvent -> {
            //TODO: Crear metodo en bd para poder consultar por los usuarios existentes.
            // luego verificar que el nombre de usuario exista, luego que coincida la contraseña
        });
    }
    public void activarBotonDeRegistrarUsuario() {
        this.botonRegistrar.setEnabled(true);
    }

    private void mostrarVista() {
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250,250));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
