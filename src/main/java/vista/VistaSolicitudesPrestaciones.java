package vista;

import database.DataBase;
import database.entidades.SolicitudPrestacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaSolicitudesPrestaciones {
    private JPanel contentPane;
    private JTable tablaSolicitudes;
    private JButton btnAprobar;
    private JButton btnRechazar;

    private DefaultTableModel model;

    private VistaPrincipalEmpleado vistaPrincipalEmpleado;

    private JFrame frame;

    public VistaSolicitudesPrestaciones(VistaPrincipalEmpleado vistaEmpleado){
        this.frame = new JFrame("Solicitudes de Prestaciones");
        this.vistaPrincipalEmpleado = vistaEmpleado;
        this.mostrarVista();
        this.inicializarListeners();
        mostrarPrestaciones();
    }

    private void mostrarVista() {
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void inicializarListeners(){

        btnRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaSolicitudes.getSelectedRow();

                if(fila != -1) {
                    int id = Integer.parseInt((String) tablaSolicitudes.getValueAt(fila, 0));
                    DataBase.modificarEstadoSolicitudDePrestaciones(id,"rechazado");
                    mostrarPrestaciones();
                }
                else{
                    informarError("Debe seleccionar una fila");
                }

            }
        });

        btnAprobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaSolicitudes.getSelectedRow();

                if(fila != -1) {
                    int id = Integer.parseInt((String) tablaSolicitudes.getValueAt(fila, 0));
                    DataBase.modificarEstadoSolicitudDePrestaciones(id,"aprobado");
                    mostrarPrestaciones();
                }
                else{
                    informarError("Debe seleccionar una fila");
                }
            }
        });


        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                vistaPrincipalEmpleado.habilitarInteraccion();
            }
        });
    }

    private void informarError(String mensaje){
        JOptionPane.showInternalMessageDialog(null, mensaje,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPrestaciones(){
        try{
            ArrayList<SolicitudPrestacion> solicitudesPendientes = DataBase.recuperarSolicitudesPrestacionesPendientes();
            this.model = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;} };

            model.addColumn("Id");
            model.addColumn("DNI");
            model.addColumn("Fecha");
            model.addColumn("Doctor");
            model.addColumn("Receta");

            tablaSolicitudes.setModel(model);
            for (SolicitudPrestacion solicitudPrestacion : solicitudesPendientes){
                model.addRow(new String[]{String.valueOf(solicitudPrestacion.idSolicitud),String.valueOf(solicitudPrestacion.dniCliente), solicitudPrestacion.fecha, solicitudPrestacion.doctor, solicitudPrestacion.pathReceta});
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

}

