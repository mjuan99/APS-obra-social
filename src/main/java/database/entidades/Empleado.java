package database.entidades;

public class Empleado {

    public String apellido;
    public String nombre;
    public int nro_documento;
    public String telefono;
    public String email;
    public String cargo;
    public String usuario;
    public String contrasenia;

    public Empleado(String apellido, String nombre, int nro_documento, String telefono, String email, String cargo, String usuario, String contrasenia){
        this.apellido = apellido;
        this.nombre = nombre;
        this.nro_documento = nro_documento;
        this.telefono = telefono;
        this.email = email;
        this.cargo = cargo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

}
