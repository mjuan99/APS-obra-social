package vista;

import database.DataBase;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame{

    private JPanel contentPane;
    private JTextField nombreUsuariotextField;
    private JPasswordField passwordField;
    private JButton iniciarSesionButton;
    private DataBase db;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;

    public VistaLogin(VistaPrincipal vistaPrincipal, DataBase database) {
        this.frame = new JFrame("Login");
        this.vistaPrincipal = vistaPrincipal;
        this.db = database;
        this.mostrarVista();
    }

    private void mostrarVista() {
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250,200));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
