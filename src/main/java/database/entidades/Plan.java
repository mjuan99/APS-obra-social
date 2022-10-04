package database.entidades;

public class Plan {
    public String nombre;
    public double costo;
    public String beneficios;

    public Plan(String nombre, double costo, String beneficios){
        this.nombre = nombre;
        this.costo = costo;
        this.beneficios = beneficios;
    }
}
