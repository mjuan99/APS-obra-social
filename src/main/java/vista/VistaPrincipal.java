package vista;

import database.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPrincipal {

    private JPanel contentPane;
    private DataBase database;
    private JButton botonRegistrarUsuario;
    private JButton botonIniciarSesion;

    public VistaPrincipal(DataBase dataBase) {
        this.database = database;
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
        this.botonRegistrarUsuario.addActionListener(actionEvent -> {
            VistaRegistrarUsuario vistaRegistrarUsuario = new VistaRegistrarUsuario(this, this.database);
            this.botonRegistrarUsuario.setEnabled(false);
        });
        this.botonIniciarSesion.addActionListener(actionEvent -> {
            VistaLogin vistaLogin = new VistaLogin(this, this.database);
            this.botonRegistrarUsuario.setEnabled(false);
        });
    }

    public void activarBotonDeRegistrarUsuario() {
        this.botonRegistrarUsuario.setEnabled(true);
    }
}
