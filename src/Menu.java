
import Controller.CategoriesController;
import Controller.GroupGoodsController;
import Controller.OrdersController;
import Controller.StockController;
import Controller.UserController;
import Model.Admin;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public void saMenu() {
        System.out.println("1. Quan ly thu kho");
        System.out.println("2. Quan ly kho");
        System.out.println("3. Quan ly hang hoa");
        System.out.println("4. Quan ly nhom hang");
        System.out.println("5. Quan ly don hang");
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
                        StockController st = new StockController();
                        st.main(user);
                        break;
                    case 3:
                        CategoriesController category = new CategoriesController();
                        category.main();
                        break;
                    case 4:
                        GroupGoodsController group = new GroupGoodsController();
                        group.main();
                        break;
                    case 5:
                        OrdersController order = new OrdersController();
                        order.main(user);
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
                        GroupGoodsController group = new GroupGoodsController();
                        group.main();
                        break;
                    case 3:
                        OrdersController order = new OrdersController();
                        order.main(user);
                        break;
//                    case 4:
//                        
//                        break;
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

    public void thukhoMenu() {
        System.out.println("1. Quan ly hang hoa");
        System.out.println("2. Quan ly nhom hang");
        System.out.println("3. Quan ly don hang");
//        System.out.println("4. Xem chi tiet kho");
        System.out.println("0. Dang xuat");
    }
}
