package vista;

import database.DataBase;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame{

    private JPanel contentPane;
    private JTextField nombreUsuariotextField;
    private JPasswordField passwordField;
    private JButton botonIniciarSesion;
    private DataBase dataBase;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;


    public VistaLogin(VistaPrincipal vistaPrincipal, DataBase database) {
        this.frame = new JFrame("Login");
        this.vistaPrincipal = vistaPrincipal;
        this.dataBase = new DataBase();
        this.mostrarVista();
        this.inicializarListeners();
    }

    private void inicializarListeners() {
        this.botonIniciarSesion.addActionListener(actionEvent -> {
            String nombreUsuario = this.nombreUsuariotextField.getText();
            String pwUsuario = String.valueOf(this.passwordField.getPassword());
            try {
                if (dataBase.loginEmpleado(nombreUsuario, pwUsuario)) {
                    VistaPrincipalEmpleado vistaPrincipalEmpleado = new VistaPrincipalEmpleado(this);
                } else if (dataBase.loginCliente(Integer.parseInt(nombreUsuario), pwUsuario)) {
                    VistaPrincipalCliente vistaPrincipalCliente = new VistaPrincipalCliente(this);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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
