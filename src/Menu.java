
import Controller.CategoriesController;
import Controller.OrdersController;
import Controller.UserController;
import DataConnect.DataConnection;
import Model.Admin;
import Repository.ChiTietKhoRepository;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public void saMenu() {
        System.out.println("1. Quan ly thu kho");
        System.out.println("2. Quan ly kho");
        System.out.println("3. Quan ly don hang");
        System.out.println("4. Xem chi tiet kho");
        System.out.println("0. Dang xuat");
    }
    public void thukhoMenu() {
        System.out.println("1. Quan ly kho");
        System.out.println("2. Quan ly don hang");
        System.out.println("3. Xem chi tiet kho");
        System.out.println("0. Dang xuat");
    }
    public void getMenu(Admin user) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------- Quan ly kho hang ---------");
        System.out.println("Xin chao, " + user.getTen());
        if (user.getRole().equals("sa")) {
            int choice;
            boolean logout = false;
            do {
                saMenu();
                System.out.println("Vui long chon 1 chuc nang: ");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        UserController userController = new UserController();
                        userController.main();
                        break;
                    case 2:
                        CategoriesController category = new CategoriesController();
                        category.main();
                        break;
                    case 3:
                        OrdersController order = new OrdersController();
                        order.main(user);
                        break;
                    case 4:
                        CategoriesController hh = new CategoriesController();
                        hh.Goodslist();
                        sc.nextLine();
                       break;
                    case 0:
                        System.out.println("Dang xuat thanh cong");
                        logout = true;
                        break;
                    default:
                        System.out.println("Chuc nang ban chon khong dung, vui long chon lai");
                        break;
                }
            } while (!logout);
        } else {
            int choice;
            boolean logout = false;
            do {
                thukhoMenu();
                System.out.println("Vui long chon 1 chuc nang: ");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        CategoriesController category = new CategoriesController();
                        category.main();
                        break;
                    case 2:
                        OrdersController order = new OrdersController();
                        order.main(user);
                        break;
                    case 3:
                        CategoriesController hh = new CategoriesController();
                        hh.Goodslist();
                        sc.nextLine();
                    case 0:
                        System.out.println("Dang xuat thanh cong");
                        logout = true;
                        break;
                    default:
                        System.out.println("chuc nang ban chon khong dung, vui long chon lai");
                        break;
                }
            } while (!logout);

        }
    }
}
