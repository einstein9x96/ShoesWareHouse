
import DataConnect.DataConnection;
import Function.LoginApp;
import Repository.AdminRepository;
import java.sql.SQLException;
import java.util.Scanner;

public class ShoesWareHouse {

    public static void main(String[] args) throws SQLException {
        DataConnection dataconn = new DataConnection();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        String user;
        String pass;
        do {
            System.out.println("--------Login--------");
            System.out.print("Username : ");
            user = sc.nextLine();
            System.out.print("Password : ");
            pass = sc.nextLine();
            LoginApp log = new LoginApp();
            String result = log.login(user, pass);
            Menu menu = new Menu();
            AdminRepository ad = new AdminRepository(dataconn);
            switch (result) {
                case "sa":
                    menu.getMenu(ad.getAdmin(user, pass));
                    break;
                case "thukho":
                    menu.getMenu(ad.getAdmin(user, pass));
                    break;
                default:
                    System.out.println(result);
                    break;
            }
        } while (!exit);
    }
}
