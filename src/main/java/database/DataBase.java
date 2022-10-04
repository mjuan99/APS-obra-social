package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DataBase {

    private final static String databaseUrl = "jdbc:sqlite:./obra-social.db";


    public void createDatabaseIfDoesNotExists() {
        try (Connection connection = getConnection()) {
            String sqlCreateDataBase = readFile("src/main/java/database/obra-social.sql");
            executeUpdate(connection, sqlCreateDataBase);
        } catch (SQLException e) {
            //database already exists, no actions needed
        } catch (IOException e){
            e.printStackTrace();
        }

        clearDataBase();
        testInsertar();
        imprimirBaseDeDatos();
    }

    private void testInsertar() {
        insertarPlan("nefasto", 999.99, "ninguno");
        insertarPlan("basico", 9999.99, "un par");
        insertarPlan("pro", 99999.99, "algunos");
        insertarCliente("Perez", "Juan", 44555666,
                "20445556660", "15/04/1997", null, true,
                "29/09/2022", "jp@gmail.com", "1234");
        insertarCliente("Garcia", "Carlos", 45666777,
                "2045666777", "16/10/1999", "basico", false,
                "28/09/2022", "cg@gmail.com", "2345");
        insertarCliente("Fernandez", "Diego", 46777888,
                "20467778880", "22/06/1995", "pro", true,
                "27/09/2022", "df@gmail.com", "3456");
        insertarFamiliar(44555666, 45666777, "hermano");
        insertarEmpleado("Sanchez", "Martin", 47888999,
                "4561234", "ms@gmail.com", "presidente", "ms4567", "4567");
        insertarEmpleado("Alvarado", "Nicolas", 48999000,
                "4564321", "na@gmail.com", "tecnico", "na5678", "5678");
        insertarSolicitudAlta("pro", 44555666, "29/09/2022");
        insertarSolicitudAlta("nefasto", 45666777, "30/09/2022");
        insertarSolicitudReintegro(45666777, "29/09/2022", "porque si", "practica1");
        insertarSolicitudPrestacion(46777888, "30/09/2022", "pinto", "practica2");
    }

    private static void imprimirBaseDeDatos() {
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

    public void insertarPlan(String nombre, double costo, String beneficios){
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into planes values (\"" + nombre + "\", " +
                    costo + ", \"" + beneficios + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarCliente(String apellido, String nombre, int nro_documento, String cuil, String fecha_nacimiento, String plan, boolean esTitular, String fecha_alta_plan, String email, String contrasenia){
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into clientes values (\"" + apellido + "\", \"" + nombre + "\", " +
                            nro_documento + ", \"" + cuil + "\", \"" + fecha_nacimiento +
                            "\", " + (plan == null ? "null" : "\"" + plan + "\"") + ", " + (esTitular ? 1 : 0) +
                            ", \"" + fecha_alta_plan + "\", \"" + email + "\", \"" + contrasenia + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarFamiliar(int dni_titular, int dni_familiar, String parentesco){
        try(Connection connection = getConnection()){
            executeUpdate(connection,
                    "insert into familiares values (" + dni_titular + ", " + dni_familiar + ", \"" + parentesco + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarEmpleado(String apellido, String nombre, int nro_documento, String telefono, String email, String cargo, String usuario, String contrasenia){
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into empleados values (\"" + apellido + "\", \"" + nombre +
                    "\", " + nro_documento + ", \"" + telefono + "\", \"" + email +
                    "\", \"" + cargo + "\", \"" + usuario + "\", \"" + contrasenia + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarSolicitudAlta(String plan, int dni_cliente, String fechaSolicitud){
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_alta (tipo_plan, cliente, fecha) values" +
                    "(\"" + plan + "\", " + dni_cliente + ", \"" + fechaSolicitud + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarSolicitudReintegro(int dni_cliente, String fechaSolicitud, String razon, String practica){
        //executeUpdate(connection, "insert into solicitudes_reintegro (cliente, fecha, razon, practica) values (41686955, \"29/09/2022\", \"porque si\", \"alguna\")");
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_reintegro (cliente, fecha, razon, practica) values ("
                    + dni_cliente + ", \"" + fechaSolicitud + "\", \"" + razon + "\", \"" + practica + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarSolicitudPrestacion(int dni_cliente, String fechaSolicitud, String razon, String practica){
        //executeUpdate(connection, "insert into solicitudes_reintegro (cliente, fecha, razon, practica) values (41686955, \"29/09/2022\", \"porque si\", \"alguna\")");
        try(Connection connection = getConnection()){
            executeUpdate(connection, "insert into solicitudes_prestaciones (cliente, fecha, razon, practica) values ("
                    + dni_cliente + ", \"" + fechaSolicitud + "\", \"" + razon + "\", \"" + practica + "\")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl);
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        return connection;
    }

    public void clearDataBase(){
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
