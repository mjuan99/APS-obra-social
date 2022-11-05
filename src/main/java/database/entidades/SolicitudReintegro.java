package database.entidades;

public class SolicitudReintegro {
    public int idSolicitud;
    public int dniCliente;
    public String fecha;
    public String doctor;
    public double monto;
    public String pathTicket;
    public String estado;

    public SolicitudReintegro(int idSolicitud, int dniCliente, String fecha, String doctor, double monto, String pathTicket, String estado) {
        this.idSolicitud = idSolicitud;
        this.dniCliente = dniCliente;
        this.fecha = fecha;
        this.doctor = doctor;
        this.monto = monto;
        this.pathTicket = pathTicket;
        this.estado = estado;
    }
}
