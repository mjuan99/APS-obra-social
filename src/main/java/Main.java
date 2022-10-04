import database.DataBase;
import vista.VistaPrincipal;

public class Main {

    public static void main(String[] args){
      DataBase.createDatabaseIfDoesNotExists();
      VistaPrincipal vistaPrincipal = new VistaPrincipal();
    }

}
