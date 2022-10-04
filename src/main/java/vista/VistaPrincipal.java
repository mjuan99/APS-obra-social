package vista;

import javax.swing.*;

public class VistaPrincipal {

    private JPanel contentPane;
    private JButton botonIniciarSesion;
    private JButton botonRegistrar;

    public VistaPrincipal() {
        this.mostrarVista();
        this.inicializarListeners();
    }

    public void mostrarVista() {
        JFrame frame = new JFrame("Vista Principal");
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(550,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void inicializarListeners() {
        this.botonRegistrar.addActionListener(actionEvent -> {
            VistaRegistrarUsuario vistaRegistrarUsuario = new VistaRegistrarUsuario(this);
            this.botonRegistrar.setEnabled(false);
        });
        this.botonIniciarSesion.addActionListener(actionEvent -> {
            VistaLogin vistaLogin = new VistaLogin(this);
        });
    }

    public void activarBotonDeRegistrarUsuario() {
        this.botonRegistrar.setEnabled(true);
    }
}
