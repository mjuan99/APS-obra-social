package vista;

import database.DataBase;
import database.entidades.Plan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class VistaPrincipalCliente extends JFrame {

    private JPanel contentPane;
    private JTable tablaPlanes;
    private VistaLogin vistaLogin;
    private JFrame frame;

    public VistaPrincipalCliente(VistaLogin vistaLogin) {
        this.frame = new JFrame("Cliente");
        this.vistaLogin = vistaLogin;
        JTable tablaPlanes = new JTable();
        tablaPlanes.setEnabled(false);
        frame.add(tablaPlanes);
        this.mostrarVista();
        mostrarPlanes();
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
        LinkedList<Plan> planes = DataBase.getPlanes();
        DefaultTableModel model = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;} };


        model.addColumn("Nombre");
        model.addColumn("Costo");
        model.addColumn("Beneficios");

        tablaPlanes.setModel(model);
        for (Plan plan : planes){
            model.addRow(new String[]{plan.nombre, String.valueOf(plan.costo),plan.beneficios});
        }

    }

}
