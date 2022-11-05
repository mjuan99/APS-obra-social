package vista;

import database.DataBase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VistaSolicitarPrestacion {
    private JTextField fechaTextField;
    private JTextField doctorTextField;
    private JButton subirRecetaBoton;
    private JButton solicitarPrestacionBoton;
    private JPanel contentPane;
    private JLabel pathRecetaLabel;
    private int dniCliente;
    private JFrame frame;
    private String pathReceta;

    public VistaSolicitarPrestacion(int dniCliente) {
        this.frame = new JFrame("Solicitar Reintegro");
        this.dniCliente = dniCliente;

        inicializarListeners();
        mostrarVista();
    }

    private void mostrarVista() {
        frame.setContentPane(this.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void inicializarListeners() {
        solicitarPrestacionBoton.addActionListener(actionEvent -> {
            try{
                String fecha = fechaTextField.getText();
                if(!esFechaValida(fecha))
                    throw new Exception("Formato de fecha invalido");
                String doctor = doctorTextField.getText();
                if(doctor.equals(""))
                    throw new Exception("Doctor incompleto");
                if(pathReceta == null)
                    throw new Exception("Debe subir la receta");
                DataBase.insertarSolicitudPrestacion(dniCliente, fecha, doctor, pathReceta);
                informarSolicitudExitosa();
                DataBase.imprimirBaseDeDatos();
            }catch (Exception e){
                informarError(e.getMessage());
            }
        });

        subirRecetaBoton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                pathReceta = fileChooser.getSelectedFile().getPath();
                pathRecetaLabel.setText(fileChooser.getSelectedFile().getName());
            }
        });
    }

    private void informarSolicitudExitosa() {
        JOptionPane.showMessageDialog(null, "La solicitud fue registrada con éxito", "Solicitud registrada", JOptionPane.INFORMATION_MESSAGE);
        this.frame.setVisible(false);
    }

    private void informarError(String mensaje){
        JOptionPane.showInternalMessageDialog(null, mensaje,
                "Error", JOptionPane.INFORMATION_MESSAGE);
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
}
