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
                } catch (NumberFormatException ex) {
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
            System.out.print("Hiện tại chưa có người dùng trong cơ sở dữ liệu");
        } else {
            System.out.println("Danh sách người dùng : ");
            adList.forEach((user) -> {
                user.displayInfo();
            });
            System.out.println("Ấn enter để quay lại menu");
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
            userList.forEach((user) -> {
                int i = 1;
                System.out.println(user.getId() + ". " + user.getTen() + " - " + user.getUserName());
            });
            System.out.print("Chọn người dùng muốn xóa : ");

            boolean deleted;
            int id;
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
                } catch (NumberFormatException | SQLException ex) {
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
            int infoNum;
            do {
                try {
                    userList.forEach((anuser) -> {
                        int i = 1;
                        System.out.println(anuser.getId() + ". " + anuser.getTen() + " - " + anuser.getUserName());
                    });
                    System.out.println("Vui lòng nhập id của người dùng cần cập nhật thông tin: ");
                    id = Integer.parseInt(sc.nextLine());
                    Admin user = adRepository.getbyId(id);
                    if (user == null) {
                        System.out.println("Người dùng bạn chọn không tồn tại");
                        choose = false;
                    } else {
                        int checkcontinue;
                        do {
                            System.out.println("Thông tin thủ kho:");
                            System.out.println("1. Tên: " + user.getTen());
                            System.out.println("2. Tài khoản: " + user.getUserName());
                            System.out.println("3. Mật khẩu: " + user.getPassword());
                            System.out.println("4. Quyền quản trị: " + user.getRole());
                            System.out.println("0. Quay lại");
                            System.out.println("Chọn thông tin thủ kho cần cập nhật: ");
                            infoNum = Integer.parseInt(sc.nextLine());
                            switch (infoNum) {
                                case 1:
                                    System.out.println("Mời bạn điền tên thủ kho");
                                    user.setTen(sc.nextLine());
                                    break;
                                case 2:
                                    System.out.println("Mời bạn điền tài khoản thủ kho");
                                    boolean validUsername;
                                    String username;
                                    do {
                                        username = sc.nextLine();
                                        validUsername = username.matches("[A-Za-z0-9]{3,18}");
                                        if (!validUsername) {
                                            System.out.print("Tên tài khoản gồm chữ cái và số có độ dài từ 3-18 ký tự viết liền!");
                                        } else {
                                            user.setUserName(username);
                                        }
                                    } while (!validUsername);
                                    break;
                                case 3:
                                    System.out.println("Mời bạn điền mật khẩu thủ kho");
                                    boolean validPw;
                                    String pass;
                                    do {
                                        pass = sc.nextLine();
                                        validPw = pass.matches(".{3,20}");
                                        if (!validPw) {
                                            System.out.print("Mật khẩu có độ dài từ 3-20 ký tự. Vui lòng nhập lại");
                                        } else {
                                            user.setPassword(pass);
                                        }
                                    } while (!validPw);
                                    break;
                                case 4:
                                    System.out.println("Mời bạn chọn quyền cho người dùng");
                                    System.out.println("1. Admin");
                                    System.out.println("2. Thủ kho");
                                    int role;
                                    boolean check;
                                    do {
                                        check = true;

                                        role = Integer.parseInt(sc.nextLine());
                                        switch (role) {
                                            case 1:
                                                user.setRole("sa");
                                                break;
                                            case 2:
                                                user.setRole("thukho");
                                                break;
                                            default:
                                                check = false;
                                                System.out.println("Lựa chọn của bạn không tồn tại, vui lòng chọn lại ");
                                                break;
                                        }
                                    } while (!check);

                                    break;
                                default:
                                    System.out.println("Lựa chọn của bạn không tồn tại, vui lòng chọn lại");
                                    break;

                            }
                            if (adRepository.update(user)) {
                                System.out.println("Cập nhật thành công");
                            } else {
                                System.out.println("Vui lòng thử lại");
                            }
                            System.out.println("Bạn có muốn cập nhật thông tin khác không?");
                            System.out.println("1. Có");
                            System.out.println("0. Không");
                            checkcontinue = Integer.parseInt(sc.nextLine());
                        } while (checkcontinue != 0);

                    }
                } catch (NumberFormatException | SQLException ex) {
                    choose = false;
                    System.out.println("Lựa chọn của bạn không đúng, vui lòng nhập lại");
                }
            } while (!choose);
            dataconn.Close();
        }
    }

    public void insertUser() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();
        Admin user = new Admin();
        System.out.println("---------Add new user---------");

        System.out.print("Username : ");
        boolean validUsername;
        String userName;
        do {
            userName = sc.nextLine();
            validUsername = userName.matches("[A-Za-z0-9]{3,30}");
            if (validUsername == false) {
                System.out.print("Tên tài khoản gồm số và chữ thường 3 đến 30 kí tự viết liền không dấu cách : ");
            } else {
                if (adRepository.isExistUsername(userName) == false) {
                    user.setUserName(userName);
                } else {
                    validUsername = false;
                    System.out.println("Username đã được sử dụng, vui lòng chọn username mới");
                }
            }
        } while (validUsername == false);

        System.out.print("Password : ");
        boolean validpass;
        String pass;
        do {
            pass = sc.nextLine();
            validpass = pass.matches(".{3,30}");
            if (validpass == false) {
                System.out.print("Nhập vào mật khẩu từ 3 đến 30 kí tự : ");
            } else {
                user.setPassword(pass);
            }
        } while (validpass == false);

        System.out.print("Tên : ");
        user.setTen(sc.nextLine());

        int role;
        boolean check;
        do {
            check = true;

            System.out.println("Chọn loại quyền người dùng : ");
            System.out.println("1.Admin");
            System.out.println("2.Thủ kho");
            role = Integer.parseInt(sc.nextLine());
            switch (role) {
                case 1:
                    user.setRole("sa");
                    break;
                case 2:
                    user.setRole("thukho");
                    break;
                default:
                    check = false;
                    System.out.println("Lựa chọn của bạn không tồn tại, vui lòng chọn lại ");
                    break;
            }
        } while (!check);

        if (adRepository.insert(user) == true) {
            System.out.println("Thêm người dùng thành công");
        } else {
            System.out.println("Thêm người dùng không thành công, vui lòng thử lại");
        }
    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        String name = "";
        System.out.println("Vui lòng nhập username người dùng cần tìm kiếm");
        name = sc.nextLine();
        Admin user = adRepository.findByUsername(name);
        System.out.println("----------Kết quả----------");
        user.displayInfo();
    }
}
