package vista;

import database.DataBase;
import javax.swing.*;

public class VistaRegistrarUsuario {

    private JPanel contentPane;
    private JTextField apellidoTextField;
    private JTextField nombreTextField;
    private JTextField dniTextField;
    private JTextField cuilTextField;
    private JButton botonRegistrar;
    private JTextField mailTextField;
    private JPasswordField contraseniaTextField;
    private DataBase db;
    private VistaPrincipal vistaPrincipal;

    public VistaRegistrarUsuario(VistaPrincipal vistaPrincipal, DataBase db) {
        this.vistaPrincipal = vistaPrincipal;
        this.db = db;
        this.mostrarVista();
        this.inicializar();
        this.inicializarListeners();
    }

    public void mostrarVista() {
        JFrame frame = new JFrame("Registrar Usuario");
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        frame.setResizable(false);
    }

    private void inicializar() {
//        botonRegistrar.setEnabled(false);
    }

    public void inicializarListeners() {
        botonRegistrar.addActionListener(actionEvent -> {
            if (todosLosCamposEstanLlenos()) {
                String nombre = this.nombreTextField.getText();
                String apellido = this.apellidoTextField.getText();
                //todo hay que validar que el nro_documento sea un entero?
//                int nro_documento = Integer.parseInt(this.dniTextField.getText());
                String cuil = this.cuilTextField.getText();
                String contrasenia = this.contraseniaTextField.getText();
                String mail = this.mailTextField.getText();
                //todo preguntar a la otra comision por los campos que no se piden
//                this.db.insertarCliente(apellido, nombre, nro_documento, cuil, null, null, null, null, mail, contrasenia);


                vistaPrincipal.activarBotonDeRegistrarUsuario();
            }
        });
    }

    private boolean todosLosCamposEstanLlenos() {
        return !this.nombreTextField.equals("") && !this.contraseniaTextField.getText().equals("");
    }

}
