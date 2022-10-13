package vista;

import database.DataBase;
import database.entidades.Empleado;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame{

    private JPanel contentPane;
    private JTextField nombreUsuariotextField;
    private JPasswordField passwordField;
    private JButton botonIniciarSesion;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;

    public VistaLogin(VistaPrincipal vistaPrincipal) {
        this.frame = new JFrame("Login");
        this.vistaPrincipal = vistaPrincipal;
        this.mostrarVista();
        this.inicializarListeners();
    }

    private void inicializarListeners() {
        this.botonIniciarSesion.addActionListener(actionEvent -> {
            //TODO: Crear metodo en bd para poder consultar por los usuarios existentes.
            // luego verificar que el nombre de usuario exista, luego que coincida la contraseña
            String nombreUsuario = this.nombreUsuariotextField.getText();
            String pwUsuario = String.valueOf(this.passwordField.getPassword());


            try {
                if (DataBase.loginEmpleado(nombreUsuario, pwUsuario)) {
                    Empleado empleado = DataBase.getEmpleado(nombreUsuario);
                    VistaPrincipalEmpleado vistaPrincipalEmpleado;

                    if(empleado.cargo.equals("administrador")) {
                        // TODO: cambiar a vista administrador
                        System.out.println("Admin logeado");
                        vistaPrincipalEmpleado = new VistaPrincipalEmpleado(this);
                    }
                    else {
                        System.out.println("Empleado logeado");
                        vistaPrincipalEmpleado = new VistaPrincipalEmpleado(this);
                    }
                    frame.setVisible(false);
                } else if (DataBase.loginCliente(Integer.parseInt(nombreUsuario), pwUsuario)) {
                    VistaPrincipalCliente vistaPrincipalCliente = new VistaPrincipalCliente(this,Integer.parseInt(nombreUsuario));
                    frame.setVisible(false);
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
