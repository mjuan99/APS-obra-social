package vista;

import database.DataBase;
import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaRegistrarUsuario {

    private JPanel contentPane;
    private JTextField apellidoTextField;
    private JTextField nombreTextField;
    private JTextField dniTextField;
    private JTextField cuilTextField;
    private JButton botonRegistrar;
    private JTextField mailTextField;
    private JPasswordField contraseniaTextField;
    private JTextField fechaNacTextField;
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;


    public VistaRegistrarUsuario(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.mostrarVista();
        this.inicializar();
        this.iniciarBotonRegistrarListener();
    }

    public void mostrarVista() {
        this.frame = new JFrame("Registrar Usuario");
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                vistaPrincipal.activarBotonDeRegistrarUsuario();
                System.exit(0);
            }
        });
    }

    private void inicializar() {
        this.inicializarGrupoRadioButtons();
    }

    public void iniciarBotonRegistrarListener() {
        //todo preguntar a la otra comision por los campos que no se piden
        botonRegistrar.addActionListener(actionEvent -> {
            try {
                if (todosLosCamposEstanLlenos()) {
                    String nombre = this.nombreTextField.getText();
                    String apellido = this.apellidoTextField.getText();
                    int nro_documento = Integer.parseInt(this.dniTextField.getText());
                    String fecha_nac = this.fechaNacTextField.getText();
                    String cuil = this.cuilTextField.getText();
                    String contrasenia = String.valueOf(this.contraseniaTextField.getPassword());
                    String mail = this.mailTextField.getText();
                    boolean esTitular;
                    if (this.siRadioButton.isSelected())
                        esTitular = true;
                    else
                        esTitular = false;

                    DataBase.insertarCliente(apellido, nombre, nro_documento, cuil, fecha_nac, null, esTitular, null, mail, contrasenia);
                    this.informarRegistroCorrecto();
                } else
                    JOptionPane.showInternalMessageDialog(null, "Debes completar todos los campos para poder registrarte",
                            "Campos incompletos", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "El DNI debe ser un número entero",
                        "DNI inválido", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void informarRegistroCorrecto() {
        JOptionPane.showMessageDialog(null, "El usuario fue registrado con éxito", "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
        this.frame.setVisible(false);
        vistaPrincipal.activarBotonDeRegistrarUsuario();
    }

    private void inicializarGrupoRadioButtons() {
        ButtonGroup radioButtonsGroup = new ButtonGroup();
        siRadioButton.setSelected(true);
        radioButtonsGroup.add(siRadioButton);
        radioButtonsGroup.add(noRadioButton);
    }


    private boolean todosLosCamposEstanLlenos() {
        return  !this.nombreTextField.equals("") &&
                !this.apellidoTextField.equals("") &&
                !this.fechaNacTextField.equals("") &&
                !this.dniTextField.getText().isEmpty() &&
                !this.cuilTextField.equals("") &&
                !this.mailTextField.equals("") &&
                !String.valueOf(this.contraseniaTextField.getPassword()).equals("");
    }

}
