import database.DataBase;
import vista.VistaPrincipal;

public class Main {

    public static void main(String[] args){
      DataBase.createDatabaseIfDoesNotExists();
      //TODO sacar esto cuando no se necesite
      DataBase.clearDataBase();
      DataBase.testInsertar();
      DataBase.imprimirBaseDeDatos();

      VistaPrincipal vistaPrincipal = new VistaPrincipal();
    }

}
