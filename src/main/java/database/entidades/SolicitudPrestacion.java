package database.entidades;

public class SolicitudPrestacion {
    public int idSolicitud;
    public int dniCliente;
    public String fecha;
    public String doctor;
    public String pathReceta;
    public String estado;

    public SolicitudPrestacion(int idSolicitud, int dniCliente, String fecha, String doctor, String pathReceta, String estado) {
        this.idSolicitud = idSolicitud;
        this.dniCliente = dniCliente;
        this.fecha = fecha;
        this.doctor = doctor;
        this.pathReceta = pathReceta;
        this.estado = estado;
    }
}
