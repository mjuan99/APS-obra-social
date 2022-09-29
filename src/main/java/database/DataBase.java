package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DataBase {
    private final static String databaseUrl = "jdbc:sqlite:./obra-social.db";

    public void createDatabaseIfDoesNotExists() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            String sqlCreateDataBase = readFile("src/main/java/database/obra-social.sql");
            executeUpdate(connection, sqlCreateDataBase);
        } catch (SQLException e) {
            //database already exists, no actions needed
            System.out.println(e.getMessage());
            clearDataBase();
        } catch (IOException e){
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            executeUpdate(connection, "insert into clientes values (\"Marten\", \"Juan\", \"DNI\", 41686955, 20416869554, \"28/01/1999\", \"pro\", \"yo\", \"29/09/2022\", \"jmm@gmail.com\", \"1234\")");
            printResultSet(executeQuery(connection, "select * from clientes"));

            executeUpdate(connection, "insert into empleados values (\"Marten\", \"Juan\", \"DNI\", 41686955, 4561234, \"mjj@gmail.com\", \"random\", \"jjj\", \"4321\")");
            printResultSet(executeQuery(connection, "select * from empleados"));

            executeUpdate(connection, "insert into planes values (\"pro\", 999.99, \"ninguno\")");
            printResultSet(executeQuery(connection, "select * from planes"));

            executeUpdate(connection, "insert into solicitudes_alta (tipo_plan, cliente, fecha) values (\"pro\", 41686955, \"29/09/2022\")");
            printResultSet(executeQuery(connection, "select * from solicitudes_alta"));

            executeUpdate(connection, "insert into solicitudes_reintegro (cliente, fecha, razon, practica) values (41686955, \"29/09/2022\", \"porque si\", \"alguna\")");
            printResultSet(executeQuery(connection, "select * from solicitudes_reintegro"));

            executeUpdate(connection, "insert into solicitudes_prestaciones (cliente, fecha, razon, practica) values (41686955, \"30/09/2022\", \"pinto\", \"otra\")");
            printResultSet(executeQuery(connection, "select * from solicitudes_prestaciones"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clearDataBase(){
        try (Connection connection = DriverManager.getConnection(databaseUrl)){
            executeUpdate(connection, "DELETE FROM Clientes" );
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

    private String readFile(String sourceFile) throws IOException {
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
