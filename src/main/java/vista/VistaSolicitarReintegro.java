package vista;

import database.DataBase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VistaSolicitarReintegro {
    private JPanel contentPane;
    private JTextField fechaTextField;
    private JButton subirTicketBoton;
    private JButton solicitarReintegroBoton;
    private JTextField doctorTextField;
    private JTextField montoTextField;
    private JLabel ticketLabel;
    private JFrame frame;
    private int dniCliente;
    private String pathTicket;

    public VistaSolicitarReintegro(int dniCliente) {
        this.frame = new JFrame("Solicitar Reintegro");
        this.dniCliente = dniCliente;

        inicializarListeners();
        mostrarVista();
    }

    private void inicializarListeners() {
        solicitarReintegroBoton.addActionListener(actionEvent -> {
            try{
                String fecha = fechaTextField.getText();
                if(!esFechaValida(fecha))
                    throw new Exception("Formato de fecha invalido");
                String doctor = doctorTextField.getText();
                if(doctor.equals(""))
                    throw new Exception("Doctor incompleto");
                double monto;
                try{
                    monto = Double.parseDouble(montoTextField.getText());
                }catch (NumberFormatException e){
                    throw new Exception("Monto debe ser un numero");
                }
                if(pathTicket == null)
                    throw new Exception("Debe subir el ticket");
                DataBase.insertarSolicitudReintegro(dniCliente, fecha, doctor, monto, pathTicket);
                informarSolicitudExitosa();
                DataBase.imprimirBaseDeDatos();
            }catch (Exception e){
                informarError(e.getMessage());
            }
        });

        subirTicketBoton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
                pathTicket = fileChooser.getSelectedFile().getPath();
                ticketLabel.setText(fileChooser.getSelectedFile().getName());
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
}
