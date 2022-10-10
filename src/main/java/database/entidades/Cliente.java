package database.entidades;

public class Cliente {
    public String apellido;
    public String nombre;
    public int nro_documento;
    public String cuil;
    public String fechaNacimiento;
    public String plan;
    public boolean esTitular;
    public String fechaAltaPlan;
    public String email;
    public String contrasenia;

    public Cliente(String apellido, String nombre, int nro_documento, String cuil, String fechaNacimiento, String plan, boolean esTitular, String fechaAltaPlan, String email, String contrasenia) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.nro_documento = nro_documento;
        this.cuil = cuil;
        this.fechaNacimiento = fechaNacimiento;
        this.plan = plan;
        this.esTitular = esTitular;
        this.fechaAltaPlan = fechaAltaPlan;
        this.email = email;
        this.contrasenia = contrasenia;
    }
}
