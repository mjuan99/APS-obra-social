package vista;

import database.DataBase;
import database.entidades.SolicitudReintegro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class VistaSolicitudesReintegroEmpleado {
    private JPanel contentPane;
    private JTable tablaSolicitudes;
    private JButton btnRechazar;
    private JButton btnAprobar;
    private JFrame frame;

    private DefaultTableModel model;
    private VistaPrincipalEmpleado vistaPrincipalEmpleado;

    public VistaSolicitudesReintegroEmpleado(VistaPrincipalEmpleado vistaEmpleado){
        this.frame = new JFrame("Solicitudes de Reintegro");
        this.vistaPrincipalEmpleado = vistaEmpleado;
        this.mostrarVista();
        this.inicializarListeners();
        mostrarReintegros();
    }

    private void mostrarReintegros(){
        try{
            ArrayList<SolicitudReintegro> solicitudesPendientes = DataBase.recuperarSolicitudesReintegroPendientes();
            this.model = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;} };

            model.addColumn("Id");
            model.addColumn("DNI");
            model.addColumn("Fecha");
            model.addColumn("Monto");
            model.addColumn("Doctor");
            model.addColumn("Ticket");

            tablaSolicitudes.setModel(model);
            for (SolicitudReintegro solicitudReintegro : solicitudesPendientes){
                model.addRow(new String[]{String.valueOf(solicitudReintegro.idSolicitud),String.valueOf(solicitudReintegro.dniCliente), solicitudReintegro.fecha, String.valueOf(solicitudReintegro.monto),solicitudReintegro.doctor, solicitudReintegro.pathTicket});
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



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
                    DataBase.modificarEstadoSolicitudDeReintegro(id,"rechazado");
                    mostrarReintegros();
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
                    DataBase.modificarEstadoSolicitudDeReintegro(id,"aprobado");
                    mostrarReintegros();
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

}
