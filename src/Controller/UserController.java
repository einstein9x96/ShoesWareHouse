package Controller;

import DataConnect.DataConnection;
import Model.Admin;
import Repository.AdminRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserController {

    Scanner sc = new Scanner(System.in);
//    DataConnection dataconn;
//    AdminRepository adRepository;
//    ArrayList<Admin> adList = new ArrayList<>();

    public void main() throws SQLException {

        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem = -1;
        do {
            System.out.println("---------Quản lý thủ kho---------");
            menu();
            System.out.print("Chọn 1 chức năng: ");
            boolean valid;
            do {
                try {
                    menuItem = Integer.parseInt(sc.nextLine());
                    valid = true;
                } catch (Exception ex) {
                    valid = false;
                    System.out.print("Bạn hãy nhập đúng lựa chọn : ");
                }
            } while (valid == false);

            switch (menuItem) {
                case 1:
                    getAllUsers();
                    sc.nextLine();
                    break;
                case 2:
                    insertUser();
                    sc.nextLine();
                    break;
                case 3:
                    editUser();
                    sc.nextLine();
                    break;
                case 4:
                    searchByName();
                    sc.nextLine();
                    break;
                case 5:
                    deleteUser();
                    sc.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.print("Lựa chọn không đúng, vui lòng nhập lại : ");
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("1. Danh sách thủ kho");
        System.out.println("2. Thêm mới thủ kho");
        System.out.println("3. Sửa thông tin thủ kho");
        System.out.println("4. Tìm kiếm thông tin thủ kho");
        System.out.println("5. Xóa thủ kho");
        System.out.println("0. Trở lại");
    }

    //Xem danh sách người dùng.
    public void getAllUsers() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> adList = adRepository.GetAll();

        if (adList.size() <= 0) {
            System.out.print("Hiện tại chưa có người dùng nào để hiển thị : ");
        } else {
            System.out.println("Danh sách người dùng : ");
            for (Admin user : adList) {
                user.displayInfo();
            }
        }
    }

    // Xóa người dùng.
    public void deleteUser() throws SQLException {

        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        if (userList.isEmpty()) {
            System.out.print("Chưa có nhân viên nào, xin hãy thêm nhân viên trước");
        } else {
            for (Admin user : userList) {
                int i = 1;
                System.out.println(user.getId() + ". " + user.getTen() + " - " + user.getUserName());
            }
            System.out.print("Chọn người dùng muốn xóa : ");

            boolean deleted;
            int id = 0;
            do {
                try {
                    id = Integer.parseInt(sc.nextLine());
                    if (adRepository.delete(id)) {
                        System.out.println("Xóa thành công!");
                        deleted = true;
                    } else {
                        System.out.println("Không xóa được người dùng, vui lòng kiểm tra id nhập vào!");
                        deleted = false;
                    }
                } catch (Exception ex) {
                    deleted = false;
                    System.out.print("Vui lòng nhập số vào : ");
                }
            } while (!deleted);
        }
    }

    public void editUser() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        if (userList.isEmpty()) {
            System.out.println("Cơ sở dữ liệu hiện chưa có người dùng.");
        } else {
            boolean choose = true;
            int id;
            do {
                try {
                    id = Integer.parseInt(sc.nextLine());
                    Admin user = adRepository.getbyId(id);
                    if (user == null) {
                        System.out.println("Người dùng bạn chọn không tồn tại");
                        choose = false;
                    } else {
                        
                    }
                } catch (Exception ex) {
                    choose = false;
                    System.out.print("Lựa chọn của bạn không đúng, vui lòng nhập lại");
                }
            } while (!choose);

        }
    }

    public void insertUser() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();
    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();
    }
}
