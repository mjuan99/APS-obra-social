package database;

import database.entidades.Cliente;
import database.entidades.Empleado;
import database.entidades.Plan;
import database.entidades.SolicitudAlta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;

public class DataBase {

    private final static String databaseUrl = "jdbc:sqlite:./obra-social.db";


    public static void createDatabaseIfDoesNotExists() {
        try (Connection connection = getConnection()) {
            String sqlCreateDataBase = readFile("src/main/java/database/obra-social.sql");
            executeUpdate(connection, sqlCreateDataBase);
        } catch (SQLException e) {
            //database already exists, no actions needed
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void testInsertar() {
        try{
            insertarPlan("basico", 999.99, "ninguno");
            insertarPlan("intermedio", 9999.99, "30% desc. farmacia");
            insertarPlan("pro", 99999.99, "60% desc. farmacia");
            insertarCliente("Perez", "Juan", 44555666,
                    "20445556660", "15/04/1997", null, true,
                    "29/09/2022", "jp@gmail.com", "1234");
            insertarCliente("Garcia", "Carlos", 45666777,
                    "2045666777", "16/10/1999", "intermedio", false,
                    "28/09/2022", "cg@gmail.com", "2345");
            insertarCliente("Fernandez", "Diego", 46777888,
                    "20467778880", "22/06/1995", "pro", true,
                    "27/09/2022", "df@gmail.com", "3456");
            insertarFamiliar(44555666, 45666777, "hermano");
            insertarEmpleado("Sanchez", "Martin", 47888999,
                    "4561234", "ms@gmail.com", "presidente", "ms4567", "4567");
            insertarEmpleado("Alvarado", "Nicolas", 48999000,
                    "4564321", "na@gmail.com", "administrador", "na5678", "5678");
            insertarSolicitudAlta("pro", 44555666, "29/09/2022");
            insertarSolicitudAlta("intermedio", 45666777, "30/09/2022");
            insertarSolicitudReintegro(45666777, "29/09/2022", "porque si", "practica1");
            insertarSolicitudPrestacion(46777888, "30/09/2022", "pinto", "practica2");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void imprimirBaseDeDatos() {
        try (Connection connection = getConnection()) {
            System.out.println("\nPlanes:");
            printResultSet(executeQuery(connection, "select * from planes"));
            System.out.println("\nClientes:");
            printResultSet(executeQuery(connection, "select * from clientes"));
            System.out.println("\nFamiliares:");
            printResultSet(executeQuery(connection, "select * from familiares"));
            System.out.println("\nEmpleados:");
            printResultSet(executeQuery(connection, "select * from empleados"));
            System.out.println("\nSolicitudes de alta:");
            printResultSet(executeQuery(connection, "select * from solicitudes_alta"));
            System.out.println("\nSolicitudes de reintegro:");
            printResultSet(executeQuery(connection, "select * from solicitudes_reintegro"));
            System.out.println("\nSolicitudes de prestaciones:");
            printResultSet(executeQuery(connection, "select * from solicitudes_prestaciones"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static LinkedList<Plan> getPlanes() throws Exception {
        LinkedList<Plan> planes = new LinkedList<>();
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT * FROM planes;");
            while (rs.next())
                planes.add(new Plan(rs.getString("nombre"), rs.getDouble("costo"), rs.getString("beneficios")));
            return planes;
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void generarCuponPago(int dniCliente, int pagoSeleccionado) throws Exception {
        try(Connection connection = getConnection()){
            int totalPago = 0;
            int mesesAPagar = 0;
            switch (pagoSeleccionado) {
                case 0: //mensual
                    mesesAPagar = 1;
                    break;
                case 1: //semestral
                    mesesAPagar = 6;
                    break;
                case 2: //anual
                    mesesAPagar = 12;
                    break;
            }
            ResultSet rs_familiares = executeQuery(connection, "SELECT dni_cliente_familiar FROM Familiares WHERE dni_cliente_cabecera = " + dniCliente + ";");
            while (rs_familiares.next()) {
                int dniFamiliar = rs_familiares.getInt("dni_cliente_familiar");
                ResultSet rs_plan = executeQuery(connection, "SELECT plan FROM Clientes WHERE nro_documento = " + dniFamiliar + ";");
                if (rs_plan.next()) {
                    String nombrePlan = rs_plan.getString("plan");
                    ResultSet rs_precioPlan = executeQuery(connection, "SELECT costo FROM Planes WHERE nombre = " + nombrePlan + ";");
                    if (rs_precioPlan.next()) {
                        float costoPlan = rs_precioPlan.getFloat("costo");
                        totalPago += costoPlan;
                    }
                }
            }
            //todo falla en la ultima query
            //todo que habria que hacer con el cupon?
            int valorCupon = mesesAPagar * totalPago;
            System.out.println(valorCupon);
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static boolean esClienteTitular(int dni) throws Exception {
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT es_titular FROM Clientes WHERE nro_documento = " + dni + ";");
            if(rs.next())
                return rs.getBoolean("es_titular");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
        return false;
    }

    public static Cliente getCliente(int dni) throws Exception{
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT * FROM Clientes WHERE nro_documento = " + dni + ";");
            if(rs.next())
                return new Cliente(rs.getString("apellido"), rs.getString("nombre"),
                        rs.getInt("nro_documento"), rs.getString("cuil"), rs.getString("fecha_nacimiento"),
                        rs.getString("plan"), rs.getBoolean("es_titular"), rs.getString("fecha_alta_plan"),
                        rs.getString("email"), rs.getString("contraseña"));
            else
                return null;
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static Empleado getEmpleado(String usr) throws Exception{
        try(Connection connection = getConnection()) {

            ResultSet rs = executeQuery(connection, "SELECT * FROM Empleados WHERE usuario = \"" + usr + "\";");

            if(rs.next()){
                return new Empleado(rs.getString("apellido"), rs.getString("nombre"),
                        rs.getInt("nro_documento"), rs.getString("telefono"), rs.getString("email"), rs.getString("cargo"),
                        rs.getString("usuario"), rs.getString("contraseña"));
            }
                else
                    return null;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            throw new Exception("Error de la base de datos");
        }
    }

    public static LinkedList<SolicitudAlta> getSolicitudesDeAlta() throws Exception {
        LinkedList<SolicitudAlta> solicitudes = new LinkedList<>();
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT * FROM Solicitudes_Alta;");
            while(rs.next())
                solicitudes.add(new SolicitudAlta(rs.getString("tipo_plan"), rs.getInt("cliente"), rs.getString("fecha")));
            return solicitudes;
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void seleccionarPlanCliente(int nro_documento, String plan, String fechaAlta) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "UPDATE Clientes SET plan = \"" + plan + "\", fecha_alta_plan = \"" +
                    fechaAlta + "\" WHERE nro_documento = " + nro_documento + ";");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void eliminarSolicitudDeAlta(String plan, int nro_documento) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "DELETE FROM Solicitudes_Alta WHERE tipo_plan = \"" +
                    plan + "\" AND cliente = " + nro_documento + ";");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarPlan(String nombre, double costo, String beneficios) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into planes values (\"" + nombre + "\", " +
                    costo + ", \"" + beneficios + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarCliente(String apellido, String nombre, int nro_documento, String cuil, String fecha_nacimiento, String plan, boolean esTitular, String fecha_alta_plan, String email, String contrasenia) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into clientes values (\"" + apellido + "\", \"" + nombre + "\", " +
                            nro_documento + ", \"" + cuil + "\", \"" + fecha_nacimiento +
                            "\", " + (plan == null ? "null" : "\"" + plan + "\"") + ", " + (esTitular ? 1 : 0) +
                            ", \"" + fecha_alta_plan + "\", \"" + email + "\", \"" + contrasenia + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarFamiliar(int dni_titular, int dni_familiar, String parentesco) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into familiares values (" + dni_titular + ", " + dni_familiar + ", \"" + parentesco + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarEmpleado(String apellido, String nombre, int nro_documento, String telefono, String email, String cargo, String usuario, String contrasenia) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into empleados values (\"" + apellido + "\", \"" + nombre +
                    "\", " + nro_documento + ", \"" + telefono + "\", \"" + email +
                    "\", \"" + cargo.toLowerCase() + "\", \"" + usuario + "\", \"" + contrasenia + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarSolicitudAlta(String plan, int dni_cliente, String fechaSolicitud) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_alta (tipo_plan, cliente, fecha) values" +
                    "(\"" + plan + "\", " + dni_cliente + ", \"" + fechaSolicitud + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarSolicitudReintegro(int dni_cliente, String fechaSolicitud, String razon, String practica) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_reintegro (cliente, fecha, razon, practica) values ("
                    + dni_cliente + ", \"" + fechaSolicitud + "\", \"" + razon + "\", \"" + practica + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static void insertarSolicitudPrestacion(int dni_cliente, String fechaSolicitud, String razon, String practica) throws Exception {
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_prestaciones (cliente, fecha, razon, practica) values ("
                    + dni_cliente + ", \"" + fechaSolicitud + "\", \"" + razon + "\", \"" + practica + "\")");
        }catch (SQLException e){
            throw new Exception("Error de la base de datos");
        }
    }

    public static boolean loginCliente(int dni_cliente, String contrasenia){
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT * FROM Clientes WHERE nro_documento = " + dni_cliente +
                    " AND contraseña = \"" + contrasenia + "\";");
            return rs.next();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean loginEmpleado(String usuario, String contrasenia){
        try(Connection connection = getConnection()){
            ResultSet rs = executeQuery(connection, "SELECT * FROM Empleados WHERE usuario = \"" + usuario +
                    "\" AND contraseña = \"" + contrasenia + "\";");
            return rs.next();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        return connection;
    }

    public static void clearDataBase(){
        try (Connection connection = DriverManager.getConnection(databaseUrl)){
            executeUpdate(connection, "DELETE FROM Clientes" );
            executeUpdate(connection, "DELETE FROM Familiares" );
            executeUpdate(connection, "DELETE FROM Empleados" );
            executeUpdate(connection, "DELETE FROM Planes" );
            executeUpdate(connection, "DELETE FROM Solicitudes_Alta" );
            executeUpdate(connection, "DELETE FROM Solicitudes_Reintegro" );
            executeUpdate(connection, "DELETE FROM Solicitudes_Prestaciones" );
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printResultSet(ResultSet rs){
        try {
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    System.out.print(rs.getString(i) + ", ");
                System.out.println();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static String readFile(String sourceFile) throws IOException {
        return Files.readString(Paths.get(sourceFile));
    }

    private static void executeUpdate(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(sql);
    }

    private static ResultSet executeQuery(Connection connection, String sql) throws SQLException{
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeQuery(sql);
    }
}
