package vista;

import database.DataBase;
import database.entidades.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton botonRegistrar;

    public VistaDarDeAltaEmpleado(VistaPrincipalAdministrador vistaAdministrador) {
        this.vistaAdministrador = vistaAdministrador;
        this.inicializarListeners();
        this.mostrarVista();
    }

    private void inicializarListeners(){
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEmpleado();
            }
        });

    }

    private void mostrarVista() {
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

    public void registrarEmpleado() {

        Empleado empleado = procesarCampos();
        if(empleado != null) {
            pedirInsercion(empleado);
        }

    }

    private Empleado procesarCampos() {
        try {
            boolean campoVacio = this.apellidoTextField.getText().equals("") ||
                    this.nombreTextField.getText().equals("") ||
                    this.documentoTextField.getText().equals("") ||
                    this.telefonoTextField.getText().equals("") ||
                    this.emailTextField.getText().equals("") ||
                    this.cargoTextField.getText().equals("") ||
                    this.usuarioTextField.getText().equals("") ||
                    String.valueOf(this.contraseniaField.getPassword()).equals("");

            if (campoVacio) throw new Exception("No puede haber campos sin completar");

            try {
                Integer.parseInt(this.documentoTextField.getText());
            } catch (NumberFormatException e) {
                throw new Exception("El DNI debe ser un número entero");
            }

            Empleado empleado = new Empleado(
                    apellidoTextField.getText(),
                    nombreTextField.getText(),
                    Integer.parseInt(documentoTextField.getText()),
                    telefonoTextField.getText(),
                    emailTextField.getText(),
                    cargoTextField.getText(),
                    usuarioTextField.getText(),
                    String.valueOf(this.contraseniaField.getPassword())
            );

            return empleado;
        } catch (Exception e) {
            popupInformacion(e.getMessage(), "Error");
        }
        return null;
    }

    private void pedirInsercion(Empleado empleado){
        try {//TODO verificar que un numero de documento se encuentra en uso para dar el mensaje de error apropiado, generalizable para el registro de usuario comun
            if(DataBase.getEmpleado(empleado.usuario) != null)
                throw new Exception("El nombre de usuario se encuentra en uso");

            DataBase.insertarEmpleado(
                    empleado.apellido,
                    empleado.nombre,
                    empleado.nro_documento,
                    empleado.telefono,
                    empleado.email,
                    empleado.cargo,
                    empleado.usuario,
                    empleado.contrasenia
            );

            popupInformacion("Registro completado con éxito", "Éxito");


        }
        catch (Exception e){popupInformacion(e.getMessage(),"Error");}

    }

    private void popupInformacion(String msj, String titulo){
        JOptionPane.showInternalMessageDialog(null, msj, titulo, JOptionPane.INFORMATION_MESSAGE);
        this.frame.setVisible(false);
        vistaAdministrador.activarBotonDeAltaDeEmpleado();
    }

}
