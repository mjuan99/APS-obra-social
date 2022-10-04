package vista;

import database.DataBase;
import database.entidades.SolicitudAlta;
import vista.VistaLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class VistaPrincipalEmpleado extends JFrame {

    private JPanel contentPane;
    private JTable tablaPlanes;
    private JButton botonSolicitarPlan;
    private VistaLogin vistaLogin;
    private JFrame frame;
    private DefaultTableModel model;

    public VistaPrincipalEmpleado(VistaLogin vistaLogin) {
        this.frame = new JFrame("Cliente");
        this.vistaLogin = vistaLogin;
        JTable tablaPlanes = new JTable();
        tablaPlanes.setEnabled(false);
        this.mostrarVista();
        mostrarPlanes();
        inicializarListeners();
    }

    private void inicializarListeners() {
        botonSolicitarPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaPlanes.getSelectedRow();
                String nombrePlan = (String) tablaPlanes.getValueAt(fila,0);
                int dniCliente = Integer.parseInt((String) tablaPlanes.getValueAt(fila,1));
                DataBase.seleccionarPlanCliente(dniCliente,nombrePlan,getDate());
                DataBase.eliminarSolicitudDeAlta(nombrePlan,dniCliente);
                DataBase.imprimirBaseDeDatos();
                mostrarPlanes();
            }
        });
    }

    private String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
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

    private void mostrarPlanes(){
        LinkedList<SolicitudAlta> solicitudAltas = DataBase.getSolicitudesDeAlta();
        this.model = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;} };

        model.addColumn("Plan");
        model.addColumn("DNI cliente");
        model.addColumn("Fecha");

        tablaPlanes.setModel(model);
        for (SolicitudAlta solicitudAlta : solicitudAltas){
            model.addRow(new String[]{solicitudAlta.plan, String.valueOf(solicitudAlta.dniCliente),solicitudAlta.fechaSolicitud});
        }
    }
}
