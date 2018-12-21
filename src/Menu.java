
import Controller.StockController;
import Controller.UserController;
import Model.Admin;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public void saMenu() {
        System.out.println("1. Quản lý thủ kho");
        System.out.println("2. Quản lý kho");
        System.out.println("3. Quản lý hàng hóa");
        System.out.println("4. Quản lý đơn hàng");
        System.out.println("0. Đăng xuất");
    }

    public void getMenu(Admin user) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------- Quản lý kho hàng ---------");
        System.out.println("Xin chào, " + user.getTen());
        if (user.getRole().equals("sa")) {
            int choice;
            boolean logout = false;
            do{
                saMenu();
                System.out.println("Vui lòng chọn 1 chức năng: ");
                choice = Integer.parseInt(sc.nextLine());
                
                switch (choice) {
                    case 1:
                        UserController userController = new UserController();
                        userController.main();
                        break;
                    case 2:
                        StockController st = new StockController();
                        st.main();
                        break;
                    case 3:
                        
                        break;
                    case 4:
                        
                        break;
                    case 0:
                        System.out.println("Đăng xuất thành công");
                        logout = true;
                        break;
                    default:
                        System.out.println("Chức năng bạn chọn không đúng, vui lòng chọn lại");
                        break;
                }
            } while (!logout);
        } else {
            thukhoMenu();
            System.out.println("Vui lòng chọn 1 chức năng: ");
        }
    }

    public void thukhoMenu() {
        System.out.println("1. Quản lý hàng hóa");
        System.out.println("2. Quản lý đơn hàng");
        System.out.println("0. Đăng xuất");
    }
}
