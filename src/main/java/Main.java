import database.DataBase;
import vista.VistaPrincipal;

public class Main {

    public static void main(String[] args){
      DataBase db = new DataBase();
      db.createDatabaseIfDoesNotExists();
      VistaPrincipal vistaPrincipal = new VistaPrincipal(db);
      //vistaPrincipal.mostrarVista();
    }

}
