import database.DataBase;

public class Main {
  public static void main(String[] args){
    DataBase db = new DataBase();
    db.createDatabaseIfDoesNotExists();
  }
}
