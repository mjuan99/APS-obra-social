package vista;

import database.DataBase;
import database.entidades.Cliente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class VistaGenerarCuponPago {

    private JPanel contentPane, panel1, panel2;
    private JButton imprimirButton;
    private JButton enviarVíaMailButton;
    private JFrame frame;
    private int dniCliente;
    private int valor;

    public VistaGenerarCuponPago(int dniCliente) {
        this.dniCliente = dniCliente;
        this.frame = new JFrame("Generar cupón de pago");
        this.mostrarVista();
    }

    private void mostrarVista() {
        this.contentPane = new JPanel();
        frame.setContentPane(this.contentPane);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        inicializarPaneles();
        inicializarLista();
        inicializarBotones();
        inicializarListeners();
    }

    private void inicializarListeners() {
        imprimirButton.addActionListener(actionEvent -> {
            if(valor > 0){
                System.out.println();
                System.out.println("---------------------");
                System.out.println("Imprimiento Ticket...");
                System.out.println("Monto: $" + valor);
                System.out.println("---------------------");
                frame.setVisible(false);
            }
        });

        enviarVíaMailButton.addActionListener(actionEvent -> {
            if(valor > 0){
                try {
                    Cliente cliente = DataBase.getCliente(dniCliente);
                    if(cliente != null) {
                        String email = cliente.email;
                        System.out.println();
                        System.out.println("---------------------");
                        System.out.println("Enviando Ticket a " + email + "...");
                        System.out.println("Monto: $" + valor);
                        System.out.println("---------------------");
                        frame.setVisible(false);
                    }
                } catch (Exception e) {
                    informarError(e.getMessage());
                }
            }
        });
    }

    private void informarError(String mensaje){
        JOptionPane.showInternalMessageDialog(null, mensaje,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void inicializarPaneles() {
        this.panel1 = new JPanel();
        this.panel2 = new JPanel();
        this.contentPane.add(this.panel1);
        this.contentPane.add(this.panel2);
    }

    private void inicializarLista() {
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        listModel.addElement("Mensual");
        listModel.addElement("Semestral");
        listModel.addElement("Anual");
        JList listaOpcionesDePago = new JList<>(listModel);
        this.panel1.add(listaOpcionesDePago);
        this.inicializarOyentesLista(listaOpcionesDePago);
    }

    private void inicializarOyentesLista(JList listaOpcionesDePago) {
        listaOpcionesDePago.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int pagoSeleccionado = listaOpcionesDePago.getSelectedIndex();
                int opcionSeleccionadaDelDialogo = JOptionPane.showConfirmDialog(null, "Desea generar cupon de pago " + listaOpcionesDePago.getSelectedValue() + " ?");
                switch (opcionSeleccionadaDelDialogo) {
                    case 0: //selecciono opcion SI
                        try {
                            valor = DataBase.generarCuponPago(dniCliente, pagoSeleccionado);
                        } catch (Exception e) {
                            //todo acomodar excepcion
                            e.printStackTrace();
                        }
                        mostrarBotones();
                        break;
                    case 1: //opcion no
                        break;
                    case 2: //opcion cancelar
                        break;
                }
            }
        });
    }

    private void inicializarBotones() {
        this.imprimirButton = new JButton("Imprimir");
        this.panel2.add(imprimirButton);
        this.enviarVíaMailButton = new JButton("Enviar vía mail");
        this.panel2.add(enviarVíaMailButton);
        this.imprimirButton.setVisible(false);
        this.enviarVíaMailButton.setVisible(false);
    }

    private void mostrarBotones() {
        this.imprimirButton.setVisible(true);
        this.enviarVíaMailButton.setVisible(true);
    }
}
