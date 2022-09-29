import database.DataBase;

public class Main {
  public static void main(String[] args){
    System.out.println("Hola Mundo");
    DataBase db = new DataBase();
    db.createDatabaseIfDoesNotExists();
  }
}
