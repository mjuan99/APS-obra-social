package vista;

import database.DataBase;
import javax.swing.*;

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

    public VistaRegistrarUsuario(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.mostrarVista();
        this.inicializar();
        this.iniciarBotonRegistrarListener();
    }

    public void mostrarVista() {
        JFrame frame = new JFrame("Registrar Usuario");
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void inicializar() {
        this.inicializarGrupoRadioButtons();
    }

    public void iniciarBotonRegistrarListener() {
        //todo preguntar a la otra comision por los campos que no se piden
        botonRegistrar.addActionListener(actionEvent -> {
            if (todosLosCamposEstanLlenos()) {
                String nombre = this.nombreTextField.getText();
                String apellido = this.apellidoTextField.getText();
                //todo hay que validar que el nro_documento sea un entero?
                int nro_documento = Integer.parseInt(this.dniTextField.getText());
                String fecha_nac = this.fechaNacTextField.getText();
                boolean esTitular;
                if (this.siRadioButton.isSelected())
                    esTitular = true;
                else
                    esTitular = false;
                String cuil = this.cuilTextField.getText();
                String contrasenia = String.valueOf(this.contraseniaTextField.getPassword());
                String mail = this.mailTextField.getText();

                DataBase.insertarCliente(apellido, nombre, nro_documento, cuil, fecha_nac, null, esTitular, "", mail, contrasenia);
                vistaPrincipal.activarBotonDeRegistrarUsuario();
            }
        });
    }

    private void inicializarGrupoRadioButtons() {
        ButtonGroup radioButtonsGroup = new ButtonGroup();
        radioButtonsGroup.add(siRadioButton);
        radioButtonsGroup.add(noRadioButton);
    }

    private boolean todosLosCamposEstanLlenos() {
        //todo terminar esto
        return !this.nombreTextField.equals("") &&
                !this.contraseniaTextField.getText().equals("");
    }

}
