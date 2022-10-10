package vista;

import database.DataBase;
import database.entidades.Cliente;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private JTextField dniTitularTextField;
    private JTextField parentescoTextField;
    private JPanel parentescoPanel;
    private VistaPrincipal vistaPrincipal;
    private JFrame frame;


    public VistaRegistrarUsuario(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.mostrarVista();
        this.inicializar();
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
            }
        });
    }

    private void inicializar() {
        this.inicializarGrupoRadioButtons();
        iniciarBotonRegistrarListener();
    }

    public void iniciarBotonRegistrarListener() {
        botonRegistrar.addActionListener(actionEvent -> registrarUsuario());
    }

    private void registrarUsuario(){
        try {
            String nombre = this.nombreTextField.getText();
            if(nombre.equals(""))
                throw new Exception("Nombre incompleto");
            String apellido = this.apellidoTextField.getText();
            if(apellido.equals(""))
                throw new Exception("Apellido incompleto");
            int nro_documento;
            try{
                nro_documento = Integer.parseInt(this.dniTextField.getText());
            }catch (NumberFormatException e){
                throw new Exception("Numero de documento debe ser un numero");
            }
            String fecha_nac = this.fechaNacTextField.getText();
            if(!esFechaValida(fecha_nac))
                throw new Exception("Formato de fecha de nacimiento invalido");
            String cuil = this.cuilTextField.getText();
            if(!cuil.matches("[0-9]+"))
                throw new Exception("CUIL debe ser un numero");
            String contrasenia = String.valueOf(this.contraseniaTextField.getPassword());
            if(contrasenia.equals(""))
                throw new Exception("Contraseña incompleta");
            String mail = this.mailTextField.getText();
            if(mail.equals(""))
                throw new Exception("Mail incompleto");
            if (this.siRadioButton.isSelected()) {
                DataBase.insertarCliente(apellido, nombre, nro_documento, cuil, fecha_nac, null, true, null, mail, contrasenia);
            }
            else {
                int dniTitular;
                try{
                    dniTitular = Integer.parseInt(dniTitularTextField.getText());
                }catch (NumberFormatException e){
                    throw new Exception("El numero de documento del titular debe ser un numero");
                }
                String parentesco = parentescoTextField.getText();
                if(parentesco.equals(""))
                    throw new Exception("Parentesco incompleto");
                Cliente clienteTitular = DataBase.getCliente(dniTitular);
                if(clienteTitular == null)
                    throw new Exception("El titular indicado no existe");
                if(!clienteTitular.esTitular)
                    throw new Exception("El titular indicado no es titular");
                DataBase.insertarCliente(apellido, nombre, nro_documento, cuil, fecha_nac, null, false, null, mail, contrasenia);
                DataBase.insertarFamiliar(dniTitular, nro_documento, parentesco);
            }
            this.informarRegistroCorrecto();
            DataBase.imprimirBaseDeDatos();
        }catch (Exception e){
            informarError(e.getMessage());
        }
    }

    private boolean esFechaValida(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        formatoFecha.setLenient(false);
        try{
            formatoFecha.parse(fecha.trim());
        }catch (ParseException e){
            return false;
        }
        return true;
    }

    private void informarRegistroCorrecto() {
        JOptionPane.showMessageDialog(null, "El usuario fue registrado con éxito", "Usuario registrado", JOptionPane.INFORMATION_MESSAGE);
        this.frame.setVisible(false);
        vistaPrincipal.activarBotonDeRegistrarUsuario();
    }

    private void informarError(String mensaje){
        JOptionPane.showInternalMessageDialog(null, mensaje,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void inicializarGrupoRadioButtons() {
        parentescoPanel.setVisible(false);
        ButtonGroup radioButtonsGroup = new ButtonGroup();
        siRadioButton.setSelected(true);
        siRadioButton.addActionListener(actionEvent -> parentescoPanel.setVisible(false));
        noRadioButton.addActionListener(actionEvent -> parentescoPanel.setVisible(true));
        radioButtonsGroup.add(siRadioButton);
        radioButtonsGroup.add(noRadioButton);
    }

}
