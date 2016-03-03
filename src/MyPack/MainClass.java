package MyPack;

/**
 * Created by taras on 27.02.2016.
 */
public class MainClass {
    public  static void main(String[] args) throws  Exception{
        MySQLAcsess dao = new MySQLAcsess();
        dao.runQueries();
    }
}
