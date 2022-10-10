package vista;

import database.DataBase;
import database.entidades.Plan;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class VistaPrincipalCliente extends JFrame {

    private JPanel contentPane;
    private JTable tablaPlanes;
    private JButton botonSolicitarPlan;
    private VistaLogin vistaLogin;
    private JFrame frame;
    private DefaultTableModel model;
    private int dniCliente;

    public VistaPrincipalCliente(VistaLogin vistaLogin, int dniCliente) {
        this.frame = new JFrame("Cliente");
        this.vistaLogin = vistaLogin;
        this.dniCliente = dniCliente;
        JTable tablaPlanes = new JTable();
        tablaPlanes.setEnabled(false);
        this.mostrarVista();
//        this.botonSolicitarPlan.setEnabled(false);
        mostrarPlanes();
        inicializarListeners();
    }

    private void inicializarListeners() {
        botonSolicitarPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fila = tablaPlanes.getSelectedRow();
                    String nombrePlan = (String) tablaPlanes.getValueAt(fila, 0);
                    DataBase.insertarSolicitudAlta(nombrePlan, dniCliente, getDate());
                    JOptionPane.showMessageDialog(null, "Alta solicitada exitosamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                    DataBase.imprimirBaseDeDatos();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un plan",
                            "No seleccionaste un plan", JOptionPane.INFORMATION_MESSAGE);
                }
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
        try {
            LinkedList<Plan> planes = DataBase.getPlanes();
            this.model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Nombre");
            model.addColumn("Costo");
            model.addColumn("Beneficios");

            tablaPlanes.setModel(model);
            for (Plan plan : planes) {
                model.addRow(new String[]{plan.nombre, String.valueOf(plan.costo), plan.beneficios});
            }
        }catch (Exception e){
            informarError(e.getMessage());
        }
    }

    private void informarError(String mensaje){
        JOptionPane.showInternalMessageDialog(null, mensaje,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }

}
