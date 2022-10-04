package database.entidades;

public class SolicitudAlta {
    public String plan;
    public int dniCliente;
    public String fechaSolicitud;

    public SolicitudAlta(String plan, int dniCliente, String fechaSolicitud){
        this.plan = plan;
        this.dniCliente = dniCliente;
        this.fechaSolicitud = fechaSolicitud;
    }
}
